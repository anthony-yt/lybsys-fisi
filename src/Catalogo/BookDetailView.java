package Catalogo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import Resena.VistaResenas;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

/**
 * Vista de detalle de un libro.
 * <p>
 * Muestra la portada, metadatos (título, autor/es, categoría, ISBN, descripción, formato),
 * y acciones relacionadas como solicitar un préstamo o leer el libro si el usuario ya lo posee.
 * Además, integra la vista de reseñas (`Resena.VistaResenas`) en la parte inferior.
 * </p>
 */
public class BookDetailView extends JPanel {
    /** Modelo del libro que se está mostrando en la vista. */
    private Book book;
    /** Controlador del catálogo para consultar y actualizar metadatos del libro. */
    private CatalogController catalogController;
    /** Controlador de préstamos que gestiona el estado del libro para el usuario actual. */
    private LoanController loanController;
    /** Botón principal que contiene la acción contextual (pedir préstamo / leer / en cola). */
    private JButton btnAccion;
    /** Botón con el nombre del autor (actúa como filtro para buscar otros libros del autor). */
    private JButton btnAuthor;

    /**
     * Constructor.
     * @param book libro a mostrar en detalle
     * @param catalogController controlador de catálogo usado para consultar y actualizar datos
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

    /**
     * Inicializa los componentes Swing principales y organiza la estructura visual.
     * - Panel izquierdo: portada del libro.
     * - Panel derecho: metadatos y acciones.
     */
    private void initComponents() {
        JPanel leftPanel = crearPanelIzquierdo();
        add(leftPanel, BorderLayout.WEST);
        
        JPanel rightPanel = crearPanelDerecho();
        add(rightPanel, BorderLayout.CENTER);
    }

    /**
     * Crea el panel izquierdo que contiene la portada del libro. Si no hay portada,
     * se muestra un texto alternativo "Sin portada".
     * @return panel con la portada del libro
     */
    private JPanel crearPanelIzquierdo() {
        final String coverPath = book.getCoverPath();

        JPanel panel = new JPanel() {
            private Image image = null;
            private boolean imageLoaded = false;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (!imageLoaded) {
                    if (coverPath != null && !coverPath.isEmpty()) {
                         File f = new File(coverPath);
                         if (f.exists()) {
                             image = new ImageIcon(coverPath).getImage();
                         }
                    }
                    imageLoaded = true;
                }

                int panelWidth = getWidth();
                int panelHeight = getHeight();

                if (image != null) {
                    int imgWidth = image.getWidth(null);
                    int imgHeight = image.getHeight(null);

                    double widthRatio = (double) panelWidth / imgWidth;
                    double heightRatio = (double) panelHeight / imgHeight;
                    
                    double scale = Math.min(widthRatio, heightRatio);

                    int newWidth = (int) (imgWidth * scale);
                    int newHeight = (int) (imgHeight * scale);

                    int x = (panelWidth - newWidth) / 2;
                    int y = (panelHeight - newHeight) / 2;

                    g2d.drawImage(image, x, y, newWidth, newHeight, this);

                } else {
                    g2d.setColor(Color.GRAY);
                    g2d.setFont(new Font("Arial", Font.PLAIN, 14));
                    FontMetrics fm = g2d.getFontMetrics();
                    String text = "Sin portada";
                    int textWidth = fm.stringWidth(text);
                    int textHeight = fm.getAscent();
                    g2d.drawString(text, (panelWidth - textWidth) / 2, (panelHeight + textHeight) / 2);
                }
            }
        };

        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(250, 400));
        return panel;
    }

    /**
     * Crea el panel derecho con todos los metadatos visibles del libro y las acciones
     * (botón de acción, filtros por autor, etc.).
     * @return panel con los metadatos y acciones
     */
    private JPanel crearPanelDerecho() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 247, 250));

        JLabel lblTitle = new JLabel(book.getTitle());
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.LEFT_ALIGNMENT); 
        panel.add(lblTitle);
        panel.add(Box.createVerticalStrut(10));

        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); 
        authorPanel.setBackground(new Color(245, 247, 250));
        authorPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        JLabel lblAuthorLabel = new JLabel("Autor/es: ");
        lblAuthorLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        btnAuthor = new JButton(book.getAuthors());
        btnAuthor.setFont(new Font("Arial", Font.PLAIN, 12));
        btnAuthor.setBorderPainted(false);
        btnAuthor.setContentAreaFilled(false);
        btnAuthor.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAuthor.setForeground(new Color(0, 102, 204)); 
        btnAuthor.setHorizontalAlignment(SwingConstants.LEFT);
        btnAuthor.setMargin(new Insets(0,0,0,0));
        
        btnAuthor.addActionListener(e -> aplicarFiltroAutor(book.getAuthors()));
        authorPanel.add(lblAuthorLabel);
        authorPanel.add(btnAuthor);
        panel.add(authorPanel);
        panel.add(Box.createVerticalStrut(5));

        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        categoryPanel.setBackground(new Color(245, 247, 250));
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        JLabel lblCatLabel = new JLabel("Categoría: ");
        lblCatLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lblCategory = new JLabel(book.getCategory());
        lblCategory.setFont(new Font("Arial", Font.PLAIN, 12));
        
        categoryPanel.add(lblCatLabel);
        categoryPanel.add(lblCategory);
        panel.add(categoryPanel);
        panel.add(Box.createVerticalStrut(5));

        JPanel isbnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        isbnPanel.setBackground(new Color(245, 247, 250));
        isbnPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        JLabel lblIsbnLabel = new JLabel("ISBN: ");
        lblIsbnLabel.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel lblIsbn = new JLabel(book.getIsbn());
        lblIsbn.setFont(new Font("Arial", Font.PLAIN, 12));
        
        isbnPanel.add(lblIsbnLabel);
        isbnPanel.add(lblIsbn);
        panel.add(isbnPanel);
        panel.add(Box.createVerticalStrut(15));

        JLabel lblDescLabel = new JLabel("Descripción:");
        lblDescLabel.setFont(new Font("Arial", Font.BOLD, 12));
        lblDescLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblDescLabel);
        
        JTextArea txtDescription = new JTextArea(book.getDescription());
        txtDescription.setFont(new Font("Arial", Font.PLAIN, 12));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);
        txtDescription.setBackground(new Color(255, 255, 255));
        txtDescription.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollDesc = new JScrollPane(txtDescription);
        scrollDesc.setPreferredSize(new Dimension(400, 100)); 
        scrollDesc.setAlignmentX(Component.LEFT_ALIGNMENT); 
        scrollDesc.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        
        panel.add(scrollDesc);
        panel.add(Box.createVerticalStrut(15));

        JPanel formatPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        formatPanel.setBackground(new Color(245, 247, 250));
        formatPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        JLabel lblFormatLabel = new JLabel("Formato: ");
        lblFormatLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel chipFormat = crearChip(book.getFormat(), new Color(100, 200, 255));
        
        formatPanel.add(lblFormatLabel);
        formatPanel.add(chipFormat);
        panel.add(formatPanel);
        panel.add(Box.createVerticalStrut(20));

        JPanel copiesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        copiesPanel.setBackground(new Color(245, 247, 250));
        copiesPanel.setAlignmentX(Component.LEFT_ALIGNMENT); 
        
        JLabel lblCopiesLabel = new JLabel("Copias disponibles: ");
        lblCopiesLabel.setFont(new Font("Arial", Font.BOLD, 12));
        
        JLabel lblCopies = new JLabel(book.getAvailableCopies() + "/" + book.getTotalCopies());
        lblCopies.setFont(new Font("Arial", Font.BOLD, 14));
        lblCopies.setForeground(book.getAvailableCopies() > 0 ? new Color(0, 150, 0) : Color.RED);
        
        copiesPanel.add(lblCopiesLabel);
        copiesPanel.add(lblCopies);
        
        copiesPanel.add(Box.createHorizontalStrut(20));

        btnAccion = crearBotonAccion();
        btnAccion.setPreferredSize(new Dimension(200, 35)); 
        copiesPanel.add(btnAccion);

        panel.add(copiesPanel);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }

    /**
     * Construye el botón principal de acción cuyo texto y comportamiento depende del estado
     * del libro para el usuario actual. Estados posibles: YA_TIENE, DISPONIBLE, EN_COLA, SIN_COPIAS.
     * @return el JButton con el comportamiento apropiado
     */
    private JButton crearBotonAccion() {
        JButton btn = new JButton();
        LoanController.EstadoLibro status = loanController.obtenerEstadoLibro(book.getId(), catalogController);
        
        btn.setFont(new Font("Arial", Font.BOLD, 13)); 
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        btn.setForeground(Color.WHITE);

        switch (status) {
            case YA_TIENE:
                btn.setText("LEER LIBRO"); 
                btn.setBackground(new Color(76, 175, 80));
                btn.setEnabled(true); 
                btn.addActionListener(e -> loanController.abrirLibro(book.getId()));
                btn.setToolTipText("Ya tienes este libro. Haz clic para leer.");
                break;
                
            case DISPONIBLE:
                btn.setText("PEDIR PRÉSTAMO");
                btn.setBackground(new Color(33, 150, 243));
                btn.setEnabled(true);
                btn.addActionListener(e -> pedirOAbrirLibro());
                break;
                
            case EN_COLA:
                btn.setText("EN COLA");
                btn.setBackground(new Color(255, 152, 0));
                btn.setEnabled(true);
                btn.setFocusable(false);
                btn.setRolloverEnabled(false);
                
                int queuePos = loanController.obtenerPosicionCola(book.getId());
                btn.setToolTipText("Posición en cola: " + queuePos);
                break;
                
            case SIN_COPIAS:
                btn.setText("SIN COPIAS");
                btn.setBackground(new Color(244, 67, 54));
                btn.setEnabled(true); 
                btn.setToolTipText("No hay copias. Click para unirte a la cola.");
                btn.addActionListener(e -> loanController.agregarACola(book.getId()));
                break;
                
            default:
                btn.setText("ERROR");
                btn.setEnabled(false);
        }
        
        return btn;
    }

    /**
     * Crea un "chip" visual (etiqueta con fondo coloreado) usado para mostrar el formato
     * del libro (por ejemplo, "PDF", "Físico", "E-book").
     * @param text texto del chip
     * @param bgColor color de fondo del chip
     * @return JLabel estilizado como chip
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
            new EmptyBorder(2, 8, 2, 8)
        ));
        return chip;
    }

    /**
     * Maneja la acción cuando el usuario pulsa el botón principal. Si el usuario ya tiene
     * el libro (estado YA_TIENE) se invoca la apertura del lector; si el libro está
     * disponible se solicita el préstamo, actualizando la UI en caso de éxito.
     */
    private void pedirOAbrirLibro() {
        LoanController.EstadoLibro status = loanController.obtenerEstadoLibro(book.getId(), catalogController);

        if (status == LoanController.EstadoLibro.YA_TIENE) {
            loanController.abrirLibro(book.getId());
            JOptionPane.showMessageDialog(this, "Abriendo: " + book.getTitle(), "Lector", JOptionPane.INFORMATION_MESSAGE);
        } else if (status == LoanController.EstadoLibro.DISPONIBLE) {
            if (loanController.pedirPrestamo(book.getId(), catalogController)) {
                JOptionPane.showMessageDialog(this, "Préstamo realizado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                removeAll();
                initComponents();
                addNotify();
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Error al realizar el préstamo", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Muestra una ventana que lista otros libros del autor indicado y permite abrir
     * el detalle de cada uno. Filtra para exluir el libro actual de la lista.
     * @param autor nombre del autor que se usará para buscar
     */
    private void aplicarFiltroAutor(String autor) {
        List<Book> encontrados = catalogController.getByAuthor(autor);
        List<Book> resultados = new ArrayList<>();
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
                JLabel lbl = new JLabel(b.getTitle() + " (" + b.getFormat() + ")");
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

    @Override
    /**
     * Hook de Swing ejecutado cuando el componente se agrega a un contenedor visible.
     * Añade la vista de reseñas en la parte inferior (o la recarga si ya existe)
     * para mostrar la lista de reseñas y el formulario de envío/edición si procede.
     */
    public void addNotify() {
        super.addNotify();
        SwingUtilities.invokeLater(() -> {
            try {
                Component[] comps = this.getComponents();
                for(Component c : comps) {
                    if(c instanceof VistaResenas) {
                        this.remove(c);
                    }
                }
                
                VistaResenas rv = new VistaResenas(book.getId(), catalogController, loanController);
                this.add(rv, BorderLayout.SOUTH);
                this.revalidate();
                this.repaint();
            } catch (Exception ex) {
                System.err.println("Unable to initialize ReviewView: " + ex.getMessage());
            }
        });
    }
}