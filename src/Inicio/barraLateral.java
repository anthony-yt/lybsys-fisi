
package Inicio;

import javax.swing.*;
import java.awt.*;
import Catalogo.*;
import Perfil.*;

public class barraLateral extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(barraLateral.class.getName());
    
    private JButton btnCatalogo;
    private JButton btnInicio;
    private JButton btnPerfil;
    private JLabel imgLogoFisi;
    private JPanel panelBarraLateral;
    private JLayeredPane panelEnCapas;

    vistaInicio vistaInicio = new vistaInicio();
    vistaCatalogo vistaCatalogo = new vistaCatalogo();
    vistaPerfil vistaPerfil = new vistaPerfil();
    
    public barraLateral() {
        initComponents();
        panelEnCapas.add(vistaInicio);
        panelEnCapas.add(vistaCatalogo);
        panelEnCapas.add(vistaPerfil);
        vistaInicio.setVisible(false);
        vistaCatalogo.setVisible(false);
        vistaPerfil.setVisible(false);
    }
                  
    private void initComponents() {

        panelBarraLateral = new JPanel();
        btnInicio = new JButton();
        btnCatalogo = new JButton();
        btnPerfil = new JButton();
        imgLogoFisi = new JLabel();
        panelEnCapas = new JLayeredPane();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panelBarraLateral.setBackground(new Color(40, 129, 217));
        panelBarraLateral.setPreferredSize(new Dimension(130, 420));

        btnInicio.setBackground(new Color(255, 204, 0));
        btnInicio.setFont(new Font("Dubai Medium", 1, 16));
        btnInicio.setForeground(new Color(0, 51, 102));
        btnInicio.setText("INICIO");
        btnInicio.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        btnCatalogo.setBackground(new Color(255, 204, 0));
        btnCatalogo.setFont(new Font("Dubai Medium", 1, 16));
        btnCatalogo.setForeground(new Color(0, 51, 102));
        btnCatalogo.setText("CATÁLOGO");
        btnCatalogo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCatalogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncatalogActionPerformed(evt);
            }
        });

        btnPerfil.setBackground(new Color(255, 204, 0));
        btnPerfil.setFont(new Font("Dubai Medium", 1, 16));
        btnPerfil.setForeground(new Color(0, 51, 102));
        btnPerfil.setText("PERFIL");
        btnPerfil.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPerfilActionPerformed(evt);
            }
        });

        imgLogoFisi.setIcon(new ImageIcon(getClass().getResource("/recursos/logo-fisi.png")));

        GroupLayout jPanel1Layout = new GroupLayout(panelBarraLateral);
        panelBarraLateral.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCatalogo, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInicio, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnPerfil, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(imgLogoFisi, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(imgLogoFisi, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(btnInicio)
                .addGap(46, 46, 46)
                .addComponent(btnCatalogo)
                .addGap(44, 44, 44)
                .addComponent(btnPerfil)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        getContentPane().add(panelBarraLateral, BorderLayout.LINE_START);

        panelEnCapas.setPreferredSize(new Dimension(700, 420));
        panelEnCapas.setLayout(new CardLayout());
        getContentPane().add(panelEnCapas, BorderLayout.CENTER);

        pack();
    }                      

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {                                        
        vistaInicio.setVisible(true);
        vistaCatalogo.setVisible(false);
        vistaPerfil.setVisible(false);
    }                                       

    private void btncatalogActionPerformed(java.awt.event.ActionEvent evt) {                                           
        vistaInicio.setVisible(false);
        vistaCatalogo.setVisible(true);
        vistaPerfil.setVisible(false);
    }                                          

    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {                                          
        vistaInicio.setVisible(false);
        vistaCatalogo.setVisible(false);
        vistaPerfil.setVisible(true);
    }                                         

    public static void main(String args[]) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        EventQueue.invokeLater(() -> new barraLateral().setVisible(true));
    }
           
}
