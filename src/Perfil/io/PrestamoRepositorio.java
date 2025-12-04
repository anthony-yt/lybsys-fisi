package perfil.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import perfil.perfil_modelo.Prestamo;

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

    private static String unquote(String s) {
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }
}

