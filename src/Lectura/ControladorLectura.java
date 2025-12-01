package Lectura;

import Lectura.ModeloLectura.*;
import recursos.LecturaException;
import java.io.File;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controlador principal para el módulo de lectura de libros PDF.
 * <p>
 * Es responsable de coordinar la comunicación entre la Vista
 * ({@link VistaLectura}), el Modelo ({@link ModeloLectura}) y los datos
 * asociados al libro abierto, como su progreso de lectura y marcadores.
 * </p>
 * <p>
 * Además implementa manejo robusto de excepciones mediante el uso de
 * {@link LecturaException} y sus subclases, ofreciendo retroalimentación clara
 * al usuario ante fallos como falta de permisos, archivos inexistentes,
 * formato no soportado o páginas fuera de rango.
 * </p>
 */
public class ControladorLectura {

    /** Vista encargada de mostrar el lector PDF. */
    private VistaLectura vista;

    /** Libro actualmente cargado en el visor. */
    private Libro libroActual;

    /** Progreso de lectura del usuario para el libro actual. */
    private ProgresoLectura progresoActual;

    /** ID del usuario que está realizando la lectura. */
    private String usuarioIdActual;

    /** ID del libro actual que se está leyendo. */
    private String libroIdActual;

    /**
     * Constructor por defecto del controlador.
     * <p>
     * La vista debe ser asociada posteriormente usando {@link #setVista(VistaLectura)}.
     * </p>
     */
    public ControladorLectura() {
    }

    /**
     * Asocia la vista al controlador y vincula el controlador a la vista.
     *
     * @param vista instancia de {@link VistaLectura}
     */
    public void setVista(VistaLectura vista) {
        this.vista = vista;
        vista.setControlador(this);
    }

    /**
     * Abre el lector de libros aplicando múltiples validaciones:
     * <ul>
     *   <li>Existencia del libro</li>
     *   <li>Formato soportado (PDF)</li>
     *   <li>Presencia del archivo físico</li>
     *   <li>Permisos del usuario para leer</li>
     *   <li>Carga del progreso previo</li>
     * </ul>
     * 
     * <p>Ante cualquier problema, se captura la excepción correspondiente
     * y se muestra un mensaje claro al usuario.</p>
     *
     * @param bookId ID del libro a abrir
     * @param userId ID del usuario que solicita la lectura
     */
    public void abrirLectura(String bookId, String userId) {
        this.libroIdActual = bookId;
        this.usuarioIdActual = userId;

        try {
            // Cargar libro
            libroActual = ModeloLectura.obtenerLibroPorId(bookId);

            // Validar formato
            if (!"PDF".equalsIgnoreCase(libroActual.getFormato())) {
                throw new LecturaException.FormatoNoSoportadoException(libroActual.getFormato());
            }

            // Validar archivo físico
            File archivo = new File(libroActual.getRutaArchivo());
            if (!archivo.exists()) {
                throw new LecturaException.ArchivoNoEncontradoException(libroActual.getRutaArchivo());
            }

            // Verificar permisos
            if (!ModeloLectura.puedeLeer(userId, bookId)) {
                throw new LecturaException.AccesoDenegadoException(userId, bookId);
            }

            // Cargar progreso
            int paginaGuardada = ModeloLectura.obtenerProgreso(userId, bookId);
            progresoActual = new ProgresoLectura(
                userId, 
                bookId, 
                paginaGuardada > 0 ? paginaGuardada : 1
            );

            // Abrir la vista
            vista.cargarLibro(bookId, userId, libroActual.getRutaArchivo(), libroActual.getFormato());
            vista.setTitle("Lectura: " + libroActual.getTitulo());
            vista.setVisible(true);

        } catch (LecturaException.LibroNoEncontradoException e) {
            mostrarError("Libro no encontrado", e.getMessage());
        } catch (LecturaException.ArchivoNoEncontradoException e) {
            mostrarError("Archivo no encontrado", e.getMessage());
        } catch (LecturaException.AccesoDenegadoException e) {
            mostrarError("Acceso denegado", e.getMessage());
        } catch (LecturaException.FormatoNoSoportadoException e) {
            mostrarError("Formato no soportado", e.getMessage());
        } catch (LecturaException e) {
            mostrarError("Error de lectura", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarError("Error inesperado", "Error al abrir el libro:\n" + e.getMessage());
        }
    }

    /**
     * Guarda el progreso de lectura del usuario en el modelo.
     * <p>
     * Si ocurre un error controlado (por ejemplo, fallo al escribir en archivo),
     * se registra en consola pero no se interrumpe la lectura.
     * </p>
     *
     * @param paginaActual página donde se encuentra el usuario
     */
    public void guardarProgreso(int paginaActual) {
        if (progresoActual == null) return;

        try {
            progresoActual.setPaginaActual(paginaActual);
            ModeloLectura.guardarProgreso(
                progresoActual.getUsuarioId(),
                progresoActual.getLibroId(),
                paginaActual
            );
        } catch (LecturaException.ErrorGuardadoException e) {
            System.err.println("No se pudo guardar el progreso: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error al guardar progreso: " + e.getMessage());
        }
    }

    /**
     * Agrega un nuevo marcador en la página actual del documento.
     *
     * @param paginaActual página donde se creará el marcador
     * @param descripcion  texto opcional agregado por el usuario
     */
    public void agregarMarcador(int paginaActual, String descripcion) {
        try {
            Marcador marcador = ModeloLectura.agregarMarcador(
                usuarioIdActual,
                libroIdActual,
                paginaActual,
                descripcion
            );

            mostrarMensajeExito("Marcador guardado",
                "Marcador creado en página " + paginaActual);

            vista.actualizarListaMarcadores();

        } catch (LecturaException.ErrorGuardadoException e) {
            mostrarError("Error al guardar marcador", e.getMessage());
        } catch (Exception e) {
            mostrarError("Error", "No se pudo guardar el marcador: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los marcadores del libro actual para el usuario actual.
     *
     * @return lista de marcadores del libro, o una lista vacía si no hay datos válidos
     */
    public List<Marcador> obtenerMarcadores() {
        if (usuarioIdActual == null || libroIdActual == null) {
            return List.of();
        }
        return ModeloLectura.obtenerMarcadores(usuarioIdActual, libroIdActual);
    }

    /**
     * Elimina un marcador específico según su ID.
     * <p>Se solicita confirmación al usuario antes de proceder.</p>
     *
     * @param marcadorId identificador del marcador a eliminar
     */
    public void eliminarMarcador(String marcadorId) {
        try {
            int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de eliminar este marcador?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                ModeloLectura.eliminarMarcador(marcadorId);
                mostrarMensajeExito("Marcador eliminado", "El marcador ha sido eliminado correctamente");
                vista.actualizarListaMarcadores();
            }

        } catch (LecturaException.MarcadorNoEncontradoException e) {
            mostrarError("Marcador no encontrado", e.getMessage());
        } catch (Exception e) {
            mostrarError("Error", "No se pudo eliminar el marcador: " + e.getMessage());
        }
    }

    /**
     * Elimina todos los marcadores asociados al libro actual.
     * <p>Si no existen marcadores, se informa al usuario.</p>
     */
    public void eliminarTodosMarcadores() {
        try {
            int cantidad = ModeloLectura.contarMarcadores(usuarioIdActual, libroIdActual);

            if (cantidad == 0) {
                mostrarInfo("Sin marcadores", "No hay marcadores para eliminar");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Eliminar todos los " + cantidad + " marcadores de este libro?",
                "Confirmar eliminación múltiple",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                int eliminados = ModeloLectura.eliminarTodosMarcadores(usuarioIdActual, libroIdActual);
                mostrarMensajeExito("Marcadores eliminados",
                    eliminados + " marcador(es) eliminado(s) correctamente");
                vista.actualizarListaMarcadores();
            }

        } catch (Exception e) {
            mostrarError("Error", "No se pudieron eliminar los marcadores: " + e.getMessage());
        }
    }

    /**
     * Navega a la página correspondiente a un marcador.
     *
     * @param marcador instancia del marcador seleccionado
     */
    public void irAMarcador(Marcador marcador) {
        if (marcador != null) {
            vista.irAPagina(marcador.getPagina());
        }
    }

    /**
     * Valida que una página solicitada esté dentro del rango permitido.
     *
     * @param pagina página solicitada por el usuario
     * @throws LecturaException.PaginaInvalidaException si la página está fuera de rango
     */
    public void validarPagina(int pagina) throws LecturaException.PaginaInvalidaException {
        if (libroActual != null) {
            int totalPaginas = libroActual.getTotalPaginas();
            if (pagina < 1 || pagina > totalPaginas) {
                throw new LecturaException.PaginaInvalidaException(pagina, totalPaginas);
            }
        }
    }

    /** @return libro actualmente abierto */
    public Libro getLibroActual() {
        return libroActual;
    }

    /** @return progreso de lectura del usuario */
    public ProgresoLectura getProgresoActual() {
        return progresoActual;
    }

    /** @return ID del usuario actual */
    public String getUsuarioIdActual() {
        return usuarioIdActual;
    }

    /** @return ID del libro actual */
    public String getLibroIdActual() {
        return libroIdActual;
    }

    /**
     * Cierra el visor guardando el progreso final del usuario.
     */
    public void cerrarVisor() {
        if (progresoActual != null) {
            guardarProgreso(progresoActual.getPaginaActual());
        }
    }

    // ───────────────────────────────────────────────────────────────
    // MÉTODOS PRIVADOS PARA INTERFAZ DE USUARIO
    // ───────────────────────────────────────────────────────────────

    /**
     * Muestra un cuadro de diálogo de error.
     *
     * @param titulo   título de la ventana
     * @param mensaje  mensaje a mostrar
     */
    private void mostrarError(String titulo, String mensaje) {
        if (vista != null) {
            JOptionPane.showMessageDialog(
                vista, mensaje, titulo, JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Muestra un mensaje de éxito en la interfaz.
     *
     * @param titulo   título de la ventana
     * @param mensaje  mensaje informativo
     */
    private void mostrarMensajeExito(String titulo, String mensaje) {
        if (vista != null) {
            JOptionPane.showMessageDialog(
                vista, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Muestra un mensaje informativo en la interfaz.
     *
     * @param titulo   título de la ventana
     * @param mensaje  mensaje informativo
     */
    private void mostrarInfo(String titulo, String mensaje) {
        if (vista != null) {
            JOptionPane.showMessageDialog(
                vista, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
