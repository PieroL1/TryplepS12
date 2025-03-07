package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.SolicitudPersonal;

public class SolicitudesDAO {

    // Método para registrar una nueva solicitud en la base de datos
    public String registrarSolicitud(SolicitudPersonal solicitud) {
        String sql = "INSERT INTO Solicitudes (id_jefe_proyecto, estado) VALUES (?, ?)";
        String numeroSolicitud = null;

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, solicitud.getIdSolicitante()); // CORREGIDO: Se usa getIdSolicitante()
            stmt.setString(2, solicitud.getEstado());

            int filasInsertadas = stmt.executeUpdate();
            if (filasInsertadas > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    numeroSolicitud = String.valueOf(generatedKeys.getInt(1)); // Obtiene el ID generado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return numeroSolicitud;
    }

    // Método para obtener una solicitud por su ID
    public SolicitudPersonal obtenerSolicitud(String numeroSolicitud) {
        SolicitudPersonal solicitud = null;
        String sql = "SELECT * FROM Solicitudes WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numeroSolicitud);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                solicitud = new SolicitudPersonal();
                solicitud.setNumeroSolicitud(rs.getInt("id")); // CORREGIDO: Se usa getInt("id") en lugar de getString("id")
                solicitud.setEstado(rs.getString("estado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitud;
    }

    // Método para listar solicitudes por solicitante (Jefe de Proyecto)
    public List<SolicitudPersonal> listarSolicitudesPorSolicitante(int idSolicitante) {
        List<SolicitudPersonal> lista = new ArrayList<>();
        String sql = "SELECT * FROM Solicitudes WHERE id_jefe_proyecto = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSolicitante); // CORREGIDO: Se usa setInt en lugar de setString
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SolicitudPersonal solicitud = new SolicitudPersonal();
                solicitud.setNumeroSolicitud(rs.getInt("id")); // CORREGIDO
                solicitud.setEstado(rs.getString("estado"));
                lista.add(solicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para listar solicitudes por estado
    public List<SolicitudPersonal> listarSolicitudesPorEstado(String estado) {
        List<SolicitudPersonal> lista = new ArrayList<>();
        String sql = "SELECT * FROM Solicitudes WHERE estado = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SolicitudPersonal solicitud = new SolicitudPersonal();
                solicitud.setNumeroSolicitud(rs.getInt("id")); // CORREGIDO
                solicitud.setEstado(rs.getString("estado"));
                lista.add(solicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Método para actualizar el estado de una solicitud
    public boolean actualizarEstadoSolicitud(int numeroSolicitud, String nuevoEstado) {
        String sql = "UPDATE Solicitudes SET estado = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, numeroSolicitud); // CORREGIDO: Se usa setInt

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
