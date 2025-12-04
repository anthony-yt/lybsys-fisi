package Perfil;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.*;

import Catalogo.ControladorCatalogo;
import Catalogo.VerDetalleLibro;
import Perfil.perfil_modelo.Prestamo;
import Perfil.perfil_modelo.Usuario;

public class PerfilVista extends JPanel {

    private static final Logger logger = Logger.getLogger(PerfilVista.class.getName());

    private JLabel lblNombreUsuario;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;

    private AccionesPrestamoListener listener;

    public interface AccionesPrestamoListener {
        void onLeerLibro(int bookId);
    }

    public PerfilVista() {
        initComponents();
    }

    public void setUsuario(Usuario usuario) {
        lblNombreUsuario.setText(usuario.getNombre());
    }

    public void mostrarPrestamos(List<Prestamo> prestamos) {
        modeloTabla.setRowCount(0);

        for (Prestamo p : prestamos) {
            modeloTabla.addRow(new Object[]{
                p.getBookId(),
                p.getTitulo(),
                p.getEstado(),
                p.getFechaVencimiento(),
            });
        }
    }

    public void setAccionesPrestamoListener(AccionesPrestamoListener listener) {
        this.listener = listener;
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(700, 420));

        JLabel titulo = new JLabel("Perfil del Usuario");
        titulo.setFont(new Font("Dubai Medium", Font.BOLD, 22));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblNombreUsuario = new JLabel("Usuario");
        lblNombreUsuario.setFont(new Font("Dubai Medium", Font.PLAIN, 18));
        panelTop.add(lblNombreUsuario);
        add(panelTop, BorderLayout.BEFORE_FIRST_LINE);

        modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Título", "Estado", "Vence"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 4;
            }
        };

        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setRowHeight(35);

        JScrollPane scroll = new JScrollPane(tablaPrestamos);
        add(scroll, BorderLayout.CENTER);
    }
}
