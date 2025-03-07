package dao;
import java.util.List;
import model.Perfil;

public interface PerfilDAO {
    List<Perfil> listarPerfiles();
    Perfil obtenerPerfil(int id);
    void agregarPerfil(Perfil perfil);
    void modificarPerfil(Perfil perfil);
    void eliminarPerfil(int id);
}

