package util;

import model.Usuarios;
import model.Postulante;

public class SessionManager {
    private static Object usuarioActual;

    public static void iniciarSesion(Object usuario) {
        usuarioActual = usuario;
    }

    public static Object getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrarSesion() {
        usuarioActual = null;
    }

    public static boolean esJefeDeProyecto() {
        return usuarioActual instanceof Usuarios && "jefe_proyecto".equalsIgnoreCase(((Usuarios) usuarioActual).getCargo());
    }

    public static boolean esJefeDeSistemas() {
        return usuarioActual instanceof Usuarios && "jefe_sistemas".equalsIgnoreCase(((Usuarios) usuarioActual).getCargo());
    }

    public static boolean esJefeDeContrataciones() {
        return usuarioActual instanceof Usuarios && "jefe_contrataciones".equalsIgnoreCase(((Usuarios) usuarioActual).getCargo());
    }

    public static boolean esEspecialistaDeContrataciones() {
        return usuarioActual instanceof Usuarios && "especialista_contrataciones".equalsIgnoreCase(((Usuarios) usuarioActual).getCargo());
    }
    
    public static boolean esAsistenteDeContrataciones() {
        return usuarioActual instanceof Usuarios && "asistente_contrataciones".equalsIgnoreCase(((Usuarios) usuarioActual).getCargo());
    }

    public static boolean esPostulante() {
        return usuarioActual instanceof Postulante;
    }
}