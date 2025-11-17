package Detalle;

/**
 * Componente ligero para representar un formato (PDF, ePub, etc.) como chip.
 */
public class FormatChip {
    private String format;


    /**
     * Crea un nuevo chip de formato.
     *
     * @param format nombre del formato (p. ej. "PDF", "ePub")
     */
    public FormatChip(String format) {
        this.format = format;
    }

    /**
     * Devuelve el formato representado por este chip.
     *
     * @return el nombre del formato
     */
    public String obtenerFormato() { return format; }

    @Override
    public String toString() {
        return "[" + format + "]";
    }
}
