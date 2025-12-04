package Login;

import java.util.Random;
import java.util.regex.Pattern;
import Registro.ModeloRegistro;
import Auth.SessionManager;

/**
 * Modelo del módulo de autenticación (Login).
 * <p>
 * Contiene la lógica de negocio, reglas de validación y la generación
 * de códigos para el sistema de seguridad (Captcha).
 * No contiene elementos de interfaz gráfica.
 * </p>
 * * @author Leonardo Palomino
 * @version 1.0
 */
public class ModeloLogin {

    /** Expresión regular para validar el formato estándar de un correo electrónico. */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    /** Almacena el código del captcha generado actualmente para su posterior validación. */
    private String captchaActual;
    private ModeloRegistro modeloRegistro = new ModeloRegistro();

    /**
     * Verifica si un correo electrónico cumple con el formato válido y pertenece
     * al dominio institucional de la UNMSM.
     * * @param correo El texto del correo electrónico a validar.
     * @return true si el correo tiene formato válido y termina en @unmsm.edu.pe, false en caso contrario.
     */
    public boolean validarFormatoCorreo(String correo) {
        return correo != null && Pattern.matches(EMAIL_REGEX, correo);
    }

    public boolean validarCorreoInstitucional(String correo) {
        return validarFormatoCorreo(correo) && modeloRegistro.correoEsInstitucional(correo);
    }

    /**
     * Comprueba que la contraseña proporcionada no sea nula ni esté vacía.
     * * @param password La contraseña ingresada por el usuario.
     * @return true si la contraseña contiene al menos un carácter que no sea espacio.
     */
    public boolean validarPasswordNoVacia(String password) {
        return password != null && !password.trim().isEmpty();
    }
    
    /**
     * Genera un código alfanumérico aleatorio de 5 caracteres para usar en el Captcha.
     * <p>
     * El código generado se almacena internamente en la variable {@code captchaActual}.
     * </p>
     * * @return El código alfanumérico generado.
     */
    public String generarCodigoCaptcha() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 5; i++) { 
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        this.captchaActual = sb.toString();
        return this.captchaActual;
    }

    /**
     * Compara la respuesta ingresada por el usuario con el código captcha generado.
     * <p>
     * La comparación no distingue entre mayúsculas y minúsculas.
     * </p>
     * * @param respuestaUsuario El texto ingresado por el usuario en el campo del captcha.
     * @return true si coincide con el código almacenado, false de lo contrario.
     */
    public boolean validarCaptcha(String respuestaUsuario) {
        return respuestaUsuario != null && respuestaUsuario.equalsIgnoreCase(captchaActual);
    }

    /**
     * Simula la autenticación del usuario contra una base de datos.
     * <p>
     * Actualmente este método es un stub (simulación) que siempre retorna éxito.
     * Debe ser conectado posteriormente a un servicio de autenticación real.
     * </p>
     * * @param correo El correo institucional validado.
     * @param password La contraseña ingresada.
     * @return true si las credenciales son correctas.
     */
    public boolean autenticarUsuario(String correo, String password) {
        String nombre;
        String correoInsti;
        String contra;

        String datos = SessionManager.buscarUsuario(correo);
        if (datos == null) { return false; }
        String[] datosSeparados = datos.split(" ");
        nombre = datosSeparados[0];
        correoInsti = datosSeparados[1];
        contra = datosSeparados[2];

        boolean contenido = validarFormatoCorreo(correo) && validarPasswordNoVacia(password);

        return correoInsti.equals(correo) && contra.equals(password) && contenido;
    }

    public boolean verificarCorreo(String correo) {
        return modeloRegistro.correoEsInstitucional(correo);
    }
}