package Lectura;

import recursos.LecturaException;
import java.io.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo único para el módulo de Lectura Online.
 * <p>
 * Versión mejorada con sistema de marcadores múltiples, seguimiento de progreso
 * y gestión de préstamos de libros.
 * </p>
 */
public class ModeloLectura {

    /** Ruta del archivo donde se guardan los libros */
    private static final String ARCHIVO_LIBROS = "datos/libros.dat";

    /** Ruta del archivo donde se guarda el progreso de lectura */
    private static final String ARCHIVO_PROGRESO = "datos/progreso_lectura.dat";

    /** Ruta del archivo donde se guardan los préstamos */
    private static final String ARCHIVO_PRESTAMOS = "datos/prestamos.dat";

    /** Ruta del archivo donde se guardan los marcadores */
    private static final String ARCHIVO_MARCADORES = "datos/marcadores.dat";

    // ───────────────────────────────────────────────────────────────
    // CLASES INTERNAS
    // ───────────────────────────────────────────────────────────────

    /**
     * Representa un libro dentro del sistema de lectura.
     */
    public static class Libro implements Serializable {
        private static final long serialVersionUID = 1L;

        private String id;
        private String titulo;
        private String autor;
        private String categoria;
        private String isbn;
        private String descripcion;
        private String formato;
        private String rutaArchivo;
        private int totalPaginas;
        private boolean disponible;

        /** Constructor vacío de la clase Libro. */
        public Libro() {}

        /**
         * Constructor principal para crear un libro con atributos esenciales.
         *
         * @param id        Identificador único del libro
         * @param titulo    Título del libro
         * @param autor     Autor del libro
         * @param formato   Formato del libro (PDF, EPUB, etc.)
         * @param rutaArchivo Ruta del archivo del libro
         */
        public Libro(String id, String titulo, String autor, String formato, String rutaArchivo) {
            this.id = id;
            this.titulo = titulo;
            this.autor = autor;
            this.formato = formato;
            this.rutaArchivo = rutaArchivo;
            this.disponible = true;
        }

        // ───────────────────────────────────────────────────────────────
        // Getters y Setters
        // ───────────────────────────────────────────────────────────────

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public String getAutor() { return autor; }
        public void setAutor(String autor) { this.autor = autor; }
        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }
        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public String getFormato() { return formato; }
        public void setFormato(String formato) { this.formato = formato; }
        public String getRutaArchivo() { return rutaArchivo; }
        public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }
        public int getTotalPaginas() { return totalPaginas; }
        public void setTotalPaginas(int totalPaginas) { this.totalPaginas = totalPaginas; }
        public boolean isDisponible() { return disponible; }
        public void setDisponible(boolean disponible) { this.disponible = disponible; }

        @Override
        public String toString() {
            return "Libro{id='" + id + "', titulo='" + titulo + "', autor='" + autor + "'}";
        }
    }

    /**
     * Representa el progreso de lectura de un usuario en un libro.
     */
    public static class ProgresoLectura implements Serializable {
        private static final long serialVersionUID = 1L;

        private String usuarioId;
        private String libroId;
        private int paginaActual;
        private String ultimaActualizacion;

        /** Constructor vacío. */
        public ProgresoLectura() {}

        /**
         * Constructor principal.
         *
         * @param usuarioId Identificador del usuario
         * @param libroId   Identificador del libro
         * @param paginaActual Página actual leída
         */
        public ProgresoLectura(String usuarioId, String libroId, int paginaActual) {
            this.usuarioId = usuarioId;
            this.libroId = libroId;
            this.paginaActual = paginaActual;
            this.ultimaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        public String getUsuarioId() { return usuarioId; }
        public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
        public String getLibroId() { return libroId; }
        public void setLibroId(String libroId) { this.libroId = libroId; }
        public int getPaginaActual() { return paginaActual; }

        /**
         * Actualiza la página actual y la fecha de la última actualización.
         *
         * @param paginaActual Nueva página actual
         */
        public void setPaginaActual(int paginaActual) {
            this.paginaActual = paginaActual;
            this.ultimaActualizacion = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        public String getUltimaActualizacion() { return ultimaActualizacion; }
    }

    /**
     * Representa un préstamo de libro para un usuario.
     */
    public static class Prestamo implements Serializable {
        private static final long serialVersionUID = 1L;

        private String usuarioId;
        private String libroId;
        private String fechaPrestamo;
        private String fechaVencimiento;
        private String estado;

        /** Constructor vacío. */
        public Prestamo() {}

        /**
         * Constructor principal.
         *
         * @param usuarioId Identificador del usuario
         * @param libroId   Identificador del libro
         * @param estado    Estado del préstamo (activo, vencido, etc.)
         */
        public Prestamo(String usuarioId, String libroId, String estado) {
            this.usuarioId = usuarioId;
            this.libroId = libroId;
            this.estado = estado;
            this.fechaPrestamo = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }

        public String getUsuarioId() { return usuarioId; }
        public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
        public String getLibroId() { return libroId; }
        public void setLibroId(String libroId) { this.libroId = libroId; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }
        public String getFechaPrestamo() { return fechaPrestamo; }
        public String getFechaVencimiento() { return fechaVencimiento; }
        public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    }

    /**
     * Representa un marcador de página dentro de un libro, con ID único y descripción opcional.
     */
    public static class Marcador implements Serializable, Comparable<Marcador> {
        private static final long serialVersionUID = 2L;

        private String id;
        private String usuarioId;
        private String libroId;
        private int pagina;
        private String descripcion;
        private String fechaCreacion;

        /** Constructor vacío. */
        public Marcador() {}

        /**
         * Crea un marcador nuevo.
         *
         * @param usuarioId   Identificador del usuario
         * @param libroId     Identificador del libro
         * @param pagina      Página donde se coloca el marcador
         * @param descripcion Descripción opcional del marcador
         */
        public Marcador(String usuarioId, String libroId, int pagina, String descripcion) {
            this.id = UUID.randomUUID().toString().substring(0, 8);
            this.usuarioId = usuarioId;
            this.libroId = libroId;
            this.pagina = pagina;
            this.descripcion = (descripcion == null || descripcion.trim().isEmpty())
                ? "Marcador página " + pagina
                : descripcion;
            this.fechaCreacion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public String getUsuarioId() { return usuarioId; }
        public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
        public String getLibroId() { return libroId; }
        public void setLibroId(String libroId) { this.libroId = libroId; }
        public int getPagina() { return pagina; }
        public void setPagina(int pagina) { this.pagina = pagina; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public String getFechaCreacion() { return fechaCreacion; }

        @Override
        public int compareTo(Marcador otro) {
            return Integer.compare(this.pagina, otro.pagina);
        }

        @Override
        public String toString() {
            return "📑 Pág. " + pagina + " - " + descripcion + " (" + fechaCreacion + ")";
        }
    }

    // ───────────────────────────────────────────────────────────────
    // MÉTODOS DE PERSISTENCIA - LIBROS
    // ───────────────────────────────────────────────────────────────

    /**
     * Obtiene un libro por su ID.
     *
     * @param libroId ID del libro
     * @return Libro encontrado
     * @throws LecturaException.LibroNoEncontradoException si no existe el libro
     */
    public static Libro obtenerLibroPorId(String libroId) throws LecturaException.LibroNoEncontradoException {
        List<Libro> libros = cargarLibros();
        for (Libro libro : libros) {
            if (libro.getId().equals(libroId)) {
                return libro;
            }
        }
        throw new LecturaException.LibroNoEncontradoException(libroId);
    }

    /**
     * Carga todos los libros desde el almacenamiento.
     *
     * @return Lista de libros
     */
    @SuppressWarnings("unchecked")
    public static List<Libro> cargarLibros() {
        crearDirectorioSiNoExiste();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_LIBROS))) {
            return (List<Libro>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("⚠️  Error al cargar libros: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista de libros en almacenamiento.
     *
     * @param libros Lista de libros a guardar
     */
    public static void guardarLibros(List<Libro> libros) {
        crearDirectorioSiNoExiste();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_LIBROS))) {
            oos.writeObject(libros);
        } catch (IOException e) {
            System.err.println("❌ Error al guardar libros: " + e.getMessage());
        }
    }
    
    // ───────────────────────────────────────────────────────────────
    // MÉTODOS DE PERSISTENCIA - PROGRESO
    // ───────────────────────────────────────────────────────────────

    /**
     * Obtiene el progreso de lectura de un usuario para un libro.
     *
     * @param usuarioId ID del usuario
     * @param libroId   ID del libro
     * @return Página actual leída, 0 si no existe progreso
     */
    public static int obtenerProgreso(String usuarioId, String libroId) {
        List<ProgresoLectura> progresos = cargarProgresos();
        for (ProgresoLectura progreso : progresos) {
            if (progreso.getUsuarioId().equals(usuarioId) && progreso.getLibroId().equals(libroId)) {
                return progreso.getPaginaActual();
            }
        }
        return 0;
    }

    /**
     * Guarda o actualiza el progreso de lectura de un usuario.
     *
     * @param usuarioId    ID del usuario
     * @param libroId      ID del libro
     * @param paginaActual Página actual leída
     * @return true si se guardó correctamente
     * @throws LecturaException.ErrorGuardadoException si ocurre un error al guardar
     */
    public static boolean guardarProgreso(String usuarioId, String libroId, int paginaActual)
            throws LecturaException.ErrorGuardadoException {
        try {
            List<ProgresoLectura> progresos = cargarProgresos();
            boolean encontrado = false;
            for (ProgresoLectura progreso : progresos) {
                if (progreso.getUsuarioId().equals(usuarioId) && progreso.getLibroId().equals(libroId)) {
                    progreso.setPaginaActual(paginaActual);
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {
                progresos.add(new ProgresoLectura(usuarioId, libroId, paginaActual));
            }
            return guardarProgresos(progresos);
        } catch (Exception e) {
            throw new LecturaException.ErrorGuardadoException("progreso", e);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<ProgresoLectura> cargarProgresos() {
        crearDirectorioSiNoExiste();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_PROGRESO))) {
            return (List<ProgresoLectura>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("⚠️  Error al cargar progresos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static boolean guardarProgresos(List<ProgresoLectura> progresos) {
        crearDirectorioSiNoExiste();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_PROGRESO))) {
            oos.writeObject(progresos);
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar progresos: " + e.getMessage());
            return false;
        }
    }

    // ───────────────────────────────────────────────────────────────
    // MÉTODOS DE PERSISTENCIA - PRÉSTAMOS
    // ───────────────────────────────────────────────────────────────

    /**
     * Verifica si un usuario puede leer un libro (préstamo activo).
     *
     * @param usuarioId ID del usuario
     * @param libroId   ID del libro
     * @return true si el préstamo está activo
     */
    public static boolean puedeLeer(String usuarioId, String libroId) {
        List<Prestamo> prestamos = cargarPrestamos();
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getUsuarioId().equals(usuarioId) &&
                prestamo.getLibroId().equals(libroId) &&
                prestamo.getEstado().equals("activo")) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private static List<Prestamo> cargarPrestamos() {
        crearDirectorioSiNoExiste();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_PRESTAMOS))) {
            return (List<Prestamo>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("⚠️  Error al cargar préstamos: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void guardarPrestamos(List<Prestamo> prestamos) {
        crearDirectorioSiNoExiste();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_PRESTAMOS))) {
            oos.writeObject(prestamos);
        } catch (IOException e) {
            System.err.println("❌ Error al guardar préstamos: " + e.getMessage());
        }
    }

    // ───────────────────────────────────────────────────────────────
    // MÉTODOS DE PERSISTENCIA - MARCADORES
    // ───────────────────────────────────────────────────────────────

    /**
     * Agrega un nuevo marcador.
     *
     * @param usuarioId   ID del usuario
     * @param libroId     ID del libro
     * @param pagina      Página del marcador
     * @param descripcion Descripción opcional
     * @return Marcador creado
     * @throws LecturaException.ErrorGuardadoException si no se pudo guardar
     */
    public static Marcador agregarMarcador(String usuarioId, String libroId, int pagina, String descripcion)
            throws LecturaException.ErrorGuardadoException {
        try {
//Yared: Aquí se agregan los marcadores y se podría hacer con manejo de archivos.
// También se puede tomar como referencia la parte de arriba con los archivos .dat pero sería con .txt supongo 
            List<Marcador> marcadores = cargarMarcadores();
            Marcador nuevoMarcador = new Marcador(usuarioId, libroId, pagina, descripcion);
            marcadores.add(nuevoMarcador);
            if (guardarMarcadores(marcadores)) {
                return nuevoMarcador;
            } else {
                throw new LecturaException.ErrorGuardadoException("marcador",
                    new IOException("No se pudo guardar el archivo"));
            }
        } catch (Exception e) {
            throw new LecturaException.ErrorGuardadoException("marcador", e);
        }
    }

    /**
     * Obtiene todos los marcadores de un usuario para un libro.
     *
     * @param usuarioId ID del usuario
     * @param libroId   ID del libro
     * @return Lista de marcadores ordenados por página
     */
    public static List<Marcador> obtenerMarcadores(String usuarioId, String libroId) {
        List<Marcador> todosMarcadores = cargarMarcadores();
        List<Marcador> resultado = new ArrayList<>();
        for (Marcador marcador : todosMarcadores) {
            if (marcador.getUsuarioId().equals(usuarioId) && marcador.getLibroId().equals(libroId)) {
                resultado.add(marcador);
            }
        }
        Collections.sort(resultado);
        return resultado;
    }

    /**
     * Elimina un marcador específico por su ID.
     *
     * @param marcadorId ID del marcador a eliminar
     * @return true si el marcador fue eliminado correctamente
     * @throws LecturaException.MarcadorNoEncontradoException si no se encuentra un marcador con ese ID
     */
    public static boolean eliminarMarcador(String marcadorId)
            throws LecturaException.MarcadorNoEncontradoException {
        List<Marcador> marcadores = cargarMarcadores();
        boolean eliminado = false;
        Iterator<Marcador> iterator = marcadores.iterator();
        while (iterator.hasNext()) {
            Marcador marcador = iterator.next();
            if (marcador.getId().equals(marcadorId)) {
                iterator.remove();
                eliminado = true;
                break;
            }
        }

        if (!eliminado) {
            throw new LecturaException.MarcadorNoEncontradoException();
        }

        return guardarMarcadores(marcadores);
    }

    /**
     * Elimina todos los marcadores de un usuario para un libro específico.
     *
     * @param usuarioId ID del usuario
     * @param libroId   ID del libro
     * @return la cantidad de marcadores eliminados
     */
    public static int eliminarTodosMarcadores(String usuarioId, String libroId) {
        List<Marcador> marcadores = cargarMarcadores();
        int eliminados = 0;

        Iterator<Marcador> iterator = marcadores.iterator();
        while (iterator.hasNext()) {
            Marcador marcador = iterator.next();
            if (marcador.getUsuarioId().equals(usuarioId) && marcador.getLibroId().equals(libroId)) {
                iterator.remove();
                eliminados++;
            }
        }

        if (eliminados > 0) {
            guardarMarcadores(marcadores);
        }

        return eliminados;
    }

    /**
     * Cuenta la cantidad de marcadores de un usuario en un libro.
     *
     * @param usuarioId ID del usuario
     * @param libroId   ID del libro
     * @return número de marcadores existentes
     */
    public static int contarMarcadores(String usuarioId, String libroId) {
        return obtenerMarcadores(usuarioId, libroId).size();
    }

    /**
     * Carga la lista de marcadores desde almacenamiento.
     *
     * @return lista de marcadores
     */
    @SuppressWarnings("unchecked")
    private static List<Marcador> cargarMarcadores() {
        crearDirectorioSiNoExiste();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_MARCADORES))) {
            return (List<Marcador>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("⚠️  Error al cargar marcadores: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Guarda la lista de marcadores en almacenamiento.
     *
     * @param marcadores lista de marcadores a guardar
     * @return true si se guardaron correctamente
     */
    private static boolean guardarMarcadores(List<Marcador> marcadores) {
        crearDirectorioSiNoExiste();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_MARCADORES))) {
            oos.writeObject(marcadores);
            return true;
        } catch (IOException e) {
            System.err.println("❌ Error al guardar marcadores: " + e.getMessage());
            return false;
        }
    }

    /**
     * Crea el directorio 'datos' si no existe.
     */
    private static void crearDirectorioSiNoExiste() {
        File directorio = new File("datos");
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
    }

    /**
     * Verifica si un archivo PDF existe y puede ser leído.
     *
     * @param rutaArchivo ruta del archivo a verificar
     * @return true si el archivo existe, es un archivo regular y es legible
     */
    public static boolean verificarArchivoPDF(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        return archivo.exists() && archivo.isFile() && archivo.canRead();
    }

    /**
     * Inicializa datos de prueba: libros y préstamos predefinidos.
     * <p>
     * Se crean libros de ejemplo y préstamos asociados a un usuario 'user123'.
     * Esta función sobrescribe los archivos de datos existentes.
     * </p>
     */
    public static void inicializarDatosPrueba() {
        System.out.println("Inicializando datos de prueba...\n");

        List<Libro> libros = new ArrayList<>();
//Yared: Aquí se agregan los libros (id, nombre, autor, formato, ruta de acceso)
// y se podría hacer con manejo de archivos. También se puede tomar como referencia
// la parte de arriba con los archivos .dat pero sería con .txt supongo
        Libro libro1 = new Libro("1", "Manual LybSys", "FISI", "PDF", 
            "src/recursos/libros/codigo.pdf");
        libro1.setCategoria("Documentacion");
        libro1.setIsbn("978-FISI-001");
        libro1.setDescripcion("Manual completo del sistema LybSys FISI");
        libro1.setTotalPaginas(50);
        libros.add(libro1);

        Libro libro2 = new Libro("2", "Java Introduccion", "FISI", "PDF", 
            "src/recursos/libros/java_intro.pdf");
        libro2.setCategoria("Programacion");
        libro2.setIsbn("978-FISI-002");
        libro2.setDescripcion("Guia basica de programacion en Java");
        libro2.setTotalPaginas(120);
        libros.add(libro2);

        guardarLibros(libros);
        System.out.println("  Libros guardados: " + libros.size());

        List<Prestamo> prestamos = new ArrayList<>();

        Prestamo prestamo1 = new Prestamo("user123", "1", "activo");
        prestamo1.setFechaVencimiento(
            LocalDateTime.now().plusDays(14).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        prestamos.add(prestamo1);

        Prestamo prestamo2 = new Prestamo("user123", "2", "activo");
        prestamo2.setFechaVencimiento(
            LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        prestamos.add(prestamo2);

        guardarPrestamos(prestamos);
        System.out.println("  Prestamos creados: " + prestamos.size());

        System.out.println("\nDatos de prueba inicializados correctamente");
    }

    /**
     * Elimina todos los archivos de datos: libros, progresos, préstamos y marcadores.
     * <p>
     * Útil para reiniciar el sistema o limpiar datos de prueba.
     * </p>
     */
    public static void limpiarTodosLosDatos() {
        try {
            new File(ARCHIVO_LIBROS).delete();
            new File(ARCHIVO_PROGRESO).delete();
            new File(ARCHIVO_PRESTAMOS).delete();
            new File(ARCHIVO_MARCADORES).delete();
            System.out.println("✓ Todos los datos han sido limpiados");
        } catch (Exception e) {
            System.err.println("❌ Error al limpiar datos: " + e.getMessage());
        }
    }
}