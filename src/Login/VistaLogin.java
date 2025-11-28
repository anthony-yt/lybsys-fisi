package Login;

import recursos.PlaceHolder;
import javax.swing.*;
import java.awt.*;

public class VistaLogin extends javax.swing.JFrame {

    private javax.swing.JButton btnIniciar;
    private javax.swing.JButton btnCrearCuenta;
    private javax.swing.JCheckBox chkRecordar;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblError;
    private javax.swing.JPanel panelPrincipal;

    public VistaLogin() {
        super("Iniciar Sesión");
        initComponents();
        configurarPlaceholders();
        this.setLocationRelativeTo(null);
    }

    private void configurarPlaceholders() {
        new PlaceHolder("Correo Electrónico", txtCorreo);
        new PlaceHolder("Contraseña", txtPassword);
    }

    private void initComponents() {
        panelPrincipal = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        chkRecordar = new javax.swing.JCheckBox();
        btnIniciar = new javax.swing.JButton();
        btnCrearCuenta = new javax.swing.JButton();
        lblError = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recursos/login.jpg")));

        lblTitulo.setFont(new java.awt.Font("Segoe UI Black", 0, 32));
        lblTitulo.setForeground(new java.awt.Color(20, 125, 255));
        lblTitulo.setText("LybSys FISI");

        txtCorreo.setFont(new java.awt.Font("SimSun", 1, 14));
        txtPassword.setFont(new java.awt.Font("SimSun", 1, 14));

        chkRecordar.setBackground(new java.awt.Color(255, 255, 255));
        chkRecordar.setFont(new java.awt.Font("Segoe UI", 0, 12));
        chkRecordar.setText("Recordarme");

        btnIniciar.setBackground(new java.awt.Color(240, 240, 240));
        btnIniciar.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnIniciar.setText("Iniciar Sesión");
        btnIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnCrearCuenta.setFont(new java.awt.Font("Segoe UI", 0, 12));
        btnCrearCuenta.setForeground(new java.awt.Color(20, 125, 255));
        btnCrearCuenta.setText("¿No tienes cuenta? Regístrate aquí");
        btnCrearCuenta.setBorderPainted(false);
        btnCrearCuenta.setContentAreaFilled(false);
        btnCrearCuenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblError.setForeground(new java.awt.Color(255, 51, 51));
        lblError.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblError.setText(" ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panelPrincipal);
        panelPrincipal.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addComponent(lblImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCorreo)
                            .addComponent(txtPassword)
                            .addComponent(chkRecordar)
                            .addComponent(btnIniciar, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(lblError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCrearCuenta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
                        .addGap(40, 40, 40))
                )
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                    .addGap(60, 60, 60)
                    .addComponent(lblTitulo)
                    .addGap(40, 40, 40)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)
                    .addComponent(chkRecordar)
                    .addGap(10, 10, 10)
                    .addComponent(lblError)
                    .addGap(20, 20, 20)
                    .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)
                    .addComponent(btnCrearCuenta)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                )
        );

        javax.swing.GroupLayout frameLayout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(frameLayout);
        frameLayout.setHorizontalGroup(
            frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        frameLayout.setVerticalGroup(
            frameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();

        this.getRootPane().setDefaultButton(btnIniciar);
    }

    public String getCorreo() { return txtCorreo.getText().trim(); }
    public String getPassword() { return new String(txtPassword.getPassword()); }
    public boolean isRecordarSeleccionado() { return chkRecordar.isSelected(); }
    public javax.swing.JButton getBtnIniciar() { return btnIniciar; }
    public javax.swing.JButton getBtnCrearCuenta() { return btnCrearCuenta; }

    public void mostrarError(String mensaje) {
        lblError.setText("<html><center>" + mensaje + "</center></html>");
        lblError.setVisible(true);
    }

    public void ocultarError() {
        lblError.setText(" ");
    }
}