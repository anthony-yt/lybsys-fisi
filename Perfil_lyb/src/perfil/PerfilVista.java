package perfil;

import javax.swing.*;

import perfil.perfil_modelo.Prestamo;
import perfil.perfil_modelo.Usuario;

public class PerfilVista extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(PerfilVista.class.getName());
    
    private java.util.List<perfil.perfil_modelo.Prestamo> prestamosActuales = new java.util.ArrayList<>();

    public PerfilVista() {
        initComponents();
        javax.swing.SwingUtilities.invokeLater(() -> {
        setAvatarRecurso("/perfil/assets/avatar.jpg");
        javax.swing.table.TableColumn col = tabla_prestamos.getColumnModel().getColumn(4);
        col.setCellRenderer(new renderizarCelda());
        col.setCellEditor(new editorCelda());
        col.setMinWidth(110);
        col.setPreferredWidth(130);
    });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        label_avatar = new javax.swing.JLabel();
        Scroll_panel_prestamos = new javax.swing.JScrollPane();
        tabla_prestamos = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        label_correol = new javax.swing.JLabel();
        label_nombrel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 204));

        label_avatar.setText("AVATAR");

        Scroll_panel_prestamos.setBackground(new java.awt.Color(100, 255, 204));
        Scroll_panel_prestamos.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabla de Prestamos"));

        tabla_prestamos.setBackground(new java.awt.Color(51, 255, 100));
        tabla_prestamos.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tabla_prestamos.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        tabla_prestamos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Portada", "Título", "Estado", "Vencimiento", "Acciones"}
        ) {
            @Override
            public boolean isCellEditable(int row, int col) {
                // Solo la columna "Acciones" (índice 4) es editable
                return col == 4;
            }
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 0 ? javax.swing.Icon.class : String.class;
            }
        }
    );
    tabla_prestamos.setGridColor(new java.awt.Color(51, 204, 255));
    tabla_prestamos.setSelectionBackground(new java.awt.Color(51, 255, 204));
    tabla_prestamos.setDefaultEditor(Object.class, null);
    tabla_prestamos.getTableHeader().setReorderingAllowed(false);
    tabla_prestamos.setRowHeight(48);
    Scroll_panel_prestamos.setViewportView(tabla_prestamos);

    jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
    jLabel2.setText("Nombre:");

    jLabel3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
    jLabel3.setText("Correo:");

    label_correol.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
    label_correol.setText("----");

    label_nombrel.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
    label_nombrel.setText("----");

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(label_avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(label_correol, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(label_nombrel, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addComponent(Scroll_panel_prestamos, javax.swing.GroupLayout.PREFERRED_SIZE, 533, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(label_correol))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(label_nombrel)
                        .addComponent(jLabel3))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(14, 14, 14)
                    .addComponent(label_avatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(18, 18, 18)))
            .addComponent(Scroll_panel_prestamos, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap())
    );

    jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/perfil/assets/logo-fisi.png"))); // NOI18N

    jButton1.setBackground(new java.awt.Color(255, 255, 102));
    jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
    jButton1.setForeground(new java.awt.Color(0, 0, 255));
    jButton1.setText("INICIO");
    jButton1.setToolTipText("");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    jButton2.setBackground(new java.awt.Color(255, 255, 102));
    jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
    jButton2.setForeground(new java.awt.Color(0, 0, 204));
    jButton2.setText("CATALOGO");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });

    jButton3.setBackground(new java.awt.Color(255, 255, 102));
    jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
    jButton3.setForeground(new java.awt.Color(0, 0, 204));
    jButton3.setText("PERFIL");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(35, 35, 35)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addComponent(jLabel1))
            .addGap(28, 28, 28)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(32, 32, 32)
            .addComponent(jLabel1)
            .addGap(40, 40, 40)
            .addComponent(jButton1)
            .addGap(33, 33, 33)
            .addComponent(jButton2)
            .addGap(35, 35, 35)
            .addComponent(jButton3)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new PerfilVista().setVisible(true));
    }

        public void mostrarPrestamos(java.util.List<perfil.perfil_modelo.Prestamo> prestamos) {
    javax.swing.table.DefaultTableModel modelo =
        (javax.swing.table.DefaultTableModel) tabla_prestamos.getModel();
    modelo.setRowCount(0);

    if (prestamos == null || prestamos.isEmpty()) {
        javax.swing.JLabel vacio = new javax.swing.JLabel("Aún no tienes préstamos");
        vacio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Scroll_panel_prestamos.setViewportView(vacio);
        return;
    }

    prestamosActuales = prestamos;
    Scroll_panel_prestamos.setViewportView(tabla_prestamos);

    for (perfil.perfil_modelo.Prestamo p : prestamos) {
    javax.swing.Icon icono = cargarPortada(p.getPortada());

    modelo.addRow(new Object[]{
        icono,
        p.getTitulo(),
        p.getEstado(),
        p.getFechaVencimiento(),
        "ACCIONES"
    });
}

}
   
    public void setAvatar(String rutaImagen) {
        int w = label_avatar.getWidth() > 0 ? label_avatar.getWidth() : 96;
        int h = label_avatar.getHeight() > 0 ? label_avatar.getHeight() : 96;

        java.awt.Image img = new javax.swing.ImageIcon(rutaImagen).getImage();
        java.awt.Image esc = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        label_avatar.setIcon(new javax.swing.ImageIcon(esc));
        label_avatar.setText(null);
    }

    private void setAvatarRecurso(String resourcePath) {
        java.net.URL url = getClass().getResource(resourcePath);
        if (url == null) {
            System.err.println("No encuentro recurso: " + resourcePath);
            return;
        }
        int w = label_avatar.getWidth() > 0 ? label_avatar.getWidth() : 96;
        int h = label_avatar.getHeight() > 0 ? label_avatar.getHeight() : 96;

        java.awt.Image img = new javax.swing.ImageIcon(url).getImage();
        java.awt.Image esc = img.getScaledInstance(w, h, java.awt.Image.SCALE_SMOOTH);
        label_avatar.setIcon(new javax.swing.ImageIcon(esc));
        label_avatar.setText(null);
    }

    private javax.swing.Icon cargarPortada(String rutaRecurso) {
        if (rutaRecurso == null || rutaRecurso.isBlank()) return null;

        java.net.URL url = getClass().getClassLoader().getResource(rutaRecurso);
        if (url == null) {
            System.err.println("No encuentro portada: " + rutaRecurso);
            return null;
        }

        java.awt.Image img = new javax.swing.ImageIcon(url).getImage();
        java.awt.Image esc = img.getScaledInstance(40, 60, java.awt.Image.SCALE_SMOOTH);
        return new javax.swing.ImageIcon(esc);
    }


    public void setUsuario(Usuario u) {
        label_nombrel.setText(u.getNombre());
        label_correol.setText(u.getCorreo());

        if (u.getAvatar() != null) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                setAvatarRecurso(u.getAvatar());
            });
        }
    }

    public interface AccionesPrestamoListener { void onLeer(int bookId); }
    private AccionesPrestamoListener accionesListener;
    public void setAccionesPrestamoListener(AccionesPrestamoListener l){ this.accionesListener = l; }

    private static class ActionPanel extends javax.swing.JPanel {
        javax.swing.JButton btnLeer = new javax.swing.JButton("Leer");
        ActionPanel() {
            setOpaque(true);
            setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 6, 2));
            add(btnLeer);
            btnLeer.setFocusable(false);
            setBorder(javax.swing.BorderFactory.createEmptyBorder(0,4,0,4));
        }
    }
    private static class renderizarCelda extends ActionPanel implements javax.swing.table.TableCellRenderer {
        @Override public java.awt.Component getTableCellRendererComponent(
            javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return this;
        }
    }
    private class editorCelda extends javax.swing.AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private final ActionPanel panel = new ActionPanel();
        private int editingRow = -1;
        editorCelda() {
            panel.btnLeer.addActionListener(e -> {
                if (accionesListener != null && editingRow >= 0) {
                    var p = prestamosActuales.get(editingRow);
                    accionesListener.onLeer(p.getBookId());
                }
                stopCellEditing();
            });
        }
        @Override public Object getCellEditorValue() { return null; }
        @Override public java.awt.Component getTableCellEditorComponent(
            javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            editingRow = row;
            panel.setBackground(table.getSelectionBackground());
            return panel;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane Scroll_panel_prestamos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel label_avatar;
    private javax.swing.JLabel label_correol;
    private javax.swing.JLabel label_nombrel;
    private javax.swing.JTable tabla_prestamos;
    // End of variables declaration//GEN-END:variables
    
}
