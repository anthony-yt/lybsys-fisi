package Catalogo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

/**
 * Vista del catálogo con diseño moderno y conexión a la nueva vista de detalle (VerDetalleLibro).
 * Requiere ControladorCatalogo.
 */
public class vistaCatalogo extends JPanel {

    private JTextField txtTitulo;
    private JComboBox<String> cbCategoria;
    private JCheckBox chkDisponibles;
    private JButton btnBuscar;
    private JPanel panelResultados;

    private ControladorCatalogo controladorCatalogo;

    // Colores y fuentes consistentes
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 12);
    private final Color COLOR_FONDO = new Color(250, 250, 250);
    private final Color COLOR_AZUL = new Color(0, 122, 255);

    public vistaCatalogo() {
        controladorCatalogo = new ControladorCatalogo(); 
        initComponents();
        // Cargar todos los libros al inicio
        mostrarLibros(controladorCatalogo.getTodosLosLibros());
    }

    private void initComponents() {
        setBackground(COLOR_FONDO);
        setLayout(new BorderLayout(0, 0));

        // --- PANEL DE FILTROS (SUPERIOR) ---
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)));

        txtTitulo = new JTextField(20);
        
        cbCategoria = new JComboBox<>(new String[]{
            "Todas las categorías", "Programación", "Bases de Datos", "Inteligencia Artificial", "Redes", "Algoritmos", "Sistemas"
        });
        cbCategoria.setBackground(Color.WHITE);

        chkDisponibles = new JCheckBox("Solo disponibles");
        chkDisponibles.setBackground(Color.WHITE);
        chkDisponibles.setFocusPainted(false);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setBackground(COLOR_AZUL);
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblBuscar = new JLabel("🔍");
        lblBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        panelFiltros.add(lblBuscar);
        panelFiltros.add(txtTitulo);
        panelFiltros.add(cbCategoria);
        panelFiltros.add(chkDisponibles);
        panelFiltros.add(btnBuscar);

        add(panelFiltros, BorderLayout.NORTH);

        // --- PANEL DE RESULTADOS (CENTRO) ---
        panelResultados = new JPanel();
        panelResultados.setBackground(COLOR_FONDO);
        // GridLayout: 0 filas (infinitas), 2 columnas, espacio H, espacio V
        panelResultados.setLayout(new GridLayout(0, 2, 15, 15)); 
        panelResultados.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scroll = new JScrollPane(panelResultados);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(null);

        add(scroll, BorderLayout.CENTER);

        // Eventos
        btnBuscar.addActionListener(e -> buscarLibros());
        txtTitulo.addActionListener(e -> buscarLibros());
    }

    public void crearBusqueda(JTextField t) {
        txtTitulo.setText(t.getText().trim());
        buscarLibros();
    }

    private void buscarLibros() {
        String tituloFiltro = txtTitulo.getText();
        String categoriaSel = (String) cbCategoria.getSelectedItem();
        
        // Ajuste: si selecciona "Todas las categorías", envía "Todos" al controlador
        String categoriaFiltro = "Todas las categorías".equals(categoriaSel) ? "Todos" : categoriaSel;
        
        boolean soloDisponibles = chkDisponibles.isSelected();

        List<Libro> resultados = controladorCatalogo.buscar(
                tituloFiltro,
                categoriaFiltro,
                soloDisponibles,
                1 // Siempre vuelve a la página 1 en una búsqueda nueva
        );

        mostrarLibros(resultados);
    }

    private void mostrarLibros(List<Libro> lista) {
        panelResultados.removeAll();

        if (lista.isEmpty()) {
            JPanel panelVacio = new JPanel(new BorderLayout());
            panelVacio.setBackground(COLOR_FONDO);
            // Mensaje centrado y estilizado para no resultados
            JLabel lbl = new JLabel("<html><center><h3 style='color:#666'>❌ No se encontraron libros</h3><p>Intenta cambiar los filtros de búsqueda</p></center></html>", SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            
            // Usar BorderLayout temporalmente para centrar el mensaje
            panelResultados.setLayout(new BorderLayout()); 
            panelResultados.add(lbl, BorderLayout.CENTER);
            
        } else {
            // Restaurar layout de rejilla (GridLayout) para mostrar tarjetas
            panelResultados.setLayout(new GridLayout(0, 2, 15, 15)); 
            
            for (Libro libro : lista) {
                panelResultados.add(crearTarjetaLibro(libro));
            }
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }

    /**
     * Crea una tarjeta visual elegante para un libro individual.
     */
    private JPanel crearTarjetaLibro(Libro libro) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 0));
        tarjeta.setBackground(Color.WHITE);
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            // Borde que simula una sombra suave
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(10, 10, 10, 10)
        ));
        tarjeta.setPreferredSize(new Dimension(300, 140)); // Altura fija para uniformidad

        // 1. Miniatura de Portada (Izquierda)
        JLabel lblPortada = new JLabel();
        lblPortada.setPreferredSize(new Dimension(80, 120));
        lblPortada.setOpaque(true);
        lblPortada.setBackground(new Color(240, 240, 240));
        lblPortada.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Carga de imagen (similar a la vista detalle)
        boolean tieneImagen = false;
        if (libro.getRutaPortada() != null && !libro.getRutaPortada().isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(libro.getRutaPortada());
                if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image img = icon.getImage().getScaledInstance(80, 120, Image.SCALE_SMOOTH);
                    lblPortada.setIcon(new ImageIcon(img));
                    tieneImagen = true;
                }
            } catch (Exception e) {}
        }
        if (!tieneImagen) {
            lblPortada.setText("No img");
            lblPortada.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        }
        tarjeta.add(lblPortada, BorderLayout.WEST);


        // 2. Información (Centro)
        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(Color.WHITE);
        panelInfo.setBorder(new EmptyBorder(0, 5, 0, 5));

        JLabel lblTitulo = new JLabel("<html><b>" + libro.getTitulo() + "</b></html>");
        lblTitulo.setFont(FUENTE_TITULO);
        lblTitulo.setForeground(new Color(33, 37, 41));
        
        JLabel lblAutor = new JLabel(libro.getAutores());
        lblAutor.setFont(FUENTE_NORMAL);
        lblAutor.setForeground(Color.GRAY);

        JLabel lblCat = new JLabel("📂 " + libro.getCategoria());
        lblCat.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblCat.setForeground(new Color(0, 102, 204));

        panelInfo.add(lblTitulo);
        panelInfo.add(Box.createVerticalStrut(4));
        panelInfo.add(lblAutor);
        panelInfo.add(Box.createVerticalStrut(4));
        panelInfo.add(lblCat);
        
        // Espacio flexible
        panelInfo.add(Box.createVerticalGlue());

        // Etiqueta de estado
        JLabel lblEstado = new JLabel();
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 11));
        if (libro.getCopiasDisponibles() > 0) {
            lblEstado.setText("✅ Disponible");
            lblEstado.setForeground(new Color(40, 167, 69));
        } else {
            lblEstado.setText("❌ Agotado");
            lblEstado.setForeground(new Color(220, 53, 69));
        }
        panelInfo.add(lblEstado);

        tarjeta.add(panelInfo, BorderLayout.CENTER);


        // 3. Botón (Sur / Abajo)
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        panelBoton.setBackground(Color.WHITE);
        
        JButton btnVer = new JButton("Ver Detalle");
        btnVer.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnVer.setBackground(new Color(240, 245, 255));
        btnVer.setForeground(COLOR_AZUL);
        btnVer.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 255)));
        btnVer.setPreferredSize(new Dimension(100, 30));
        btnVer.setFocusPainted(false);
        btnVer.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVer.addActionListener(e -> abrirDetalleLibro(libro));
        
        panelBoton.add(btnVer);
        tarjeta.add(panelBoton, BorderLayout.SOUTH);

        return tarjeta;
    }

    /**
     * Abre la ventana con el detalle completo del libro usando la clase VerDetalleLibro.
     */
    private void abrirDetalleLibro(Libro libro) {
        JFrame frameDetalle = new JFrame("BiblioFisi - " + libro.getTitulo());
        frameDetalle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Instancia la clase de vista de detalle (la moderna)
        VerDetalleLibro panelDetalle = new VerDetalleLibro(libro, controladorCatalogo);
        
        frameDetalle.setContentPane(panelDetalle);
        frameDetalle.setSize(900, 650); // Tamaño inicial grande para el detalle
        frameDetalle.setMinimumSize(new Dimension(800, 500));
        frameDetalle.setLocationRelativeTo(this); 
        frameDetalle.setVisible(true);
    }
}