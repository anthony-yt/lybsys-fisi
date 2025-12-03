package Resena;

import Catalogo.ControladorCatalogo;
import Catalogo.ControladorPrestamo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Vista que muestra el conjunto de reseñas para un libro y permite al usuario crear/editar su reseña.
 *
 * Incluye: muestra de calificación promedio, formulario (estrellas + textarea), lista paginada de reseñas
 * y botones para editar/eliminar la reseña propia.
 */
public class VistaResenas extends JPanel {
    private final String idLibro;
    private final ControladorResenas controlador;
    private final ControladorPrestamo controladorPrestamos;

    private final JPanel listaResenas = new JPanel();
    private int pagina = 1;
    private final int tamanoPagina = 5;

    // controles del formulario
    private final ComponenteEstrellas estrellasInput;
    private final JTextArea txtResena;
    
    // Botones de control
    private final JButton btnEnviar;
    private final JButton btnEliminar; // Nuevo botón

    private final JLabel lblPromedio;
    private final ComponenteEstrellas estrellasPromedio;
    private Resena resenaUsuario = null;

    /**
     * Crea la vista de reseñas para un libro.
     * @param idLibro id del libro a mostrar reseñas
     * @param catalogController controlador del catálogo (lecturas complementarias)
     * @param controladorPrestamos controlador de préstamos para condiciones de acceso
     */
    public VistaResenas(String idLibro, ControladorCatalogo catalogController, ControladorPrestamo controladorPrestamos) {
        this.idLibro = idLibro;
        this.controladorPrestamos = controladorPrestamos;
        this.controlador = new ControladorResenas("datos/reviews.csv", controladorPrestamos);

        setLayout(new GridLayout(1, 2, 15, 0)); 
        setBorder(BorderFactory.createTitledBorder("Comentarios y Valoraciones"));
        setPreferredSize(new Dimension(800, 250));

        // --- COLUMNA IZQUIERDA ---
        JPanel panelIzquierdo = new JPanel(new BorderLayout(0, 10));
        
        // Panel Superior (Promedios)
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(245, 247, 250));
        top.setBorder(new EmptyBorder(5, 5, 5, 5));

        lblPromedio = new JLabel("—");
        lblPromedio.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(lblPromedio, BorderLayout.WEST);

        estrellasPromedio = new ComponenteEstrellas(false, null);
        top.add(estrellasPromedio, BorderLayout.EAST);

        panelIzquierdo.add(top, BorderLayout.NORTH);

        // Panel Formulario
        JPanel form = new JPanel();
        form.setBorder(BorderFactory.createTitledBorder(null, "Tu opinión", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 11)));
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        
        estrellasInput = new ComponenteEstrellas(true, v -> {});
        JPanel panelEstrellasInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelEstrellasInput.add(estrellasInput);
        form.add(panelEstrellasInput);
        
        form.add(Box.createVerticalStrut(5));

        txtResena = new JTextArea(2, 20); 
        txtResena.setLineWrap(true);
        txtResena.setWrapStyleWord(true);
        JScrollPane scrollInput = new JScrollPane(txtResena);
        scrollInput.setPreferredSize(new Dimension(0, 60)); 
        scrollInput.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(scrollInput);
        
        form.add(Box.createVerticalStrut(8));

        // --- PANEL DE BOTONES (NUEVO DISEÑO) ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        btnEnviar = new JButton("Enviar Reseña");
        btnEnviar.addActionListener(e -> enviarAccion());
        panelBotones.add(btnEnviar);

        panelBotones.add(Box.createHorizontalStrut(10)); // Espacio entre botones

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setBackground(new Color(220, 53, 69)); // Rojo suave
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setVisible(false); // Oculto por defecto
        btnEliminar.addActionListener(e -> eliminarAccion());
        panelBotones.add(btnEliminar);

        // Alineación del panel de botones a la izquierda
        panelBotones.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(panelBotones);

        // Contenedor del formulario
        JPanel formContainer = new JPanel(new BorderLayout());
        formContainer.add(form, BorderLayout.NORTH);
        panelIzquierdo.add(formContainer, BorderLayout.CENTER);


        // --- COLUMNA DERECHA ---
        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder(null, "Comentarios de la comunidad", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.ITALIC, 11)));
        
        listaResenas.setLayout(new BoxLayout(listaResenas, BoxLayout.Y_AXIS));
        JScrollPane listScroll = new JScrollPane(listaResenas);
        listScroll.setBorder(null);
        
        panelDerecho.add(listScroll, BorderLayout.CENTER);

        add(panelIzquierdo); 
        add(panelDerecho);   

        refresh();
    }

    /**
     * Refresca la vista: recalcula promedio, carga la reseña del usuario actual y lista de reseñas.
     */
    private void refresh() {
        SwingUtilities.invokeLater(() -> {
            try {
                double promedio = controlador.obtenerPromedio(idLibro);
                lblPromedio.setText(String.format("%.1f / 5.0", promedio));
                estrellasPromedio.setValor((int) Math.round(promedio));

                Optional<Resena> ur = controlador.buscarResenaUsuario(idLibro);
                resenaUsuario = ur.orElse(null);
                
                // Lógica de visualización de botones actualizada
                if (resenaUsuario != null) {
                    estrellasInput.setValor(resenaUsuario.getCalificacion());
                    txtResena.setText(resenaUsuario.getTexto());
                    btnEnviar.setText("Modificar");
                    btnEliminar.setVisible(true); // Mostrar botón eliminar
                } else {
                    estrellasInput.setValor(0);
                    txtResena.setText("");
                    btnEnviar.setText("Enviar Reseña");
                    btnEliminar.setVisible(false); // Ocultar botón eliminar
                }

                boolean permitido = controladorPrestamos.usuarioTieneLibro(idLibro);
                
                estrellasInput.setVisible(permitido);
                txtResena.setEnabled(permitido);
                btnEnviar.setEnabled(permitido);
                
                if (!permitido) {
                    txtResena.setToolTipText("Debes prestar o leer el libro para opinar");
                    txtResena.setText("Debes leer el libro para opinar."); 
                    btnEliminar.setVisible(false);
                } else {
                    txtResena.setToolTipText(null);
                    if(resenaUsuario == null && txtResena.getText().startsWith("Debes")) txtResena.setText("");
                }

                cargarPaginaResenas(1);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Carga una página de reseñas (paginación simple) y actualiza la lista.
     * @param paginaCargar página que cargar (1-based)
     * @throws IOException si ocurre un error en la lectura
     */
    private void cargarPaginaResenas(int paginaCargar) throws IOException {
        this.pagina = paginaCargar;
        listaResenas.removeAll();
        
        List<Resena> todas = controlador.obtenerResenas(idLibro, 1, Integer.MAX_VALUE);
        if (todas.isEmpty()) {
            JLabel lblVacio = new JLabel("<html><center>Sé el primero en<br>opinar sobre este libro.</center></html>");
            lblVacio.setHorizontalAlignment(SwingConstants.CENTER);
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setBorder(new EmptyBorder(20,0,0,0));
            JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER));
            centro.add(lblVacio);
            listaResenas.add(centro);
        }

        List<Resena> resenas = controlador.obtenerResenas(idLibro, pagina, tamanoPagina);
        for (Resena r : resenas) {
            listaResenas.add(crearTarjetaResena(r));
            listaResenas.add(Box.createVerticalStrut(8));
        }

        if (pagina * tamanoPagina < todas.size()) {
            JButton btnMas = new JButton("Ver más...");
            btnMas.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnMas.addActionListener(e -> {
                try {
                    cargarPaginaResenas(this.pagina + 1);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            listaResenas.add(btnMas);
        }

        listaResenas.revalidate();
        listaResenas.repaint();
    }

    /**
     * Crea una tarjeta visual para una reseña con información del autor, fecha, calificación y texto.
     * Si el usuario actual es el autor, incluye botones de editar y eliminar.
     * @param r reseña a mostrar
     * @return panel con la tarjeta
     */
    private JPanel crearTarjetaResena(Resena r) {
        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
            new EmptyBorder(5, 5, 5, 5)
        ));
        tarjeta.setBackground(Color.WHITE);
        
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Color.WHITE);
        
        JLabel lblUser = new JLabel(r.getIdUsuario());
        lblUser.setFont(new Font("Arial", Font.BOLD, 12));
        encabezado.add(lblUser, BorderLayout.WEST);
        
        ComponenteEstrellas s = new ComponenteEstrellas(false, null);
        s.setValor(r.getCalificacion());
        JPanel pStar = new JPanel(); 
        pStar.setBackground(Color.WHITE);
        pStar.add(s);
        encabezado.add(pStar, BorderLayout.EAST);
        
        tarjeta.add(encabezado, BorderLayout.NORTH);

        JTextArea txt = new JTextArea(r.getTexto());
        txt.setLineWrap(true);
        txt.setWrapStyleWord(true);
        txt.setEditable(false);
        txt.setFont(new Font("SansSerif", Font.PLAIN, 11)); 
        txt.setBackground(Color.WHITE);
        tarjeta.add(txt, BorderLayout.CENTER);

        // AQUÍ HE ELIMINADO EL CÓDIGO QUE AGREGABA LOS BOTONES AZUL Y ROJO
        // Ya no hay botones en la tarjeta de la lista.

        return tarjeta;
    }

    /**
     * Lógica de envío/actualización de la reseña: validaciones y llamadas al controlador.
     */
    private void enviarAccion() {
        int calificacion = estrellasInput.getValor();
        String texto = txtResena.getText().trim();
        if (calificacion <= 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una calificación", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!texto.isEmpty() && (texto.length() < 10 || texto.length() > 500)) {
            JOptionPane.showMessageDialog(this, "El texto debe tener entre 10 y 500 caracteres", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                if (resenaUsuario == null) {
                    controlador.enviarResena(idLibro, calificacion, texto);
                    JOptionPane.showMessageDialog(this, "Reseña enviada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    controlador.actualizarResena(resenaUsuario.getId(), calificacion, texto);
                    JOptionPane.showMessageDialog(this, "Reseña actualizada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
                refresh();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Nueva función para manejar la eliminación desde el botón del formulario
    private void eliminarAccion() {
        if (resenaUsuario == null) return;

        int ok = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar tu reseña?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            try {
                controlador.eliminarResena(resenaUsuario.getId());
                resenaUsuario = null; // Limpiar referencia local
                refresh(); // Esto ocultará el botón eliminar y reseteará el formulario
                JOptionPane.showMessageDialog(this, "Reseña eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}