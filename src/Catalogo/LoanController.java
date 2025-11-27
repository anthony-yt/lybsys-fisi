package Catalogo;

import java.util.*;

/**
 * Controlador simplificado para gestionar préstamos y colas de espera (simulación en memoria).
 * Implementa un singleton y mantiene un mapa de préstamos por usuario y contadores de cola por libro.
 */
public class LoanController {
    private static LoanController instance;
    private Map<String, List<String>> userLoans = new HashMap<>(); // userId -> list of bookIds
    private Map<String, Integer> loanQueue = new HashMap<>(); // bookId -> user count in queue
    private String idUsuarioActual = "user1"; // Simulación de usuario actual
    private Map<String, List<String>> completedLoans = new HashMap<>(); // historial: userId -> list of bookIds prestados completados

    private LoanController() {
        // Datos iniciales simulados
        userLoans.put("user1", new ArrayList<>(Arrays.asList("1", "2")));
        userLoans.put("user2", new ArrayList<>(Arrays.asList("4", "5")));
        // historial de préstamos completados (simulación)
        completedLoans.put("user1", new ArrayList<>(List.of("3","4")));
        completedLoans.put("user2", new ArrayList<>(List.of("1","2")));
    }

    /**
     * Obtiene la instancia singleton del controlador de préstamos.
     * @return instancia única de `LoanController`
     */
    public static LoanController obtenerInstancia() {
        if (instance == null) {
            instance = new LoanController();
        }
        return instance;
    }

    /**
     * Establece el usuario actual (simulación).
     * @param userId identificador de usuario
     */
    public void establecerUsuarioActual(String userId) {
        this.idUsuarioActual = userId;
    }

    /**
     * Obtiene el identificador del usuario actualmente autenticado (simulado).
     * @return id del usuario actual
     */
    public String obtenerIdUsuarioActual() {
        return idUsuarioActual;
    }

    /**
     * Obtiene los IDs de los libros prestados por el usuario actual.
     * @return lista de IDs de libros
     */
    public List<String> obtenerPrestamosUsuarioActual() {
        return userLoans.getOrDefault(idUsuarioActual, new ArrayList<>());
    }

    /**
     * Verifica si el usuario actual ya tiene prestado el libro indicado.
     * @param bookId id del libro
     * @return true si el usuario ya tiene el libro, false en caso contrario
     */
    public boolean usuarioTieneLibro(String bookId) {
        return obtenerPrestamosUsuarioActual().contains(bookId);
    }

    /**
     * Intenta prestar el libro al usuario actual.
     * @param bookId id del libro
     * @param catalogController controlador de catálogo para buscar el libro
     * @return true si el préstamo fue exitoso, false si no
     */
    public boolean pedirPrestamo(String bookId, CatalogController catalogController) {
        if (usuarioTieneLibro(bookId)) {
            return false; // Ya lo tiene
        }

        Optional<Book> book = catalogController.getById(bookId);
        if (book.isPresent() && book.get().getAvailableCopies() > 0) {
            book.get().setAvailableCopies(book.get().getAvailableCopies() - 1);

            userLoans.putIfAbsent(idUsuarioActual, new ArrayList<>());
            userLoans.get(idUsuarioActual).add(bookId);

            loanQueue.remove(bookId); // Remover de la cola si estaba
            return true;
        }
        return false;
    }

    /**
     * Verifica si el usuario actual ha completado el préstamo del libro anteriormente (historial)
     */
    /**
     * Determina si el usuario actual ha completado previamente el préstamo del libro indicado,
     * según el historial simulado `completedLoans`.
     * @param bookId id del libro
     * @return true si hay un registro de préstamo completado, false en caso contrario
     */
    public boolean usuarioHaCompletadoPrestamo(String bookId) {
        List<String> historial = completedLoans.getOrDefault(idUsuarioActual, new ArrayList<>());
        return historial.contains(bookId);
    }

    /**
     * Devuelve un libro prestado por el usuario actual.
     * @param bookId id del libro
     * @param catalogController controlador de catálogo para actualizar inventario
     * @return true si la devolución fue procesada, false en caso contrario
     */
    public boolean devolverLibro(String bookId, CatalogController catalogController) {
        if (!usuarioTieneLibro(bookId)) {
            return false;
        }

        Optional<Book> book = catalogController.getById(bookId);
        if (book.isPresent()) {
            book.get().setAvailableCopies(book.get().getAvailableCopies() + 1);
            userLoans.get(idUsuarioActual).remove(bookId);
            return true;
        }
        return false;
    }

    /**
     * Añade el usuario actual a la cola de espera del libro.
     * @param bookId id del libro
     */
    public void agregarACola(String bookId) {
        if (!usuarioTieneLibro(bookId)) {
            int currentQueue = loanQueue.getOrDefault(bookId, 0);
            loanQueue.put(bookId, currentQueue + 1);
        }
    }

    /**
     * Obtiene la posición estimada en la cola para el libro indicado.
     * Nota: implementación simulada.
     * @param bookId id del libro
     * @return posición en cola (simulada)
     */
    public int obtenerPosicionCola(String bookId) {
        return loanQueue.getOrDefault(bookId, 0);
    }

    /**
     * Obtiene el estado del libro para el usuario actual.
     * @param bookId id del libro
     * @param catalogController controlador de catálogo para consultar existencias
     * @return estado del libro para el usuario actual
     */
    public EstadoLibro obtenerEstadoLibro(String bookId, CatalogController catalogController) {
        if (usuarioTieneLibro(bookId)) {
            return EstadoLibro.YA_TIENE;
        }

        Optional<Book> book = catalogController.getById(bookId);
        if (book.isEmpty()) {
            return EstadoLibro.NO_ENCONTRADO;
        }

        if (book.get().getAvailableCopies() > 0) {
            return EstadoLibro.DISPONIBLE;
        } else if (loanQueue.getOrDefault(bookId, 0) > 0) {
            return EstadoLibro.EN_COLA;
        } else {
            return EstadoLibro.SIN_COPIAS;
        }
    }

    public enum EstadoLibro {
        YA_TIENE,    // Usuario ya tiene el libro (botón Leer)
        DISPONIBLE,  // Disponible para prestar
        EN_COLA,     // En cola de espera
        SIN_COPIAS,  // Sin copias disponibles
        NO_ENCONTRADO// Libro no existe
    }

    /**
     * Abre/Lee un libro que el usuario ya tiene (simulación).
     * @param bookId id del libro
     */
    public void abrirLibro(String bookId) {
        if (usuarioTieneLibro(bookId)) {
            System.out.println("Abriendo libro: " + bookId);
            // Aquí iría la lógica para abrir el lector de PDF/E-book
        }
    }
}
