

/**
 * Representa un usuario del sistema, ya sea admin o usuario común.
 */
public class Usuario {
    private String nombreUsuario;
    private String contraseña;
    private String correo;
    private boolean esAdmin;

    public Usuario(String nombreUsuario, String contraseña, String correo, boolean esAdmin) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.correo = correo;
        this.esAdmin = esAdmin;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public String getContraseña() {
        return contraseña;
    }
    public String getCorreo() {
        return correo;
    }
    public boolean isAdmin() {
        return esAdmin;
    }

    public String toArchivo() {
        return esAdmin ? nombreUsuario + "," + contraseña + "," + correo + ",1"
                : nombreUsuario + "," + contraseña + "," + correo;
    }
}