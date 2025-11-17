package recursos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

/**
 * Implementación de un placeholder para componentes de texto en Swing.
 *
 * <p>La clase {@code PlaceHolder} muestra un texto guía sobre un
 * {@link JTextComponent} (por ejemplo, {@link JTextField} o {@link JPasswordField})
 * cuando su contenido está vacío. Este texto no interfiere con la interacción del
 * usuario y desaparece automáticamente cuando se escribe en el campo o cuando el
 * componente cambia de estado, según el modo configurado.</p>
 *
 * <p>El placeholder se ajusta automáticamente a la fuente, color e insets del
 * componente padre. Es ideal para mejorar la usabilidad al indicar claramente la
 * información esperada en cada campo.</p>
 *
 * <p>La visibilidad del placeholder se controla mediante la enumeración
 * {@link ModoMostrar}, permitiendo personalizar cuándo debe mostrarse:
 * siempre, solo al enfocar o solo al perder el foco.</p>
 *
 * @author  
 */
public class PlaceHolder extends JLabel
        implements FocusListener, DocumentListener
{
    /**
     * Define el comportamiento de visualización del placeholder.
     */
    public enum ModoMostrar
    {
        /** Mostrar siempre el placeholder. */
        SIEMPRE,

        /** Mostrar únicamente cuando el componente obtiene el foco. */
        AL_FOCALIZAR,

        /** Mostrar únicamente cuando el componente pierde el foco. */
        AL_PERDER_FOCO
    }

    /**
     * Referencia al componente de texto asociado al placeholder.
     */
    private JTextComponent componenteTexto;

    /**
     * Documento interno del componente de texto, utilizado para escuchar cambios.
     */
    private Document documentoTexto;

    /**
     * Regla que determina cuándo debe mostrarse el placeholder.
     */
    private ModoMostrar modoMostrar = ModoMostrar.SIEMPRE;

    /**
     * Si es true, el placeholder solo se mostrará la primera vez.
     */
    private boolean mostrarUnaVez = false;

    /**
     * Número de veces que el componente perdió el foco.
     */
    private int contadorPerdidasFoco = 0;

    /**
     * Crea un placeholder que se mostrará siempre mientras el campo esté vacío.
     *
     * @param texto           texto del placeholder
     * @param componenteTexto componente asociado donde se mostrará
     */
    public PlaceHolder(String texto, JTextComponent componenteTexto)
    {
        this(texto, componenteTexto, ModoMostrar.SIEMPRE);
    }

    /**
     * Crea un placeholder con un comportamiento de visualización específico.
     *
     * @param texto           texto guía del placeholder
     * @param componenteTexto componente objetivo
     * @param modoMostrar     modo de visualización definido en {@link ModoMostrar}
     */
    public PlaceHolder(String texto, JTextComponent componenteTexto, ModoMostrar modoMostrar)
    {
        this.componenteTexto = componenteTexto;
        setModoMostrar(modoMostrar);
        this.documentoTexto = componenteTexto.getDocument();

        setText(texto);
        setFont(componenteTexto.getFont());
        setForeground(Color.GRAY);
        setBorder(new EmptyBorder(componenteTexto.getInsets()));
        setHorizontalAlignment(JLabel.LEADING);

        componenteTexto.addFocusListener(this);
        documentoTexto.addDocumentListener(this);

        componenteTexto.setLayout(new BorderLayout());
        componenteTexto.add(this);

        verificarVisibilidad();
    }

    /**
     * Cambia la opacidad del color del placeholder.
     *
     * @param alpha valor entre 0.0 y 1.0 que representa el nivel de transparencia
     */
    public void cambiarAlpha(float alpha)
    {
        cambiarAlpha((int)(alpha * 255));
    }

    /**
     * Cambia la opacidad del color del placeholder.
     *
     * @param alpha valor entre 0 y 255 que representa la transparencia
     */
    public void cambiarAlpha(int alpha)
    {
        alpha = Math.max(0, Math.min(255, alpha));

        Color color = getForeground();
        Color colorConAlpha = new Color(
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                alpha
        );

        super.setForeground(colorConAlpha);
    }

    /**
     * Cambia el estilo de texto del placeholder (negrita, cursiva, etc.).
     *
     * @param estilo estilo definido por constantes de {@link Font}
     */
    public void cambiarEstilo(int estilo)
    {
        setFont(getFont().deriveFont(estilo));
    }

    /**
     * Obtiene el modo actual de visualización del placeholder.
     *
     * @return valor de la enumeración {@link ModoMostrar}
     */
    public ModoMostrar getModoMostrar()
    {
        return modoMostrar;
    }

    /**
     * Establece el modo de visualización del placeholder.
     *
     * @param modoMostrar nuevo modo de visualización
     */
    public void setModoMostrar(ModoMostrar modoMostrar)
    {
        this.modoMostrar = modoMostrar;
    }

    /**
     * Indica si el placeholder debe mostrarse solamente una vez.
     *
     * @return true si solo debe mostrarse la primera vez
     */
    public boolean getMostrarUnaVez()
    {
        return mostrarUnaVez;
    }

    /**
     * Define si el placeholder debe mostrarse únicamente la primera vez
     * que el componente pierde el foco.
     *
     * @param mostrarUnaVez true para mostrar solo una vez
     */
    public void setMostrarUnaVez(boolean mostrarUnaVez)
    {
        this.mostrarUnaVez = mostrarUnaVez;
    }

    /**
     * Determina si el placeholder debe estar visible según:
     * <ul>
     *     <li>si el campo está vacío,</li>
     *     <li>si debe mostrarse solo una vez,</li>
     *     <li>si el componente tiene o no el foco,</li>
     *     <li>el modo definido en {@link ModoMostrar}.</li>
     * </ul>
     */
    private void verificarVisibilidad()
    {
        if (documentoTexto.getLength() > 0)
        {
            setVisible(false);
            return;
        }

        if (mostrarUnaVez && contadorPerdidasFoco > 0)
        {
            setVisible(false);
            return;
        }

        boolean tieneFoco = componenteTexto.hasFocus();

        switch (modoMostrar)
        {
            case SIEMPRE:
                setVisible(true);
                break;

            case AL_FOCALIZAR:
                setVisible(tieneFoco);
                break;

            case AL_PERDER_FOCO:
                setVisible(!tieneFoco);
                break;
        }
    }

    // ───────────────────────────────────────────────────────────────
    // IMPLEMENTACIÓN DE FOCUSLISTENER
    // ───────────────────────────────────────────────────────────────

    /**
     * Evento ejecutado cuando el componente obtiene el foco.
     *
     * @param e evento de foco
     */
    @Override
    public void focusGained(FocusEvent e)
    {
        verificarVisibilidad();
    }

    /**
     * Evento ejecutado cuando el componente pierde el foco.
     * Incrementa el contador utilizado para el modo "mostrar una vez".
     *
     * @param e evento de foco
     */
    @Override
    public void focusLost(FocusEvent e)
    {
        contadorPerdidasFoco++;
        verificarVisibilidad();
    }

    // ───────────────────────────────────────────────────────────────
    // IMPLEMENTACIÓN DE DOCUMENTLISTENER
    // ───────────────────────────────────────────────────────────────

    /**
     * Se ejecuta cuando se inserta texto en el componente.
     *
     * @param e evento del documento
     */
    @Override
    public void insertUpdate(DocumentEvent e)
    {
        verificarVisibilidad();
    }

    /**
     * Se ejecuta cuando se elimina texto del componente.
     *
     * @param e evento del documento
     */
    @Override
    public void removeUpdate(DocumentEvent e)
    {
        verificarVisibilidad();
    }

    /**
     * No utilizado para componentes de texto plano.
     *
     * @param e evento del documento
     */
    @Override
    public void changedUpdate(DocumentEvent e) {}
}