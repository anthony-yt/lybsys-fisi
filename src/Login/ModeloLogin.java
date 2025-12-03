package Login;

import java.util.regex.Pattern;
import Registro.ModeloRegistro;
import Auth.SessionManager;

public class ModeloLogin {
    
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private ModeloRegistro modeloRegistro = new ModeloRegistro();

    public boolean validarFormatoCorreo(String correo) {
        return correo != null && Pattern.matches(EMAIL_REGEX, correo);
    }

    public boolean validarPasswordNoVacia(String password) {
        return password != null && !password.trim().isEmpty();
    }
    
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