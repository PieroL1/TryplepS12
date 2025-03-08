package util;

import model.Usuarios;

public class SessionManager {
    private static Usuarios usuarioActual;

    public static void iniciarSesion(Usuarios usuario) {
        usuarioActual = usuario;
    }

    public static Usuarios getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static boolean esJefeDeProyecto() {
        return usuarioActual != null && "jefe_proyecto".equalsIgnoreCase(usuarioActual.getCargo());
    }

    public static boolean esJefeDeSistemas() {
        return usuarioActual != null && "jefe_sistemas".equalsIgnoreCase(usuarioActual.getCargo());
    }
}