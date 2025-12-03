package Login;

import Auth.SessionManager;
import Registro.VistaRegistro;
import Registro.ControladorRegistro;
import Registro.ModeloRegistro;
import Inicio.barraLateral;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane; 

public class ControladorLogin {

    private ModeloLogin modelo;
    private VistaLogin vista;

    public ControladorLogin(ModeloLogin modelo, VistaLogin vista) {
        this.modelo = modelo;
        this.vista = vista;
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnIniciar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarInicioSesion();
            }
        });

        vista.getBtnCrearCuenta().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                navegarARegistro();
            }
        });
    }

    private void manejarInicioSesion() {
        vista.ocultarError(); 
        
        String correo = vista.getCorreo();
        String password = vista.getPassword();

        if (!modelo.validarPasswordNoVacia(password)) {
            vista.mostrarError("La contraseña no puede estar vacía.");
            return;
        }

        if (!modelo.validarFormatoCorreo(correo)) {
            vista.mostrarError("El formato del correo es inválido.");
            return;
        }

        if(!modelo.verificarCorreo(correo)) {
            vista.mostrarError("Debes colocar tu correo institucional");
        }

        if (modelo.autenticarUsuario(correo, password)) {
            JOptionPane.showMessageDialog(vista, 
                    "¡Bienvenido! Has iniciado sesión correctamente.", 
                    "Login Exitoso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            vista.dispose();
            SessionManager.agregarUsuarioActual(correo);
            new barraLateral().setVisible(true); 
        } else {
            vista.mostrarError("Credenciales incorrectas.");
        }
    }

    private void navegarARegistro() {
        vista.dispose(); // Cierra ventana actual
        
        // Abre registro
        VistaRegistro vRegistro = new VistaRegistro();
        ModeloRegistro mRegistro = new ModeloRegistro();
        new ControladorRegistro(mRegistro, vRegistro);
        vRegistro.setVisible(true);
    }
}