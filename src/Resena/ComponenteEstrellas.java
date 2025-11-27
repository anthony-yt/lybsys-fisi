package Resena;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

/**
 * Componente visual que muestra un conjunto de 5 estrellas.
 * Puede operar en modo 'interactivo' para recoger valor (1..5) o modo 'solo lectura' para mostrar.
 */
public class ComponenteEstrellas extends JPanel {
    private final JButton[] estrellas = new JButton[5];
    private int valor = 0; // 0-5

    /**
     * Crea un componente de estrellas.
     * @param interactivo true si el usuario puede seleccionar la calificación
     * @param onChange callback opcional cuando cambia el valor (recibe el nuevo valor)
     */
    public ComponenteEstrellas(boolean interactivo, Consumer<Integer> onChange) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBackground(new Color(245, 247, 250));
        for (int i = 0; i < 5; i++) {
            JButton s = crearBotonEstrella(i + 1, interactivo, onChange);
            estrellas[i] = s;
            add(s);
        }
    }

    /**
     * Crea un botón visual que actúa como estrella (rellena o vacía)
     * @param valorEstrella valor entero para esa estrella (1..5)
     * @param interactivo si el botón reacciona a clicks
     * @param onChange callback ejecutado si el usuario cambia el rating
     * @return botón configurado
     */
    private JButton crearBotonEstrella(int valorEstrella, boolean interactivo, Consumer<Integer> onChange) {
        JButton btn = new JButton("☆");
        btn.setBorder(null);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Dialog", Font.PLAIN, 18));
        btn.setForeground(new Color(255, 200, 0));
        if (interactivo) {
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setValor(valorEstrella);
                    if (onChange != null) onChange.accept(valor);
                }
            });
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    resaltar(valorEstrella);
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    actualizarEstrellas();
                }
            });
        }
        return btn;
    }

    /**
     * Muestra estrellas resaltadas hasta la posición indicada (provisional).
     * @param hasta número de estrellas a resaltar
     */
    private void resaltar(int hasta) {
        for (int i = 0; i < estrellas.length; i++) {
            estrellas[i].setText(i < hasta ? "★" : "☆");
        }
    }

    /**
     * Actualiza el texto de los botones según el valor interno `valor`.
     */
    private void actualizarEstrellas() {
        for (int i = 0; i < estrellas.length; i++) {
            estrellas[i].setText(i < valor ? "★" : "☆");
        }
    }

    /**
     * Establece el valor de la calificación (0..5).
     * @param val valor a establecer
     */
    public void setValor(int val) {
        this.valor = Math.max(0, Math.min(5, val));
        actualizarEstrellas();
    }

    /**
     * Obtiene el valor actual de la calificación.
     * @return valor (0..5)
     */
    public int getValor() { return valor; }
}
