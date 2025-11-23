package lybsysfisi;

import Lectura.ControladorLectura;
import Lectura.ModeloLectura;
import Lectura.VistaLectura;

//Yared: Esto es un main de prueba
public class LybsysFisi {
    
    public static void main(String[] args) {
        System.out.println("    SISTEMA DE LECTURA ONLINE - LYBSYS FISI");
        System.out.println("    Con marcadores y excepciones\n");
        
        try {
//Yared: Inicializar datos de prueba (Se debe cambiar el nombre del método) 
            ModeloLectura.inicializarDatosPrueba();

            // Crear controlador y vista
            ControladorLectura controlador = new ControladorLectura();
            VistaLectura vista = new VistaLectura();
            
            // Conectar controlador y vista
            controlador.setVista(vista);
            
//Yared: Abrir lectura con un libro de prueba y usuario de prueba
            controlador.abrirLectura("1", "user123");
            // Texto de prueba
            System.out.println("\nINSTRUCCIONES DE USO:");
            System.out.println("  - Usa los botones Anterior/Siguiente para navegar");
            System.out.println("  - Ajusta el zoom con los botones Zoom +/-");
            System.out.println("  - Activa el modo noche para lectura nocturna");
            System.out.println("  - Ingresa un numero de pagina y presiona 'Ir'");
            System.out.println("  - Panel derecho: gestiona tus marcadores");
            System.out.println("    * Agregar: crea un nuevo marcador");
            System.out.println("    * Doble clic: ir a la pagina del marcador");
            System.out.println("    * Eliminar: borra el marcador seleccionado");
            System.out.println("    * Eliminar Todos: borra todos los marcadores");
            System.out.println("\nNOTA IMPORTANTE:");
            System.out.println("  Asegurate de tener archivos PDF en:");
            System.out.println("  src/recursos/libros/codigo.pdf");
            System.out.println("  src/recursos/libros/java_intro.pdf");
            
        } catch (Exception e) {
            System.err.println("\nERROR AL INICIALIZAR EL SISTEMA:");
            System.err.println("   " + e.getMessage());
            e.printStackTrace();
            System.err.println("\nSOLUCIONES:");
            System.err.println("   1. Verifica que existan los archivos PDF");
            System.err.println("   2. Actualiza las rutas en ModeloLectura.inicializarDatosPrueba()");
            System.err.println("   3. Asegurate de tener PDFBox en el classpath");
        }
    }
}