
package Inicio;

import javax.swing.*;
import java.awt.*;
import Login.*;

/**
 * La clase vistaInicio representa la pantalla de inicio de la aplicación.
 * Extiende JPanel y proporciona una interfaz gráfica con:
 * - Barra de búsqueda global para buscar libros
 * - Botón para cerrar sesión
 * - Panel superior con bienvenida
 * - Imagen de un lector
 * - Información sobre cómo utilizar el sistema
 * 
 * Esta vista es el primer panel que el usuario ve al acceder a la aplicación.
 * 
 * @author anthony-yt
 * @version 1.0
 */
public class vistaInicio extends JPanel {

    private JButton btnBuscar;
    private JButton btnCerrarSesion;
    private JLabel etiIntroduccion;
    private JLabel etiBienvenida;
    private JLabel etiInformacion;
    private JLabel imgChicoLector;
    private JPanel panelBarraSuperior;
    private JTextField txtBarraBusqueda;  
    private barraBusqueda buscador;
    private barraLateral barraLateral;

    /**
     * Constructor de la clase vistaInicio.
     * Inicializa los componentes gráficos de la vista de inicio.
     */
    public vistaInicio(barraLateral b) {
        barraLateral = b;
        initComponents();
    }
                
    /**
     * Inicializa todos los componentes gráficos de la vista de inicio.
     * Configura:
     * - Panel superior con bienvenida y botón de cerrar sesión
     * - Barra de búsqueda con botón de búsqueda
     * - Etiquetas informativas
     * - Imagen del lector
     * 
     * También establece los listeners de acción para los botones y campos de texto.
     */
    private void initComponents() {
        // Crear instancias de todos los componentes gráficos
        panelBarraSuperior = new JPanel();
        btnCerrarSesion = new JButton();
        etiBienvenida = new JLabel();
        txtBarraBusqueda = new JTextField();
        etiIntroduccion = new JLabel();
        btnBuscar = new JButton();
        etiInformacion = new JLabel();
        imgChicoLector = new JLabel();
        buscador = new barraBusqueda(txtBarraBusqueda);

        // Configurar fondo blanco del panel principal
        setBackground(new Color(255, 255, 255));

        // Configurar panel superior con color azul oscuro
        panelBarraSuperior.setBackground(new Color(0, 51, 102));

        // Configurar botón de cerrar sesión con color rojo
        btnCerrarSesion.setBackground(new Color(255, 51, 51));
        btnCerrarSesion.setFont(new Font("Dubai Medium", 1, 14));
        btnCerrarSesion.setForeground(new Color(255, 255, 255));
        btnCerrarSesion.setText("<html>CERRAR<br>SESIÓN</html>");
        btnCerrarSesion.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCerrarSesion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        btnCerrarSesion.setHorizontalTextPosition(SwingConstants.CENTER);
        // Agregar evento para cerrar sesión
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlogoutActionPerformed(evt);
                barraLateral.dispose();

                VistaLogin vistaLogin = new VistaLogin();
                ModeloLogin modeloLogin = new ModeloLogin();
                new ControladorLogin(modeloLogin, vistaLogin);
                vistaLogin.setVisible(true);
            }
        });

        // Configurar etiqueta de bienvenida
        etiBienvenida.setFont(new Font("Dubai Medium", 1, 24));
        etiBienvenida.setForeground(new Color(255, 255, 255));
        etiBienvenida.setText("¡Bienvenido a LibSys!");

        GroupLayout jPanel1Layout = new GroupLayout(panelBarraSuperior);
        panelBarraSuperior.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addComponent(etiBienvenida)
                .addGap(153, 153, 153)
                .addComponent(btnCerrarSesion, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(btnCerrarSesion, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
                    .addComponent(etiBienvenida))
                .addGap(9, 9, 9))
        );

        txtBarraBusqueda.setFont(new Font("Segoe UI", 0, 16));
        // Configurar barra de búsqueda con alineación izquierda
        txtBarraBusqueda.setHorizontalAlignment(JTextField.LEFT);
        // Agregar evento para buscar al presionar Enter
        txtBarraBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchbarActionPerformed(evt);
            }
        });

        // Configurar etiqueta de introducción con texto descriptivo
        etiIntroduccion.setFont(new Font("Segoe UI Variable", 2, 17));
        etiIntroduccion.setText("<html>Tu portal de conocimiento está listo. Accede a miles de títulos y<br>gestiona tu experiencia de lectura de forma sencilla e inteligente.</html>");

        // Configurar botón de búsqueda con color azul oscuro
        btnBuscar.setBackground(new Color(0, 51, 102));
        btnBuscar.setFont(new Font("Dubai Medium", 1, 16));
        btnBuscar.setForeground(new Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        // Agregar evento para ejecutar búsqueda
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        // Configurar etiqueta informativa con instrucciones de uso
        etiInformacion.setFont(new Font("Segoe UI Variable", 0, 17));
        etiInformacion.setHorizontalAlignment(SwingConstants.CENTER);
        etiInformacion.setText("<html>    Utiliza el Buscador Global o haz clic en la<br>opción Catálogo en el menú lateral. Podrás aplicar<br>filtros detallados por título, autor, categoría o<br>formato, y ver la disponibilidad al instante.</html>");
        etiInformacion.setAlignmentX(0.5F);
        etiInformacion.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
        etiInformacion.setHorizontalTextPosition(SwingConstants.LEADING);

        // Cargar y configurar imagen del lector
        imgChicoLector.setIcon(new ImageIcon(getClass().getResource("/recursos/chicolector.png")));

        // Configurar layout general usando GroupLayout para distribución de componentes
        javax.swing.GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        // Configurar posicionamiento horizontal de componentes
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(panelBarraSuperior, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 527, Short.MAX_VALUE)
                .addComponent(imgChicoLector, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(txtBarraBusqueda, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(etiInformacion, GroupLayout.PREFERRED_SIZE, 463, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(etiIntroduccion, GroupLayout.PREFERRED_SIZE, 562, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        // Configurar posicionamiento vertical de componentes
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelBarraSuperior, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(etiIntroduccion, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBarraBusqueda, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(imgChicoLector, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(etiInformacion, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }                      

    /**
     * Manejador de eventos del botón "CERRAR SESIÓN".
     * Implementa la lógica para cerrar la sesión del usuario.
     * 
     * @param evt evento de acción generado al hacer clic en el botón de cerrar sesión
     */
    private void btnlogoutActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // Lógica para cerrar la sesión del usuario
        // TODO add your handling code here:
    }                                         

    /**
     * Manejador de eventos de la barra de búsqueda.
     * Se ejecuta cuando el usuario presiona Enter en la barra de búsqueda.
     * Implementa la búsqueda de libros según el texto ingresado.
     * 
     * @param evt evento de acción generado al presionar Enter en la barra de búsqueda
     */
    private void txtsearchbarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // Obtener el texto ingresado en la barra de búsqueda y realizar búsqueda
        JTextField txtBusqueda = txtBarraBusqueda;
        if ("".equals(txtBusqueda.getText().trim())) { return; }
        barraLateral.btnCatalogActionPerformed(evt);
        barraLateral.getVistaCatalogo().crearBusqueda(txtBusqueda);
        buscador = null;
    }                                            

    /**
     * Manejador de eventos del botón "Buscar".
     * Implementa la búsqueda de libros basándose en el texto
     * ingresado en la barra de búsqueda.
     * 
     * @param evt evento de acción generado al hacer clic en el botón Buscar
     */
    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {                                        
        // Ejecutar búsqueda con el contenido de la barra de búsqueda
        JTextField txtBusqueda = txtBarraBusqueda;
        if ("".equals(txtBusqueda.getText().trim())) { return; }
        barraLateral.btnCatalogActionPerformed(evt);
        barraLateral.getVistaCatalogo().crearBusqueda(txtBusqueda);
        buscador = null;
    }

}
