package service;

import dao.PerfilDAO;
import dao.PerfilDAOImpl;
import model.Perfil;
import java.util.List;

public class PerfilService {
    private PerfilDAO perfilDAO;

    public PerfilService() {
        perfilDAO = new PerfilDAOImpl();
    }

    public List<Perfil> listarPerfiles() {
        return perfilDAO.listarPerfiles();
    }

    public void agregarPerfil(Perfil perfil) {
        perfilDAO.agregarPerfil(perfil);
    }

    public void modificarPerfil(Perfil perfil) {
        perfilDAO.modificarPerfil(perfil);
    }

    public void eliminarPerfil(int id) {
        perfilDAO.eliminarPerfil(id);
    }
}