package Perfil.perfil_modelo;

public class Usuario {
    private final String nombre;
    private final String correo;
    private final String avatar;

    public Usuario(String nombre, String correo, String avatar) {
        this.nombre = nombre;
        this.correo = correo;
        this.avatar = avatar;
    }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getAvatar() { return avatar; }
}