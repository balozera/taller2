
/**
 * Representa una tarea asignada a un usuario.
 */

public class Tarea {
    private String titulo;
    private String descripcion;
    private String fechaCreacion;
    private String fechaLimite;
    private boolean completada;
    private String correoUsuario;

    public Tarea(String titulo, String descripcion, String fechaCreacion, String fechaLimite, boolean completada, String correoUsuario) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.fechaLimite = fechaLimite;
        this.completada = completada;
        this.correoUsuario = correoUsuario;
    }

    public String getTitulo() {
        return titulo;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public String getFechaCreacion() {
        return fechaCreacion;
    }
    public String getFechaLimite() {
        return fechaLimite;
    }
    public boolean estaCompleta() {
        return completada;
    }
    public String getCorreo() {
        return correoUsuario;
    }

    public String toArchivo() {
        return titulo + "," + descripcion + "," + fechaCreacion + "," + fechaLimite + "," + completada + "," + correoUsuario;
    }
}


