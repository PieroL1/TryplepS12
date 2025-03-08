package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.DetalleSolicitud;
import model.SolicitudPersonal;

public class SolicitudesDAO {

     // Método para registrar una nueva solicitud en la base de datos
    public String registrarSolicitud(SolicitudPersonal solicitud) {
        String sqlSolicitud = "INSERT INTO Solicitudes (id_jefe_proyecto, estado) VALUES (?, ?)";
        String sqlDetalle = "INSERT INTO solicitudes_perfiles (id_solicitud, id_perfil, cantidad) VALUES (?, ?, ?)";
        String numeroSolicitud = null;
        Connection conn = null;
        PreparedStatement stmtSolicitud = null;
        PreparedStatement stmtDetalle = null;

        try {
            conn = ConexionDB.getConexion();
            conn.setAutoCommit(false); // Iniciar transacción

            stmtSolicitud = conn.prepareStatement(sqlSolicitud, Statement.RETURN_GENERATED_KEYS);
            stmtSolicitud.setInt(1, solicitud.getIdSolicitante());
            stmtSolicitud.setString(2, solicitud.getEstado());

            int filasInsertadas = stmtSolicitud.executeUpdate();
            if (filasInsertadas > 0) {
                ResultSet generatedKeys = stmtSolicitud.getGeneratedKeys();
                if (generatedKeys.next()) {
                    numeroSolicitud = String.valueOf(generatedKeys.getInt(1)); // Obtiene el ID generado
                    int idSolicitud = generatedKeys.getInt(1);

                    stmtDetalle = conn.prepareStatement(sqlDetalle);
                    for (DetalleSolicitud detalle : solicitud.getDetalles()) {
                        // Verificar si el perfil existe antes de agregarlo
                        if (perfilExiste(detalle.getPerfil().getId())) {
                            stmtDetalle.setInt(1, idSolicitud);
                            stmtDetalle.setInt(2, detalle.getPerfil().getId());
                            stmtDetalle.setInt(3, detalle.getCantidad());
                            stmtDetalle.addBatch();
                        } else {
                            throw new SQLException("El perfil con ID " + detalle.getPerfil().getId() + " no existe.");
                        }
                    }
                    stmtDetalle.executeBatch();
                }
            }
            conn.commit(); // Confirmar transacción
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir transacción en caso de error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (stmtSolicitud != null) {
                try {
                    stmtSolicitud.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmtDetalle != null) {
                try {
                    stmtDetalle.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return numeroSolicitud;
    }

    // Método para verificar si un perfil existe
    private boolean perfilExiste(int idPerfil) {
        String sql = "SELECT COUNT(*) FROM perfiles WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idPerfil);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Método para obtener una solicitud por su ID
    public SolicitudPersonal obtenerSolicitudPorId(int id) {
        SolicitudPersonal solicitud = null;
        String sql = "SELECT * FROM Solicitudes WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                solicitud = new SolicitudPersonal();
                solicitud.setNumeroSolicitud(rs.getInt("id")); // CORREGIDO: Se usa getInt("id") en lugar de getString("id")
                solicitud.setEstado(rs.getString("estado"));
                solicitud.setFechaRegistro(rs.getTimestamp("fecha_solicitud")); // Agregar fecha de solicitud
                solicitud.setNombreJefeProyecto(obtenerNombreJefeProyecto(rs.getInt("id_jefe_proyecto"))); // Obtener nombre del jefe de proyecto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitud;
    }

    // Método para listar todas las solicitudes
    public List<SolicitudPersonal> listarSolicitudes() {
        List<SolicitudPersonal> lista = new ArrayList<>();
        String sql = "SELECT * FROM Solicitudes";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SolicitudPersonal solicitud = new SolicitudPersonal();
                solicitud.setNumeroSolicitud(rs.getInt("id")); // CORREGIDO
                solicitud.setEstado(rs.getString("estado"));
                solicitud.setFechaRegistro(rs.getTimestamp("fecha_solicitud")); // Agregar fecha de solicitud
                solicitud.setNombreJefeProyecto(obtenerNombreJefeProyecto(rs.getInt("id_jefe_proyecto"))); // Obtener nombre del jefe de proyecto
                lista.add(solicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
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
                solicitud.setFechaRegistro(rs.getTimestamp("fecha_solicitud")); // Agregar fecha de solicitud
                solicitud.setNombreJefeProyecto(obtenerNombreJefeProyecto(rs.getInt("id_jefe_proyecto"))); // Obtener nombre del jefe de proyecto
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
                solicitud.setFechaRegistro(rs.getTimestamp("fecha_solicitud")); // Agregar fecha de solicitud
                solicitud.setNombreJefeProyecto(obtenerNombreJefeProyecto(rs.getInt("id_jefe_proyecto"))); // Obtener nombre del jefe de proyecto
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

    // Método para obtener el nombre del jefe de proyecto
    private String obtenerNombreJefeProyecto(int idJefeProyecto) {
        String nombre = null;
        String sql = "SELECT nombre FROM Usuarios WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idJefeProyecto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nombre = rs.getString("nombre");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombre;
    }
}