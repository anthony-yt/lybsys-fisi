package Auth;

/**
 * Manejador de sesión (stub) usado para pruebas locales.
 *
 * En la versión final este componente debe conectarse con el módulo de
 * autenticación para devolver el usuario real. Por ahora expone métodos
 * sencillos para consultar la sesión actual.
 */
public class SessionManager {

    /**
     * Devuelve el id del usuario actualmente logueado o {@code null} si no hay sesión.
     *
     * Nota: actualmente devuelve un usuario simulado para facilitar pruebas.
     * Sustituir por la implementación real cuando exista el módulo de Auth.
     *
     * @return id del usuario logueado o {@code null}
     */
    public static String obtenerUsuarioActual() {
        // TODO: conectar con la capa real de autenticación (falta terminar)
        return "user-1"; // simulado: cambiar a null para probar sin sesión
    }

    /**
     * Indica si hay un usuario autenticado.
     *
     * @return {@code true} si hay usuario logueado
     */
    public static boolean estaLogueado() {
        return obtenerUsuarioActual() != null;
    }
}

