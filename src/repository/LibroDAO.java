package repository;

import Catalogo.Libro;
import java.util.HashMap;
import java.util.Map;

/**
 * DAO en memoria para libros e inventario.
 *
 * Proporciona operaciones básicas para obtener libros y gestionar el conteo
 * de copias disponibles. Está pensado para pruebas locales sin persistencia
 * externa.
 */
public class LibroDAO {
    private final Map<String, Libro> libros = new HashMap<>();
    private final Map<String, Integer> inventario = new HashMap<>();

    /**
     * Crea el DAO con un libro de ejemplo para pruebas.
     */
    public LibroDAO() {
        // Poblado inicial de ejemplo (puedes añadir más)
        Libro ejemplo = new Libro("libro-1", "Programación en Java", "Informática", true);
        libros.put(ejemplo.getId(), ejemplo);
        inventario.put(ejemplo.getId(), 1); // 1 copia disponible por defecto
    }

    /**
     * Obtiene un libro por su id.
     *
     * @param id identificador del libro
     * @return instancia de {@link Libro} o {@code null} si no existe
     */
    public Libro obtenerPorId(String id) {
        return libros.get(id);
    }

    /**
     * Devuelve el número de copias disponibles de un libro.
     *
     * @param bookId id del libro
     * @return número de copias disponibles
     */
    public int obtenerCopiasDisponibles(String bookId) {
        return inventario.getOrDefault(bookId, 0);
    }

    /**
     * Intenta disminuir el contador de copias disponibles en una unidad.
     *
     * @param bookId id del libro
     * @return {@code true} si se pudo disminuir (había copias), {@code false} en caso contrario
     */
    public boolean disminuirCopia(String bookId) {
        int c = obtenerCopiasDisponibles(bookId);
        if (c <= 0) return false;
        inventario.put(bookId, c - 1);
        return true;
    }

    /**
     * Incrementa el contador de copias disponibles (p.ej. tras una devolución).
     *
     * @param bookId id del libro
     */
    public void aumentarCopia(String bookId) {
        int c = obtenerCopiasDisponibles(bookId);
        inventario.put(bookId, c + 1);
    }

    /**
     * Añade un libro y su número de copias al DAO (método de ayuda para pruebas).
     *
     * @param libro instancia de {@link Libro}
     * @param copias número de copias iniciales
     */
    public void agregarLibro(Libro libro, int copias) {
        libros.put(libro.getId(), libro);
        inventario.put(libro.getId(), copias);
    }
}
