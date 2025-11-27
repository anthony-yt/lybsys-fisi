package Catalogo;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controlador del catálogo en memoria.
 * Proporciona carga inicial (seed), búsqueda, paginación y consultas por ID/autor.
 */
public class CatalogController {

    private List<Book> allBooks = new ArrayList<>();
    private List<Book> lastSearchResult = new ArrayList<>();
    private int currentPage = 1;
    private int pageSize = 10;

        /**
         * Crea el controlador y carga datos iniciales para pruebas.
         */
        public CatalogController() {
        seedData();
    }

    // Carga inicial de datos
    private void seedData() {
        // Libros con metadata completo — ampliado para pruebas (en español)
        allBooks.add(new Book("1", "Introducción a Java", "Joshua Bloch, Guy Steele",
                "Programación", "978-0596009205",
                "Una introducción comprensiva a Java que cubre desde conceptos básicos hasta avanzados.",
                "src/imagenes/IntroducciónAJava.png", 5, 5, "E-book"));

        allBooks.add(new Book("2", "Fundamentos de Bases de Datos", "Ramez Elmasri, Shamkant B. Navathe",
                "Bases de Datos", "978-0136086208",
                "Cobertura completa de conceptos de bases de datos relacionales y diseño de esquemas.",
                "src/imagenes/FundamentosDeBasesDeDatos.png", 4, 4, "Físico"));

        allBooks.add(new Book("3", "Redes de Computadoras", "Andrew S. Tanenbaum",
                "Redes", "978-0130661029",
                "Análisis detallado de protocolos, arquitecturas y tecnologías de red.",
                "src/imagenes/RedesDeComputadoras.png", 3, 1, "Físico"));

        allBooks.add(new Book("4", "Sistemas Operativos Modernos", "Andrew S. Tanenbaum, Herbert Bos",
                "Sistemas", "978-0136006633",
                "Estudio en profundidad de sistemas operativos contemporáneos.",
                "src/imagenes/SistemasOperativosModernos.png", 4, 3, "Físico"));

        allBooks.add(new Book("5", "Estructuras de Datos y Algoritmos", "Thomas H. Cormen",
                "Algoritmos", "978-0262033848",
                "La referencia definitiva para algoritmos y estructuras de datos.",
                "src/imagenes/EstructurasDeDatosyAlgoritmos.png", 6, 5, "E-book"));

        allBooks.add(new Book("6", "Inteligencia Artificial: Un Enfoque Moderno", "Stuart Russell, Peter Norvig",
                "Inteligencia Artificial", "978-0136042594",
                "Cobertura integral de técnicas y aplicaciones de IA modernas.",
                "src/imagenes/InteligenciaArtificial.png", 3, 3, "PDF"));

        allBooks.add(new Book("7", "Aprendizaje Automático", "Aurélien Géron",
                "Inteligencia Artificial", "978-0278962282",
                "Guía práctica para machine learning con ejemplos y código.",
                "src/imagenes/AprendizajeAutomático.png", 3, 0, "E-book"));

        allBooks.add(new Book("8", "Ingeniería de Software", "Ian Sommerville",
                "Sistemas", "978-0137035151",
                "Principios y prácticas modernas en desarrollo de software.",
                "src/imagenes/IngenieriaDeSoftware.png", 4, 4, "Físico"));

        allBooks.add(new Book("9", "Diseño y Análisis de Algoritmos", "Sanjoy Dasgupta, Christos Papadimitriou, Umesh Vazirani",
                "Algoritmos", "978-0073523408",
                "Métodos para diseñar algoritmos eficientes con análisis matemático.",
                "src/imagenes/DiseñoyAnalisisDeAlgoritmos.png", 3, 2, "Físico"));

        allBooks.add(new Book("10", "Bases de Datos Distribuidas", "Ceri Stefano, Giuseppe Pelagatti",
                "Bases de Datos", "978-0201593204",
                "Conceptos y técnicas para sistemas de bases de datos distribuidas.",
                "src/imagenes/BasesDeDatosDistribuidas.png", 3, 2, "Físico"));

        allBooks.add(new Book("11", "Programación en C++", "Bjarne Stroustrup",
                "Programación", "978-0321563842",
                "Referencia completa del lenguaje C++ por su creador.",
                "src/imagenes/ProgramacionEnC++.png", 4, 0, "E-book"));

        allBooks.add(new Book("12", "Minería de Datos", "Ian Witten, Eibe Frank, Mark Hall",
                "Inteligencia Artificial", "978-0128042915",
                "Técnicas y herramientas para extraer patrones de grandes volúmenes de datos.",
                "src/imagenes/MineriaDeDatos.png", 3, 3, "Físico"));

        allBooks.add(new Book("13", "Administración de Redes", "Craig Hunt",
                "Redes", "978-1491950357",
                "Guía práctica para administración y configuración de redes.",
                "src/imagenes/AdministracionDeRedes.png", 4, 3, "E-book"));

        // Entradas adicionales para pruebas (mismo autor en varios libros)
        allBooks.add(new Book("14", "Redes: Teoría y Práctica", "Andrew S. Tanenbaum",
                "Redes", "978-1111111111",
                "Material adicional sobre diseño de redes y protocolos.", "src/imagenes/RedesTeoriayPractica.png",
                2, 2, "Físico"));

        allBooks.add(new Book("15", "Sistemas Operativos: Casos Prácticos", "Andrew S. Tanenbaum",
                "Sistemas", "978-2222222222",
                "Casos prácticos y ejercicios sobre sistemas operativos.", "src/imagenes/SistemasOperativosCasosPracticos.png",
                2, 1, "E-book"));

        allBooks.add(new Book("16", "Java Avanzado", "Joshua Bloch",
                "Programación", "978-3333333333",
                "Buenas prácticas y patrones avanzados en Java.", "src/imagenes/JavaAvanzado.png", 
                3, 3, "PDF"));

        allBooks.add(new Book("17", "Patrones de Diseño en Java", "Joshua Bloch",
                "Programación", "978-4444444444",
                "Implementación de patrones de diseño en Java con ejemplos.", "src/imagenes/PatronesDeDiseñoEnJava.png", 
                2, 2, "E-book"));

        allBooks.add(new Book("18", "Machine Learning con Python", "Aurélien Géron",
                "Inteligencia Artificial", "978-5555555555",
                "Ejemplos prácticos de machine learning con scikit-learn y TensorFlow.", "src/imagenes/MachineLearningConPython.png", 
                4, 2, "E-book"));

        allBooks.add(new Book("19", "Introducción a la Seguridad Informática", "Ian Sommerville",
                "Sistemas", "978-6666666666",
                "Conceptos básicos y medidas de seguridad en sistemas.", "src/imagenes/IntroduccionALaSeguridadInformatica.png", 
                3, 3, "Físico"));

        allBooks.add(new Book("20", "Algoritmos para Big Data", "Sanjoy Dasgupta",
                "Algoritmos", "978-7777777777",
                "Estrategias y algoritmos para procesamiento de grandes volúmenes de datos.", "src/imagenes/AlgoritmosParaBigData.png", 
                3, 2, "E-book"));

        allBooks.add(new Book("21", "Bases de Datos NoSQL", "Ramez Elmasri",
                "Bases de Datos", "978-8888888888",
                "Modelos y uso de bases de datos NoSQL modernas.", "src/imagenes/BasesDeDatosNoSQL.png", 
                2, 2, "PDF"));

        allBooks.add(new Book("22", "Arquitectura de Software", "Ian Sommerville",
                "Sistemas", "978-9999999999",
                "Principios de arquitectura y diseño de software a gran escala.", "src/imagenes/ArquitecturaDeSoftware.png", 
                3, 1, "Físico"));

        allBooks.add(new Book("23", "Estructuras Avanzadas de Datos", "Thomas H. Cormen",
                "Algoritmos", "978-1212121212",
                "Temas avanzados en estructuras de datos y su análisis.", "src/imagenes/EstructurasAvanzadasDeDatos.png", 
                2, 2, "E-book"));

        allBooks.add(new Book("24", "Procesamiento de Lenguaje Natural", "Ian Witten",
                "Inteligencia Artificial", "978-1313131313",
                "Técnicas y aplicaciones de PLN en la práctica.", "src/imagenes/ProcesamientoDeLenguajeNatural.png", 
                2, 2, "Físico"));

        allBooks.add(new Book("25", "Redes Inalámbricas y Movilidad", "Craig Hunt",
                "Redes", "978-1414141414",
                "Diseño y gestión de redes inalámbricas y móviles.", "src/imagenes/RedesInalambricasyMovilidad.png", 
                3, 3, "Físico"));
    }

        /**
         * Devuelve una copia de la lista completa de libros del catálogo.
         * @return lista de libros
         */
        public List<Book> getAllBooks() {
                return new ArrayList<>(allBooks);
        }

        /**
         * Realiza la búsqueda de libros y devuelve resultados paginados.
         * @param titleQuery texto a buscar en el título (nullable)
         * @param category categoría a filtrar ("Todos" para no filtrar)
         * @param onlyAvailable si true, filtra solo libros con copias disponibles
         * @param page número de página (1-based)
         * @return lista paginada de resultados
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

        /**
         * Calcula el número total de páginas del último resultado de búsqueda.
         * @return número total de páginas (>=1)
         */
        public int getTotalPages() {
                int total = lastSearchResult.size();
                return Math.max(1, (int) Math.ceil((double) total / pageSize));
        }

        /**
         * Obtiene la página actual en la paginación.
         * @return página actual (1-based)
         */
        public int getCurrentPage() {
                return currentPage;
        }

        /**
         * Obtiene el tamaño de página configurado.
         * @return número de elementos por página
         */
        public int getPageSize() {
                return pageSize;
        }

        /**
         * Obtiene un libro por su identificador.
         * @param id identificador del libro
         * @return Optional con el libro si se encuentra
         */
        public Optional<Book> getById(String id) {
                return allBooks.stream().filter(b -> b.getId().equals(id)).findFirst();
        }

        /**
         * Obtener libros por autor (coincidencia parcial, case-insensitive).
         * @param author nombre o parte del nombre del autor
         * @return lista de libros que coinciden
         */
        public List<Book> getByAuthor(String author) {
        if (author == null || author.trim().isEmpty()) return Collections.emptyList();
        String a = author.trim().toLowerCase();
        return allBooks.stream()
                .filter(b -> b.getAuthors() != null && b.getAuthors().toLowerCase().contains(a))
                .collect(Collectors.toList());
    }
}
