package Resena;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Modelo que representa una reseña de un libro.
 * <p>
 * Contiene la información que define una reseña: id, identificador del libro, identificador del usuario,
 * calificación (1-5), texto de la reseña y fecha en formato ISO.
 */
public class Resena {
    private final int id;
    private final String idLibro;
    private final String idUsuario;
    private int calificacion; // 1-5
    private String texto; // 10-500 chars
    private final String fecha; // fecha cuando se crea la reseña

    /**
     * Crea una nueva instancia de {@code Resena}.
     *
     * @param id          identificador único de la reseña
     * @param idLibro     identificador del libro al que aplica la reseña
     * @param idUsuario   identificador del usuario que crea la reseña
     * @param calificacion calificación entera entre 1 y 5
     * @param texto        contenido textual de la reseña
     * @param fecha        fecha de creación/actualización en formato ISO (yyyy-MM-dd)
     */
    public Resena(int id, String idLibro, String idUsuario, int calificacion, String texto, String fecha) {
        this.id = id;
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
        this.calificacion = calificacion;
        this.texto = texto;
        this.fecha = fecha;
    }

    /**
     * Devuelve el id de la reseña.
     * @return id de la reseña
     */
    public int getId() { return id; }
    /**
     * Obtiene el id del libro al que pertenece la reseña.
     * @return id del libro
     */
    public String getIdLibro() { return idLibro; }
    /**
     * Obtiene el id del usuario que escribió la reseña.
     * @return id del usuario
     */
    public String getIdUsuario() { return idUsuario; }
    /**
     * Devuelve la calificación (1..5) dada en la reseña.
     * @return calificación
     */
    public int getCalificacion() { return calificacion; }
    /**
     * Establece una nueva calificación para la reseña.
     * @param calificacion calificación válida entre 1 y 5
     */
    public void setCalificacion(int calificacion) { this.calificacion = calificacion; }
    /**
     * Retorna el texto completo de la reseña.
     * @return texto de la reseña
     */
    public String getTexto() { return texto; }
    /**
     * Establece el texto de la reseña (entre 10 y 500 caracteres si se valida antes de guardar).
     * @param texto el contenido de la reseña
     */
    public void setTexto(String texto) { this.texto = texto; }
    /**
     * Obtiene la fecha de la reseña en formato ISO (yyyy-MM-dd).
     * @return fecha de la reseña
     */
    public String getFecha() { return fecha; }
    /**
     * Devuelve la fecha actual en formato ISO yyyy-MM-dd.
     * @return fecha actual ISO
     */
    public static String fechaActualISO() { return LocalDate.now().format(DateTimeFormatter.ISO_DATE); }
}
