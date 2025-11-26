package Main;
import Login.*; 
public class mainLibSys {

    public static void main(String[] args) {
        ModeloLogin modelo = new ModeloLogin();
        VistaLogin vista = new VistaLogin();
        new ControladorLogin(modelo, vista);
        vista.setVisible(true);
    }
    
}