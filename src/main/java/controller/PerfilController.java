package controller;

import model.Perfil;
import service.PerfilService;
import java.util.List;

public class PerfilController {
    private PerfilService perfilService;

    public PerfilController() {
        perfilService = new PerfilService();
    }

    public List<Perfil> listarPerfiles() {
        return perfilService.listarPerfiles();
    }

    public void agregarPerfil(Perfil perfil) {
        perfilService.agregarPerfil(perfil);
    }

    public void modificarPerfil(Perfil perfil) {
        perfilService.modificarPerfil(perfil);
    }

    public void eliminarPerfil(int id) {
        perfilService.eliminarPerfil(id);
    }
}