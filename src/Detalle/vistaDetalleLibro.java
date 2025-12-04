package Detalle;

import Catalogo.Libro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel Swing que muestra el detalle de un libro.
 *
 * Contiene los elementos mínimos: portada, metadatos (título, autor, categoría, ISBN),
 * descripción, chips de formatos y el botón principal para pedir préstamo/leer/cola.
 * Este panel expone métodos utilizados por {@link ControladorDetalleLibro} para
 * actualizar su contenido y estado.
 */
public class vistaDetalleLibro extends JPanel {
    private ControladorDetalleLibro controller;

    // Componentes UI mínimos
    private JLabel portadaLabel;
    private JLabel tituloLabel;
    private JLabel autorLabel;
    private JLabel categoriaLabel;
    private JLabel isbnLabel;
    private JTextArea descripcionArea;
    private JPanel chipsPanel;
    private JButton botonAccion;

    public vistaDetalleLibro() {
        this.controller = new ControladorDetalleLibro(this);
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(8,8));

        // Portada y metadatos
        JPanel left = new JPanel(new BorderLayout());
        portadaLabel = new JLabel();
        portadaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        portadaLabel.setPreferredSize(new Dimension(220, 300));
        portadaLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        left.add(portadaLabel, BorderLayout.NORTH);

        JPanel meta = new JPanel();
        meta.setLayout(new BoxLayout(meta, BoxLayout.Y_AXIS));
        tituloLabel = new JLabel("Título");
        tituloLabel.setFont(tituloLabel.getFont().deriveFont(Font.BOLD, 16f));
        autorLabel = new JLabel("Autor(es)");
        categoriaLabel = new JLabel("Categoría");
        isbnLabel = new JLabel("ISBN: ");
        meta.add(tituloLabel);
        meta.add(Box.createVerticalStrut(4));
        meta.add(autorLabel);
        meta.add(categoriaLabel);
        meta.add(isbnLabel);
        left.add(meta, BorderLayout.CENTER);

        add(left, BorderLayout.WEST);

        // Descripción, chips y acciones
        JPanel right = new JPanel(new BorderLayout(4,4));
        descripcionArea = new JTextArea();
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setEditable(false);
        descripcionArea.setRows(10);
        right.add(new JScrollPane(descripcionArea), BorderLayout.CENTER);

        chipsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        right.add(chipsPanel, BorderLayout.NORTH);

        botonAccion = new JButton("Pedir préstamo");
        botonAccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.manejarBotonAccion();
            }
        });

        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionRow.add(botonAccion);
        right.add(actionRow, BorderLayout.SOUTH);

        add(right, BorderLayout.CENTER);
    }

    /**
     * Inicializa la vista y carga el libro indicado.
     */
    public void inicializarPara(String bookId) {
        controller.inicializar(bookId);
    }

    /**
     * Muestra los datos del libro en la UI.
     *
     * Actualiza los componentes visuales con la información disponible del libro.
     *
     * @param libro objeto con los metadatos del libro a mostrar
     */
    public void mostrarLibro(Libro libro) {
        tituloLabel.setText(libro.getTitulo());
        // Nota: Libro actual tiene solo algunos campos; ampliar según modelo real
        categoriaLabel.setText("Categoría: " + libro.getCategoria());
        descripcionArea.setText("Descripción no disponible.");
        // Portada placeholder
        portadaLabel.setIcon(null);
        // chips (ejemplo)
        chipsPanel.removeAll();
        chipsPanel.add(new JLabel("[PDF]"));
        chipsPanel.add(new JLabel("[ePub]"));
        revalidate();
        repaint();
    }

    public void establecerTextoBotonAccion(String texto) {
        botonAccion.setText(texto);
    }

    public void establecerBotonAccionHabilitado(boolean habilitado) {
        botonAccion.setEnabled(habilitado);
    }

    public void navegarCatalogoPorAutor(String autorId) {
        System.out.println("Navegar al catálogo filtrado por autor: " + autorId);
    }

    public void establecerTooltipBotonAccion(String texto) {
        botonAccion.setToolTipText(texto);
    }

    public void mostrarToast(String mensaje) {
        // Usamos JOptionPane como notificación simple
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void abrirLector(String bookId) {
        // Integrar con ReaderController cuando exista
        System.out.println("Abrir lector para libro: " + bookId);
    }
}

