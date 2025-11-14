package perfil.perfil_modelo;

public class Prestamo {
    private final int loanId;
    private final int bookId;
    private final String titulo;
    private final String estado;
    private final String fechaVencimiento;
    private final String portada;

    public Prestamo(int loanId, int bookId, String titulo, String estado,
                    String fechaVencimiento, String portada) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.titulo = titulo;
        this.estado = estado;
        this.fechaVencimiento = fechaVencimiento;
        this.portada = portada;
    }

    public int getLoanId() { return loanId; }
    public int getBookId() { return bookId; }
    public String getTitulo() { return titulo; }
    public String getEstado() { return estado; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public String getPortada() { return portada; }
}
