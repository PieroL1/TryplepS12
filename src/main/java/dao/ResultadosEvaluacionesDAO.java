package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultadosEvaluacionesDAO {

    public boolean registrarNotas(int idPostulacion, double notaPsicologica, double notaConocimiento, double notaEntrevista) {
        String sql = "INSERT INTO resultados_evaluaciones (id_postulacion, nota_psicologica, nota_conocimiento, nota_entrevista) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPostulacion);
            stmt.setDouble(2, notaPsicologica);
            stmt.setDouble(3, notaConocimiento);
            stmt.setDouble(4, notaEntrevista);

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}