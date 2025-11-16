package Detalle;

import Catalogo.Libro;
import Auth.SessionManager;
import Prestamo.PrestamoController;
import Prestamo.PrestamoService;

/**
 * Controlador para la vista de detalle de libro.
 *
 * Coordina la carga de datos desde {@link DetalleService}, decide el estado del
 * botón principal (Leer / Pedir préstamo / En cola) y maneja acciones de la
 * vista como solicitar un préstamo o navegar al catálogo filtrado por autor.
 *
 * Este controlador delega la lógica de préstamos a {@link PrestamoController}.
 */
public class ControladorDetalleLibro {
    private vistaDetalleLibro view;
    private DetalleService service;
    private Libro libro;
    private PrestamoController prestamoController;

    public ControladorDetalleLibro(vistaDetalleLibro view) {
        this.view = view;
        this.service = new DetalleService();
        this.prestamoController = new PrestamoController();
    }

    /**
     * Inicializa la vista con los datos del libro indicado.
     *
     * Carga la información del libro mediante {@link DetalleService#cargarLibro(String)}
     * y actualiza el estado de los controles según la disponibilidad y la sesión
     * del usuario.
     *
     * @param bookId identificador del libro a mostrar
     */
    public void inicializar(String bookId) {
        // Cargar datos (este método debe conectarse a DAO/Service real)
        this.libro = service.cargarLibro(bookId);
        view.mostrarLibro(libro);
        actualizarEstadoAccion();
        // TODO: cargar reseñas/otros componentes
    }

    /**
     * Actualiza el estado del botón principal en la vista.
     *
     * El método decide entre los estados:
     * - "Leer" si el usuario ya tiene el libro.
     * - "Pedir préstamo" si hay copias disponibles.
     * - "En cola" (deshabilitado) si no hay copias.
     */
    private void actualizarEstadoAccion() {
        String userId = SessionManager.obtenerUsuarioActual();

        // Si el usuario ya tiene el libro -> mostrar "Leer"
        if (userId != null && prestamoController.usuarioTieneLibro(userId, libro.getId())) {
            view.establecerBotonAccionHabilitado(true);
            view.establecerTextoBotonAccion("Leer");
            view.establecerTooltipBotonAccion("");
            return;
        }

        // Si hay copias -> permitir pedir préstamo
        int copias = prestamoController.copiasDisponibles(libro.getId());
        if (copias > 0) {
            view.establecerBotonAccionHabilitado(true);
            view.establecerTextoBotonAccion("Pedir préstamo");
            view.establecerTooltipBotonAccion("");
            return;
        }

        // Si no hay copias -> deshabilitado con tooltip
        view.establecerBotonAccionHabilitado(false);
        view.establecerTextoBotonAccion("En cola");
        view.establecerTooltipBotonAccion("No hay copias disponibles");
    }

    /**
     * Maneja la acción del botón principal.
     *
     * - Si no hay usuario autenticado muestra un mensaje solicitando login.
     * - Si el usuario puede leer abre el lector.
     * - Si se concede un préstamo actualiza el estado a "Leer".
     * - Si no hay copias añade al usuario a la cola.
     */
    public void manejarBotonAccion() {
        String userId = SessionManager.obtenerUsuarioActual();
        if (userId == null) {
            view.mostrarToast("Debes iniciar sesión para pedir un préstamo");
            // TODO: navegar a Login
            return;
        }

        PrestamoService.EstadoPrestamo resultado = prestamoController.solicitarPrestamoOLeer(userId, libro.getId());
        switch (resultado) {
            case LEER:
                view.mostrarToast("Abriendo lector...");
                view.abrirLector(libro.getId());
                break;
            case PRESTADO:
                view.mostrarToast("Préstamo confirmado");
                // después de prestar, actualizar estado a "Leer"
                view.establecerTextoBotonAccion("Leer");
                view.establecerBotonAccionHabilitado(true);
                break;
            case EN_COLA:
                view.mostrarToast("Has sido añadido a la cola");
                view.establecerTextoBotonAccion("En cola");
                view.establecerBotonAccionHabilitado(false);
                view.establecerTooltipBotonAccion("Estás en la cola de espera para este libro");
                break;
            case ERROR_NO_AUTENTICADO:
                view.mostrarToast("Error: usuario no autenticado");
                break;
        }
    }

    /**
     * Maneja el evento "Ver autor" y delega la navegación a la vista.
     *
     * @param autorId identificador del autor seleccionado
     */
    public void verAutor(String autorId) {
        // Redirigir al catálogo con filtro por autor
        view.navegarCatalogoPorAutor(autorId);
    }
}

