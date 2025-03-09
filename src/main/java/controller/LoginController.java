package controller;

import javax.swing.JOptionPane;
import model.Usuarios;
import model.Postulante;
import service.LoginService;
import service.PostulanteService;
import util.SessionManager;
import view.FormGestionarPerfiles;
import view.FormAsignarEspecialista;
import view.FormComplementarSolicitud;
import view.FormMenuPrincipal;
import view.PortalPostulante;
import view.AsistentesContratacionesMenu;
import view.AsistentesContratacionesMenu;

public class LoginController {
    private LoginService loginService;
    private PostulanteService postulanteService;

    public LoginController() {
        loginService = new LoginService();
        postulanteService = new PostulanteService();
    }

    public boolean iniciarSesion(String credencial, String password) {
        Usuarios usuario = loginService.autenticar(credencial, password);
        if (usuario != null) {
            SessionManager.iniciarSesion(usuario);
            redirigirSegunRol();
            return true;
        }

        Postulante postulante = postulanteService.login(credencial, password);
        if (postulante != null) {
            SessionManager.iniciarSesion(postulante);
            new PortalPostulante().setVisible(true);
            return true;
        }

        return false;
    }

    private void redirigirSegunRol() {
        if (SessionManager.esJefeDeSistemas()) {
            FormGestionarPerfiles formGestionarPerfiles = new FormGestionarPerfiles();
            formGestionarPerfiles.setVisible(true);
        } else if (SessionManager.esJefeDeProyecto()) {
            FormMenuPrincipal formMenuPrincipal = new FormMenuPrincipal();
            formMenuPrincipal.setVisible(true);
        } else if (SessionManager.esJefeDeContrataciones()) {
            FormAsignarEspecialista formAsignarEspecialista = new FormAsignarEspecialista();
            formAsignarEspecialista.setVisible(true);
        } else if (SessionManager.esEspecialistaDeContrataciones()) {
            FormComplementarSolicitud formComplementarSolicitud = new FormComplementarSolicitud();
            formComplementarSolicitud.setVisible(true);
        } else if (SessionManager.esAsistenteDeContrataciones()) {
            AsistentesContratacionesMenu asistenteMenu = new AsistentesContratacionesMenu();
            asistenteMenu.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Rol no reconocido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}