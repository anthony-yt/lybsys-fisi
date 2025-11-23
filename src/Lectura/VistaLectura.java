package Lectura;

import recursos.LecturaException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

/**
 * Vista principal del módulo de lectura de libros digitales.
 *
 * <p>Implementa el componente "Vista" del patrón MVC, proporcionando un visor
 * de documentos PDF, barra de navegación, marcadores, lista de libros
 * disponibles y funciones de zoom y modo noche.</p>
 *
 * <p>Esta vista delega toda la lógica en {@link ControladorLectura}, limitándose
 * únicamente a gestionar el renderizado, los eventos de interfaz y la
 * actualización visual.</p>
 */
public class VistaLectura extends javax.swing.JFrame {
    /** Logger para registrar eventos, errores y acciones de la vista. */
    private static final Logger logger = Logger.getLogger(VistaLectura.class.getName());
    
    /** Controlador asociado encargado de manejar la lógica del módulo de lectura. */
    private ControladorLectura controlador;
    
    /** Documento PDF actualmente cargado en memoria. */
    private PDDocument documento;
    
    /** Renderizador de páginas PDF utilizado para mostrar el contenido. */
    private PDFRenderer renderer;
    
    /** Número de la página actualmente visible. Comienza en 1. */
    private int paginaActual = 1;

    /** Cantidad total de páginas del documento PDF. */
    private int totalPaginas = 1;

    /** Identificador del libro actual. */
    private String bookId;

    /** Identificador del usuario lector. */
    private String userId;

    /** Nivel de zoom aplicado al renderizado del PDF. */
    private float zoom = 1.0f;

    /** Indica si el modo noche está activado. */
    private boolean modoNoche = false;

    /** Imagen renderizada almacenada para evitar renderizados repetitivos. */
    private BufferedImage paginaEnCache;

    /** Página actualmente cacheada. */
    private int paginaCacheada = -1;

    /** Zoom aplicado a la página cacheada. */
    private float zoomCacheado = -1;

    /** Modelo que administra las filas de la tabla de marcadores. */
    private DefaultTableModel modeloMarcadores;

    /** Modelo que administra la lista de libros disponibles. */
    private DefaultListModel<String> modeloLibros;
    
    /**
    * Constructor principal de la vista de lectura.
    *
    * <p>Configura los paneles principales, inicializa los modelos de listas,
    * la tabla de marcadores, los listener de eventos, y establece los tamaños
    * y divisiones iniciales de la interfaz.</p>
    */
    public VistaLectura() {
        initComponents();
        setLocationRelativeTo(null);
        setSize(1400, 850);

        // Configurar split panes
        splitPrincipal.setDividerLocation(250);
        splitDerecho.setDividerLocation(900);

        // Configurar tabla de marcadores
        configurarTablaMarcadores();

        // Configurar lista de libros
        configurarListaLibros();
    }
    
    /**
     * Configura el modelo, columnas, selección y eventos de la tabla
     * que contiene los marcadores creados por el usuario.
     */
    private void configurarTablaMarcadores() {
        String[] columnas = {"Pagina", "Descripcion", "Fecha"};
        modeloMarcadores = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaMarcadores.setModel(modeloMarcadores);
        tablaMarcadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tablaMarcadores.getColumnModel().getColumn(0).setPreferredWidth(60);
        tablaMarcadores.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaMarcadores.getColumnModel().getColumn(2).setPreferredWidth(120);

        tablaMarcadores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    irAMarcadorSeleccionado();
                }
            }
        });
    }

    /**
    * Configura el listado lateral de libros disponibles para el usuario.
    * Incluye modelo, modo de selección y listener para detectar cambios de libro.
    */
    private void configurarListaLibros() {
        modeloLibros = new DefaultListModel<>();
        listaLibros.setModel(modeloLibros);
        listaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Listener para cambiar de libro
        listaLibros.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cambiarLibroSeleccionado();
            }
        });
    }

    /**
     * Carga en la lista los libros que el usuario tiene permiso para leer.
     *
     * @param userId identificador del usuario que inició sesión
     */
    public void cargarLibrosDisponibles(String userId) {
        modeloLibros.clear();
        List<Lectura.ModeloLectura.Libro> libros = Lectura.ModeloLectura.cargarLibros();

        for (Lectura.ModeloLectura.Libro libro : libros) {
            if (Lectura.ModeloLectura.puedeLeer(userId, libro.getId())) {
                modeloLibros.addElement(libro.getId() + " - " + libro.getTitulo());
            }
        }

        if (modeloLibros.isEmpty()) {
            modeloLibros.addElement("(Sin libros disponibles)");
        }
    }

    /**
     * Cambia el libro mostrado en el visor cuando el usuario selecciona
     * un nuevo elemento en la lista de libros.
     *
     * <p>Si hay progreso guardado en el libro anterior, se pregunta si
     * se desea cambiar de libro.</p>
     */
    private void cambiarLibroSeleccionado() {
        String seleccion = listaLibros.getSelectedValue();
        if (seleccion == null || seleccion.contains("Sin libros")) {
            return;
        }

        // Extraer ID del libro
        String libroIdNuevo = seleccion.split(" - ")[0];

        // Si es el mismo libro, no hacer nada
        if (libroIdNuevo.equals(bookId)) {
            return;
        }

        // Confirmar cambio si hay progreso
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Cambiar a otro libro? El progreso actual se guardara.",
                "Cambiar libro",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Cerrar documento actual
            cerrarDocumentoActual();

            // Abrir nuevo libro
            controlador.abrirLectura(libroIdNuevo, userId);
        } else {
            // Restaurar selección anterior
            for (int i = 0; i < modeloLibros.size(); i++) {
                if (modeloLibros.get(i).startsWith(bookId + " -")) {
                    listaLibros.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    /**
     * Cierra de forma segura el documento PDF actualmente abierto,
     * liberando recursos y eliminando la caché visual.
     */
    private void cerrarDocumentoActual() {
        try {
            if (documento != null) {
                documento.close();
                documento = null;
            }
            paginaEnCache = null;
            labelImagen.setIcon(null);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Error al cerrar documento", e);
        }
    }

    /**
    * Establece el controlador responsable de la lógica de lectura.
    *
    * @param controlador instancia del controlador de lectura
    */
    public void setControlador(ControladorLectura controlador) {
        this.controlador = controlador;
    }

    /**
     * Carga un libro PDF desde ruta local, configurando las variables internas,
     * restaurando progreso, seleccionándolo en la lista y mostrando los marcadores.
     *
     * @param bookId identificador del libro
     * @param userId identificador del usuario
     * @param rutaArchivo ruta absoluta del PDF
     * @param formato formato del archivo (actualmente solo PDF)
     */
    public void cargarLibro(String bookId, String userId, String rutaArchivo, String formato) {
        this.bookId = bookId;
        this.userId = userId;

        try {
            File archivoPDF = new File(rutaArchivo);
            documento = Loader.loadPDF(archivoPDF);
            renderer = new PDFRenderer(documento);
            totalPaginas = documento.getNumberOfPages();

            int paginaGuardada = Lectura.ModeloLectura.obtenerProgreso(userId, bookId);

            if (paginaGuardada > 0) {
                paginaActual = paginaGuardada;
                mostrarMensajeEstado("Reanudando desde pagina " + paginaGuardada);
            } else {
                paginaActual = 1;
                mostrarMensajeEstado("Iniciando lectura del libro");
            }

            // Cargar libros disponibles
            cargarLibrosDisponibles(userId);

            // Seleccionar libro actual en la lista
            for (int i = 0; i < modeloLibros.size(); i++) {
                if (modeloLibros.get(i).startsWith(bookId + " -")) {
                    listaLibros.setSelectedIndex(i);
                    break;
                }
            }

            // Cargar marcadores
            actualizarListaMarcadores();
            actualizarContadorMarcadores();

            actualizarPagina();
            setLocationRelativeTo(null);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar libro", e);
            JOptionPane.showMessageDialog(this,
                    "Error al abrir PDF:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error inesperado al cargar libro", e);
            JOptionPane.showMessageDialog(this,
                    "Error inesperado: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
    * Actualiza la tabla de marcadores tomando los datos desde el controlador,
    * y habilita/deshabilita los botones según corresponda.
    */
    public void actualizarListaMarcadores() {
        modeloMarcadores.setRowCount(0);

        List<Lectura.ModeloLectura.Marcador> marcadores = controlador.obtenerMarcadores();

        for (Lectura.ModeloLectura.Marcador marcador : marcadores) {
            Object[] fila = {
                    marcador.getPagina(),
                    marcador.getDescripcion(),
                    marcador.getFechaCreacion()
            };
            modeloMarcadores.addRow(fila);
        }

        actualizarContadorMarcadores();

        boolean hayMarcadores = marcadores.size() > 0;
        btnEliminarMarcador.setEnabled(hayMarcadores);
        btnEliminarTodos.setEnabled(hayMarcadores);
    }

    /**
    * Muestra la cantidad actual de marcadores en la etiqueta correspondiente.
    */
    private void actualizarContadorMarcadores() {
        int cantidad = modeloMarcadores.getRowCount();
        labelContadorMarcadores.setText("Marcadores: " + cantidad);
    }

    /**
    * Redirige al usuario a la página asociada al marcador seleccionado
    * en la tabla de marcadores.
    */
    private void irAMarcadorSeleccionado() {
        int filaSeleccionada = tablaMarcadores.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int pagina = (int) modeloMarcadores.getValueAt(filaSeleccionada, 0);
            irAPagina(pagina);
        }
    }

    /**
    * Cambia a la página especificada, validando previamente su rango.
    *
    * @param pagina número de página al que se desea ir
    */
    public void irAPagina(int pagina) {
        try {
            controlador.validarPagina(pagina);
            paginaActual = pagina;
            actualizarPagina();
            mostrarMensajeEstado("Navegando a pagina " + pagina);
        } catch (LecturaException.PaginaInvalidaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Pagina invalida",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
    * Renderiza la página actual del PDF, aplicando zoom, modo noche
    * y utilizando caché cuando sea posible. También actualiza progreso,
    * barras e información visual.
    */
    private void actualizarPagina() {
        try {
            if (paginaCacheada == paginaActual && zoomCacheado == zoom && paginaEnCache != null && !modoNoche) {
                labelImagen.setIcon(new ImageIcon(paginaEnCache));
            } else {
                BufferedImage imagen = renderer.renderImage(paginaActual - 1, zoom);

                if (modoNoche) {
                    imagen = aplicarModoNoche(imagen);
                } else {
                    paginaEnCache = imagen;
                    paginaCacheada = paginaActual;
                    zoomCacheado = zoom;
                }

                labelImagen.setIcon(new ImageIcon(imagen));
            }

            labelPagina.setText("Pagina " + paginaActual + " / " + totalPaginas);

            int porcentaje = (int) ((paginaActual * 100.0) / totalPaginas);
            progressBar.setValue(porcentaje);
            progressBar.setString(porcentaje + "%");

            btnAnterior.setEnabled(paginaActual > 1);
            btnSiguiente.setEnabled(paginaActual < totalPaginas);

            controlador.guardarProgreso(paginaActual);

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al renderizar pagina", e);
            JOptionPane.showMessageDialog(this,
                    "Error al renderizar pagina:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error inesperado al renderizar", e);
        }
    }

    /**
    * Aplica inversión de colores a una imagen para simular el modo noche.
    *
    * @param src imagen original
    * @return imagen transformada
    */
    private BufferedImage aplicarModoNoche(BufferedImage src) {
        BufferedImage out = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int rgb = src.getRGB(x, y);
                int r = 255 - ((rgb >> 16) & 0xFF);
                int g = 255 - ((rgb >> 8) & 0xFF);
                int b = 255 - (rgb & 0xFF);
                int nuevo = (0xFF << 24) | (r << 16) | (g << 8) | b;
                out.setRGB(x, y, nuevo);
            }
        }

        return out;
    }

    /**
    * Muestra un mensaje temporal en la barra inferior del visor.
    *
    * @param mensaje texto a mostrar
    */
    private void mostrarMensajeEstado(String mensaje) {
        labelEstado.setText(mensaje);
        Timer timer = new Timer(3000, e -> labelEstado.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }

    /**
    * Libera recursos, cierra el documento y notifica al controlador antes
    * de cerrar la ventana de lectura.
    */
    @Override
    public void dispose() {
        cerrarDocumentoActual();

        if (controlador != null) {
            controlador.cerrarVisor();
        }

        super.dispose();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelSuperior = new javax.swing.JPanel();
        btnAnterior = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnZoomMas = new javax.swing.JButton();
        btnZoomMenos = new javax.swing.JButton();
        btnModoNoche = new javax.swing.JButton();
        splitPrincipal = new javax.swing.JSplitPane();
        panelLibros = new javax.swing.JPanel();
        labelTituloLibros = new javax.swing.JLabel();
        scrollLibros = new javax.swing.JScrollPane();
        listaLibros = new javax.swing.JList<>();
        splitDerecho = new javax.swing.JSplitPane();
        panelContenido = new javax.swing.JPanel();
        scrollImagen = new javax.swing.JScrollPane();
        labelImagen = new javax.swing.JLabel();
        panelMarcadores = new javax.swing.JPanel();
        labelTituloMarcadores = new javax.swing.JLabel();
        scrollMarcadores = new javax.swing.JScrollPane();
        tablaMarcadores = new javax.swing.JTable();
        panelBotonesMarcadores = new javax.swing.JPanel();
        btnAgregarMarcador = new javax.swing.JButton();
        btnEliminarMarcador = new javax.swing.JButton();
        btnEliminarTodos = new javax.swing.JButton();
        labelContadorMarcadores = new javax.swing.JLabel();
        panelInferior = new javax.swing.JPanel();
        labelPagina = new javax.swing.JLabel();
        txtIrPagina = new javax.swing.JTextField();
        btnIrPagina = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        labelEstado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Visor de Lectura");

        panelSuperior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        btnAnterior.setText("< Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        panelSuperior.add(btnAnterior);

        btnSiguiente.setText("Siguiente >");
        btnSiguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguienteActionPerformed(evt);
            }
        });
        panelSuperior.add(btnSiguiente);

        btnZoomMas.setText("Zoom +");
        btnZoomMas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomMasActionPerformed(evt);
            }
        });
        panelSuperior.add(btnZoomMas);

        btnZoomMenos.setText("Zoom -");
        btnZoomMenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnZoomMenosActionPerformed(evt);
            }
        });
        panelSuperior.add(btnZoomMenos);

        btnModoNoche.setText("Modo Noche");
        btnModoNoche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModoNocheActionPerformed(evt);
            }
        });
        panelSuperior.add(btnModoNoche);

        getContentPane().add(panelSuperior, java.awt.BorderLayout.PAGE_START);

        splitPrincipal.setDividerLocation(250);

        panelLibros.setLayout(new java.awt.BorderLayout());

        labelTituloLibros.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTituloLibros.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTituloLibros.setText("MIS LIBROS");
        labelTituloLibros.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 5));
        panelLibros.add(labelTituloLibros, java.awt.BorderLayout.PAGE_START);

        scrollLibros.setViewportView(listaLibros);

        panelLibros.add(scrollLibros, java.awt.BorderLayout.CENTER);

        splitPrincipal.setLeftComponent(panelLibros);

        splitDerecho.setDividerLocation(900);

        panelContenido.setLayout(new java.awt.BorderLayout());

        labelImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        scrollImagen.setViewportView(labelImagen);

        panelContenido.add(scrollImagen, java.awt.BorderLayout.CENTER);

        splitDerecho.setLeftComponent(panelContenido);

        panelMarcadores.setLayout(new java.awt.BorderLayout());

        labelTituloMarcadores.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        labelTituloMarcadores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelTituloMarcadores.setText("MARCADORES");
        labelTituloMarcadores.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 5));
        panelMarcadores.add(labelTituloMarcadores, java.awt.BorderLayout.PAGE_START);

        tablaMarcadores.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Pag.", "Descripcion", "Fecha"
                }
        ) {
            boolean[] canEdit = new boolean [] {
                    false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollMarcadores.setViewportView(tablaMarcadores);

        panelMarcadores.add(scrollMarcadores, java.awt.BorderLayout.CENTER);

        panelBotonesMarcadores.setLayout(new java.awt.GridLayout(4, 1, 5, 5));

        btnAgregarMarcador.setText("+ Agregar");
        btnAgregarMarcador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarMarcadorActionPerformed(evt);
            }
        });
        panelBotonesMarcadores.add(btnAgregarMarcador);

        btnEliminarMarcador.setText("Eliminar");
        btnEliminarMarcador.setEnabled(false);
        btnEliminarMarcador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarMarcadorActionPerformed(evt);
            }
        });
        panelBotonesMarcadores.add(btnEliminarMarcador);

        btnEliminarTodos.setText("Eliminar Todos");
        btnEliminarTodos.setEnabled(false);
        btnEliminarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTodosActionPerformed(evt);
            }
        });
        panelBotonesMarcadores.add(btnEliminarTodos);

        labelContadorMarcadores.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelContadorMarcadores.setText("Marcadores: 0");
        panelBotonesMarcadores.add(labelContadorMarcadores);

        panelMarcadores.add(panelBotonesMarcadores, java.awt.BorderLayout.PAGE_END);

        splitDerecho.setRightComponent(panelMarcadores);

        splitPrincipal.setRightComponent(splitDerecho);

        getContentPane().add(splitPrincipal, java.awt.BorderLayout.CENTER);

        panelInferior.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        labelPagina.setText("Pagina 1 / 1");
        panelInferior.add(labelPagina);

        txtIrPagina.setColumns(5);
        panelInferior.add(txtIrPagina);

        btnIrPagina.setText("Ir");
        btnIrPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIrPaginaActionPerformed(evt);
            }
        });
        panelInferior.add(btnIrPagina);

        progressBar.setStringPainted(true);
        panelInferior.add(progressBar);

        labelEstado.setText(" ");
        panelInferior.add(labelEstado);

        getContentPane().add(panelInferior, java.awt.BorderLayout.PAGE_END);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Método principal que inicia la vista configurando el Look and Feel
     * y mostrando la ventana del visor de lectura.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new VistaLectura().setVisible(true));
    }


    /**
     * Acción del botón "Anterior".
     * <p>Retrocede una página en el documento si no se encuentra en la primera.
     * Actualiza el renderizado del visor.</p>
     *
     * @param evt evento de clic generado por el botón
     */
    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {
        if (paginaActual > 1) {
            paginaActual--;
            actualizarPagina();
        }
    }

    /**
    * Acción del botón "Siguiente".
    * <p>Avanza a la página siguiente del documento si no se encuentra en la última.
    * Refresca la vista después del cambio.</p>
    *
    * @param evt evento de clic generado por el botón
    */
    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {
        if (paginaActual < totalPaginas) {
            paginaActual++;
            actualizarPagina();
        }
    }

    /**
    * Acción del botón "Zoom +".
    * <p>Aumenta el nivel de zoom del PDF hasta un máximo permitido.
    * Limpia la caché del renderizado y actualiza la vista.</p>
    *
    * @param evt evento de clic generado por el botón
    */
    private void btnZoomMasActionPerformed(java.awt.event.ActionEvent evt) {
        if (zoom < 3.0f) {
            zoom += 0.25f;
            paginaEnCache = null;
            actualizarPagina();
            mostrarMensajeEstado("Zoom: " + (int)(zoom * 100) + "%");
        }
    }

    /**
    * Acción del botón "Zoom -".
    * <p>Reduce el nivel de zoom del PDF hasta un mínimo permitido.
    * Borra la caché del renderizado y vuelve a dibujar la página.</p>
    *
    * @param evt evento de clic generado por el botón
    */
    private void btnZoomMenosActionPerformed(java.awt.event.ActionEvent evt) {
        if (zoom > 0.5f) {
            zoom -= 0.25f;
            paginaEnCache = null;
            actualizarPagina();
            mostrarMensajeEstado("Zoom: " + (int)(zoom * 100) + "%");
        }
    }

    /**
     * Acción del botón "Modo Noche".
     * <p>Activa o desactiva el modo noche invirtiendo colores del PDF
     * para facilitar la lectura en ambientes oscuros. Vuelve a renderizar
     * la página con el modo aplicado.</p>
     *
     * @param evt evento de clic generado por el botón
     */
    private void btnModoNocheActionPerformed(java.awt.event.ActionEvent evt) {
        modoNoche = !modoNoche;
        btnModoNoche.setText(modoNoche ? "Modo Dia" : "Modo Noche");
        paginaEnCache = null;
        actualizarPagina();
        mostrarMensajeEstado(modoNoche ? "Modo noche activado" : "Modo dia activado");
    }

    /**
     * Acción del botón "Ir".
     * <p>Permite al usuario navegar directamente a una página ingresada
     * en el campo de texto. Valida el número antes de cambiar de página.
     * Muestra mensajes de error si el número es inválido.</p>
     *
     * @param evt evento de clic generado por el botón
     */
    private void btnIrPaginaActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String texto = txtIrPagina.getText().trim();
            if (texto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese un numero de pagina",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int pagina = Integer.parseInt(texto);
            irAPagina(pagina);
            txtIrPagina.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un numero valido",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
    * Acción del botón "Agregar Marcador".
    * <p>Crea un nuevo marcador para la página actual, solicitando una
    * descripción opcional al usuario. Envía los datos al controlador.</p>
    *
    * @param evt evento de clic generado por el botón
    */
    private void btnAgregarMarcadorActionPerformed(java.awt.event.ActionEvent evt) {
        String descripcion = JOptionPane.showInputDialog(
                this,
                "Ingrese una descripcion para el marcador (opcional):",
                "Nuevo Marcador",
                JOptionPane.QUESTION_MESSAGE
        );

        if (descripcion != null) {
            controlador.agregarMarcador(paginaActual, descripcion);
        }
    }

    /**
     * Acción del botón "Eliminar Marcador".
     * <p>Elimina el marcador seleccionado en la tabla.
     * Si no hay selección, muestra una advertencia al usuario.</p>
     *
     * @param evt evento de clic generado por el botón
     */
    private void btnEliminarMarcadorActionPerformed(java.awt.event.ActionEvent evt) {
        int filaSeleccionada = tablaMarcadores.getSelectedRow();

        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un marcador para eliminar",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Lectura.ModeloLectura.Marcador> marcadores = controlador.obtenerMarcadores();
        if (filaSeleccionada < marcadores.size()) {
            Lectura.ModeloLectura.Marcador marcador = marcadores.get(filaSeleccionada);
            controlador.eliminarMarcador(marcador.getId());
        }
    }

    /**
    * Acción del botón "Eliminar Todos".
    * <p>Elimina todos los marcadores del libro actual mediante el controlador.</p>
    *
    * @param evt evento de clic generado por el botón
    */
    private void btnEliminarTodosActionPerformed(java.awt.event.ActionEvent evt) {
        controlador.eliminarTodosMarcadores();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgregarMarcador;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnEliminarMarcador;
    private javax.swing.JButton btnEliminarTodos;
    private javax.swing.JButton btnIrPagina;
    private javax.swing.JButton btnModoNoche;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnZoomMas;
    private javax.swing.JButton btnZoomMenos;
    private javax.swing.JLabel labelContadorMarcadores;
    private javax.swing.JLabel labelEstado;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelPagina;
    private javax.swing.JLabel labelTituloLibros;
    private javax.swing.JLabel labelTituloMarcadores;
    private javax.swing.JList<String> listaLibros;
    private javax.swing.JPanel panelBotonesMarcadores;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelInferior;
    private javax.swing.JPanel panelLibros;
    private javax.swing.JPanel panelMarcadores;
    private javax.swing.JPanel panelSuperior;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JScrollPane scrollImagen;
    private javax.swing.JScrollPane scrollLibros;
    private javax.swing.JScrollPane scrollMarcadores;
    private javax.swing.JSplitPane splitDerecho;
    private javax.swing.JSplitPane splitPrincipal;
    private javax.swing.JTable tablaMarcadores;
    private javax.swing.JTextField txtIrPagina;
    // End of variables declaration//GEN-END:variables
}