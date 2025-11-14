package Catalogo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ControladorCatalogo {

    private List<Libro> libros = new ArrayList<>();

    // Paginación
    private int tamPagina = 6; // libros por página
    private int paginaActual = 1;
    private List<Libro> ultimoResultado = new ArrayList<>();

    public ControladorCatalogo() {
        cargarDatos();
    }

    // Carga inicial de datos
    private void cargarDatos() {
        libros.add(new Libro("1", "Introducción a Java", "Programación", true));
        libros.add(new Libro("2", "Fundamentos de Bases de Datos", "Bases de Datos", true));
        libros.add(new Libro("3", "Redes de Computadoras", "Redes", false));
        libros.add(new Libro("4", "Sistemas Operativos Modernos", "Sistemas", true));
        libros.add(new Libro("5", "Estructuras de Datos y Algoritmos", "Algoritmos", true));
        libros.add(new Libro("6", "Inteligencia Artificial: Un Enfoque Moderno", "Inteligencia Artificial", true));
        libros.add(new Libro("7", "Aprendizaje Automático", "Inteligencia Artificial", false));
        libros.add(new Libro("8", "Ingeniería de Software", "Sistemas", true));
        libros.add(new Libro("9", "Diseño y Análisis de Algoritmos", "Algoritmos", true));
        libros.add(new Libro("10", "Bases de Datos Distribuidas", "Bases de Datos", true));
        libros.add(new Libro("11", "Programación en C++", "Programación", false));
        libros.add(new Libro("12", "Minería de Datos", "Inteligencia Artificial", true));
        libros.add(new Libro("13", "Administración de Redes", "Redes", true));
    }

    public List<Libro> getTodosLosLibros() {
        return new ArrayList<>(libros);
    }

    /**
     * Realiza la búsqueda de libros y devuelve resultados paginados.
     */
    public List<Libro> buscar(String titulo, String categoria, boolean soloDisponibles, int pagina) {

        paginaActual = Math.max(1, pagina);

        Stream<Libro> stream = libros.stream();

        if (titulo != null && !titulo.trim().isEmpty()) {
            String t = titulo.trim().toLowerCase();
            stream = stream.filter(l -> l.getTitulo().toLowerCase().contains(t));
        }

        if (categoria != null && !categoria.equalsIgnoreCase("Todos") && !categoria.isEmpty()) {
            String c = categoria.toLowerCase();
            stream = stream.filter(l -> l.getCategoria().toLowerCase().equals(c));
        }

        if (soloDisponibles) {
            stream = stream.filter(Libro::isDisponible);
        }

        List<Libro> filtrados = stream.collect(Collectors.toList());
        ultimoResultado = filtrados;

        // Paginación
        int desde = (paginaActual - 1) * tamPagina;
        if (desde >= filtrados.size()) return Collections.emptyList();

        int hasta = Math.min(desde + tamPagina, filtrados.size());
        return filtrados.subList(desde, hasta);
    }

    public int getTotalPaginas() {
        int total = ultimoResultado.size();
        return Math.max(1, (int) Math.ceil((double) total / tamPagina));
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public int getTamPagina() {
        return tamPagina;
    }

    public Optional<Libro> obtenerPorId(String id) {
        return libros.stream().filter(l -> l.getId().equals(id)).findFirst();
    }
}
