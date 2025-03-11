package dao;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.DetalleSolicitud;
import model.Solicitud;
import model.SolicitudPerfil;
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
    
    public List<SolicitudPersonal> obtenerSolicitudesPendientes() {
        List<SolicitudPersonal> solicitudes = new ArrayList<>();
        String sql = "SELECT * FROM solicitudes WHERE estado = 'pendiente'";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SolicitudPersonal solicitud = new SolicitudPersonal();
                solicitud.setId(rs.getInt("id"));
                solicitud.setIdJefeProyecto(rs.getInt("id_jefe_proyecto"));
                solicitud.setEstado(rs.getString("estado"));
                solicitud.setFechaSolicitud(rs.getDate("fecha_solicitud"));
                solicitudes.add(solicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    public boolean asignarEspecialista(int solicitudId, String especialistaId) {
        String sql = "UPDATE solicitudes SET especialista_id = ?, estado = 'asignada' WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, especialistaId);
            stmt.setInt(2, solicitudId);
            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

      public List<SolicitudPerfil> obtenerSolicitudesAsignadasConPerfiles(String especialistaId) {
        List<SolicitudPerfil> solicitudesPerfiles = new ArrayList<>();
        String sql = "SELECT s.id, s.fecha_solicitud, p.nombre, sp.cantidad " +
                     "FROM solicitudes s " +
                     "JOIN solicitudes_perfiles sp ON s.id = sp.id_solicitud " +
                     "JOIN perfiles p ON sp.id_perfil = p.id " +
                     "WHERE s.especialista_id = ? AND s.estado = 'asignada'";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, especialistaId);
            System.out.println("DAO: ejecutando consulta SQL con especialista ID: " + especialistaId);  // Imprimir el ID del especialista
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                SolicitudPerfil solicitudPerfil = new SolicitudPerfil();
                solicitudPerfil.setId(rs.getInt("id"));
                solicitudPerfil.setFechaSolicitud(rs.getDate("fecha_solicitud"));
                solicitudPerfil.setNombrePerfil(rs.getString("nombre"));
                solicitudPerfil.setCantidad(rs.getInt("cantidad"));
                solicitudesPerfiles.add(solicitudPerfil);
            }
            System.out.println("DAO: número de solicitudes obtenidas: " + solicitudesPerfiles.size());  // Imprimir el número de solicitudes obtenidas
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudesPerfiles;
    }

    public boolean guardarInformacionComplementaria(int solicitudId, String fechaExamen, String fechaEntrevista, String sueldo, String tipoContrato) {
        String sql = "INSERT INTO informacion_complementaria (solicitud_id, fecha_examen, fecha_entrevista, sueldo, tipo_contrato) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, solicitudId);
            stmt.setDate(2, Date.valueOf(fechaExamen));
            stmt.setDate(3, Date.valueOf(fechaEntrevista));
            stmt.setBigDecimal(4, new BigDecimal(sueldo));
            stmt.setString(5, tipoContrato);

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
     public List<SolicitudPerfil> obtenerSolicitudesConInformacionComplementaria() {
        List<SolicitudPerfil> solicitudesPerfiles = new ArrayList<>();
        String sql = "SELECT s.id, s.fecha_solicitud, sp.cantidad, p.nombre as nombre_perfil, ic.fecha_examen, ic.fecha_entrevista " +
                     "FROM solicitudes s " +
                     "JOIN solicitudes_perfiles sp ON s.id = sp.id_solicitud " +
                     "JOIN perfiles p ON sp.id_perfil = p.id " +
                     "LEFT JOIN informacion_complementaria ic ON s.id = ic.solicitud_id";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SolicitudPerfil solicitudPerfil = new SolicitudPerfil();
                solicitudPerfil.setId(rs.getInt("id"));
                solicitudPerfil.setFechaSolicitud(rs.getDate("fecha_solicitud"));
                solicitudPerfil.setNombrePerfil(rs.getString("nombre_perfil"));
                solicitudPerfil.setCantidad(rs.getInt("cantidad"));
                solicitudPerfil.setFechaExamen(rs.getDate("fecha_examen"));
                solicitudPerfil.setFechaEntrevista(rs.getDate("fecha_entrevista"));
                solicitudesPerfiles.add(solicitudPerfil);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudesPerfiles;
    }
     
     public boolean registrarPostulacion(int postulanteId, int solicitudId) {
        String sql = "INSERT INTO postulaciones (id_postulante, id_solicitud, fecha_postulacion) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, postulanteId);
            stmt.setInt(2, solicitudId);
            stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
     
      public List<Solicitud> obtenerSolicitudesPorRevisar() {
        List<Solicitud> solicitudes = new ArrayList<>();
        String sql = "SELECT s.id, s.fecha_solicitud, p.nombre AS perfil, s.estado " +
                     "FROM solicitudes s " +
                     "JOIN solicitudes_perfiles sp ON s.id = sp.id_solicitud " +
                     "JOIN perfiles p ON sp.id_perfil = p.id " +
                     "WHERE s.estado = 'por revisar'";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Solicitud solicitud = new Solicitud();
                solicitud.setId(rs.getInt("id"));
                solicitud.setFechaSolicitud(rs.getDate("fecha_solicitud"));
                solicitud.setPerfil(rs.getString("perfil"));
                solicitud.setEstado(rs.getString("estado"));
                solicitudes.add(solicitud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return solicitudes;
    }

    public boolean cambiarEstadoSolicitud(int idSolicitud, String nuevoEstado) {
        String sql = "UPDATE solicitudes SET estado = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idSolicitud);

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}