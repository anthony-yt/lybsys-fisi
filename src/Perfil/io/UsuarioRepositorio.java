package Perfil.io;

import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import Perfil.perfil_modelo.Usuario;

public class UsuarioRepositorio {

    public static List<Usuario> cargarUsuarios(String rutaCsv) throws Exception {
        List<Usuario> usuarios = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Path.of(rutaCsv), StandardCharsets.UTF_8)) {
            String linea = br.readLine();
            if (linea == null) throw new IllegalArgumentException("CSV vacío: " + rutaCsv);

            while ((linea = br.readLine()) != null) {
                if (linea.isBlank()) continue;
                String[] c = linea.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String nombre = unquote(c[0].trim());
                String correo = unquote(c[1].trim());
                String avatar = c.length > 2 ? unquote(c[2].trim()) : null;
                if (avatar != null && avatar.isBlank()) avatar = null;

                usuarios.add(new Usuario(nombre, correo, avatar));
            }
        }
        return usuarios;
    }

    public static Usuario buscarPorCorreo(String rutaCsv, String correoBuscado) throws Exception {
        for (Usuario u : cargarUsuarios(rutaCsv)) {
            if (u.getCorreo().equalsIgnoreCase(correoBuscado)) {
                return u;
            }
        }
        throw new IllegalArgumentException("No se encontró usuario con correo: " + correoBuscado);
    }

    private static String unquote(String s) {
        return (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\""))
                ? s.substring(1, s.length() - 1)
                : s;
    }
}

