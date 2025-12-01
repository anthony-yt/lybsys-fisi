package Catalogo;

/**
 * Modelo que representa un libro del catálogo.
 * Contiene metadatos básicos usados por las vistas y controladores.
 */
public class Libro {
    private String id;
    private String titulo;
    private String categoria;
    private boolean disponible;
    private String autores;
    private String isbn;
    private String descripcion;
    private String rutaPortada;
    private int totalCopias;
    private int copiasDisponibles;
    private String formato; // "PDF", "Físico", "E-book"

    /**
     * Constructor básico del libro.
     * @param id identificador único
     * @param titulo título del libro
     * @param categoria categoría o materia
     * @param disponible si está disponible al menos una copia
     */
    public Libro(String id, String titulo, String categoria, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.disponible = disponible;
        this.autores = "Autor desconocido";
        this.isbn = "N/A";
        this.descripcion = "Sin descripción";
        this.rutaPortada = null;
        this.totalCopias = 1;
        this.copiasDisponibles = disponible ? 1 : 0;
        this.formato = "Físico";
    }

    /**
     * Constructor completo del libro.
     * @param id identificador único
     * @param titulo título del libro
     * @param autores autores (cadena)
     * @param categoria categoría o materia
     * @param isbn código ISBN
     * @param descripcion descripción extensa
     * @param rutaPortada ruta a la imagen de portada
     * @param totalCopias número total de copias
     * @param copiasDisponibles número de copias disponibles actualmente
     * @param formato formato (p.ej. "PDF", "Físico", "E-book")
     */
    public Libro(String id, String titulo, String autores, String categoria, String isbn,
                 String descripcion, String rutaPortada, int totalCopias, int copiasDisponibles, String formato) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.categoria = categoria;
        this.isbn = isbn;
        this.descripcion = descripcion;
        this.rutaPortada = rutaPortada;
        this.totalCopias = totalCopias;
        this.copiasDisponibles = copiasDisponibles;
        this.formato = formato;
        this.disponible = copiasDisponibles > 0;
    }

    // Getters
    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public boolean isDisponible() { return disponible; }
    public String getAutores() { return autores; }
    public String getIsbn() { return isbn; }
    public String getDescripcion() { return descripcion; }
    public String getRutaPortada() { return rutaPortada; }
    public int getTotalCopias() { return totalCopias; }
    public int getCopiasDisponibles() { return copiasDisponibles; }
    public String getFormato() { return formato; }

    // Setters
    public void setAutores(String autores) { this.autores = autores; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setRutaPortada(String rutaPortada) { this.rutaPortada = rutaPortada; }
    public void setTotalCopias(int totalCopias) { this.totalCopias = totalCopias; }
    /**
     * Actualiza el número de copias disponibles y marca el flag `disponible`.
     * @param copiasDisponibles número de copias disponibles
     */
    public void setCopiasDisponibles(int copiasDisponibles) {
        this.copiasDisponibles = copiasDisponibles;
        this.disponible = copiasDisponibles > 0;
    }
    public void setFormato(String formato) { this.formato = formato; }
}