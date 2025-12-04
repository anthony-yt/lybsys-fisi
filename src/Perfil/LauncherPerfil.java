package Perfil;

import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import java.util.List;

import Perfil.io.PrestamoRepositorio;
import Perfil.io.UsuarioRepositorio;

import Perfil.perfil_modelo.Prestamo;
import Perfil.perfil_modelo.Usuario;

public class LauncherPerfil {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                String correoObjetivo = "luima@correo.com";

                Usuario usuario = UsuarioRepositorio.buscarPorCorreo(
                        "data/usuarios.csv",
                        correoObjetivo
                );

                List<Prestamo> prestamos = PrestamoRepositorio.cargarPrestamosPorCorreo(
                        "data/prestamos.csv",
                        usuario.getCorreo()
                );

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
                        JOptionPane.showMessageDialog(
                            vista,
                            "Leyendo:\n" +
                            "Título: " + encontrado.getTitulo() + "\n" +
                            "Estado: " + encontrado.getEstado() + "\n" +
                            "Vence: " + encontrado.getFechaVencimiento(),
                            "Leer libro",
                            JOptionPane.INFORMATION_MESSAGE
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                            vista,
                            "No encontré el libro con id " + bookId,
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

                // ====== Ventana para probar PerfilVista =======
                JFrame frame = new JFrame("Perfil");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(vista);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                    null,
                    "Error: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
