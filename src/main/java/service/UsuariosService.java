package service;

import dao.UsuariosDAO;
import model.Usuarios;

import java.util.List;

public class UsuariosService {
    private UsuariosDAO usuariosDAO;

    public UsuariosService() {
        usuariosDAO = new UsuariosDAO();
    }

    public List<Usuarios> obtenerUsuariosPorRol(String rol) {
        return usuariosDAO.obtenerUsuariosPorRol(rol);
    }
}