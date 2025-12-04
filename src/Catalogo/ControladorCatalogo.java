package Catalogo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controlador del catálogo en memoria.
 * Continúa desde el código base, pero ahora los métodos y variables están en español y con más detalles para los primeros 13 libros existentes.
 * Proporciona carga inicial, búsqueda, paginación y consultas por ID/autor.
 */
public class ControladorCatalogo {

    private List<Libro> libros = new ArrayList<>();
    private List<Libro> ultimoResultado = new ArrayList<>();
    private int paginaActual = 1;
    private int tamPagina = 10; // Ajustado a 10 por consistencia con la extensión

    /**
     * Crea el controlador y carga los datos iniciales para pruebas.
     */
    public ControladorCatalogo() {
        cargarDatos();
    }
    
    private void cargarDatos() {
        libros.add(new Libro("1", "Introducción a Java", 
            "Joshua Bloch, Guy Steele",
            "Programación", "978-0596009205",
            "Una introducción comprensiva a Java que cubre desde conceptos básicos hasta avanzados.",
            "src\\recursos\\imagenes\\IntroducciónAJava.png", 5, 5, "E-book"));
        libros.add(new Libro("2", "Fundamentos de Bases de Datos", 
            "Ramez Elmasri, Shamkant B. Navathe",
            "Bases de Datos", "978-0136086208",
            "Cobertura completa de conceptos de bases de datos relacionales y diseño de esquemas.",
            "src\\recursos\\imagenes\\FundamentosDeBasesDeDatos.png", 4, 4, "Físico"));
        libros.add(new Libro("3", "Redes de Computadoras", 
            "Andrew S. Tanenbaum",
            "Redes", "978-0130661029",
            "Análisis detallado de protocolos, arquitecturas y tecnologías de red.",
            "src\\recursos\\imagenes\\RedesDeComputadoras.png", 3, 1, "Físico"));
        libros.add(new Libro("4", "Sistemas Operativos Modernos", 
            "Andrew S. Tanenbaum, Herbert Bos",
            "Sistemas", "978-0136006633",
            "Estudio en profundidad de sistemas operativos contemporáneos.",
            "src\\recursos\\imagenes\\SistemasOperativosModernos.png", 4, 3, "Físico"));
        libros.add(new Libro("5", "Estructuras de Datos y Algoritmos", 
            "Thomas H. Cormen",
            "Algoritmos", "978-0262033848",
            "La referencia definitiva para algoritmos y estructuras de datos.",
            "src\\recursos\\imagenes\\EstructurasDeDatosyAlgoritmos.png", 6, 5, "E-book"));
        libros.add(new Libro("6", "Inteligencia Artificial: Un Enfoque Moderno", 
            "Stuart Russell, Peter Norvig",
            "Inteligencia Artificial", "978-0136042594",
            "Cobertura integral de técnicas y aplicaciones de IA modernas.",
            "src\\recursos\\imagenes\\InteligenciaArtificial.png", 3, 3, "PDF"));
        libros.add(new Libro("7", "Aprendizaje Automático", 
            "Aurélien Géron",
            "Inteligencia Artificial", "978-0278962282",
            "Guía práctica para machine learning con ejemplos y código.",
            "src\\recursos\\imagenes\\AprendizajeAutomático.png", 3, 0, "E-book"));
        libros.add(new Libro("8", "Ingeniería de Software", 
            "Ian Sommerville",
            "Sistemas", "978-0137035151",
            "Principios y prácticas modernas en desarrollo de software.",
            "src\\recursos\\imagenes\\IngenieriaDeSoftware.png", 4, 4, "Físico"));
        libros.add(new Libro("9", "Diseño y Análisis de Algoritmos", 
            "Sanjoy Dasgupta, Christos Papadimitriou, Umesh Vazirani",
            "Algoritmos", "978-0073523408",
            "Métodos para diseñar algoritmos eficientes con análisis matemático.",
            "src\\recursos\\imagenes\\DiseñoyAnalisisDeAlgoritmos.png", 3, 2, "Físico"));
        libros.add(new Libro("10", "Bases de Datos Distribuidas", 
            "Ceri Stefano, Giuseppe Pelagatti",
            "Bases de Datos", "978-0201593204",
            "Conceptos y técnicas para sistemas de bases de datos distribuidas.",
            "src\\recursos\\imagenes\\BasesDeDatosDistribuidas.png", 3, 2, "Físico"));
        libros.add(new Libro("11", "Programación en C++", 
            "Bjarne Stroustrup",
            "Programación", "978-0321563842",
            "Referencia completa del lenguaje C++ por su creador.",
            "src\\recursos\\imagenes\\ProgramacionEnC++.png", 4, 0, "E-book"));
        libros.add(new Libro("12", "Minería de Datos", 
            "Ian Witten, Eibe Frank, Mark Hall",
            "Inteligencia Artificial", "978-0128042915",
            "Técnicas y herramientas para extraer patrones de grandes volúmenes de datos.",
            "src\\recursos\\imagenes\\MineriaDeDatos.png", 3, 3, "Físico"));
        libros.add(new Libro("13", "Administración de Redes", 
            "Craig Hunt",
            "Redes", "978-1491950357",
            "Guía práctica para administración y configuración de redes.",
            "src\\recursos\\imagenes\\AdministracionDeRedes.png", 4, 3, "E-book"));

        // Resto de los libros extra (del 14 al 25) tomados del listado extendido
        libros.add(new Libro("14", "Redes: Teoría y Práctica", "Andrew S. Tanenbaum",
                "Redes", "978-1111111111",
                "Material adicional sobre diseño de redes y protocolos.",
                "src\\recursos\\imagenes\\RedesTeoriayPractica.png", 2, 2, "Físico"));
        libros.add(new Libro("15", "Sistemas Operativos: Casos Prácticos", "Andrew S. Tanenbaum",
                "Sistemas", "978-2222222222",
                "Casos prácticos y ejercicios sobre sistemas operativos.",
                "src\\recursos\\imagenes\\SistemasOperativosCasosPracticos.png", 2, 1, "E-book"));
        libros.add(new Libro("16", "Java Avanzado", "Joshua Bloch",
                "Programación", "978-3333333333",
                "Buenas prácticas y patrones avanzados en Java.",
                "src\\recursos\\imagenes\\JavaAvanzado.png", 3, 3, "PDF"));
        libros.add(new Libro("17", "Patrones de Diseño en Java", "Joshua Bloch",
                "Programación", "978-4444444444",
                "Implementación de patrones de diseño en Java con ejemplos.",
                "src\\recursos\\imagenes\\PatronesDeDiseñoEnJava.png", 2, 2, "E-book"));
        libros.add(new Libro("18", "Machine Learning con Python", "Aurélien Géron",
                "Inteligencia Artificial", "978-5555555555",
                "Ejemplos prácticos de machine learning con scikit-learn y TensorFlow.",
                "src\\recursos\\imagenes\\MachineLearningConPython.png", 4, 2, "E-book"));
        libros.add(new Libro("19", "Introducción a la Seguridad Informática", "Ian Sommerville",
                "Sistemas", "978-6666666666",
                "Conceptos básicos y medidas de seguridad en sistemas.",
                "src\\recursos\\imagenes\\IntroduccionALaSeguridadInformatica.png", 3, 3, "Físico"));
        libros.add(new Libro("20", "Algoritmos para Big Data", "Sanjoy Dasgupta",
                "Algoritmos", "978-7777777777",
                "Estrategias y algoritmos para procesamiento de grandes volúmenes de datos.",
                "src\\recursos\\imagenes\\AlgoritmosParaBigData.png", 3, 2, "E-book"));
        libros.add(new Libro("21", "Bases de Datos NoSQL", "Ramez Elmasri",
                "Bases de Datos", "978-8888888888",
                "Modelos y uso de bases de datos NoSQL modernas.",
                "src\\recursos\\imagenes\\BasesDeDatosNoSQL.png", 2, 2, "PDF"));
        libros.add(new Libro("22", "Arquitectura de Software", "Ian Sommerville",
                "Sistemas", "978-9999999999",
                "Principios de arquitectura y diseño de software a gran escala.",
                "src\\recursos\\imagenes\\ArquitecturaDeSoftware.png", 3, 1, "Físico"));
        libros.add(new Libro("23", "Estructuras Avanzadas de Datos", "Thomas H. Cormen",
                "Algoritmos", "978-1212121212",
                "Temas avanzados en estructuras de datos y su análisis.",
                "src\\recursos\\imagenes\\EstructurasAvanzadasDeDatos.png", 2, 2, "E-book"));
        libros.add(new Libro("24", "Procesamiento de Lenguaje Natural", "Ian Witten",
                "Inteligencia Artificial", "978-1313131313",
                "Técnicas y aplicaciones de PLN en la práctica.",
                "src\\recursos\\imagenes\\ProcesamientoDeLenguajeNatural.png", 2, 2, "Físico"));
        libros.add(new Libro("25", "Redes Inalámbricas y Movilidad", "Craig Hunt",
                "Redes", "978-1414141414",
                "Diseño y gestión de redes inalámbricas y móviles.",
                "src\\recursos\\imagenes\\RedesInalambricasyMovilidad.png", 3, 3, "Físico"));
    }

    /**
     * Devuelve una copia de la lista completa de libros del catálogo.
     * @return lista de libros
     */
    public List<Libro> getTodosLosLibros() {
        return new ArrayList<>(libros);
    }

    /**
     * Realiza la búsqueda de libros y devuelve resultados paginados.
     * @param consultaTitulo texto a buscar en el título (nullable)
     * @param categoria categoría a filtrar ("Todos" para no filtrar)
     * @param soloDisponibles si true, filtra solo libros con copias disponibles
     * @param pagina número de página (base 1)
     * @return lista paginada de resultados
     */
    public List<Libro> buscar(String consultaTitulo, String categoria, boolean soloDisponibles, int pagina) {
        paginaActual = Math.max(1, pagina);

        Stream<Libro> stream = libros.stream();

        if (consultaTitulo != null && !consultaTitulo.trim().isEmpty()) {
            String t = consultaTitulo.trim().toLowerCase();
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

    /**
     * Calcula el número total de páginas del último resultado de búsqueda.
     * @return número total de páginas (>=1)
     */
    public int getTotalPaginas() {
        int total = ultimoResultado.size();
        return Math.max(1, (int) Math.ceil((double) total / tamPagina));
    }

    /**
     * Obtiene la página actual en la paginación.
     * @return página actual (base 1)
     */
    public int getPaginaActual() {
        return paginaActual;
    }

    /**
     * Obtiene el tamaño de página configurado.
     * @return número de elementos por página
     */
    public int getTamPagina() {
        return tamPagina;
    }

    /**
     * Obtiene un libro por su identificador.
     * @param id identificador del libro
     * @return Optional con el libro si se encuentra
     */
    public Optional<Libro> obtenerPorId(String id) {
        return libros.stream().filter(l -> l.getId().equals(id)).findFirst();
    }

    /**
     * Obtiene libros por autor (coincidencia parcial, insensible a mayúsculas).
     * @param autor nombre o parte del nombre del autor
     * @return lista de libros que coinciden
     */
    public List<Libro> obtenerPorAutor(String autor) {
        if (autor == null || autor.trim().isEmpty()) return Collections.emptyList();
        String a = autor.trim().toLowerCase();
        return libros.stream()
                .filter(l -> l.getAutores() != null && l.getAutores().toLowerCase().contains(a))
                .collect(Collectors.toList());
    }
}