package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Postulacion;

public class PostulacionDAO {

    public boolean registrarPostulacion(int postulanteId, int solicitudId) {
        System.out.println("Intentando registrar postulación con postulanteId: " + postulanteId + " y solicitudId: " + solicitudId);
        String sql = "INSERT INTO postulaciones (id_postulante, id_solicitud, fecha_postulacion) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postulanteId);
            stmt.setInt(2, solicitudId);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int filasInsertadas = stmt.executeUpdate();
            System.out.println("Filas insertadas: " + filasInsertadas);
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al registrar postulación: " + e.getMessage());
        }
        return false;
    }

    public boolean actualizarPostulante(int id, String nombre, String apellido, String email, String telefono, String direccion, String experiencia) {
        String sql = "UPDATE postulantes SET nombre = ?, apellido = ?, email = ?, telefono = ?, direccion = ?, experiencia = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, apellido);
            stmt.setString(3, email);
            stmt.setString(4, telefono);
            stmt.setString(5, direccion);
            stmt.setString(6, experiencia);
            stmt.setInt(7, id);

            int filasActualizadas = stmt.executeUpdate();
            System.out.println("Filas actualizadas: " + filasActualizadas);
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar postulante: " + e.getMessage());
        }
        return false;
    }
    
      public List<Postulacion> obtenerPostulaciones() {
        List<Postulacion> postulaciones = new ArrayList<>();
        String sql = "SELECT p.id, p.id_postulante, p.id_solicitud, p.fecha_postulacion, po.nombre, po.apellido, po.experiencia " +
                     "FROM postulaciones p " +
                     "JOIN postulantes po ON p.id_postulante = po.id";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Postulacion postulacion = new Postulacion();
                postulacion.setId(rs.getInt("id"));
                postulacion.setIdPostulante(rs.getInt("id_postulante"));
                postulacion.setIdSolicitud(rs.getInt("id_solicitud"));
                postulacion.setFechaPostulacion(rs.getDate("fecha_postulacion"));
                postulacion.setNombrePostulante(rs.getString("nombre"));
                postulacion.setApellidoPostulante(rs.getString("apellido"));
                postulacion.setExperienciaPostulante(rs.getString("experiencia"));
                postulaciones.add(postulacion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postulaciones;
    }
}