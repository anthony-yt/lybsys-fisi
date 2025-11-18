package Catalogo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Optional;

/**
 * Vista de detalle de un libro.
 * Muestra portada, metadatos, estado de copias y acciones relacionadas (pedir préstamo, añadir a cola, abrir).
 */
public class BookDetailView extends JPanel {
    private Book book;
    private CatalogController catalogController;
    private LoanController loanController;
    private JButton btnAccion;
    private JButton btnAuthor;

    /**
     * Crea la vista de detalle para un libro dado.
     * @param book libro a mostrar
     * @param catalogController controlador de catálogo para operaciones relacionadas
     */
    public BookDetailView(Book book, CatalogController catalogController) {
        this.book = book;
        this.catalogController = catalogController;
        this.loanController = LoanController.obtenerInstancia();
        
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        
        initComponents();
    }

    private void initComponents() {
        // Panel izquierdo: Portada
        JPanel leftPanel = crearPanelIzquierdo();
        add(leftPanel, BorderLayout.WEST);
        // Panel derecho: Metadatos y acciones
        JPanel rightPanel = crearPanelDerecho();
        add(rightPanel, BorderLayout.CENTER);
    }

    /**
     * Crea el panel izquierdo con la portada del libro.
     * @return panel con portada
     */
    private JPanel crearPanelIzquierdo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        panel.setPreferredSize(new Dimension(250, 400));

        // Portada del libro
        JLabel lblPortada = new JLabel();
        if (book.getCoverPath() != null) {
            ImageIcon icon = new ImageIcon(book.getCoverPath());
            Image img = icon.getImage().getScaledInstance(220, 350, Image.SCALE_SMOOTH);
            lblPortada.setIcon(new ImageIcon(img));
        } else {
            lblPortada.setText("Sin portada");
            lblPortada.setHorizontalAlignment(SwingConstants.CENTER);
            lblPortada.setFont(new Font("Arial", Font.PLAIN, 14));
            lblPortada.setForeground(Color.GRAY);
        }
        lblPortada.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(lblPortada, BorderLayout.CENTER);
        return panel;
    }

    /**
     * Crea el panel derecho con metadatos, chips y botón de acción.
     * @return panel derecho
     */
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));

        // Título
        JLabel lblTitle = new JLabel(book.getTitle());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(10));

        // Autor con botón
        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        authorPanel.setBackground(new Color(245, 247, 250));
        JLabel lblAuthorLabel = new JLabel("Autor/es:");
        lblAuthorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        btnAuthor = new JButton(book.getAuthors());
        btnAuthor.setFont(new Font("Arial", Font.PLAIN, 12));
        btnAuthor.setFocusPainted(false);
        btnAuthor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAuthor.addActionListener(e -> aplicarFiltroAutor(book.getAuthors()));
        authorPanel.add(lblAuthorLabel);
        authorPanel.add(btnAuthor);
        panel.add(authorPanel);
        panel.add(Box.createVerticalStrut(10));

        // Categoría
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        categoryPanel.setBackground(new Color(245, 247, 250));
        JLabel lblCatLabel = new JLabel("Categoría:");
        lblCatLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lblCategory = new JLabel(book.getCategory());
        lblCategory.setFont(new Font("Arial", Font.PLAIN, 12));
        categoryPanel.add(lblCatLabel);
        categoryPanel.add(lblCategory);
        panel.add(categoryPanel);
        panel.add(Box.createVerticalStrut(10));

        // ISBN
        JPanel isbnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        isbnPanel.setBackground(new Color(245, 247, 250));
        JLabel lblIsbnLabel = new JLabel("ISBN:");
        lblIsbnLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lblIsbn = new JLabel(book.getIsbn());
        lblIsbn.setFont(new Font("Arial", Font.PLAIN, 12));
        isbnPanel.add(lblIsbnLabel);
        isbnPanel.add(lblIsbn);
        panel.add(isbnPanel);
        panel.add(Box.createVerticalStrut(15));

        // Descripción
        JLabel lblDescLabel = new JLabel("Descripción:");
        lblDescLabel.setFont(new Font("Arial", Font.BOLD, 12));
        lblDescLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblDescLabel);
        
        JTextArea txtDescription = new JTextArea(book.getDescription());
        txtDescription.setFont(new Font("Arial", Font.PLAIN, 11));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);
        txtDescription.setBackground(new Color(255, 255, 255));
        txtDescription.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        txtDescription.setPreferredSize(new Dimension(400, 100));
        
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(scrollDesc);
        panel.add(Box.createVerticalStrut(15));

        // Chips de formato
        JPanel formatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        formatPanel.setBackground(new Color(245, 247, 250));
        JLabel lblFormatLabel = new JLabel("Formato:");
        lblFormatLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel chipFormat = crearChip(book.getFormat(), new Color(100, 200, 255));
        
        formatPanel.add(lblFormatLabel);
        formatPanel.add(chipFormat);
        formatPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(formatPanel);
        panel.add(Box.createVerticalStrut(15));

        // Disponibilidad de copias
        JPanel copiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        copiesPanel.setBackground(new Color(245, 247, 250));
        JLabel lblCopiesLabel = new JLabel("Copias disponibles:");
        lblCopiesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lblCopies = new JLabel(book.getAvailableCopies() + "/" + book.getTotalCopies());
        lblCopies.setFont(new Font("Arial", Font.BOLD, 14));
        lblCopies.setForeground(book.getAvailableCopies() > 0 ? Color.GREEN : Color.RED);
        copiesPanel.add(lblCopiesLabel);
        copiesPanel.add(lblCopies);
        panel.add(copiesPanel);
        panel.add(Box.createVerticalStrut(20));

        // Botón de acción
        btnAccion = crearBotonAccion();
        btnAccion.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(btnAccion);

        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    /**
     * Crea el botón de acción cuyo texto/estado depende del estado del libro.
     * @return botón configurado
     */
    private JButton crearBotonAccion() {
        JButton btn = new JButton();
        LoanController.EstadoLibro status = loanController.obtenerEstadoLibro(book.getId(), catalogController);
        
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(200, 40));
        
        switch (status) {
            case YA_TIENE:
                btn.setText("📚 Pedir préstamo");
                btn.setBackground(new Color(76, 175, 80));
                btn.setForeground(Color.WHITE);
                // Ya lo tiene: mostramos la opción pedir préstamo pero deshabilitada
                btn.setEnabled(false);
                btn.setToolTipText("Ya tienes este libro. Lee desde Perfil.");
                break;
                
            case DISPONIBLE:
                btn.setText("📚 Pedir préstamo");
                btn.setBackground(new Color(33, 150, 243));
                btn.setForeground(Color.WHITE);
                btn.setEnabled(true);
                btn.addActionListener(e -> pedirOAbrirLibro());
                break;
                
            case EN_COLA:
                btn.setText("⏳ En cola");
                btn.setBackground(new Color(255, 152, 0));
                btn.setForeground(Color.WHITE);
                btn.setEnabled(false);
                int queuePos = loanController.obtenerPosicionCola(book.getId());
                btn.setToolTipText("Posición en cola: " + queuePos);
                break;
                
            case SIN_COPIAS:
                btn.setText("❌ Sin copias");
                btn.setBackground(new Color(244, 67, 54));
                btn.setForeground(Color.WHITE);
                btn.setEnabled(false);
                btn.setToolTipText("No hay copias disponibles. Añádete a la cola.");
                btn.addActionListener(e -> loanController.agregarACola(book.getId()));
                break;
                
            default:
                btn.setText("Error");
                btn.setEnabled(false);
        }
        
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        return btn;
    }

    /**
     * Crea una pequeña etiqueta (chip) con fondo coloreado.
     */
    private JLabel crearChip(String text, Color bgColor) {
        JLabel chip = new JLabel(text);
        chip.setFont(new Font("Arial", Font.PLAIN, 11));
        chip.setBackground(bgColor);
        chip.setForeground(Color.WHITE);
        chip.setOpaque(true);
        chip.setBorder(new EmptyBorder(5, 10, 5, 10));
        chip.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor),
            new EmptyBorder(5, 10, 5, 10)
        ));
        return chip;
    }
    /**
     * Intenta pedir el préstamo o abrir el libro según el estado actual.
     */
    private void pedirOAbrirLibro() {
        LoanController.EstadoLibro status = loanController.obtenerEstadoLibro(book.getId(), catalogController);

        if (status == LoanController.EstadoLibro.YA_TIENE) {
            loanController.abrirLibro(book.getId());
            JOptionPane.showMessageDialog(this, "Abriendo: " + book.getTitle(), "Lector", JOptionPane.INFORMATION_MESSAGE);
        } else if (status == LoanController.EstadoLibro.DISPONIBLE) {
            if (loanController.pedirPrestamo(book.getId(), catalogController)) {
                JOptionPane.showMessageDialog(this, "Préstamo realizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Actualizar vista
                removeAll();
                initComponents();
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el préstamo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void aplicarFiltroAutor(String autor) {
        // Mostrar sólo los OTROS libros del autor en una ventana compacta
        java.util.List<Book> encontrados = catalogController.getByAuthor(autor);
        java.util.List<Book> resultados = new java.util.ArrayList<>();
        for (Book b : encontrados) {
            if (!b.getId().equals(this.book.getId())) {
                resultados.add(b);
            }
        }

        JFrame frame = new JFrame("Otros libros de: " + autor);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(420, Math.min(400, 60 + resultados.size() * 60));
        frame.setLocationRelativeTo(this);

        JPanel content = new JPanel(new BorderLayout());
        JPanel list = new JPanel();
        list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
        list.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        if (resultados.isEmpty()) {
            JLabel lbl = new JLabel("No se encontraron otros libros para: " + autor);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            list.add(lbl);
        } else {
            for (Book b : resultados) {
                JPanel row = new JPanel(new BorderLayout(8,8));
                row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                JLabel lbl = new JLabel(b.getTitle() + "   (" + b.getFormat() + ")");
                JButton btn = new JButton("Ver detalle");
                btn.addActionListener(e -> {
                    BookDetailView bd = new BookDetailView(b, catalogController);
                    JFrame d = new JFrame("Detalle: " + b.getTitle());
                    d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    d.setContentPane(bd);
                    d.pack();
                    d.setLocationRelativeTo(frame);
                    d.setVisible(true);
                });
                row.add(lbl, BorderLayout.CENTER);
                row.add(btn, BorderLayout.EAST);
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
                list.add(row);
            }
        }

        JScrollPane scroll = new JScrollPane(list);
        content.add(scroll, BorderLayout.CENTER);
        frame.setContentPane(content);
        frame.setVisible(true);
    }
}
