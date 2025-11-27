package Resena;

import Catalogo.LoanController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Controlador de negocio para las reseñas.
 *
 * Encapsula las operaciones de alto nivel (fetch/paginación, promedio, crear/editar/eliminar)
 * y aplica reglas de negocio (validaciones, control de acceso).
 */
public class ControladorResenas {
    private final RepositorioResenas repositorio;
    private final LoanController controladorPrestamos;

    /**
     * Crea el controlador con un repositorio apuntando al CSV dado.
     * @param csvPath ruta al CSV donde se almacenan las reseñas
     * @param controladorPrestamos controlador de préstamos para validar acceso
     */
    public ControladorResenas(String csvPath, LoanController controladorPrestamos) {
        this.repositorio = new RepositorioResenas(csvPath);
        this.controladorPrestamos = controladorPrestamos;
    }

    /**
     * Obtiene reseñas para un libro con paginación.
     * @param idLibro id del libro
     * @param pagina página (1-based)
     * @param tamanoPagina número de reseñas por página
     * @return sublista de reseñas para la página solicitada
     * @throws IOException si falla la lectura
     */
    public List<Resena> obtenerResenas(String idLibro, int pagina, int tamanoPagina) throws IOException {
        List<Resena> todas = repositorio.cargarPorLibro(idLibro);
        int desde = Math.max(0, (pagina - 1) * tamanoPagina);
        int hasta = Math.min(todas.size(), desde + tamanoPagina);
        return todas.subList(desde, hasta);
    }

    /**
     * Obtiene la calificación promedio del libro.
     * @param idLibro id del libro
     * @return promedio de calificación
     * @throws IOException si falla la lectura
     */
    public double obtenerPromedio(String idLibro) throws IOException {
        return repositorio.obtenerPromedioCalificacion(idLibro);
    }

    /**
     * Busca la reseña del usuario actual para el libro dado.
     * @param idLibro id del libro
     * @return Optional con la reseña si existe
     * @throws IOException si falla la lectura
     */
    public Optional<Resena> buscarResenaUsuario(String idLibro) throws IOException {
        String idUsuario = controladorPrestamos.obtenerIdUsuarioActual();
        return repositorio.buscarPorLibroYUsuario(idLibro, idUsuario);
    }

    /**
     * Crea y persiste una reseña para el libro por el usuario actual, si tiene permiso.
     * @param idLibro id del libro
     * @param calificacion calificación 1..5
     * @param texto texto de la reseña
     * @return reseña persistida
     * @throws IOException si falla la escritura
     */
    public Resena enviarResena(String idLibro, int calificacion, String texto) throws IOException {
        String idUsuario = controladorPrestamos.obtenerIdUsuarioActual();
        if (calificacion < 1 || calificacion > 5) throw new IllegalArgumentException("Calificación debe estar entre 1 y 5");
        if (texto != null) {
            if (texto.length() < 10 || texto.length() > 500) throw new IllegalArgumentException("Texto debe ser entre 10 y 500 caracteres");
        }
        if (!controladorPrestamos.usuarioHaCompletadoPrestamo(idLibro) && !controladorPrestamos.usuarioTieneLibro(idLibro)) {
            throw new IllegalStateException("Solo puedes reseñar si completaste el préstamo del libro");
        }
        Resena r = new Resena(0, idLibro, idUsuario, calificacion, texto == null ? "" : texto, Resena.fechaActualISO());
        return repositorio.agregarResena(r);
    }

    /**
     * Actualiza una reseña existente. Verifica la propiedad antes de permitir la edición.
     * @param idResena id de la reseña a actualizar
     * @param calificacion nueva calificación
     * @param texto nuevo texto
     * @return reseña actualizada
     * @throws IOException si falla la escritura
     */
    public Resena actualizarResena(int idResena, int calificacion, String texto) throws IOException {
        if (calificacion < 1 || calificacion > 5) throw new IllegalArgumentException("Calificación debe estar entre 1 y 5");
        if (texto != null && (texto.length() < 10 || texto.length() > 500)) throw new IllegalArgumentException("Texto debe ser entre 10 y 500 caracteres");
        Optional<Resena> r = repositorio.cargarTodas().stream().filter(rr -> rr.getId() == idResena).findFirst();
        if (r.isEmpty()) throw new IllegalArgumentException("Reseña no encontrada");
        if (!r.get().getIdUsuario().equals(controladorPrestamos.obtenerIdUsuarioActual())) throw new IllegalStateException("No puedes editar una reseña que no es tuya");
        return repositorio.actualizarResena(idResena, calificacion, texto);
    }

    /**
     * Elimina una reseña si el usuario actual es su autor.
     * @param idResena id de la reseña
     * @return true si se eliminó correctamente
     * @throws IOException si falla la escritura
     */
    public boolean eliminarResena(int idResena) throws IOException {
        Optional<Resena> r = repositorio.cargarTodas().stream().filter(rr -> rr.getId() == idResena).findFirst();
        if (r.isEmpty()) throw new IllegalArgumentException("Reseña no encontrada");
        if (!r.get().getIdUsuario().equals(controladorPrestamos.obtenerIdUsuarioActual())) throw new IllegalStateException("No puedes eliminar una reseña que no es tuya");
        return repositorio.eliminarResena(idResena);
    }
}
