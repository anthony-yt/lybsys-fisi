package Registro;

/**
 * Modelo encargado de realizar todas las validaciones necesarias para el proceso
 * de registro de usuarios. Esta clase no interactúa con la interfaz gráfica ni
 * muestra mensajes: su responsabilidad es puramente lógica.
 *
 * <p>Validaciones implementadas:</p>
 * <ul>
 *   <li>Disponibilidad del nombre de usuario.</li>
 *   <li>Formato del correo institucional.</li>
 *   <li>Longitud del código de estudiante.</li>
 *   <li>Coincidencia de contraseñas.</li>
 *   <li>Requisitos individuales de la contraseña.</li>
 *   <li>Cálculo del progreso total de seguridad de la contraseña.</li>
 * </ul>
 *
 * <p>Este modelo puede ser extendido posteriormente para integrarse con
 * persistencia en base de datos o archivos.</p>
 */
public class ModeloRegistro {

    /**
     * Verifica si un nombre de usuario ya se encuentra registrado.
     *
     * <p>Actualmente la validación es simulada usando valores fijos.
     * En un escenario real debería consultarse una base de datos o archivo.</p>
     *
     * @param nombreUsuario nombre ingresado por el usuario
     * @return {@code true} si el usuario ya existe, {@code false} en caso contrario
     */
    public boolean usuarioYaExiste(String nombreUsuario) {
        return nombreUsuario.equalsIgnoreCase("admin")
                || nombreUsuario.equalsIgnoreCase("usuario1");
    }

    /**
     * Valida si el correo electrónico pertenece al dominio institucional
     * de la Universidad Nacional Mayor de San Marcos.
     *
     * @param correoElectronico correo ingresado por el usuario
     * @return {@code true} si el correo finaliza en {@code @unmsm.edu.pe},
     *         {@code false} en caso contrario o si el valor es nulo
     */
    public boolean correoEsInstitucional(String correoElectronico) {
        return correoElectronico != null && correoElectronico.endsWith("@unmsm.edu.pe");
    }

    /**
     * Valida si el código de estudiante cumple con la longitud requerida.
     *
     * <p>Un código institucional válido debe contener exactamente 8 caracteres.</p>
     *
     * @param codigoEstudiante código ingresado por el usuario
     * @return {@code true} si el código tiene exactamente 8 caracteres,
     *         {@code false} en caso contrario o si es nulo
     */
    public boolean logitudCodigoEstudiante(String codigoEstudiante) {
        return codigoEstudiante != null && codigoEstudiante.length() == 8;
    }

    /**
     * Verifica si la contraseña y su confirmación coinciden exactamente.
     *
     * <p>Se utiliza un manejo seguro frente a valores nulos para evitar
     * {@link NullPointerException}, tratando {@code null} como cadena vacía.</p>
     *
     * @param contrasena texto de la contraseña ingresada
     * @param confirmacion texto de la confirmación
     * @return {@code true} si ambas coinciden exactamente, {@code false} de lo contrario
     */
    public boolean contrasenaCoincide(String contrasena, String confirmacion) {
        String c1 = contrasena == null ? "" : contrasena;
        String c2 = confirmacion == null ? "" : confirmacion;
        return c1.equals(c2);
    }

    // ───────────────────────────────────────────────────────────────
    // VALIDACIONES INDIVIDUALES DE REQUISITOS DE CONTRASEÑA
    // ───────────────────────────────────────────────────────────────

    /**
     * Valida si la contraseña contiene al menos una letra (mayúscula o minúscula).
     *
     * @param contrasena contraseña a evaluar
     * @return {@code true} si contiene letras, {@code false} en caso contrario
     */
    public boolean contieneLetra(String contrasena) {
        return contrasena != null && contrasena.matches(".*[A-Za-z].*");
    }

    /**
     * Valida si la contraseña contiene al menos un número decimal.
     *
     * @param contrasena contraseña a evaluar
     * @return {@code true} si contiene números, {@code false} en caso contrario
     */
    public boolean contieneNumero(String contrasena) {
        return contrasena != null && contrasena.matches(".*\\d.*");
    }

    /**
     * Valida si la contraseña contiene al menos un carácter especial.
     *
     * <p>Se considera carácter especial a cualquier símbolo que no sea letra
     * ni número.</p>
     *
     * @param contrasena contraseña a evaluar
     * @return {@code true} si contiene al menos un carácter especial,
     *         {@code false} en caso contrario
     */
    public boolean contieneCaracterEspecial(String contrasena) {
        return contrasena != null && contrasena.matches(".*[^A-Za-z0-9].*");
    }

    /**
     * Verifica si la contraseña cumple con la longitud mínima requerida
     * para considerarse segura.
     *
     * @param contrasena contraseña a evaluar
     * @return {@code true} si la longitud es mayor o igual a 8 caracteres,
     *         {@code false} en caso contrario
     */
    public boolean cumpleLongitudMinima(String contrasena) {
        return contrasena != null && contrasena.length() >= 8;
    }

    // ───────────────────────────────────────────────────────────────
    // CÁLCULO DE PROGRESO
    // ───────────────────────────────────────────────────────────────

    /**
     * Calcula el porcentaje total de cumplimiento de los requisitos de seguridad
     * de la contraseña. Cada uno de los cuatro requisitos aporta exactamente
     * un 25% al valor total.
     *
     * <p>Requisitos evaluados:</p>
     * <ul>
     *   <li>Contiene letras.</li>
     *   <li>Contiene números.</li>
     *   <li>Contiene caracteres especiales.</li>
     *   <li>Longitud mínima de 8 caracteres.</li>
     * </ul>
     *
     * <p>Este método no evalúa la fuerza cryptográfica de la contraseña, sólo
     * el cumplimiento de los requisitos definidos para la interfaz de registro.</p>
     *
     * @param contrasena contraseña evaluada
     * @return un valor entero entre 0 y 100 indicando el progreso total
     */
    public int calcularProgresoContrasena(String contrasena) {
        int progreso = 0;

        if (contieneLetra(contrasena)) progreso += 25;
        if (contieneNumero(contrasena)) progreso += 25;
        if (contieneCaracterEspecial(contrasena)) progreso += 25;
        if (cumpleLongitudMinima(contrasena)) progreso += 25;

        return progreso;
    }
}