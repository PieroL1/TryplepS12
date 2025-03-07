package util;
public class SessionManager {
    private static String usuarioActual;
    
    public static void iniciarSesion(String nroRegistro) {
        usuarioActual = nroRegistro;
    }
    
    public static String getUsuarioActual() {
        return usuarioActual;
    }
    
    public static void cerrarSesion() {
        usuarioActual = null;
    }
}