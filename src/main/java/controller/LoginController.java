package controller;

import javax.swing.JOptionPane;
import model.Usuarios;
import service.LoginService;
import util.SessionManager;
import view.FormGestionarPerfiles;
import view.FormRegistrarSolicitud;
import view.FormMenuPrincipal;

public class LoginController {
    private LoginService loginService;

    public LoginController() {
        loginService = new LoginService();
    }

    public boolean iniciarSesion(String nombre, String password) {
        Usuarios usuario = loginService.autenticar(nombre, password);
        if (usuario != null) {
            SessionManager.iniciarSesion(usuario);
            redirigirSegunRol();
            return true;
        }
        return false;
    }

    private void redirigirSegunRol() {
        if (SessionManager.esJefeDeProyecto()) {
            FormGestionarPerfiles formGestionarPerfiles= new  FormGestionarPerfiles();
            formGestionarPerfiles.setVisible(true);
        } else if (SessionManager.esJefeDeSistemas()) {
            FormMenuPrincipal formMenuPrincipal = new FormMenuPrincipal();
            formMenuPrincipal.setVisible(true);
        } else {
            // Redirigir a una ventana por defecto o mostrar un mensaje de error.
            JOptionPane.showMessageDialog(null, "Rol no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}