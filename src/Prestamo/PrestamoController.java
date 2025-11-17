package Prestamo;

/**
 * Controlador que actúa como fachada para la lógica de préstamos.
 */
public class PrestamoController {
    private PrestamoService service;

    public PrestamoController() {
        this.service = new PrestamoService();
    }

    /**
     * Solicita tomar acción sobre el libro: pide préstamo o abre lectura.
     */
    public PrestamoService.EstadoPrestamo borrowOrOpen(String userId, String bookId) {
        return service.solicitarPrestamoOLeer(userId, bookId);
    }

    /**
     * Versión en español (compatibilidad): solicita préstamo o abrir lectura.
     */
    public PrestamoService.EstadoPrestamo solicitarPrestamoOLeer(String userId, String bookId) {
        return service.solicitarPrestamoOLeer(userId, bookId);
    }

    /**
     * Indica si el usuario ya tiene el libro prestado.
     *
     * @param userId id del usuario
     * @param bookId id del libro
     * @return {@code true} si el usuario tiene el libro en préstamos activos
     */
    public boolean usuarioTieneLibro(String userId, String bookId) {
        return service.usuarioTieneLibro(userId, bookId);
    }

    /**
     * Número de copias disponibles del libro.
     *
     * @param bookId id del libro
     * @return número de copias disponibles
     */
    public int copiasDisponibles(String bookId) {
        return service.copiasDisponibles(bookId);
    }
}
