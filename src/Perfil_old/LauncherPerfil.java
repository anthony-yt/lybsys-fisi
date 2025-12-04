package perfil;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.util.List;
import perfil.io.PrestamoRepositorio;
import perfil.io.UsuarioRepositorio;
import perfil.perfil_modelo.Prestamo;
import perfil.perfil_modelo.Usuario;

public class LauncherPerfil {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                //aqui se pone logica de registro :v
                String correoObjetivo = "luima@correo.com";

                Usuario usuario = UsuarioRepositorio.buscarPorCorreo("data/usuarios.csv", correoObjetivo);

                List<Prestamo> prestamos = PrestamoRepositorio.cargarPrestamosPorCorreo(
                        "data/prestamos.csv", usuario.getCorreo());

                PerfilVista vista = new PerfilVista();
                vista.setUsuario(usuario);
                vista.mostrarPrestamos(prestamos);
                
                vista.setAccionesPrestamoListener(bookId -> {

    Prestamo encontrado = null;
    for (Prestamo p : prestamos) {
        if (p.getBookId() == bookId) {
            encontrado = p;
            break;
        }
    }

    if (encontrado != null) {
        javax.swing.JOptionPane.showMessageDialog(
            vista,
            "Leyendo:\n" +
            "Título: " + encontrado.getTitulo() + "\n" +
            "Estado: " + encontrado.getEstado() + "\n" +
            "Vence: " + encontrado.getFechaVencimiento(),
            "Leer libro",
            javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    } else {
        javax.swing.JOptionPane.showMessageDialog(
            vista,
            "No encontré el libro con id " + bookId,
            "Error",
            javax.swing.JOptionPane.ERROR_MESSAGE
        );
    }
});

                vista.setAccionesPrestamoListener(bookId ->
                    JOptionPane.showMessageDialog(
                        vista,
                        "Mostrando libro con ID: " + bookId,
                        "Leer",
                        JOptionPane.INFORMATION_MESSAGE
                    )
                );

                vista.setLocationRelativeTo(null);
                vista.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    null, "Error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
