package Resena;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Repositorio que maneja la persistencia de las reseñas en CSV.
 *
 * Implementa operaciones básicas de carga y almacenado: cargar todas, por libro, búsqueda por par
 * (libro, usuario), agregar, actualizar y eliminar una reseña. Utiliza un CSV con cabecera
 * [id,bookId,userId,rating,text,date].
 */
public class RepositorioResenas {
    private final Path csvPath;

    /**
     * Construye un repositorio que opera sobre un CSV en la ruta indicada.
     * @param csvPath ruta al archivo CSV de reseñas
     */
    public RepositorioResenas(String csvPath) {
        this.csvPath = Path.of(csvPath);
    }

    /**
     * Carga todas las reseñas desde el CSV.
     * @return lista de reseñas cargadas
     * @throws IOException si hay problemas de E/S
     */
    public List<Resena> cargarTodas() throws IOException {
        List<Resena> out = new ArrayList<>();
        if (!Files.exists(csvPath)) return out;

        try (BufferedReader br = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {
            String linea = br.readLine(); // header
            if (linea == null) return out;
            String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] cols = linea.split(regex, -1);
                for (int i = 0; i < cols.length; i++) cols[i] = descomillas(cols[i].trim());
                try {
                    int id = Integer.parseInt(cols[0]);
                    String idLibro = cols[1];
                    String idUsuario = cols[2];
                    int calificacion = Integer.parseInt(cols[3]);
                    String texto = cols[4];
                    String fecha = cols[5];
                    out.add(new Resena(id, idLibro, idUsuario, calificacion, texto, fecha));
                } catch (Exception ex) {
                    System.err.println("Reseña omitida; error parse: " + linea);
                }
            }
        }
        return out;
    }

    /**
     * Carga reseñas filtradas por el identificador del libro. Devuelve las reseñas ordenadas
     * por fecha descendente.
     * @param idLibro id del libro
     * @return lista de reseñas del libro
     * @throws IOException si ocurre un problema de lectura
     */
    public List<Resena> cargarPorLibro(String idLibro) throws IOException {
        return cargarTodas().stream().filter(r -> r.getIdLibro().equals(idLibro))
                .sorted(Comparator.comparing(Resena::getFecha).reversed())
                .collect(Collectors.toList());
    }

    /**
     * Busca la reseña de un usuario sobre un libro específico.
     * @param idLibro id del libro
     * @param idUsuario id del usuario
     * @return Optional con la reseña si existe
     * @throws IOException si ocurre un problema de lectura
     */
    public Optional<Resena> buscarPorLibroYUsuario(String idLibro, String idUsuario) throws IOException {
        return cargarTodas().stream().filter(r -> r.getIdLibro().equals(idLibro) && r.getIdUsuario().equals(idUsuario)).findFirst();
    }

    /**
     * Agrega una nueva reseña al repositorio. Valida unicidad por (idLibro,idUsuario).
     * @param resena reseña a agregar (id opcional; se sobreescribe)
     * @return reseña agregada con id asignado
     * @throws IOException si ocurre un error de escritura
     */
    public Resena agregarResena(Resena resena) throws IOException {
        List<Resena> all = cargarTodas();
        Optional<Resena> existing = all.stream().filter(r -> r.getIdLibro().equals(resena.getIdLibro()) && r.getIdUsuario().equals(resena.getIdUsuario())).findAny();
        if (existing.isPresent()) throw new IllegalStateException("El usuario ya escribió una reseña para este libro");

        int siguienteId = all.stream().mapToInt(Resena::getId).max().orElse(0) + 1;
        Resena nueva = new Resena(siguienteId, resena.getIdLibro(), resena.getIdUsuario(), resena.getCalificacion(), resena.getTexto(), Resena.fechaActualISO());
        all.add(nueva);
        guardarTodas(all);
        return nueva;
    }

    /**
     * Actualiza la reseña identificada por idResena con nuevos valores.
     * @param idResena id de la reseña a actualizar
     * @param calificacion nueva calificación
     * @param texto nuevo texto
     * @return reseña actualizada
     * @throws IOException si ocurre un error de escritura
     */
    public Resena actualizarResena(int idResena, int calificacion, String texto) throws IOException {
        List<Resena> all = cargarTodas();
        boolean encontrado = false;
        for (int i = 0; i < all.size(); i++) {
            Resena r = all.get(i);
            if (r.getId() == idResena) {
                Resena actualizado = new Resena(r.getId(), r.getIdLibro(), r.getIdUsuario(), calificacion, texto, Resena.fechaActualISO());
                all.set(i, actualizado);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) throw new IllegalArgumentException("Reseña no encontrada: " + idResena);
        guardarTodas(all);
        return all.stream().filter(r -> r.getId() == idResena).findFirst().get();
    }

    /**
     * Elimina la reseña con el id provisto.
     * @param idResena id de la reseña a eliminar
     * @return true si se eliminó, false si no se encontró
     * @throws IOException si ocurre un error de escritura
     */
    public boolean eliminarResena(int idResena) throws IOException {
        List<Resena> all = cargarTodas();
        boolean removed = all.removeIf(r -> r.getId() == idResena);
        if (removed) guardarTodas(all);
        return removed;
    }

    /**
     * Calcula la calificación promedio de un libro a partir de sus reseñas.
     * @param idLibro id del libro
     * @return promedio de calificación (0.0 si no hay reseñas)
     * @throws IOException si ocurre un problema de lectura
     */
    public double obtenerPromedioCalificacion(String idLibro) throws IOException {
        List<Resena> forBook = cargarPorLibro(idLibro);
        if (forBook.isEmpty()) return 0.0;
        double sum = forBook.stream().mapToInt(Resena::getCalificacion).sum();
        return sum / forBook.size();
    }

    /**
     * Guarda la lista de reseñas en el CSV (sobrescribe todo).
     */
    private void guardarTodas(List<Resena> all) throws IOException {
        Files.createDirectories(csvPath.getParent());
        try (BufferedWriter bw = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
            bw.write("id,bookId,userId,rating,text,date");
            bw.newLine();
            for (Resena r : all) {
                bw.write(String.format("%d,%s,%s,%d,%s,%s",
                        r.getId(), comillas(r.getIdLibro()), comillas(r.getIdUsuario()), r.getCalificacion(), comillas(r.getTexto()), comillas(r.getFecha())));
                bw.newLine();
            }
        }
    }

    private static String descomillas(String s) {
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) return s.substring(1, s.length() - 1);
        return s;
    }

    private static String comillas(String s) {
        if (s == null) return "";
        String safe = s.replace("\"", "\"\"");
        return "\"" + safe + "\"";
    }
}
