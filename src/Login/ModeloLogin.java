package Login;

import java.util.regex.Pattern;

public class ModeloLogin {
    
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public boolean validarFormatoCorreo(String correo) {
        return correo != null && Pattern.matches(EMAIL_REGEX, correo);
    }

    public boolean validarPasswordNoVacia(String password) {
        return password != null && !password.trim().isEmpty();
    }
    
    public boolean autenticarUsuario(String correo, String password) {
        return validarFormatoCorreo(correo) && validarPasswordNoVacia(password);
    }
}