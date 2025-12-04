package Catalogo;

import java.util.*;
import Lectura.VistaLectura;

/**
 * Controlador simplificado para gestionar préstamos y colas de espera (simulación en memoria).
 * Implementa un singleton y mantiene un mapa de préstamos por usuario y contadores de cola por libro.
 */
public class ControladorPrestamo {
    private static ControladorPrestamo instancia;
    private Map<String, List<String>> prestamosPorUsuario = new HashMap<>(); // idUsuario -> lista de idLibros
    private Map<String, Integer> colaPrestamo = new HashMap<>(); // idLibro -> cantidad de usuarios en cola
    private static String idUsuarioActual;

    private ControladorPrestamo() {
        // Datos iniciales simulados
        prestamosPorUsuario.put("usuario1", new ArrayList<>(Arrays.asList("1", "2")));
        prestamosPorUsuario.put("usuario2", new ArrayList<>(Arrays.asList("4", "5")));
    }

    /**
     * Obtiene la instancia singleton del controlador de préstamos.
     * @return instancia única de `ControladorPrestamo`
     */
    public static ControladorPrestamo obtenerInstancia() {
        if (instancia == null) {
            instancia = new ControladorPrestamo();
        }
        return instancia;
    }

    /**
     * Establece el usuario actual.
     * @param idUsuario identificador de usuario
     */
    public static void establecerUsuarioActual(String idUsuario) {
        idUsuarioActual = idUsuario;
    }

    /**
     * Devuelve el usuario actual.
     * @return String del usuario actual
     */
    public String obtenerIdUsuarioActual() {
        return idUsuarioActual;
    }

    /**
     * Obtiene los IDs de los libros prestados por el usuario actual.
     * @return lista de IDs de libros
     */
    public List<String> obtenerPrestamosUsuarioActual() {
        return prestamosPorUsuario.getOrDefault(idUsuarioActual, new ArrayList<>());
    }

    /**
     * Verifica si el usuario actual ya tiene prestado el libro indicado.
     * @param idLibro id del libro
     * @return true si el usuario ya tiene el libro, false en caso contrario
     */
    public boolean usuarioTieneLibro(String idLibro) {
        return obtenerPrestamosUsuarioActual().contains(idLibro);
    }

    /**
     * Intenta prestar el libro al usuario actual.
     * @param idLibro id del libro
     * @param controladorCatalogo controlador de catálogo para buscar el libro
     * @return true si el préstamo fue exitoso, false si no
     */
    public boolean pedirPrestamo(String idLibro, ControladorCatalogo controladorCatalogo) {
        if (usuarioTieneLibro(idLibro)) {
            return false; // Ya lo tiene
        }

        Optional<Libro> libro = controladorCatalogo.obtenerPorId(idLibro);
        if (libro.isPresent() && libro.get().getCopiasDisponibles() > 0) {
            libro.get().setCopiasDisponibles(libro.get().getCopiasDisponibles() - 1);

            prestamosPorUsuario.putIfAbsent(idUsuarioActual, new ArrayList<>());
            prestamosPorUsuario.get(idUsuarioActual).add(idLibro);

            colaPrestamo.remove(idLibro); // Remover de la cola si estaba
            return true;
        }
        return false;
    }

    /**
     * Devuelve un libro prestado por el usuario actual.
     * @param idLibro id del libro
     * @param controladorCatalogo controlador de catálogo para actualizar inventario
     * @return true si la devolución fue procesada, false en caso contrario
     */
    public boolean devolverLibro(String idLibro, ControladorCatalogo controladorCatalogo) {
        if (!usuarioTieneLibro(idLibro)) {
            return false;
        }

        Optional<Libro> libro = controladorCatalogo.obtenerPorId(idLibro);
        if (libro.isPresent()) {
            libro.get().setCopiasDisponibles(libro.get().getCopiasDisponibles() + 1);
            prestamosPorUsuario.get(idUsuarioActual).remove(idLibro);
            return true;
        }
        return false;
    }

    /**
     * Añade el usuario actual a la cola de espera del libro.
     * @param idLibro id del libro
     */
    public void agregarACola(String idLibro) {
        if (!usuarioTieneLibro(idLibro)) {
            int enCola = colaPrestamo.getOrDefault(idLibro, 0);
            colaPrestamo.put(idLibro, enCola + 1);
        }
    }

    /**
     * Obtiene la posición estimada en la cola para el libro indicado.
     * Nota: implementación simulada.
     * @param idLibro id del libro
     * @return posición en cola (simulada)
     */
    public int obtenerPosicionCola(String idLibro) {
        return colaPrestamo.getOrDefault(idLibro, 0);
    }

    /**
     * Obtiene el estado del libro para el usuario actual.
     * @param idLibro id del libro
     * @param controladorCatalogo controlador de catálogo para consultar existencias
     * @return estado del libro para el usuario actual
     */
    public EstadoLibro obtenerEstadoLibro(String idLibro, ControladorCatalogo controladorCatalogo) {
        if (usuarioTieneLibro(idLibro)) {
            return EstadoLibro.YA_TIENE;
        }

        Optional<Libro> libro = controladorCatalogo.obtenerPorId(idLibro);
        if (libro.isEmpty()) {
            return EstadoLibro.NO_ENCONTRADO;
        }

        if (libro.get().getCopiasDisponibles() > 0) {
            return EstadoLibro.DISPONIBLE;
        } else if (colaPrestamo.getOrDefault(idLibro, 0) > 0) {
            return EstadoLibro.EN_COLA;
        } else {
            return EstadoLibro.SIN_COPIAS;
        }
    }

    public enum EstadoLibro {
        YA_TIENE,    // Usuario ya tiene el libro (botón Leer)
        DISPONIBLE,  // Disponible para prestar
        EN_COLA,     // En cola de espera
        SIN_COPIAS,  // Sin copias disponibles
        NO_ENCONTRADO// Libro no existe
    }

    /**
     * Abre/Lee un libro que el usuario ya tiene (simulación).
     * @param idLibro id del libro
     */
    // Importa tu VistaLectura (ajusta el paquete real)
   // <-- cambia "Lectura" por el paquete correcto

    /**
     * Abre/Lee un libro que el usuario ya tiene.
     * @param idLibro id del libro
     * @param controladorCatalogo controlador de catálogo para obtener datos del libro
     */
    public void abrirLibro(String idLibro, ControladorCatalogo controladorCatalogo) {
        if (!usuarioTieneLibro(idLibro)) {
            System.out.println("El usuario no tiene este libro prestado.");
            return;
        }

        Optional<Libro> libroOpt = controladorCatalogo.obtenerPorId(idLibro);
        if (libroOpt.isEmpty()) {
            System.out.println("Libro no encontrado en el catálogo.");
            return;
        }

        Libro libro = libroOpt.get();

        
        String rutaArchivo = libro.getRutaPdf();   
        String formato     = libro.getFormato();   

        try {
            VistaLectura vistaLectura = new VistaLectura();          
            vistaLectura.cargarLibro(idLibro, idUsuarioActual, rutaArchivo, formato);
            vistaLectura.setVisible(true);

            System.out.println("Abriendo libro " + idLibro + " desde: " + rutaArchivo);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No se pudo abrir el lector de PDF.");
        }
    }
}