package dao;

import model.Postulante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostulanteDAO {

    public Postulante login(String email, String password) {
        Postulante postulante = null;
        String sql = "SELECT * FROM postulantes WHERE email = ? AND password = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                postulante = new Postulante();
                postulante.setId(rs.getInt("id"));
                postulante.setNombre(rs.getString("nombre"));
                postulante.setEmail(rs.getString("email"));
                postulante.setTelefono(rs.getString("telefono"));
                postulante.setCv(rs.getString("cv"));
                postulante.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postulante;
    }
}