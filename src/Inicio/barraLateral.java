
package Inicio;

import javax.swing.*;
import java.awt.*;
import Catalogo.*;
import Perfil.*;

/**
 * La clase barraLateral representa una barra lateral de navegación implementada como JFrame.
 * Proporciona una interfaz gráfica con botones para navegar entre diferentes vistas:
 * - INICIO: muestra la vista de inicio
 * - CATÁLOGO: muestra el catálogo de libros
 * - PERFIL: muestra el perfil del usuario
 * 
 * La clase utiliza un panel de capas (JLayeredPane) para gestionar la visualización
 * de múltiples vistas de manera alternada.
 * 
 * @author anthony-yt
 * @version 1.0
 */
public class barraLateral extends JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(barraLateral.class.getName());
    
    private JButton btnCatalogo;
    private JButton btnInicio;
    private JButton btnPerfil;
    private JLabel imgLogoFisi;
    private JPanel panelBarraLateral;
    private JLayeredPane panelEnCapas;

    private vistaInicio vistaInicio = new vistaInicio(this);
    private vistaCatalogo vistaCatalogo = new vistaCatalogo();
    private vistaPerfil vistaPerfil = new vistaPerfil();
    
    public vistaCatalogo getVistaCatalogo()
    {
        return vistaCatalogo;
    }
    /**
     * Constructor de la clase barraLateral.
     * Inicializa los componentes de la interfaz gráfica y configura
     * las vistas para que no sean visibles inicialmente.
     */
    public barraLateral() {
        initComponents();
        this.setLocationRelativeTo(null);
        // Agregar las tres vistas al panel de capas
        panelEnCapas.add(vistaInicio);
        panelEnCapas.add(vistaCatalogo);
        panelEnCapas.add(vistaPerfil);
        // Todas las vistas comienzan ocultas
        vistaInicio.setVisible(false);
        vistaCatalogo.setVisible(false);
        vistaPerfil.setVisible(false);
    }

    /**
     * Inicializa los componentes gráficos del frame.
     * Configura los botones de navegación, el panel de barra lateral,
     * el panel de capas y el logo de FISI.
     * También establece los listeners de acción para cada botón.
     */
    private void initComponents() {
        // Crear instancias de componentes
        panelBarraLateral = new JPanel();
        btnInicio = new JButton();
        btnCatalogo = new JButton();
        btnPerfil = new JButton();
        imgLogoFisi = new JLabel();
        panelEnCapas = new JLayeredPane();

        // Configurar cierre de la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Configurar panel lateral con color azul
        panelBarraLateral.setBackground(new Color(40, 129, 217));
        panelBarraLateral.setPreferredSize(new Dimension(130, 420));

        // Configurar botón INICIO
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

        // Configurar botón CATÁLOGO
        btnCatalogo.setBackground(new Color(255, 204, 0));
        btnCatalogo.setFont(new Font("Dubai Medium", 1, 16));
        btnCatalogo.setForeground(new Color(0, 51, 102));
        btnCatalogo.setText("CATÁLOGO");
        btnCatalogo.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCatalogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCatalogActionPerformed(evt);
            }
        });

        // Configurar botón PERFIL
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

        // Cargar y asignar logo de FISI
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

        // Configurar panel de capas para mostrar múltiples vistas
        panelEnCapas.setPreferredSize(new Dimension(700, 420));
        panelEnCapas.setLayout(new CardLayout());
        getContentPane().add(panelEnCapas, BorderLayout.CENTER);

        // Empaquetar y mostrar todos los componentes
        pack();
    }                      

    /**
     * Manejador de eventos del botón "INICIO".
     * Hace visible la vista de inicio y oculta las demás vistas.
     * 
     * @param evt evento de acción generado al hacer clic en el botón INICIO
     */
    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // Mostrar la vista de inicio
        vistaInicio.setVisible(true);
        // Ocultar las otras vistas
        vistaCatalogo.setVisible(false);
        vistaPerfil.setVisible(false);
    }                                       

    /**
     * Manejador de eventos del botón "CATÁLOGO".
     * Hace visible la vista del catálogo y oculta las demás vistas.
     * 
     * @param evt evento de acción generado al hacer clic en el botón CATÁLOGO
     */
    public void btnCatalogActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // Mostrar la vista del catálogo
        vistaInicio.setVisible(false);
        vistaCatalogo.setVisible(true);
        // Ocultar las otras vistas
        vistaPerfil.setVisible(false);
    }                                          

    /**
     * Manejador de eventos del botón "PERFIL".
     * Hace visible la vista del perfil y oculta las demás vistas.
     * 
     * @param evt evento de acción generado al hacer clic en el botón PERFIL
     */
    private void btnPerfilActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // Mostrar la vista del perfil
        vistaInicio.setVisible(false);
        vistaCatalogo.setVisible(false);
        vistaPerfil.setVisible(true);
    }                                         

    /**
     * Método principal que inicia la aplicación.
     * Configura el "Look and Feel" de Nimbus si está disponible
     * y lanza la ventana principal de la aplicación.
     * 
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String args[]) {
        try {
            // Buscar e instalar el Look and Feel "Nimbus" para mejorar la apariencia
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            // Registrar errores en la configuración del Look and Feel
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Crear y mostrar la ventana principal en el hilo de eventos
        EventQueue.invokeLater(() -> new barraLateral().setVisible(true));
    }
           
}
