package Inicio;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que añade comportamiento de autocompletado a un campo de texto.
 * <p>
 * Proporciona sugerencias basadas en una lista de cadenas (por ejemplo, títulos de libros)
 * y muestra un popup con opciones filtradas mientras el usuario escribe.
 * </p>
 * Uso típico:
 * - Instanciar con el `JTextField` objetivo
 * - (Opcional) llamar a `setData(List<String>)` para proporcionar datos dinámicos
 * - El autocompletado escucha escritura, teclas y clics sobre las sugerencias
 *
 * Esta clase no extiende componentes Swing; es un helper que opera sobre un `JTextField` existente.
 *
 * @author anthony-yt
 * @version 1.0
 */
public class barraBusqueda {

    private final JTextField campoTexto; // campo asociado donde se escribe la búsqueda

    // Componentes para mostrar las sugerencias (JList + modelo)
    private JList<String> listaSugerencia;
    private DefaultListModel<String> listaModelo;

    // Popup que contendrá la lista de sugerencias y su factory
    private Popup sugerenciaPopup = null;
    private final PopupFactory popupFactory = PopupFactory.getSharedInstance();

    // Datos de ejemplo: títulos que se usan para generar sugerencias
    // Se puede reemplazar con `setData(...)` para cargar desde BD/API
    private List<String> listaLibros = List.of(
            "Introducción a Java", "Fundamentos de Bases de Datos", "Redes de Computadoras",
            "Sistemas Operativos Modernos", "Estructuras de Datos y Algoritmos", "Inteligencia Artificial: Un Enfoque Moderno",
            "Aprendizaje Automático", "Ingeniería de Software", "Diseño y Análisis de Algoritmos",
            "Bases de Datos Distribuidas", "Programación en C++", "Minería de Datos", "Administración de Redes"
    );

    /**
     * Crea un helper de autocompletado asociado a un `JTextField`.
     * Inicializa los listeners y estructuras internas.
     *
     * @param campoTexto campo de texto al que se le añade autocompletado
     */
    public barraBusqueda(JTextField campoTexto) {
        this.campoTexto = campoTexto;
        initAutocomplete();
    }

    /**
     * Reemplaza la lista de sugerencias por una lista externa (por ejemplo, desde una BD o API).
     *
     * @param libro lista de cadenas usadas para generar sugerencias
     */
    public void setData(List<String> libro) {
        this.listaLibros = libro;
    }

    /**
     * Inicializa el autocompletado: modelo de lista, listeners de documento,
     * control de teclado y clic sobre sugerencias.
     * <p>
     * Este método prepara la `JList` que mostrará sugerencias, y conecta
     * los distintos listeners para reaccionar a la escritura y navegación.
     * </p>
     */
    private void initAutocomplete() {

        listaModelo = new DefaultListModel<>();
        listaSugerencia = new JList<>(listaModelo);
        listaSugerencia.setFocusable(false);

        // Listener escritura: actualizar sugerencias al modificar el texto
        campoTexto.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override public void insertUpdate(javax.swing.event.DocumentEvent e) { showSuggestions(); }
            @Override public void removeUpdate(javax.swing.event.DocumentEvent e) { showSuggestions(); }
            @Override public void changedUpdate(javax.swing.event.DocumentEvent e) { showSuggestions(); }
        });

        // Listener teclado: navegar por las sugerencias con flechas, aceptar con Enter, cerrar con Escape
        campoTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {

                if (sugerenciaPopup == null) return;
                int size = listaModelo.size();
                if (size == 0) return;

                int sel = listaSugerencia.getSelectedIndex();

                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_DOWN:
                        sel = Math.min(size - 1, sel + 1);
                        listaSugerencia.setSelectedIndex(sel);
                        e.consume();
                        break;

                    case java.awt.event.KeyEvent.VK_UP:
                        sel = Math.max(0, sel - 1);
                        listaSugerencia.setSelectedIndex(sel);
                        e.consume();
                        break;

                    case java.awt.event.KeyEvent.VK_ENTER:
                        if (sel >= 0) {
                            acceptSuggestion(listaModelo.get(sel));
                            e.consume();
                        }
                        break;

                    case java.awt.event.KeyEvent.VK_ESCAPE:
                        hideSuggestions();
                        break;
                }
            }
        });

        // Click en sugerencias: aceptar el valor clicado
        listaSugerencia.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int idx = listaSugerencia.locationToIndex(e.getPoint());
                if (idx >= 0) acceptSuggestion(listaModelo.get(idx));
            }
        });
    }

    // ---------------- MÉTODOS DEL AUTOCOMPLETADO ----------------

    /**
     * Genera y muestra el popup de sugerencias basado en el texto actual
     * del campo. Filtra `listaLibros` buscando coincidencias (contiene)
     * e inserta los resultados en el `listaModelo`.
     */
    private void showSuggestions() {
        String text = campoTexto.getText().trim().toLowerCase();
        // Cerrar cualquier popup previo y limpiar modelo
        hideSuggestions();
        listaModelo.clear();

        if (text.isEmpty()) return;

        List<String> matches = new ArrayList<>();
        for (String s : listaLibros) {
            if (s.toLowerCase().contains(text)) {
                matches.add(s);
            }
        }

        if (matches.isEmpty()) return;

        for (String s : matches) listaModelo.addElement(s);

        // Seleccionar la primera sugerencia por defecto
        listaSugerencia.setSelectedIndex(0);

        // Envolver lista en scroll y dimensionarlo según cantidad
        JScrollPane scroll = new JScrollPane(listaSugerencia);
        scroll.setPreferredSize(new Dimension(
                campoTexto.getWidth(),
                Math.min(150, matches.size() * 22)
        ));

        Point p = campoTexto.getLocationOnScreen();

        // Crear y mostrar popup justo debajo del campo
        sugerenciaPopup = popupFactory.getPopup(
                campoTexto,
                scroll,
                p.x,
                p.y + campoTexto.getHeight()
        );

        sugerenciaPopup.show();
    }

    /**
     * Oculta y elimina el popup de sugerencias si está visible.
     */
    private void hideSuggestions() {
        if (sugerenciaPopup != null) {
            sugerenciaPopup.hide();
            sugerenciaPopup = null;
        }
    }

    /**
     * Acepta la sugerencia seleccionada: la escribe en el campo y
     * cierra el popup de sugerencias.
     *
     * @param value texto de la sugerencia seleccionada
     */
    private void acceptSuggestion(String value) {
        campoTexto.setText(value);
        hideSuggestions();
    }
}