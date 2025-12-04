package Perfil;

import java.awt.*;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.table.*;

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
                "Leer"
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
            new Object[]{"ID", "Título", "Estado", "Vence", "Acción"},
            0
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return col == 4;
            }
        };

        tablaPrestamos = new JTable(modeloTabla);
        tablaPrestamos.setRowHeight(35);

        tablaPrestamos.getColumnModel().getColumn(4).setCellRenderer(new BotonRenderer());
        tablaPrestamos.getColumnModel().getColumn(4).setCellEditor(new BotonEditor());

        JScrollPane scroll = new JScrollPane(tablaPrestamos);
        add(scroll, BorderLayout.CENTER);
    }

    private class BotonRenderer extends JButton implements TableCellRenderer {
        public BotonRenderer() {
            setText("Leer");
            setBackground(new Color(255, 204, 0));
            setForeground(Color.BLACK);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {
            return this;
        }
    }

    private class BotonEditor extends DefaultCellEditor {
        private JButton boton;
        private int idLibro;

        public BotonEditor() {
            super(new JTextField());
            boton = new JButton("Leer");
            boton.setBackground(new Color(255, 204, 0));

            boton.addActionListener(e -> {
                if (listener != null) {
                    listener.onLeerLibro(idLibro);
                }
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int col) {

            idLibro = (int) table.getValueAt(row, 0);
            return boton;
        }
    }
}
