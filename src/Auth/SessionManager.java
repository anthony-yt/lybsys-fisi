package Auth;

import Catalogo.ControladorPrestamo;
import java.io.*;

/**
 * Manejador de sesión (stub) usado para pruebas locales.
 *
 * En la versión final este componente debe conectarse con el módulo de
 * autenticación para devolver el usuario real. Por ahora expone métodos
 * sencillos para consultar la sesión actual.
 */
public class SessionManager {

    private static String user;
    private static String rutaUsuarios = "datos\\usuarios.txt";

    /**
     * Agrega el id del usuario actualmente logueado.
     * 
     * @return id del usuario logueado o {@code null}
     */
    public static void agregarUsuarioActual(String s) {
        user = s;
        ControladorPrestamo.establecerUsuarioActual(s);
        System.out.println("SesionActual: " + s);
    }
    
    /**
     * Devuelve el id del usuario actualmente logueado o {@code null} si no hay sesión.
     *
     * @return id del usuario logueado o {@code null}
     */
    public static String obtenerUsuarioActual() {
        return user;
    }

    /**
     * Indica si hay un usuario autenticado.
     *
     * @return {@code true} si hay usuario logueado
     */
    public static boolean estaLogueado() {
        String log = obtenerUsuarioActual();
        return log != null || !"".equals(log);
    }

    /**
     * Agrega los nuevos usuarios a la base de datos.
     * 
     * @param nombre el nombre del usuario
     * @param usuario el correo del usuario
     * @param contrasena la contraseña del usuario
     */
    public static void escribirUsuarios(String nombre, String usuario, String contrasena) {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(rutaUsuarios, true))){
            escritor.write(nombre.trim() + " " + usuario.trim() + " " + contrasena.trim());
            escritor.newLine();
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el archivo");
        }
    }

    /**
     * Busca el usuario dentro de la base de datos.
     * @param correo filtra por correo
     * @return {@code String} nombre de usuario, correo y contraseña
     */
    public static String buscarUsuario(String correo) {
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaUsuarios))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(" ");
                if (campos[1].equals(correo)) {
                    return linea;
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo leer el archivo");
        }
        return null;
    }

    /**
     * Busca coincidencias en la base de usuarios.
     * @param palabra la palabra a buscar
     * @return {@code true} si encontró la coincidencia exacta
     */
    public static boolean buscarCoincidencia(String palabra) {
        try (BufferedReader lector = new BufferedReader(new FileReader(rutaUsuarios))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] campos = linea.split(" ");
                for (String p : campos) {
                    if (p.equals(palabra)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo buscar en el archivo");
            return true;
        }
        return false;
    }
}

