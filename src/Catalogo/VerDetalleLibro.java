package Catalogo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * Vista de detalle de un libro optimizada visualmente y totalmente funcional.
 * Requiere ControladorCatalogo, ControladorPrestamo y Libro.
 */
public class VerDetalleLibro extends JPanel {
    private Libro libro;
    private ControladorCatalogo controladorCatalogo;
    private ControladorPrestamo controladorPrestamo;

    // --- ESTILOS Y FUENTES ---
    private final Font FUENTE_TITULO = new Font("Segoe UI", Font.BOLD, 26);
    private final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD, 14);
    private final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    private final Font FUENTE_BOTON = new Font("Segoe UI", Font.BOLD, 15);
    private final Color COLOR_FONDO = new Color(250, 250, 250);
    private final Color COLOR_TEXTO_SECUNDARIO = new Color(100, 100, 100);

    public VerDetalleLibro(Libro libro, ControladorCatalogo controladorCatalogo) {
        this.libro = libro;
        this.controladorCatalogo = controladorCatalogo;
        this.controladorPrestamo = ControladorPrestamo.obtenerInstancia();

        setLayout(new BorderLayout(20, 20)); 
        setBackground(COLOR_FONDO);
        setBorder(new EmptyBorder(25, 25, 25, 25));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // ZONA CENTRAL (Portada + Información)
        JPanel panelCentral = new JPanel(new BorderLayout(25, 0));
        panelCentral.setBackground(COLOR_FONDO);

        panelCentral.add(crearPanelIzquierdo(), BorderLayout.WEST);
        panelCentral.add(crearPanelDerecho(), BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // ZONA INFERIOR (Botón de Acción anclado abajo)
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBackground(COLOR_FONDO);
        panelInferior.setBorder(new EmptyBorder(15, 0, 0, 0));
        
        JButton botonAccion = crearBotonAccion();
        panelInferior.add(botonAccion);
        
        add(panelInferior, BorderLayout.SOUTH);
    }

    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 300));
        panel.setBackground(Color.WHITE);
        
        panel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(220, 220, 220), 1),
            new EmptyBorder(5, 5, 5, 5)
        ));

        JLabel etiquetaPortada = new JLabel();
        etiquetaPortada.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaPortada.setVerticalAlignment(SwingConstants.CENTER);

        boolean tieneImagen = false;
        if (libro.getRutaPortada() != null && !libro.getRutaPortada().isEmpty()) {
            try {
                ImageIcon icono = new ImageIcon(libro.getRutaPortada());
                if (icono.getImageLoadStatus() == MediaTracker.COMPLETE) {
                    Image imagen = icono.getImage().getScaledInstance(190, 290, Image.SCALE_SMOOTH);
                    etiquetaPortada.setIcon(new ImageIcon(imagen));
                    tieneImagen = true;
                }
            } catch (Exception e) {}
        }

        if (!tieneImagen) {
            etiquetaPortada.setText("<html><center><p style='font-size:12px; color:#999'>Sin Portada</p></center></html>");
            panel.setBackground(new Color(245, 245, 245)); 
        }

        panel.add(etiquetaPortada, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(COLOR_FONDO);

        // TÍTULO
        JLabel etiquetaTitulo = new JLabel("<html>" + libro.getTitulo() + "</html>");
        etiquetaTitulo.setFont(FUENTE_TITULO);
        etiquetaTitulo.setForeground(new Color(33, 37, 41));
        etiquetaTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(etiquetaTitulo);
        
        panel.add(Box.createVerticalStrut(10)); 

        // AUTOR
        JPanel panelAutor = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelAutor.setBackground(COLOR_FONDO);
        panelAutor.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblAutor = new JLabel("Autor: ");
        lblAutor.setFont(FUENTE_SUBTITULO);
        lblAutor.setForeground(COLOR_TEXTO_SECUNDARIO);
        
        JButton btnAutor = new JButton(libro.getAutores());
        btnAutor.setFont(FUENTE_NORMAL);
        btnAutor.setForeground(new Color(0, 102, 204));
        btnAutor.setBorderPainted(false);
        btnAutor.setContentAreaFilled(false);
        btnAutor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAutor.setFocusPainted(false);
        btnAutor.setMargin(new Insets(0, 0, 0, 0));
        btnAutor.addActionListener(e -> aplicarFiltroAutor(libro.getAutores()));
        
        panelAutor.add(lblAutor);
        panelAutor.add(btnAutor);
        panel.add(panelAutor);

        panel.add(Box.createVerticalStrut(15));

        // DATOS RÁPIDOS
        agregarDato(panel, "Categoría:", libro.getCategoria());
        agregarDato(panel, "ISBN:", libro.getIsbn());
        agregarDato(panel, "Formato:", libro.getFormato());
        
        panel.add(Box.createVerticalStrut(15));

        // DISPONIBILIDAD
        JPanel panelEstado = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelEstado.setBackground(COLOR_FONDO);
        panelEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        boolean disponible = libro.getCopiasDisponibles() > 0;
        String txtDisp = disponible ? "Disponible: " + libro.getCopiasDisponibles() + "/" + libro.getTotalCopias() : "No disponible";
        Color colorDisp = disponible ? new Color(40, 167, 69) : new Color(220, 53, 69);
        
        JLabel lblEstado = new JLabel(txtDisp);
        lblEstado.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblEstado.setForeground(Color.WHITE);
        lblEstado.setOpaque(true);
        lblEstado.setBackground(colorDisp);
        lblEstado.setBorder(new EmptyBorder(5, 12, 5, 12));
        
        panelEstado.add(lblEstado);
        panel.add(panelEstado);

        panel.add(Box.createVerticalStrut(20));

        // DESCRIPCIÓN
        JLabel lblDesc = new JLabel("Descripción");
        lblDesc.setFont(FUENTE_SUBTITULO);
        lblDesc.setForeground(COLOR_TEXTO_SECUNDARIO);
        lblDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblDesc);
        
        panel.add(Box.createVerticalStrut(5));

        JTextArea areaDescripcion = new JTextArea(libro.getDescripcion());
        areaDescripcion.setFont(FUENTE_NORMAL);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setWrapStyleWord(true);
        areaDescripcion.setEditable(false);
        areaDescripcion.setBackground(Color.WHITE);
        areaDescripcion.setForeground(new Color(60, 60, 60));
        areaDescripcion.setBorder(new EmptyBorder(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(areaDescripcion);
        scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        scroll.setBorder(new LineBorder(new Color(230, 230, 230)));
        scroll.setPreferredSize(new Dimension(100, 120));
        
        panel.add(scroll);

        return panel;
    }

    private void agregarDato(JPanel panel, String titulo, String valor) {
        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 4));
        fila.setBackground(COLOR_FONDO);
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel lblTit = new JLabel(titulo + " ");
        lblTit.setFont(FUENTE_SUBTITULO);
        lblTit.setForeground(COLOR_TEXTO_SECUNDARIO);
        
        JLabel lblVal = new JLabel(valor);
        lblVal.setFont(FUENTE_NORMAL);
        
        fila.add(lblTit);
        fila.add(lblVal);
        panel.add(fila);
    }

    private JButton crearBotonAccion() {
        JButton boton = new JButton();
        ControladorPrestamo.EstadoLibro estado = controladorPrestamo.obtenerEstadoLibro(libro.getId(), controladorCatalogo);

        boton.setFont(FUENTE_BOTON);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setPreferredSize(new Dimension(300, 50));

        switch (estado) {
            case YA_TIENE:
                boton.setText("📖 Ya tienes este libro");
                boton.setBackground(new Color(220, 220, 220)); 
                boton.setForeground(Color.GRAY);
                boton.setEnabled(false);
                break;
            case DISPONIBLE:
                boton.setText("📚 Solicitar Préstamo");
                boton.setBackground(new Color(0, 122, 255));
                boton.setForeground(Color.WHITE);
                boton.addActionListener(e -> pedirOAbrirLibro());
                break;
            case EN_COLA:
                boton.setText("⏳ Ver posición en cola");
                boton.setBackground(new Color(255, 193, 7));
                boton.setForeground(Color.BLACK);
                boton.addActionListener(e -> JOptionPane.showMessageDialog(this, 
                    "Tu posición en la cola es: " + controladorPrestamo.obtenerPosicionCola(libro.getId()),
                    "Estado de la cola", JOptionPane.INFORMATION_MESSAGE));
                break;
            case SIN_COPIAS:
                boton.setText("🔔 Unirse a lista de espera");
                boton.setBackground(new Color(220, 53, 69));
                boton.setForeground(Color.WHITE);
                boton.addActionListener(e -> {
                    controladorPrestamo.agregarACola(libro.getId());
                    JOptionPane.showMessageDialog(this, "Te has unido a la cola de espera.");
                    actualizarVista();
                });
                break;
            default:
                boton.setText("Consultar");
        }
        
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        boton.setOpaque(true);
        
        return boton;
    }

    private void pedirOAbrirLibro() {
        ControladorPrestamo.EstadoLibro estado = controladorPrestamo.obtenerEstadoLibro(libro.getId(), controladorCatalogo);

        if (estado == ControladorPrestamo.EstadoLibro.YA_TIENE) {
            controladorPrestamo.abrirLibro(libro.getId());
            JOptionPane.showMessageDialog(this, "Abriendo libro...", "Lector", JOptionPane.INFORMATION_MESSAGE);
        } else if (estado == ControladorPrestamo.EstadoLibro.DISPONIBLE) {
            if (controladorPrestamo.pedirPrestamo(libro.getId(), controladorCatalogo)) {
                JOptionPane.showMessageDialog(this, "Préstamo realizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarVista();
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void actualizarVista() {
        removeAll();
        inicializarComponentes();
        revalidate();
        repaint();
    }

    private void aplicarFiltroAutor(String autor) {
        List<Libro> librosEncontrados = controladorCatalogo.obtenerPorAutor(autor);
        List<Libro> resultados = new ArrayList<>();
        
        for (Libro l : librosEncontrados) {
            if (!l.getId().equals(this.libro.getId())) {
                resultados.add(l);
            }
        }

        JFrame ventana = new JFrame("Más libros de: " + autor);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ventana.setSize(450, 400);
        ventana.setLocationRelativeTo(this);

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(new EmptyBorder(10, 10, 10, 10));

        if (resultados.isEmpty()) {
            JLabel lblVacio = new JLabel("No se encontraron otros libros de este autor.");
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblVacio.setBorder(new EmptyBorder(20,0,0,0));
            panelContenido.add(lblVacio);
        } else {
            for (Libro libroActual : resultados) {
                JPanel panelFila = new JPanel(new BorderLayout(10, 10));
                panelFila.setBackground(Color.WHITE);
                panelFila.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
                panelFila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                
                JLabel lblTitulo = new JLabel("• " + libroActual.getTitulo());
                lblTitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                
                JButton btnVer = new JButton("Ver");
                btnVer.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                btnVer.setFocusPainted(false);
                btnVer.addActionListener(e -> {
                    VerDetalleLibro panelDetalle = new VerDetalleLibro(libroActual, controladorCatalogo);
                    JFrame vDetalle = new JFrame("Detalle: " + libroActual.getTitulo());
                    vDetalle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    vDetalle.setContentPane(panelDetalle);
                    vDetalle.setSize(800, 600);
                    vDetalle.setLocationRelativeTo(ventana);
                    vDetalle.setVisible(true);
                });

                panelFila.add(lblTitulo, BorderLayout.CENTER);
                panelFila.add(btnVer, BorderLayout.EAST);
                
                panelContenido.add(panelFila);
                panelContenido.add(Box.createVerticalStrut(5));
            }
        }

        JScrollPane scroll = new JScrollPane(panelContenido);
        scroll.setBorder(null);
        ventana.setContentPane(scroll);
        ventana.setVisible(true);
    }
}