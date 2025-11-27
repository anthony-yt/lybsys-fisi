package Resena;

import Catalogo.LoanController;

import java.io.IOException;
import java.util.List;

/**
 * Clase de demostración para probar funcionalidades del módulo de reseñas en consola.
 */
public class DemoResenas {
    public static void main(String[] args) throws IOException {
        LoanController controladorPrestamos = LoanController.obtenerInstancia();
        ControladorResenas cr = new ControladorResenas("data/reviews.csv", controladorPrestamos);

        String idLibro = "1";
        System.out.println("Usuario actual: " + controladorPrestamos.obtenerIdUsuarioActual());
        System.out.println("Ha completado préstamo del libro 1? " + controladorPrestamos.usuarioHaCompletadoPrestamo(idLibro));
        System.out.println("Lo tiene actualmente? " + controladorPrestamos.usuarioTieneLibro(idLibro));

        System.out.println("Todas las reseñas para el libro 1:");
        List<Resena> r = cr.obtenerResenas(idLibro,1,10);
        for (Resena rr: r) {
            System.out.println(rr.getId()+" por " + rr.getIdUsuario() + " calificacion="+ rr.getCalificacion() + " texto=" + rr.getTexto());
        }
        System.out.println("Promedio: " + cr.obtenerPromedio(idLibro));
    }
}
