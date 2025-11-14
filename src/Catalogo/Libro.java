package Catalogo;

public class Libro {
    private String id;
    private String titulo;
    private String categoria;
    private boolean disponible;

    public Libro(String id, String titulo, String categoria, boolean disponible) {
        this.id = id;
        this.titulo = titulo;
        this.categoria = categoria;
        this.disponible = disponible;
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getCategoria() { return categoria; }
    public boolean isDisponible() { return disponible; }
}
