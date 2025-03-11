package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContratosDAO {

     public boolean generarContrato(int idPostulacion, String fechaInicio, String fechaFin, double salario, String tipoContrato) {
        String sqlInsertContrato = "INSERT INTO contratos (id_postulacion, fecha_inicio, fecha_fin, salario, tipo_contrato) VALUES (?, ?, ?, ?, ?)";
        String sqlUpdatePerfil = "UPDATE solicitudes_perfil sp " +
                                 "JOIN postulaciones p ON sp.id_solicitud = p.id_solicitud " +
                                 "SET sp.cantidad = sp.cantidad - 1 " +
                                 "WHERE p.id = ?";
        String sqlCheckPerfil = "SELECT COUNT(*) AS count FROM solicitudes_perfil WHERE id_solicitud = (SELECT id_solicitud FROM postulaciones WHERE id = ?) AND cantidad > 0";
        String sqlUpdateSolicitud = "UPDATE solicitudes SET estado = 'completado' WHERE id = (SELECT id_solicitud FROM postulaciones WHERE id = ?)";

        try (Connection conn = ConexionDB.getConexion()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtInsertContrato = conn.prepareStatement(sqlInsertContrato);
                 PreparedStatement stmtUpdatePerfil = conn.prepareStatement(sqlUpdatePerfil);
                 PreparedStatement stmtCheckPerfil = conn.prepareStatement(sqlCheckPerfil);
                 PreparedStatement stmtUpdateSolicitud = conn.prepareStatement(sqlUpdateSolicitud)) {

                // Insertar contrato
                stmtInsertContrato.setInt(1, idPostulacion);
                stmtInsertContrato.setString(2, fechaInicio);
                stmtInsertContrato.setString(3, fechaFin);
                stmtInsertContrato.setDouble(4, salario);
                stmtInsertContrato.setString(5, tipoContrato);
                stmtInsertContrato.executeUpdate();

                // Actualizar cantidad en perfil
                stmtUpdatePerfil.setInt(1, idPostulacion);
                stmtUpdatePerfil.executeUpdate();

                // Verificar si todas las cantidades son 0
                stmtCheckPerfil.setInt(1, idPostulacion);
                try (ResultSet rs = stmtCheckPerfil.executeQuery()) {
                    if (rs.next() && rs.getInt("count") == 0) {
                        // Actualizar estado de la solicitud a 'completado'
                        stmtUpdateSolicitud.setInt(1, idPostulacion);
                        stmtUpdateSolicitud.executeUpdate();
                    }
                }

                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}