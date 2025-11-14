package Catalogo;

public class Book {
    private String id;
    private String title;
    private String category;
    private boolean available;

    public Book(String id, String title, String category, boolean available) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.available = available;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }
}
