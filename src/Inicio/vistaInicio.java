
package Inicio;

import javax.swing.*;
import java.awt.*;

public class vistaInicio extends JPanel {

    private JButton btnBuscar;
    private JButton btnCerrarSesion;
    private JLabel etiIntroduccion;
    private JLabel etiBienvenida;
    private JLabel etiInformacion;
    private JLabel imgChicoLector;
    private JPanel panelBarraSuperior;
    private JTextField txtBarraBusqueda;  

    public vistaInicio() {
        initComponents();
    }
                
    private void initComponents() {

        panelBarraSuperior = new JPanel();
        btnCerrarSesion = new JButton();
        etiBienvenida = new JLabel();
        txtBarraBusqueda = new JTextField();
        etiIntroduccion = new JLabel();
        btnBuscar = new JButton();
        etiInformacion = new JLabel();
        imgChicoLector = new JLabel();

        setBackground(new Color(255, 255, 255));

        panelBarraSuperior.setBackground(new Color(0, 51, 102));

        btnCerrarSesion.setBackground(new Color(255, 51, 51));
        btnCerrarSesion.setFont(new Font("Dubai Medium", 1, 14));
        btnCerrarSesion.setForeground(new Color(255, 255, 255));
        btnCerrarSesion.setText("<html>CERRAR<br>SESIÓN</html>");
        btnCerrarSesion.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCerrarSesion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        btnCerrarSesion.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnlogoutActionPerformed(evt);
            }
        });

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

        txtBarraBusqueda.setHorizontalAlignment(JTextField.LEFT);
        txtBarraBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtsearchbarActionPerformed(evt);
            }
        });

        etiIntroduccion.setFont(new Font("Segoe UI Variable", 2, 17));
        etiIntroduccion.setText("<html>Tu portal de conocimiento está listo. Accede a miles de títulos y<br>gestiona tu experiencia de lectura de forma sencilla e inteligente.</html>");

        btnBuscar.setBackground(new Color(0, 51, 102));
        btnBuscar.setFont(new Font("Dubai Medium", 1, 16));
        btnBuscar.setForeground(new Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnbuscarActionPerformed(evt);
            }
        });

        etiInformacion.setFont(new Font("Segoe UI Variable", 0, 17));
        etiInformacion.setHorizontalAlignment(SwingConstants.CENTER);
        etiInformacion.setText("<html>    Utiliza el Buscador Global o haz clic en la<br>opción Catálogo en el menú lateral. Podrás aplicar<br>filtros detallados por título, autor, categoría o<br>formato, y ver la disponibilidad al instante.</html>");
        etiInformacion.setAlignmentX(0.5F);
        etiInformacion.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
        etiInformacion.setHorizontalTextPosition(SwingConstants.LEADING);

        imgChicoLector.setIcon(new ImageIcon(getClass().getResource("/recursos/chicolector.png")));

        javax.swing.GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
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

    private void btnlogoutActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void txtsearchbarActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void btnbuscarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }

}
