package repository;

import java.util.*;

/**
 * DAO en memoria para préstamos y colas de espera.
 *
 * Guarda un conjunto de préstamos activos y colas por libro. Está pensado
 * para pruebas locales sin persistencia externa.
 */
public class PrestamoDAO {
    // Conjunto de préstamos activos representados como "userId:bookId"
    private final Set<String> prestamosActivos = new HashSet<>();
    // Colas por libro
    private final Map<String, Queue<String>> colas = new HashMap<>();
    private final LibroDAO libroDAO;

    /**
     * Crea el DAO usando el {@link LibroDAO} proporcionado para gestionar inventario.
     *
     * @param libroDAO instancia de {@link LibroDAO}
     */
    public PrestamoDAO(LibroDAO libroDAO) {
        this.libroDAO = libroDAO;
    }

    private String key(String userId, String bookId) {
        return userId + ":" + bookId;
    }

    /**
     * Comprueba si un usuario tiene actualmente un préstamo activo del libro.
     *
     * @param userId id del usuario
     * @param bookId id del libro
     * @return {@code true} si existe un préstamo activo
     */
    public boolean usuarioTienePrestamo(String userId, String bookId) {
        return prestamosActivos.contains(key(userId, bookId));
    }

    /**
     * Cuenta cuántas personas hay en la cola de espera para un libro.
     *
     * @param bookId id del libro
     * @return tamaño de la cola
     */
    public int contarEnCola(String bookId) {
        return colas.getOrDefault(bookId, new LinkedList<>()).size();
    }

    /**
     * Registra un préstamo si hay copias disponibles. Devuelve true si se prestó.
     *
     * @param userId id del usuario
     * @param bookId id del libro
     * @return {@code true} si el préstamo fue registrado (había copias)
     */
    public boolean registrarPrestamo(String userId, String bookId) {
        if (libroDAO.disminuirCopia(bookId)) {
            prestamosActivos.add(key(userId, bookId));
            return true;
        }
        return false;
    }

    /**
     * Registra la devolución de un libro por parte de un usuario.
     *
     * @param userId id del usuario
     * @param bookId id del libro
     */
    public void registrarDevolucion(String userId, String bookId) {
        prestamosActivos.remove(key(userId, bookId));
        libroDAO.aumentarCopia(bookId);
        // si hay cola, no la procesamos automáticamente aquí (simplificar)
    }

    /**
     * Añade a un usuario a la cola de espera para un libro.
     *
     * @param userId id del usuario
     * @param bookId id del libro
     */
    public void ponerEnCola(String userId, String bookId) {
        colas.computeIfAbsent(bookId, k -> new LinkedList<>()).add(userId);
    }
}
