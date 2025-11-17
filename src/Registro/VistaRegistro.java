package Registro;

import recursos.PlaceHolder;

/**
 * Representa la interfaz gráfica utilizada durante el proceso
 * de registro de nuevos usuarios dentro del sistema.
 *
 * <p>Esta clase forma parte del patrón MVC actuando como la "Vista".
 * Su responsabilidad principal es mostrar los campos de entrada,
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
     * de esta vista siguiendo el patrón MVC.
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
     *   <li>Inicializa los componentes generados por NetBeans.</li>
     *   <li>Centra la ventana en la pantalla.</li>
     *   <li>Configura los PlaceHolder para cada campo de entrada.</li>
     * </ul>
     *
     * <p>El título de la ventana se establece como "Registro".</p>
     */
    public VistaRegistro() {
        super("Registro");
        initComponents();
        this.setLocationRelativeTo(null);
        
        // Configuración de PlaceHolders
        PlaceHolder usuarioPH = new PlaceHolder("Usuario", usuario);
        PlaceHolder correoElectronicoPH = new PlaceHolder("Correo Electronico (unmsm.edu.pe)", correoElectronico);
        PlaceHolder codigoEstudiantePH = new PlaceHolder("Codigo de Estudiante", codigoEstudiante);
        PlaceHolder contrasenaPH = new PlaceHolder("**********", contrasena);
        PlaceHolder confirmacionContrasenaPH = new PlaceHolder("**********", confirmacionContrasena);
    }

    // ───────────────────────────────────────────────────────────────
    // GETTERS
    // ───────────────────────────────────────────────────────────────
    
    public javax.swing.JTextField getCampoUsuario() { return usuario; }
    public javax.swing.JTextField getCampoCorreo() { return correoElectronico; }
    public javax.swing.JTextField getCampoCodigoEstudiante() { return codigoEstudiante; }
    public javax.swing.JPasswordField getCampoContrasena() { return contrasena; }
    public javax.swing.JPasswordField getCampoConfirmacionContrasena() { return confirmacionContrasena; }
    public javax.swing.JButton getBotonRegistrar() { return botonRegistrar; }
    public javax.swing.JProgressBar getBarraProgreso() { return barraProgreso; }

    public javax.swing.JLabel getEtiquetaRequisitoLetra() { return requisito1; }
    public javax.swing.JLabel getEtiquetaRequisitoEspecial() { return requisito2; }
    public javax.swing.JLabel getEtiquetaRequisitoNumero() { return requisito3; }
    public javax.swing.JLabel getEtiquetaRequisitoLongitud() { return requisito4; }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        correoElectronico = new javax.swing.JTextField();
        codigoEstudiante = new javax.swing.JTextField();
        contrasena = new javax.swing.JPasswordField();
        confirmacionContrasena = new javax.swing.JPasswordField();
        botonRegistrar = new javax.swing.JButton();
        barraProgreso = new javax.swing.JProgressBar();
        titulo = new javax.swing.JLabel();
        usuario = new javax.swing.JTextField();
        requisito1 = new javax.swing.JLabel();
        requisito2 = new javax.swing.JLabel();
        requisito3 = new javax.swing.JLabel();
        requisito4 = new javax.swing.JLabel();
        diseño = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        correoElectronico.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        correoElectronico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correoElectronicoActionPerformed(evt);
            }
        });

        codigoEstudiante.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        codigoEstudiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                codigoEstudianteActionPerformed(evt);
            }
        });

        contrasena.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        contrasena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasenaActionPerformed(evt);
            }
        });

        confirmacionContrasena.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N

        botonRegistrar.setText("Registrarse");
        botonRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRegistrarActionPerformed(evt);
            }
        });

        titulo.setFont(new java.awt.Font("Segoe UI Black", 0, 32)); // NOI18N
        titulo.setForeground(new java.awt.Color(20, 125, 255));
        titulo.setText("LybSys FISI");

        usuario.setFont(new java.awt.Font("SimSun", 1, 14)); // NOI18N
        usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuarioActionPerformed(evt);
            }
        });

        requisito1.setText("▶ Al menos debería tener una letra.");
        requisito1.setPreferredSize(new java.awt.Dimension(266, 22));

        requisito2.setText("▶ Al menos debería tener una caracter especial.");
        requisito2.setPreferredSize(new java.awt.Dimension(266, 22));

        requisito3.setText("▶ Al menos deberia tener un número.");
        requisito3.setPreferredSize(new java.awt.Dimension(266, 22));

        requisito4.setText("▶ Debería tener 8 caracteres como mínimo.");
        requisito4.setPreferredSize(new java.awt.Dimension(266, 22));

        diseño.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/registro.jpg"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(diseño, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(requisito1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(requisito2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(requisito3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(requisito4, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(contrasena)
                                .addComponent(codigoEstudiante)
                                .addComponent(correoElectronico)
                                .addComponent(barraProgreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(usuario)
                                .addComponent(confirmacionContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(titulo))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(134, 134, 134)
                                .addComponent(botonRegistrar)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo)
                .addGap(18, 18, 18)
                .addComponent(usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(correoElectronico, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(codigoEstudiante, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(contrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(barraProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(requisito1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requisito2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requisito3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(requisito4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(confirmacionContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(botonRegistrar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(diseño, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        correoElectronico.getAccessibleContext().setAccessibleName("Correo Electrónico");
        correoElectronico.getAccessibleContext().setAccessibleDescription("Correo Electrónico (@unmsm.edu.pe)");
        codigoEstudiante.getAccessibleContext().setAccessibleName("Código de Estudiante");
        codigoEstudiante.getAccessibleContext().setAccessibleDescription("Código de Estudiante");
        contrasena.getAccessibleContext().setAccessibleName("Contraseña");
        contrasena.getAccessibleContext().setAccessibleDescription("Contraseña");
        confirmacionContrasena.getAccessibleContext().setAccessibleName("confirmacionContraseña");
        confirmacionContrasena.getAccessibleContext().setAccessibleDescription("confirmacionContraseña");
        usuario.getAccessibleContext().setAccessibleName("Usuario");
        usuario.getAccessibleContext().setAccessibleDescription("Usuario");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void correoElectronicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correoElectronicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correoElectronicoActionPerformed

    private void botonRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRegistrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonRegistrarActionPerformed

    private void usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuarioActionPerformed

    private void codigoEstudianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_codigoEstudianteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_codigoEstudianteActionPerformed

    private void contrasenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contrasenaActionPerformed

    /**
     * Método principal: inicializa el Registro.
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgreso;
    private javax.swing.JButton botonRegistrar;
    private javax.swing.JTextField codigoEstudiante;
    private javax.swing.JPasswordField confirmacionContrasena;
    private javax.swing.JPasswordField contrasena;
    private javax.swing.JTextField correoElectronico;
    private javax.swing.JLabel diseño;
    private javax.swing.JLabel requisito1;
    private javax.swing.JLabel requisito2;
    private javax.swing.JLabel requisito3;
    private javax.swing.JLabel requisito4;
    private javax.swing.JLabel titulo;
    private javax.swing.JTextField usuario;
    // End of variables declaration//GEN-END:variables
}
