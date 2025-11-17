package Prestamo;

import repository.LibroDAO;
import repository.PrestamoDAO;

/**
 * Servicio que maneja la lógica de préstamos/lectura usando DAOs en memoria.
 *
 * Provee operaciones para solicitar acceso a lectura o préstamo, consultar si
 * un usuario ya tiene un libro y obtener el número de copias disponibles.
 */
public class PrestamoService {

    /**
     * Estados posibles tras solicitar una acción sobre un libro.
     */
    public enum EstadoPrestamo { LEER, PRESTADO, EN_COLA, ERROR_NO_AUTENTICADO }

    private final LibroDAO libroDAO;
    private final PrestamoDAO prestamoDAO;

    /**
     * Crea el servicio y los DAOs en memoria usados para pruebas locales.
     */
    public PrestamoService() {
        this.libroDAO = new LibroDAO();
        this.prestamoDAO = new PrestamoDAO(libroDAO);
    }

    /**
     * Solicita acción sobre un libro: abrir lectura si el usuario ya lo tiene,
     * registrar un préstamo si hay copias disponibles, o añadir al usuario a la
     * cola de espera en caso contrario.
     *
     * @param userId id del usuario que realiza la solicitud
     * @param bookId id del libro objetivo
     * @return {@link EstadoPrestamo} resultado de la operación
     */
    public EstadoPrestamo solicitarPrestamoOLeer(String userId, String bookId) {
        if (userId == null) return EstadoPrestamo.ERROR_NO_AUTENTICADO;
        if (usuarioTieneLibro(userId, bookId)) return EstadoPrestamo.LEER;
        int copias = libroDAO.obtenerCopiasDisponibles(bookId);
        if (copias > 0) {
            boolean reservado = prestamoDAO.registrarPrestamo(userId, bookId);
            return reservado ? EstadoPrestamo.PRESTADO : EstadoPrestamo.EN_COLA;
        }
        // No hay copias: poner en cola
        prestamoDAO.ponerEnCola(userId, bookId);
        return EstadoPrestamo.EN_COLA;
    }

    /**
     * Indica si el usuario ya tiene el libro prestado.
     *
     * @param userId id del usuario
     * @param bookId id del libro
     * @return {@code true} si el usuario tiene el libro en préstamos activos
     */
    public boolean usuarioTieneLibro(String userId, String bookId) {
        return prestamoDAO.usuarioTienePrestamo(userId, bookId);
    }

    /**
     * Devuelve el número de copias disponibles del libro.
     *
     * @param bookId id del libro
     * @return número de copias disponibles
     */
    public int copiasDisponibles(String bookId) {
        return libroDAO.obtenerCopiasDisponibles(bookId);
    }
}
