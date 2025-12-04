package Login;

import Registro.VistaRegistro;
import Registro.ControladorRegistro;
import Registro.ModeloRegistro;
import Inicio.barraLateral;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Auth.SessionManager;

/**
 * Controlador principal del módulo de Login.
 * <p>
 * Gestiona la comunicación entre {@link ModeloLogin} y {@link VistaLogin}.
 * Maneja eventos de usuario, lógica de validación de campos, generación dinámica
 * de imágenes Captcha y la navegación hacia otras vistas del sistema.
 * </p>
 */
public class ControladorLogin {

    private ModeloLogin modelo;
    private VistaLogin vista;

    /**
     * Constructor que inicializa el controlador.
     *
     * @param modelo Instancia del modelo de lógica de negocio.
     * @param vista Instancia de la interfaz gráfica.
     */
    public ControladorLogin(ModeloLogin modelo, VistaLogin vista) {
        this.modelo = modelo;
        this.vista = vista;
        inicializarEventos();
        generarYMostrarCaptcha();
    }

    /**
     * Solicita un nuevo código al modelo, genera una imagen con ruido visual
     * representando dicho código y la actualiza en la vista.
     */
    private void generarYMostrarCaptcha() {
        String codigo = modelo.generarCodigoCaptcha();
        
        int ancho = 120;
        int alto = 40;
        BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = imagen.createGraphics();
        
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, ancho, alto);
        
        Random r = new Random();
        g.setColor(Color.GRAY);
        for (int i = 0; i < 15; i++) {
            int x1 = r.nextInt(ancho);
            int y1 = r.nextInt(alto);
            int x2 = r.nextInt(ancho);
            int y2 = r.nextInt(alto);
            g.drawLine(x1, y1, x2, y2);
        }
        
        g.setFont(new Font("Arial", Font.BOLD, 22));
        g.setColor(new Color(20, 100, 200));
        g.drawString(codigo, 25, 28);
        
        g.dispose();
        
        vista.setCaptchaImagen(new ImageIcon(imagen));
    }

    /**
     * Configura los escuchadores de eventos para los botones y componentes interactivos.
     */
    private void inicializarEventos() {
        vista.getBtnIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarInicioSesion();
            }
        });

        vista.getBtnCrearCuenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegarARegistro();
            }
        });

        vista.getLblCaptchaImagen().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                generarYMostrarCaptcha();
            }
        });
    }

    /**
     * Ejecuta el flujo principal de inicio de sesión: validación de captcha,
     * validación de campos, autenticación y redirección.
     */
    private void manejarInicioSesion() {
        vista.ocultarError(); 
        
        String correo = vista.getCorreo();
        String password = vista.getPassword();
        String captchaInput = vista.getCaptchaRespuesta();

        if (!modelo.validarCaptcha(captchaInput)) {
            vista.mostrarError("Código incorrecto. Intenta de nuevo.");
            generarYMostrarCaptcha();
            return; 
        }

        if (!modelo.validarPasswordNoVacia(password)) {
            vista.mostrarError("La contraseña no puede estar vacía.");
            return;
        }

        if (!modelo.validarCorreoInstitucional(correo)) {
            vista.mostrarError("Usa tu correo institucional (@unmsm.edu.pe).");
            return;
        }

        String nombreUsuario = modelo.autenticarUsuario(correo, password);
        if (!nombreUsuario.equals("")) {
            JOptionPane.showMessageDialog(vista, 
                    "¡Bienvenido! Has iniciado sesión correctamente.", 
                    "Login Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
            SessionManager.agregarUsuarioActual(nombreUsuario);
            vista.dispose(); 
            new barraLateral().setVisible(true); 
        } else {
            vista.mostrarError("Credenciales incorrectas.");
            generarYMostrarCaptcha();
        }
    }

    /**
     * Cierra la vista actual y abre la ventana de Registro de Usuario.
     */
    private void navegarARegistro() {
        vista.dispose(); 
        VistaRegistro vRegistro = new VistaRegistro();
        ModeloRegistro mRegistro = new ModeloRegistro();
        new ControladorRegistro(mRegistro, vRegistro);
        vRegistro.setVisible(true);
    }
}