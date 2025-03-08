package dao;

import java.sql.*;
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
        String sql = "INSERT INTO perfiles (nombre) VALUES (?)";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, perfil.getNombre());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void modificarPerfil(Perfil perfil) {
        String sql = "UPDATE perfiles SET nombre = ? WHERE id = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, perfil.getNombre());
            stmt.setInt(2, perfil.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void eliminarPerfil(int id) {
        String sql = "DELETE FROM perfiles WHERE id = ?";
        
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}