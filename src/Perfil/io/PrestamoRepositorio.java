package Perfil.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import Perfil.perfil_modelo.Prestamo;

public class PrestamoRepositorio {

    public static List<Prestamo> cargarDesdeCSV(String ruta) throws IOException {
        return cargarDesdeArchivo(ruta, ",");
    }
    
    public static List<Prestamo> cargarPrestamosPorCorreo(String ruta, String correoUsuario) throws IOException {
    List<Prestamo> out = new ArrayList<>();
    Path p = Path.of(ruta);

    try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
        String linea;
        boolean primera = true;

        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

        while ((linea = br.readLine()) != null) {
            if (linea.isBlank()) continue;
            if (primera) { primera = false; continue; }

            String[] cols = linea.split(regex, -1);
            if (cols.length < 7) {
                System.err.println("Línea omitida (columnas insuficientes): " + linea);
                continue;
            }
            for (int i = 0; i < cols.length; i++) cols[i] = unquote(cols[i].trim());

            String correoFila = cols[6];
            if (!correoFila.equalsIgnoreCase(correoUsuario)) {
                continue;
            }

            try {
                int loanId = Integer.parseInt(cols[0]);
                int bookId = Integer.parseInt(cols[1]);
                String titulo = cols[2];
                String estado = cols[3];
                String fechaVencimiento = cols[4];
                String portada = cols[5].isEmpty() ? null : cols[5];

                out.add(new Prestamo(loanId, bookId, titulo, estado, fechaVencimiento, portada));
            } catch (NumberFormatException nfe) {
                System.err.println("Línea omitida (IDs no numéricos): " + linea);
            }
        }
    }
    return out;
}

    public static List<Prestamo> cargarDesdeArchivo(String ruta, String delimitador) throws IOException {
        List<Prestamo> out = new ArrayList<>();
        Path p = Path.of(ruta);

        try (BufferedReader br = Files.newBufferedReader(p, StandardCharsets.UTF_8)) {
            String linea;
            boolean primera = true;

            String regex = delimitador.equals(",")
                    ? ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"
                    : java.util.regex.Pattern.quote(delimitador);

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                if (primera) {
                    primera = false;
                    continue;
                }
                String[] cols = linea.split(regex, -1);

                if (cols.length < 6) {
                    System.err.println("Línea omitida (columnas insuficientes): " + linea);
                    continue;
                }

                for (int i = 0; i < cols.length; i++) {
                    cols[i] = unquote(cols[i].trim());
                }

                try {
                    int loanId = Integer.parseInt(cols[0]);
                    int bookId = Integer.parseInt(cols[1]);
                    String titulo = cols[2];
                    String estado = cols[3];
                    String fechaVencimiento = cols[4]; // yyyy-MM-dd
                    String portada = cols[5].isEmpty() ? null : cols[5];

                    out.add(new Prestamo(loanId, bookId, titulo, estado, fechaVencimiento, portada));
                } catch (NumberFormatException nfe) {
                    System.err.println("Línea omitida (IDs no numéricos): " + linea);
                }
            }
        }
        return out;
    }

    /**
     * Corrige comillas dobles al extraer texto del csv.
     * @param s Texto a corregir
     * @return {@code String} Texto sin comillas
     */
    private static String unquote(String s) {
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * Agrega nuevos préstamos al csv.
     * @param ruta Ruta del csv
     * @param loanId Índice del préstamo
     * @param bookId Id del libro
     * @param titulo Título del libro
     * @param estado Estado de pedido del libro
     * @param fechaVencimiento Expiración del préstamo
     * @param portada Ruta de la portada del libro
     * @param correoUsuario Correo del usuario
     */
    public static void agregarPrestamo(String ruta, int loanId, int bookId, String titulo, String estado, String fechaVencimiento, String portada, String correoUsuario) {
        Path p = Path.of(ruta);

        try (var bw = Files.newBufferedWriter(p, StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND)) {

            String linea = String.join(",", csv(loanId), csv(bookId), csv(titulo), csv(estado), csv(fechaVencimiento), csv(portada == null ? "" : portada), csv(correoUsuario));

            bw.write(linea);
            bw.newLine();
        }
        catch (IOException e) {
            System.err.println("No se pudo leer el archivo csv");
        }
    }

    /**
     * Funcion auxiliar para eliminar cadenas problemáticas para el csv.
     * @param o Objeto a examinar
     * @return {@code String} vacío si el objeto es nulo, o la cadena corregida
     */
    private static String csv(Object o) {
        if (o == null) return "";

        String s = o.toString();
        boolean necesitaComillas =
                s.contains(",") || s.contains("\"") || s.contains("\n");

        if (s.contains("\"")) {
            s = s.replace("\"", "\"\"");
        }

        return necesitaComillas ? "\"" + s + "\"" : s;
    }
}

