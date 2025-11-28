package Registro;

import Inicio.barraLateral;
import Login.*;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

/**
 * Controlador principal encargado de gestionar la comunicación entre
 * {@link ModeloRegistro} y {@link VistaRegistro}. 
 * 
 * <p>Coordina eventos de la interfaz y ejecuta validaciones del modelo,
 * permitiendo un flujo correcto y ordenado.</p>
 * 
 * <p>Responsabilidades del controlador:</p>
 * <ul>
 *   <li>Capturar eventos de los campos y botones de la vista.</li>
 *   <li>Actualizar requisitos visuales de la contraseña en tiempo real.</li>
 *   <li>Ejecutar validaciones de datos antes del registro.</li>
 *   <li>Comunicar mensajes al usuario mediante la vista.</li>
 * </ul>
 */
public class ControladorRegistro {

    private final ModeloRegistro modeloRegistro;
    private final VistaRegistro vistaRegistro;

    /**
     * Constructor del controlador.
     *
     * <p>Inicializa el controlador con su modelo y vista, registra
     * los eventos necesarios y establece los vínculos entre componentes.</p>
     *
     * @param modeloRegistro instancia del modelo responsable de las validaciones
     * @param vistaRegistro instancia de la interfaz gráfica del formulario
     */
    public ControladorRegistro(ModeloRegistro modeloRegistro, VistaRegistro vistaRegistro) {
        this.modeloRegistro = modeloRegistro;
        this.vistaRegistro = vistaRegistro;

        vistaRegistro.establecerControlador(this);
        agregarEventos();
    }

    
    /**
     * Registra escuchadores para los campos de contraseña y el botón de registro.
     *
     * <p>Se emplean {@link CaretListener} para monitorear cambios en tiempo real
     * en los campos de contraseña y actualizar visualmente los requisitos.</p>
     */
    private void agregarEventos() {
        CaretListener caretListener = (CaretEvent e) -> actualizarEstadoContrasena();

        vistaRegistro.getTxtContrasena().addCaretListener(caretListener);
        vistaRegistro.getTxtConfirmacionContrasena().addCaretListener(caretListener);
        vistaRegistro.getBtnRegistrar().addActionListener(e -> registrarUsuario());
        vistaRegistro.getBtnIniciarSesion().addActionListener(e -> navegarALogin());
    }

    /**
     * Actualiza en tiempo real los requisitos visuales de la contraseña:
     * <ul>
     *   <li>Cambia los colores de los requisitos (verde si se cumplen).</li>
     *   <li>Actualiza el progreso de la barra de seguridad.</li>
     *   <li>Aplica color dorado cuando todos los requisitos están cumplidos y la confirmación coincide.</li>
     * </ul>
     *
     * <p>Además, por razones de seguridad, los arreglos que contienen la contraseña
     * y su confirmación se limpian inmediatamente después de ser utilizados.</p>
     *
     * <p>Este método actúa como retroalimentación inmediata para el usuario.</p>
     *
     * @see ModeloRegistro#calcularProgresoContrasena(java.lang.String)
     * @see ModeloRegistro#contrasenaCoincide(java.lang.String, java.lang.String)
     */
    private void actualizarEstadoContrasena() {
        char[] contrasenaChars = vistaRegistro.getTxtContrasena().getPassword();
        char[] confirmacionChars = vistaRegistro.getTxtConfirmacionContrasena().getPassword();

        String contrasenaTexto = new String(contrasenaChars);
        String confirmacionTexto = new String(confirmacionChars);

        int progreso = modeloRegistro.calcularProgresoContrasena(contrasenaTexto);
        vistaRegistro.getBarraProgreso().setValue(progreso);

        // Actualizar colores de requisitos
        vistaRegistro.getLblRequisitoLetra().setForeground(
                modeloRegistro.contieneLetra(contrasenaTexto) ? Color.GREEN : Color.BLACK);
        vistaRegistro.getLblRequisitoEspecial().setForeground(
                modeloRegistro.contieneCaracterEspecial(contrasenaTexto) ? Color.GREEN : Color.BLACK);
        vistaRegistro.getLblRequisitoNumero().setForeground(
                modeloRegistro.contieneNumero(contrasenaTexto) ? Color.GREEN : Color.BLACK);
        vistaRegistro.getLblRequisitoLongitud().setForeground(
                modeloRegistro.cumpleLongitudMinima(contrasenaTexto) ? Color.GREEN : Color.BLACK);

        // Barra dorada cuando todo es correcto
        if (progreso == 100 &&
            modeloRegistro.contrasenaCoincide(contrasenaTexto, confirmacionTexto)) {
            vistaRegistro.getBarraProgreso().setForeground(new Color(212, 175, 55));
        } else {
            vistaRegistro.getBarraProgreso().setForeground(new Color(20, 125, 255));
        }

        Arrays.fill(contrasenaChars, '\0');
        Arrays.fill(confirmacionChars, '\0');
    }

    /**
     * Valida todos los campos del formulario antes de completar el registro.
     *
     * <p>Las validaciones se realizan en el siguiente orden:</p>
     * <ol>
     *   <li>Verificación de campos obligatorios no vacíos.</li>
     *   <li>Comprobación de que el usuario no exista previamente.</li>
     *   <li>Validación del formato de correo institucional.</li>
     *   <li>Revisión de la longitud del código de estudiante.</li>
     *   <li>Coincidencia entre contraseña y su confirmación.</li>
     *   <li>Verificación de que la contraseña cumpla los 4 requisitos de seguridad.</li>
     * </ol>
     *
     * <p>En caso de error en alguna validación, se muestra un mensaje descriptivo
     * al usuario y se limpian los arreglos de contraseñas por seguridad.</p>
     *
     * <p>Si todas las validaciones se cumplen, se notifica un registro exitoso,
     * se cierra la vista actual y se navega hacia la interfaz principal del sistema.</p>
     *
     * @see ModeloRegistro#usuarioYaExiste(java.lang.String)
     * @see ModeloRegistro#correoEsInstitucional(java.lang.String)
     * @see ModeloRegistro#contrasenaCoincide(java.lang.String, java.lang.String)
     * @see ModeloRegistro#calcularProgresoContrasena(java.lang.String)
     */
    private void registrarUsuario() {
        String nombreUsuario = vistaRegistro.getTxtUsuario().getText().trim();
        String correoElectronico = vistaRegistro.getTxtCorreoElectronico().getText().trim();
        String codigoEstudiante = vistaRegistro.getTxtCodigoEstudiante().getText().trim();

        char[] contrasenaChars = vistaRegistro.getTxtContrasena().getPassword();
        char[] confirmacionChars = vistaRegistro.getTxtConfirmacionContrasena().getPassword();

        String contrasenaTexto = new String(contrasenaChars);
        String confirmacionTexto = new String(confirmacionChars);

        // Campos vacíos
        if (nombreUsuario.isBlank() || correoElectronico.isBlank() ||
            codigoEstudiante.isBlank() || contrasenaTexto.isBlank() ||
            confirmacionTexto.isBlank()) {

            JOptionPane.showMessageDialog(vistaRegistro,
                    "Todos los campos son obligatorios.",
                    "Campos incompletos",
                    JOptionPane.WARNING_MESSAGE);

            Arrays.fill(contrasenaChars, '\0');
            Arrays.fill(confirmacionChars, '\0');
            return;
        }

        // Usuario existente
        if (modeloRegistro.usuarioYaExiste(nombreUsuario)) {
            JOptionPane.showMessageDialog(vistaRegistro,
                    "El usuario ingresado ya existe.",
                    "Error", JOptionPane.ERROR_MESSAGE);

            Arrays.fill(contrasenaChars, '\0');
            Arrays.fill(confirmacionChars, '\0');
            return;
        }

        // Correo institucional
        if (!modeloRegistro.correoEsInstitucional(correoElectronico)) {
            JOptionPane.showMessageDialog(vistaRegistro,
                    "El correo debe ser institucional (@unmsm.edu.pe).",
                    "Error", JOptionPane.ERROR_MESSAGE);

            Arrays.fill(contrasenaChars, '\0');
            Arrays.fill(confirmacionChars, '\0');
            return;
        }

        // Código de estudiante
        if (!modeloRegistro.logitudCodigoEstudiante(codigoEstudiante)) {
            JOptionPane.showMessageDialog(vistaRegistro,
                    "El código de estudiante debe tener 8 dígitos.",
                    "Error", JOptionPane.ERROR_MESSAGE);

            Arrays.fill(contrasenaChars, '\0');
            Arrays.fill(confirmacionChars, '\0');
            return;
        }

        // Coincidencia de contraseña
        if (!modeloRegistro.contrasenaCoincide(contrasenaTexto, confirmacionTexto)) {
            JOptionPane.showMessageDialog(vistaRegistro,
                    "Las contraseñas no coinciden.",
                    "Error", JOptionPane.ERROR_MESSAGE);

            Arrays.fill(contrasenaChars, '\0');
            Arrays.fill(confirmacionChars, '\0');
            return;
        }

        // Requisitos completos
        int progreso = modeloRegistro.calcularProgresoContrasena(contrasenaTexto);
        if (progreso < 100) {
            JOptionPane.showMessageDialog(vistaRegistro,
                    "La contraseña no cumple los 4 requisitos.",
                    "Error", JOptionPane.ERROR_MESSAGE);

            Arrays.fill(contrasenaChars, '\0');
            Arrays.fill(confirmacionChars, '\0');
            return;
        }

        JOptionPane.showMessageDialog(vistaRegistro,
                "Usuario registrado exitosamente.",
                "Registro completado", JOptionPane.INFORMATION_MESSAGE);
        
        vistaRegistro.dispose();
        new barraLateral().setVisible(true);
        
        Arrays.fill(contrasenaChars, '\0');
        Arrays.fill(confirmacionChars, '\0');
    }
    
    /**
     * Navega desde la vista de registro hacia la vista de inicio de sesión.
     *
     * <p>Este método cierra la ventana actual del formulario de registro
     * y crea las instancias necesarias para mostrar la ventana de login,
     * estableciendo su controlador correspondiente.</p>
     *
     * <p>Es utilizado cuando el usuario selecciona la opción
     * "¿Ya tienes una cuenta? Iniciar sesión".</p>
     */
    private void navegarALogin() {
        vistaRegistro.dispose();

        VistaLogin vistaLogin = new VistaLogin();
        ModeloLogin modeloLogin = new ModeloLogin();
        new ControladorLogin(modeloLogin, vistaLogin);
        vistaLogin.setVisible(true);
    }
}