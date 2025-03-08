package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Perfil;

public class PerfilDAOImpl implements PerfilDAO {
     
     @Override
    public List<Perfil> listarPerfiles() {
        List<Perfil> perfiles = new ArrayList<>();
        String sql = "SELECT id, nombre FROM perfiles";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("id"));
                perfil.setNombre(rs.getString("nombre"));
                perfiles.add(perfil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return perfiles;
    }
    
    @Override
    public Perfil obtenerPerfil(int id) {
        Perfil perfil = null;
        String sql = "SELECT id, nombre FROM perfiles WHERE id = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                perfil = new Perfil();
                perfil.setId(rs.getInt("id"));
                perfil.setNombre(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return perfil;
    }
    
    @Override
    public void agregarPerfil(Perfil perfil) {
        //perfil.setId(sequence++);
       // perfilesDb.put(perfil.getId(), perfil);
    }
    
    @Override
    public void modificarPerfil(Perfil perfil) {
       // perfilesDb.put(perfil.getId(), perfil);
    }
    
    @Override
    public void eliminarPerfil(int id) {
       // perfilesDb.remove(id);
    }
}