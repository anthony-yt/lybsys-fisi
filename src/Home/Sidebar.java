
package Home;

import Catalogo.*;
import perfil.*;

/**
 * Ventana principal con la barra lateral que permite cambiar entre vistas (Home, Catálogo, Perfil).
 * Implementa botones para seleccionar la vista activa y contiene el contenedor `layerpane`.
 */
public class Sidebar extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Sidebar.class.getName());
    
    vistaHome vHome = new vistaHome();
    vistaCatalogo vCatalog = new vistaCatalogo();
    vistaPerfil vPerfil = new vistaPerfil();
    
    /**
     * Constructor. Inicializa componentes y añade vistas al panel en capas.
     */
    public Sidebar() {
        initComponents();
        layerpane.add(vHome);
        layerpane.add(vCatalog);
        layerpane.add(vPerfil);
        vHome.setVisible(false);
        vCatalog.setVisible(false);
        vPerfil.setVisible(false);
    }
                  
    /**
     * Generado por NetBeans: crea e inicializa componentes Swing y layout.
     */
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnhome = new javax.swing.JButton();
        btncatalog = new javax.swing.JButton();
        btnperfil = new javax.swing.JButton();
        imglogofisi = new javax.swing.JLabel();
        layerpane = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(40, 129, 217));
        jPanel1.setPreferredSize(new java.awt.Dimension(130, 420));

        btnhome.setBackground(new java.awt.Color(255, 204, 0));
        btnhome.setFont(new java.awt.Font("Dubai Medium", 1, 16)); // NOI18N
        btnhome.setForeground(new java.awt.Color(0, 51, 102));
        btnhome.setText("INICIO");
        btnhome.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnhome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhomeActionPerformed(evt);
            }
        });

        btncatalog.setBackground(new java.awt.Color(255, 204, 0));
        btncatalog.setFont(new java.awt.Font("Dubai Medium", 1, 16)); // NOI18N
        btncatalog.setForeground(new java.awt.Color(0, 51, 102));
        btncatalog.setText("CATÁLOGO");
        btncatalog.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btncatalog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncatalogActionPerformed(evt);
            }
        });

        btnperfil.setBackground(new java.awt.Color(255, 204, 0));
        btnperfil.setFont(new java.awt.Font("Dubai Medium", 1, 16)); // NOI18N
        btnperfil.setForeground(new java.awt.Color(0, 51, 102));
        btnperfil.setText("PERFIL");
        btnperfil.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnperfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnperfilActionPerformed(evt);
            }
        });

        imglogofisi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo-fisi.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btncatalog, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnhome, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnperfil, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(imglogofisi, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(imglogofisi, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnhome)
                .addGap(46, 46, 46)
                .addComponent(btncatalog)
                .addGap(44, 44, 44)
                .addComponent(btnperfil)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

        layerpane.setPreferredSize(new java.awt.Dimension(700, 420));
        layerpane.setLayout(new java.awt.CardLayout());
        getContentPane().add(layerpane, java.awt.BorderLayout.CENTER);

        pack();
    }                      

    /**
     * Muestra la vista Home y oculta las demás vistas.
     */
    private void btnhomeActionPerformed(java.awt.event.ActionEvent evt) {
        vHome.setVisible(true);
        vCatalog.setVisible(false);
        vPerfil.setVisible(false);
    }                                       

    /**
     * Muestra la vista Catálogo y oculta las demás vistas.
     */
    private void btncatalogActionPerformed(java.awt.event.ActionEvent evt) {
        vHome.setVisible(false);
        vCatalog.setVisible(true);
        vPerfil.setVisible(false);
    }                                          

    /**
     * Muestra la vista Perfil y oculta las demás vistas.
     */
    private void btnperfilActionPerformed(java.awt.event.ActionEvent evt) {
        vHome.setVisible(false);
        vCatalog.setVisible(false);
        vPerfil.setVisible(true);
    }                                         

    /**
     * Punto de entrada principal para iniciar la aplicación (útil para pruebas rápidas).
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(String args[]) {

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

        java.awt.EventQueue.invokeLater(() -> new Sidebar().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton btncatalog;
    private javax.swing.JButton btnhome;
    private javax.swing.JButton btnperfil;
    private javax.swing.JLabel imglogofisi;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLayeredPane layerpane;
    // End of variables declaration                   
}
