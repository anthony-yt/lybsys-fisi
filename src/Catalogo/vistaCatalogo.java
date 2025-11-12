package Catalogo;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class vistaCatalogo extends JPanel {

    private JTextField txtTitulo;
    private JComboBox<String> cbCategoria;
    private JCheckBox chkDisponibles;
    private JButton btnBuscar;
    private JPanel panelResultados;

    private CatalogController catalogController;

    public vistaCatalogo() {
        catalogController = new CatalogController(); // usamos el controlador real
        initComponents();
        // Mostrar todos los libros al inicio
        mostrarLibros(catalogController.getAllBooks());
    }

    private void initComponents() {
        setBackground(new Color(245, 247, 250));
        setLayout(new BorderLayout(10, 10));

        // 🔹 Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltros.setBackground(new Color(230, 240, 255));

        txtTitulo = new JTextField(15);
        cbCategoria = new JComboBox<>(new String[]{
            "Todos", "Programación", "Bases de Datos", "Inteligencia Artificial", "Redes", "Algoritmos", "Sistemas"
        });
        chkDisponibles = new JCheckBox("Solo disponibles");
        btnBuscar = new JButton("Buscar");

        panelFiltros.add(new JLabel("Título:"));
        panelFiltros.add(txtTitulo);
        panelFiltros.add(new JLabel("Categoría:"));
        panelFiltros.add(cbCategoria);
        panelFiltros.add(chkDisponibles);
        panelFiltros.add(btnBuscar);

        add(panelFiltros, BorderLayout.NORTH);

        // 🔹 Panel de resultados
        panelResultados = new JPanel();
        panelResultados.setBackground(Color.WHITE);
        panelResultados.setLayout(new GridLayout(0, 2, 20, 20)); // 2 columnas fijas, filas automáticas

        // 🔹 Scroll vertical
        JScrollPane scroll = new JScrollPane(panelResultados);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16); // suaviza el scroll
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(scroll, BorderLayout.CENTER);

        // Evento de búsqueda
        btnBuscar.addActionListener(e -> buscarLibros());
    }

    private void buscarLibros() {
        String tituloFiltro = txtTitulo.getText();
        String categoriaFiltro = cbCategoria.getSelectedItem().toString();
        boolean soloDisponibles = chkDisponibles.isSelected();

        List<Book> resultados = catalogController.search(
                tituloFiltro,
                categoriaFiltro,
                soloDisponibles,
                1
        );

        mostrarLibros(resultados);
    }

    private void mostrarLibros(List<Book> lista) {
        panelResultados.removeAll();

        if (lista.isEmpty()) {
            JLabel lbl = new JLabel("❌ No se encontraron libros.", SwingConstants.CENTER);
            lbl.setFont(new Font("Dubai", Font.PLAIN, 16));
            panelResultados.add(lbl);
        } else {
            for (Book libro : lista) {
                JPanel card = new JPanel(new BorderLayout(10, 10));
                card.setPreferredSize(new Dimension(220, 160)); // Tamaño fijo
                card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                card.setBackground(Color.WHITE);
                card.setOpaque(true);

                JLabel lblTitulo = new JLabel("<html><b>" + libro.getTitle() + "</b></html>");
                lblTitulo.setFont(new Font("Dubai", Font.BOLD, 14));
                JLabel lblCat = new JLabel("Categoría: " + libro.getCategory());
                JLabel lblDisp = new JLabel(libro.isAvailable() ? "Disponible ✅" : "No disponible ❌");

                JPanel info = new JPanel(new GridLayout(3, 1));
                info.setOpaque(false);
                info.add(lblTitulo);
                info.add(lblCat);
                info.add(lblDisp);

                JButton btnDetalle = new JButton("Ver detalle");
                btnDetalle.setBackground(new Color(0, 102, 204));
                btnDetalle.setForeground(Color.WHITE);
                btnDetalle.setFocusPainted(false);
                btnDetalle.setCursor(new Cursor(Cursor.HAND_CURSOR));

                btnDetalle.addActionListener(e -> JOptionPane.showMessageDialog(
                        this,
                        libro.getTitle()
                        + "\nCategoría: " + libro.getCategory()
                        + "\nDisponible: " + (libro.isAvailable() ? "Sí" : "No"),
                        "Detalle del libro",
                        JOptionPane.INFORMATION_MESSAGE
                ));

                card.add(info, BorderLayout.CENTER);
                card.add(btnDetalle, BorderLayout.SOUTH);
                panelResultados.add(card);
            }
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }
}
