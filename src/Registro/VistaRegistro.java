package Registro;

import recursos.PlaceHolder;

/**
 * Representa la interfaz gráfica utilizada durante el proceso
 * de registro de nuevos usuarios dentro del sistema.
 *
 * <p> Su responsabilidad principal es mostrar los campos de entrada,
 * proveer getters para que el controlador acceda a ellos y notificar
 * eventos al controlador mediante listeners.</p>
 *
 * <p>En el constructor se configuran los componentes, se inicializan los
 * placeholders de los campos de texto y se centra la ventana en pantalla.</p>
 */
public class VistaRegistro extends javax.swing.JFrame {   
    
    /** Logger general de la vista. */
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(VistaRegistro.class.getName());
    
    /** Controlador que gestiona los eventos de la vista. */
    private ControladorRegistro controlador;
    
    /**
     * Permite establecer el controlador que manejará los eventos
     * de esta vista.
     *
     * @param controlador instancia del controlador correspondiente.
     */
    public void establecerControlador(ControladorRegistro controlador) {
        this.controlador = controlador;
    }
    
    /**
     * Constructor principal de la vista.
     *
     * <p>Realiza las siguientes acciones:</p>
     * <ul>
     *   <li>Inicializa todos los componentes generados por el diseñador.</li>
     *   <li>Centra la ventana en la pantalla.</li>
     *   <li>Configura los {@code PlaceHolder} utilizados para guiar al usuario
     *       en los campos de entrada.</li>
     * </ul>
     *
     * <p>El título de la ventana se establece como {@code "Registro"}.</p>
     *
     * <p>Esta clase no maneja lógica de validación ni acciones; dichas
     * responsabilidades corresponden al controlador asociado.</p>
     */
    public VistaRegistro() {
        super("Registro");
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Configuración de PlaceHolders
        PlaceHolder usuarioPH = new PlaceHolder("Usuario", txtUsuario);
        PlaceHolder correoElectronicoPH = new PlaceHolder("Correo Electronico (unmsm.edu.pe)", txtCorreoElectronico);
        PlaceHolder codigoEstudiantePH = new PlaceHolder("Codigo de Estudiante", txtCodigoEstudiante);
        PlaceHolder contrasenaPH = new PlaceHolder("**********", txtContrasena);
        PlaceHolder confirmacionContrasenaPH = new PlaceHolder("**********", txtConfirmacionContrasena);
    }

    // ───────────────────────────────────────────────────────────────
    // GETTERS
    // ───────────────────────────────────────────────────────────────
    
    /**
     * Obtiene el campo de texto del nombre de usuario.
     *
     * @return componente gráfico asociado al nombre de usuario
     */
    public javax.swing.JTextField getTxtUsuario() { return txtUsuario; }

    /**
     * Obtiene el campo de texto del correo institucional.
     *
     * @return componente de entrada para el correo institucional
     */
    public javax.swing.JTextField getTxtCorreoElectronico() { return txtCorreoElectronico; }

    /**
     * Obtiene el campo destinado al código de estudiante.
     *
     * @return campo de texto del código de estudiante
     */
    public javax.swing.JTextField getTxtCodigoEstudiante() { return txtCodigoEstudiante; }

    /**
     * Retorna el campo de ingreso de contraseña.
     *
     * @return componente gráfico asociado a la contraseña
     */
    public javax.swing.JPasswordField getTxtContrasena() { return txtContrasena; }

    /**
     * Retorna el campo donde se ingresa la confirmación de contraseña.
     *
     * @return campo gráfico para confirmar la contraseña
     */
    public javax.swing.JPasswordField getTxtConfirmacionContrasena() { return txtConfirmacionContrasena; }

    /**
     * Botón principal utilizado para iniciar el proceso de registro.
     *
     * @return botón "Registrarse"
     */
    public javax.swing.JButton getBtnRegistrar() { return btnRegistrar; }

    /**
     * Botón que permite navegar hacia la ventana de inicio de sesión.
     *
     * @return botón "Iniciar sesión"
     */
    public javax.swing.JButton getBtnIniciarSesion() { return btnIniciarSesion; }

    /**
     * Barra que representa visualmente el nivel de seguridad
     * de la contraseña ingresada.
     *
     * @return barra de progreso utilizada para mostrar el nivel de seguridad
     */
    public javax.swing.JProgressBar getBarraProgreso() { return barraProgreso; }

    /**
     * Etiqueta que muestra el cumplimiento del requisito
     * "contener una letra".
     *
     * @return etiqueta asociada al requisito de letra
     */
    public javax.swing.JLabel getLblRequisitoLetra() { return lblRequisito1; }

    /**
     * Etiqueta que muestra el cumplimiento del requisito
     * "contener un carácter especial".
     *
     * @return etiqueta asociada al requisito de carácter especial
     */
    public javax.swing.JLabel getLblRequisitoEspecial() { return lblRequisito2; }

    /**
     * Etiqueta que muestra el cumplimiento del requisito
     * "contener un número".
     *
     * @return etiqueta del requisito de número
     */
    public javax.swing.JLabel getLblRequisitoNumero() { return lblRequisito3; }

    /**
     * Etiqueta que muestra el cumplimiento del requisito
     * "longitud mínima de contraseña".
     *
     * @return etiqueta del requisito de longitud
     */
    public javax.swing.JLabel getLblRequisitoLongitud() { return lblRequisito4; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        txtCorreoElectronico = new javax.swing.JTextField();
        txtCodigoEstudiante = new javax.swing.JTextField();
        txtContrasena = new javax.swing.JPasswordField();
        txtConfirmacionContrasena = new javax.swing.JPasswordField();
        btnRegistrar = new javax.swing.JButton();
        barraProgreso = new javax.swing.JProgressBar();
        lblTitulo = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblRequisito1 = new javax.swing.JLabel();
        lblRequisito2 = new javax.swing.JLabel();
        lblRequisito3 = new javax.swing.JLabel();
        lblRequisito4 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        btnIniciarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtCorreoElectronico.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        txtCorreoElectronico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorreoElectronicoActionPerformed(evt);
            }
        });

        txtCodigoEstudiante.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        txtCodigoEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodigoEstudianteActionPerformed(evt);
            }
        });

        txtContrasena.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        txtContrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtContrasenaActionPerformed(evt);
            }
        });

        txtConfirmacionContrasena.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N

        btnRegistrar.setText("Registrarse");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI Black", 0, 32)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(20, 125, 255));
        lblTitulo.setText("LybSys FISI");

        txtUsuario.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        txtUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsuarioActionPerformed(evt);
            }
        });

        lblRequisito1.setText("▶ Al menos debería tener una letra.");
        lblRequisito1.setPreferredSize(new java.awt.Dimension(266, 22));

        lblRequisito2.setText("▶ Al menos debería tener una caracter especial.");
        lblRequisito2.setPreferredSize(new java.awt.Dimension(266, 22));

        lblRequisito3.setText("▶ Al menos deberia tener un número.");
        lblRequisito3.setPreferredSize(new java.awt.Dimension(266, 22));

        lblRequisito4.setText("▶ Debería tener 8 caracteres como mínimo.");
        lblRequisito4.setPreferredSize(new java.awt.Dimension(266, 22));

        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/registro.jpg"))); // NOI18N

        btnIniciarSesion.setBackground(javax.swing.UIManager.getDefaults().getColor("window"));
        btnIniciarSesion.setForeground(new java.awt.Color(20, 125, 255));
        btnIniciarSesion.setText("¿Ya tienes una cuenta? Inicia sesión");
        btnIniciarSesion.setBorderPainted(false);
        btnIniciarSesion.setContentAreaFilled(false);
        btnIniciarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarSesionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblRequisito1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRequisito2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRequisito3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblRequisito4, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtContrasena)
                                .addComponent(txtCodigoEstudiante)
                                .addComponent(txtCorreoElectronico)
                                .addComponent(barraProgreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtUsuario)
                                .addComponent(txtConfirmacionContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(lblTitulo))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(btnRegistrar))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(71, 71, 71)
                                .addComponent(btnIniciarSesion)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addGap(18, 18, 18)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCorreoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCodigoEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(barraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(lblRequisito1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRequisito2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRequisito3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblRequisito4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtConfirmacionContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRegistrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnIniciarSesion)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        txtCorreoElectronico.getAccessibleContext().setAccessibleName("Correo Electrónico");
        txtCorreoElectronico.getAccessibleContext().setAccessibleDescription("Correo Electrónico (@unmsm.edu.pe)");
        txtCodigoEstudiante.getAccessibleContext().setAccessibleName("Código de Estudiante");
        txtCodigoEstudiante.getAccessibleContext().setAccessibleDescription("Código de Estudiante");
        txtContrasena.getAccessibleContext().setAccessibleName("Contraseña");
        txtContrasena.getAccessibleContext().setAccessibleDescription("Contraseña");
        txtConfirmacionContrasena.getAccessibleContext().setAccessibleName("confirmacionContraseña");
        txtConfirmacionContrasena.getAccessibleContext().setAccessibleDescription("confirmacionContraseña");
        txtUsuario.getAccessibleContext().setAccessibleName("Usuario");
        txtUsuario.getAccessibleContext().setAccessibleDescription("Usuario");

        pack();
    }// </editor-fold>                        

    private void txtCorreoElectronicoActionPerformed(java.awt.event.ActionEvent evt) {                                                     
        // TODO add your handling code here:
    }                                                    

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void txtUsuarioActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void txtCodigoEstudianteActionPerformed(java.awt.event.ActionEvent evt) {                                                    
        // TODO add your handling code here:
    }                                                   

    private void txtContrasenaActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             

    private void btnIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        // TODO add your handling code here:
    }                                                

    /**
     * Punto de entrada principal para ejecutar la vista de registro.
     *
     * <p>Configura el estilo visual Nimbus (si está disponible) y crea la
     * instancia de la vista junto con su modelo y controlador. Finalmente,
     * muestra la ventana de registro al usuario.</p>
     *
     * @param args argumentos de línea de comandos (no utilizados)
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

        java.awt.EventQueue.invokeLater(() -> {
            VistaRegistro vista = new VistaRegistro();
            ModeloRegistro modelo = new ModeloRegistro();
            new ControladorRegistro(modelo, vista);
            vista.setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JProgressBar barraProgreso;
    private javax.swing.JButton btnIniciarSesion;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblRequisito1;
    private javax.swing.JLabel lblRequisito2;
    private javax.swing.JLabel lblRequisito3;
    private javax.swing.JLabel lblRequisito4;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTextField txtCodigoEstudiante;
    private javax.swing.JPasswordField txtConfirmacionContrasena;
    private javax.swing.JPasswordField txtContrasena;
    private javax.swing.JTextField txtCorreoElectronico;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration                   
}