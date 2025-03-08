package service;

import dao.UsuariosDAO;
import model.Usuarios;

public class LoginService {
    private UsuariosDAO usuariosDAO;

    public LoginService() {
        usuariosDAO = new UsuariosDAO();
    }

    public Usuarios autenticar(String nombre, String password) {
        Usuarios usuario = usuariosDAO.obtenerUsuarioPorNombre(nombre);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }
}