package Catalogo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatalogController {
    private List<Book> allBooks = new ArrayList<>();

    // Paginación
    private int pageSize = 6; // libros por página
    private int currentPage = 1;
    private List<Book> lastSearchResult = new ArrayList<>();

    public CatalogController() {
        seedData();
    }

    // Carga inicial de datos
    private void seedData() {
        allBooks.add(new Book("1", "Introducción a Java", "Programación", true));
        allBooks.add(new Book("2", "Fundamentos de Bases de Datos", "Bases de Datos", true));
        allBooks.add(new Book("3", "Redes de Computadoras", "Redes", false));
        allBooks.add(new Book("4", "Sistemas Operativos Modernos", "Sistemas", true));
        allBooks.add(new Book("5", "Estructuras de Datos y Algoritmos", "Algoritmos", true));
        allBooks.add(new Book("6", "Inteligencia Artificial: Un Enfoque Moderno", "Inteligencia Artificial", true));
        allBooks.add(new Book("7", "Aprendizaje Automático", "Inteligencia Artificial", false));
        allBooks.add(new Book("8", "Ingeniería de Software", "Sistemas", true));
        allBooks.add(new Book("9", "Diseño y Análisis de Algoritmos", "Algoritmos", true));
        allBooks.add(new Book("10", "Bases de Datos Distribuidas", "Bases de Datos", true));
        allBooks.add(new Book("11", "Programación en C++", "Programación", false));
        allBooks.add(new Book("12", "Minería de Datos", "Inteligencia Artificial", true));
        allBooks.add(new Book("13", "Administración de Redes", "Redes", true));
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(allBooks);
    }

    /**
     * Realiza la búsqueda de libros y devuelve resultados paginados.
     */
    public List<Book> search(String titleQuery, String category, boolean onlyAvailable, int page) {
        currentPage = Math.max(1, page);

        Stream<Book> stream = allBooks.stream();

        if (titleQuery != null && !titleQuery.trim().isEmpty()) {
            String t = titleQuery.trim().toLowerCase();
            stream = stream.filter(b -> b.getTitle().toLowerCase().contains(t));
        }

        if (category != null && !category.equalsIgnoreCase("Todos") && !category.isEmpty()) {
            String c = category.toLowerCase();
            stream = stream.filter(b -> b.getCategory().toLowerCase().equals(c));
        }

        if (onlyAvailable) {
            stream = stream.filter(Book::isAvailable);
        }

        List<Book> filtered = stream.collect(Collectors.toList());
        lastSearchResult = filtered;

        // Paginación
        int fromIndex = (currentPage - 1) * pageSize;
        if (fromIndex >= filtered.size()) return Collections.emptyList();

        int toIndex = Math.min(fromIndex + pageSize, filtered.size());
        return filtered.subList(fromIndex, toIndex);
    }

    public int getTotalPages() {
        int total = lastSearchResult.size();
        return Math.max(1, (int) Math.ceil((double) total / pageSize));
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    // Obtener un libro por su ID
    public Optional<Book> getById(String id) {
        return allBooks.stream().filter(b -> b.getId().equals(id)).findFirst();
    }
}
