package dao;

import model.ResultadosEvaluaciones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultadosEvaluacionesDAO {

    public List<ResultadosEvaluaciones> obtenerResultadosEvaluaciones() {
        List<ResultadosEvaluaciones> resultados = new ArrayList<>();
        String sql = "SELECT re.id, re.id_postulacion, re.nota_psicologica, re.nota_conocimiento, re.nota_entrevista, " +
                     "po.nombre AS nombre_postulante, po.apellido AS apellido_postulante, po.experiencia AS experiencia_postulante, " +
                     "pf.nombre AS nombre_perfil " +
                     "FROM resultados_evaluaciones re " +
                     "JOIN postulaciones p ON re.id_postulacion = p.id " +
                     "JOIN postulantes po ON p.id_postulante = po.id " +
                     "JOIN solicitudes_perfiles sp ON p.id_solicitud = sp.id_solicitud " +
                     "JOIN perfiles pf ON sp.id_perfil = pf.id " +
                     "WHERE sp.cantidad > 0";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ResultadosEvaluaciones resultado = new ResultadosEvaluaciones();
                resultado.setId(rs.getInt("id"));
                resultado.setIdPostulacion(rs.getInt("id_postulacion"));
                resultado.setNotaPsicologica(rs.getDouble("nota_psicologica"));
                resultado.setNotaConocimiento(rs.getDouble("nota_conocimiento"));
                resultado.setNotaEntrevista(rs.getDouble("nota_entrevista"));
                resultado.setNombrePostulante(rs.getString("nombre_postulante"));
                resultado.setApellidoPostulante(rs.getString("apellido_postulante"));
                resultado.setExperienciaPostulante(rs.getString("experiencia_postulante"));
                resultado.setNombrePerfil(rs.getString("nombre_perfil"));
                resultados.add(resultado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultados;
    }
    
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