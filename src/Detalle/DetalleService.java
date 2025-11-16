package Detalle;

import Catalogo.Libro;

/**
 * Servicio que encapsula la lógica relacionada con la vista de detalle.
 *
 * Actualmente actúa como un stub que devuelve datos de ejemplo. En el futuro
 * este servicio puede delegar a DAOs o a servicios remotos para obtener
 * información real.
 */
public class DetalleService {

    public DetalleService() {
    }

    /**
     * Carga la información del libro indicado.
     *
     * @param bookId identificador del libro
     * @return instancia de {@link Libro} con datos de ejemplo (puede ser null si no existe)
     */
    public Libro cargarLibro(String bookId) {
        return new Libro(bookId, "Título de ejemplo", "Categoría ejemplo", true);
    }

    /**
     * Indica si el libro tiene ejemplares disponibles.
     *
     * @param libro el libro a comprobar
     * @return {@code true} si el libro tiene ejemplares disponibles
     */
    public boolean estaDisponible(Libro libro) {
        return libro != null && libro.isDisponible();
    }

    /**
     * Simula la acción principal (pedir préstamo o abrir lectura) para pruebas.
     *
     * @param libro el libro objetivo
     */
    public void simularAccion(Libro libro) {
        if (estaDisponible(libro)) {
            System.out.println("Solicitando préstamo para: " + libro.getTitulo());
        } else {
            System.out.println("No hay copias disponibles para: " + libro.getTitulo());
        }
    }

    // TODO: añadir métodos reales: borrowOrOpen(userId, libro), availableCopies(bookId),
    // userHasBook(userId, bookId), etc.
}
