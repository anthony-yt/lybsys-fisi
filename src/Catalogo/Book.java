package Catalogo;

/**
 * Modelo que representa un libro del catálogo.
 * Contiene metadatos básicos usados por las vistas y controladores.
 */
public class Book {
    private String id;
    private String title;
    private String category;
    private boolean available;
    private String authors;
    private String isbn;
    private String description;
    private String coverPath;
    private int totalCopies;
    private int availableCopies;
    private String format; // "PDF", "Físico", "E-book"

    public Book(String id, String title, String category, boolean available) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.available = available;
        this.authors = "Autor desconocido";
        this.isbn = "N/A";
        this.description = "Sin descripción";
        this.coverPath = null;
        this.totalCopies = 1;
        this.availableCopies = available ? 1 : 0;
        this.format = "Físico";
    }

    /**
     * Constructor completo del libro.
     * @param id identificador único
     * @param title título del libro
     * @param authors autores (cadena)
     * @param category categoría o materia
     * @param isbn código ISBN
     * @param description descripción extensa
     * @param coverPath ruta a la imagen de portada
     * @param totalCopies número total de copias
     * @param availableCopies número de copias disponibles actualmente
     * @param format formato (p.ej. "PDF", "Físico", "E-book")
     */
    // Constructor completo
    public Book(String id, String title, String authors, String category, String isbn,
                String description, String coverPath, int totalCopies, int availableCopies, String format) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.category = category;
        this.isbn = isbn;
        this.description = description;
        this.coverPath = coverPath;
        this.totalCopies = totalCopies;
        this.availableCopies = availableCopies;
        this.format = format;
        this.available = availableCopies > 0;
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }
    public String getAuthors() { return authors; }
    public String getIsbn() { return isbn; }
    public String getDescription() { return description; }
    public String getCoverPath() { return coverPath; }
    public int getTotalCopies() { return totalCopies; }
    public int getAvailableCopies() { return availableCopies; }
    public String getFormat() { return format; }

    // Setters
    public void setAuthors(String authors) { this.authors = authors; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setDescription(String description) { this.description = description; }
    public void setCoverPath(String coverPath) { this.coverPath = coverPath; }
    public void setTotalCopies(int totalCopies) { this.totalCopies = totalCopies; }
    /**
     * Actualiza el número de copias disponibles y marca el flag `available`.
     * @param availableCopies número de copias disponibles
     */
    public void setAvailableCopies(int availableCopies) { 
        this.availableCopies = availableCopies;
        this.available = availableCopies > 0;
    }
    public void setFormat(String format) { this.format = format; }
}
