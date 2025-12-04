package Login;

import recursos.PlaceHolder;
import javax.swing.*;
import java.awt.*;

/**
 * Vista gráfica para el inicio de sesión de usuarios.
 * <p>
 * Implementa un {@link JFrame} con un diseño de dos columnas: una imagen
 * ilustrativa a la izquierda y el formulario de ingreso a la derecha.
 * Incluye componentes para ingresar credenciales y visualizar el Captcha.
 * </p>
 */
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
    
    // Componentes para el Captcha
    private javax.swing.JLabel lblCaptchaImagen;
    private javax.swing.JTextField txtCaptcha;

    /**
     * Constructor de la vista. Inicializa los componentes gráficos, configura
     * los placeholders y centra la ventana en la pantalla.
     */
    public VistaLogin() {
        super("Iniciar Sesión");
        initComponents();
        configurarPlaceholders();
        configurarImagenLateral();
        this.setLocationRelativeTo(null);
    }

    /**
     * Carga y redimensiona la imagen lateral del login para ajustarla al panel.
     * <p>
     * Se utiliza una imagen de recursos y se escala suavemente para evitar
     * distorsiones pixeladas.
     * </p>
     */
    private void configurarImagenLateral() {
         try {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/recursos/login.jpg"));
            Image imagen = iconoOriginal.getImage();
            Image imagenRedimensionada = imagen.getScaledInstance(306, 580, java.awt.Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(imagenRedimensionada));
        } catch (Exception e) {
            System.err.println("Error al cargar imagen lateral: " + e.getMessage());
        }
    }

    /**
     * Configura los textos fantasma (Placeholders) en los campos de texto
     * para guiar al usuario sobre qué información ingresar.
     */
    private void configurarPlaceholders() {
        new PlaceHolder("Correo Institucional (@unmsm.edu.pe)", txtCorreo);
        new PlaceHolder("Contraseña", txtPassword);
        new PlaceHolder("Ingrese el código de la imagen", txtCaptcha);
    }

    /**
     * Inicializa y organiza todos los componentes visuales de la interfaz
     * utilizando {@link GroupLayout}.
     */
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
        
        lblCaptchaImagen = new javax.swing.JLabel();
        txtCaptcha = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Segoe UI Black", 0, 32));
        lblTitulo.setForeground(new java.awt.Color(20, 125, 255));
        lblTitulo.setText("LybSys FISI");
        
        txtCorreo.setFont(new java.awt.Font("SimSun", 1, 14));
        txtPassword.setFont(new java.awt.Font("SimSun", 1, 14));

        chkRecordar.setBackground(new java.awt.Color(255, 255, 255));
        chkRecordar.setFont(new java.awt.Font("Segoe UI", 0, 12));
        chkRecordar.setText("Recordarme");

        lblCaptchaImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblCaptchaImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(200, 200, 200)));
        lblCaptchaImagen.setToolTipText("Clic para recargar imagen");
        lblCaptchaImagen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        txtCaptcha.setFont(new java.awt.Font("SimSun", 1, 14));
        txtCaptcha.setHorizontalAlignment(SwingConstants.CENTER);

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
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblCaptchaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCaptcha, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addGap(50, 50, 50)
                    .addComponent(lblTitulo)
                    .addGap(30, 30, 30)
                    .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(20, 20, 20)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)
                    .addComponent(chkRecordar)
                    .addGap(15, 15, 15)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblCaptchaImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCaptcha, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(15, 15, 15)
                    .addComponent(lblError)
                    .addGap(15, 15, 15)
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
    public String getCaptchaRespuesta() { return txtCaptcha.getText().trim(); }
    
    public javax.swing.JButton getBtnIniciar() { return btnIniciar; }
    public javax.swing.JButton getBtnCrearCuenta() { return btnCrearCuenta; }
    
    /**
     * Obtiene el componente etiqueta donde se muestra la imagen del captcha.
     * Utilizado para agregarle listeners de ratón.
     * @return JLabel del captcha.
     */
    public javax.swing.JLabel getLblCaptchaImagen() { return lblCaptchaImagen; } 

    /**
     * Actualiza la imagen mostrada en el componente del captcha.
     * @param icono Nuevo icono con la imagen generada.
     */
    public void setCaptchaImagen(Icon icono) {
        lblCaptchaImagen.setIcon(icono);
    }
    
    /**
     * Muestra un mensaje de error visible en la interfaz en color rojo.
     * @param mensaje Texto del error a mostrar.
     */
    public void mostrarError(String mensaje) {
        lblError.setText("<html><center>" + mensaje + "</center></html>");
        lblError.setVisible(true);
    }

    /**
     * Oculta el mensaje de error de la interfaz.
     */
    public void ocultarError() { lblError.setText(" "); }
}