package recursos;

/**
 * Jerarquía de excepciones personalizadas utilizadas en el módulo de Lectura.
 * <p>
 * Esta clase define una serie de excepciones específicas que permiten manejar
 * de forma clara y estructurada los distintos tipos de errores que pueden ocurrir
 * al cargar libros, validar permisos, navegar entre páginas o interactuar con
 * marcadores.
 * </p>
 * <p>
 * Todas las excepciones heredan de {@link LecturaException} para mantener una
 * estructura común y permitir un manejo unificado desde las capas superiores.
 * </p>
 */
public class LecturaException extends Exception {

    /**
     * Crea una excepción de lectura con un mensaje descriptivo.
     *
     * @param mensaje descripción del error ocurrido
     */
    public LecturaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Crea una excepción de lectura con un mensaje y causa original.
     *
     * @param mensaje descripción del error
     * @param causa   excepción original que provocó el error
     */
    public LecturaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    // ============================================
    // EXCEPCIONES ESPECÍFICAS
    // ============================================

    /**
     * Excepción lanzada cuando se solicita un libro que no existe en el sistema.
     */
    public static class LibroNoEncontradoException extends LecturaException {

        private final String libroId;

        /**
         * Crea la excepción indicando el ID del libro inexistente.
         *
         * @param libroId identificador del libro que no se encontró
         */
        public LibroNoEncontradoException(String libroId) {
            super("No se encontró el libro con ID: " + libroId);
            this.libroId = libroId;
        }

        /**
         * Obtiene el ID del libro solicitado.
         *
         * @return identificador del libro
         */
        public String getLibroId() {
            return libroId;
        }
    }

    /**
     * Excepción lanzada cuando el archivo físico del PDF no se encuentra
     * en la ruta esperada.
     */
    public static class ArchivoNoEncontradoException extends LecturaException {

        private final String rutaArchivo;

        /**
         * Crea la excepción especificando la ruta que no existe.
         *
         * @param rutaArchivo ruta donde se esperaba encontrar el archivo PDF
         */
        public ArchivoNoEncontradoException(String rutaArchivo) {
            super("El archivo del libro no existe: " + rutaArchivo +
                    "\n\nAsegúrate de que el PDF esté en la carpeta correcta.");
            this.rutaArchivo = rutaArchivo;
        }

        /**
         * Obtiene la ruta donde se buscó el archivo.
         *
         * @return ruta del archivo PDF
         */
        public String getRutaArchivo() {
            return rutaArchivo;
        }
    }

    /**
     * Excepción lanzada cuando el usuario intenta acceder a un libro
     * sin contar con un préstamo activo.
     */
    public static class AccesoDenegadoException extends LecturaException {

        private final String usuarioId;
        private final String libroId;

        /**
         * Crea la excepción indicando el usuario y libro con acceso denegado.
         *
         * @param usuarioId ID del usuario que intenta acceder
         * @param libroId   ID del libro al que intenta acceder
         */
        public AccesoDenegadoException(String usuarioId, String libroId) {
            super("No tienes un préstamo activo de este libro.\n" +
                    "Solicita un préstamo primero para poder leerlo.");
            this.usuarioId = usuarioId;
            this.libroId = libroId;
        }

        /**
         * Obtiene el ID del usuario.
         *
         * @return identificador del usuario
         */
        public String getUsuarioId() {
            return usuarioId;
        }

        /**
         * Obtiene el ID del libro.
         *
         * @return identificador del libro
         */
        public String getLibroId() {
            return libroId;
        }
    }

    /**
     * Excepción lanzada cuando ocurre un error al renderizar una página del PDF.
     */
    public static class ErrorRenderizadoException extends LecturaException {

        private final int numeroPagina;

        /**
         * Crea la excepción indicando la página que falló durante el renderizado.
         *
         * @param numeroPagina número de página que no pudo renderizarse
         * @param causa        excepción original asociada al error
         */
        public ErrorRenderizadoException(int numeroPagina, Throwable causa) {
            super("Error al renderizar la página " + numeroPagina, causa);
            this.numeroPagina = numeroPagina;
        }

        /**
         * Obtiene el número de página que causó el fallo.
         *
         * @return página con error
         */
        public int getNumeroPagina() {
            return numeroPagina;
        }
    }

    /**
     * Excepción lanzada cuando ocurre un error al guardar progreso o marcadores.
     */
    public static class ErrorGuardadoException extends LecturaException {

        private final String tipoGuardado;

        /**
         * Crea la excepción especificando el tipo de dato que no pudo guardarse.
         *
         * @param tipoGuardado tipo de guardado fallido (ej. "progreso", "marcador")
         * @param causa        excepción original que provocó el error
         */
        public ErrorGuardadoException(String tipoGuardado, Throwable causa) {
            super("Error al guardar " + tipoGuardado, causa);
            this.tipoGuardado = tipoGuardado;
        }

        /**
         * Obtiene el tipo de guardado que falló.
         *
         * @return tipo de operación de guardado
         */
        public String getTipoGuardado() {
            return tipoGuardado;
        }
    }

    /**
     * Excepción lanzada cuando el número de página solicitado está fuera de rango.
     */
    public static class PaginaInvalidaException extends LecturaException {

        private final int paginaSolicitada;
        private final int totalPaginas;

        /**
         * Crea la excepción indicando la página inválida y los límites existentes.
         *
         * @param paginaSolicitada página solicitada por el usuario
         * @param totalPaginas     cantidad total de páginas del documento
         */
        public PaginaInvalidaException(int paginaSolicitada, int totalPaginas) {
            super("Página " + paginaSolicitada + " fuera de rango.\n" +
                    "Debe estar entre 1 y " + totalPaginas);
            this.paginaSolicitada = paginaSolicitada;
            this.totalPaginas = totalPaginas;
        }

        /**
         * Obtiene la página solicitada por el usuario.
         *
         * @return número de página inválido
         */
        public int getPaginaSolicitada() {
            return paginaSolicitada;
        }

        /**
         * Obtiene la cantidad total de páginas disponibles.
         *
         * @return total de páginas del libro
         */
        public int getTotalPaginas() {
            return totalPaginas;
        }
    }

    /**
     * Excepción lanzada cuando ocurre un error al cargar datos desde los archivos
     * de almacenamiento del sistema.
     */
    public static class ErrorCargaDatosException extends LecturaException {

        private final String tipoArchivo;

        /**
         * Crea la excepción indicando qué tipo de archivo causó el error.
         *
         * @param tipoArchivo tipo de archivo cargado (ej. "JSON", "CSV")
         * @param causa       excepción original
         */
        public ErrorCargaDatosException(String tipoArchivo, Throwable causa) {
            super("Error al cargar datos de " + tipoArchivo, causa);
            this.tipoArchivo = tipoArchivo;
        }

        /**
         * Obtiene el tipo de archivo que causó el error.
         *
         * @return tipo de archivo
         */
        public String getTipoArchivo() {
            return tipoArchivo;
        }
    }

    /**
     * Excepción lanzada cuando el usuario intenta abrir un libro con
     * un formato no soportado por el visor.
     */
    public static class FormatoNoSoportadoException extends LecturaException {

        private final String formato;

        /**
         * Crea la excepción indicando el formato problemático.
         *
         * @param formato extensión o tipo de archivo no soportado
         */
        public FormatoNoSoportadoException(String formato) {
            super("El formato '" + formato + "' no es soportado.\n" +
                    "Formatos soportados: PDF");
            this.formato = formato;
        }

        /**
         * Obtiene el formato que causó la excepción.
         *
         * @return formato no soportado
         */
        public String getFormato() {
            return formato;
        }
    }

    /**
     * Excepción lanzada cuando se intenta acceder a marcadores inexistentes.
     */
    public static class MarcadorNoEncontradoException extends LecturaException {

        /**
         * Crea la excepción indicando que no existen marcadores asociados.
         */
        public MarcadorNoEncontradoException() {
            super("No hay marcadores guardados para este libro");
        }
    }
}