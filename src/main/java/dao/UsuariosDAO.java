package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Usuarios;

public class UsuariosDAO {

    // Método para insertar un nuevo usuario (Crear)
    public boolean crearUsuario(Usuarios usuario, String password) {
        String sql = "INSERT INTO Usuarios (nombre, cargo, password) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCargo());
            stmt.setString(3, password); // Contraseña sin encriptar

            int filasInsertadas = stmt.executeUpdate();
            return filasInsertadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un usuario por su ID (Leer)
    public Usuarios obtenerUsuario(String id) {
        Usuarios usuario = null;
        String sql = "SELECT id, nombre, cargo FROM Usuarios WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuarios(rs.getString("id"), rs.getString("nombre"), rs.getString("cargo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // Método para obtener un usuario por su cargo
    public Usuarios obtenerUsuarioPorCargo(String cargo) {
        Usuarios usuario = null;
        String sql = "SELECT id, nombre, cargo FROM Usuarios WHERE cargo = ? LIMIT 1";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cargo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuarios(rs.getString("id"), rs.getString("nombre"), rs.getString("cargo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    // Método para actualizar un usuario (Actualizar)
    public boolean actualizarUsuario(Usuarios usuario, String password) {
        String sql = "UPDATE Usuarios SET nombre = ?, cargo = ?, password = ? WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCargo());
            stmt.setString(3, password);
          stmt.setString(4, usuario.getId());

            int filasActualizadas = stmt.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un usuario (Eliminar)
    public boolean eliminarUsuario(String id) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            int filasEliminadas = stmt.executeUpdate();
            return filasEliminadas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para validar usuario en el login
    public Usuarios validarUsuario(String nombre, String password) {
        Usuarios usuario = null;
        String sql = "SELECT id, nombre, cargo FROM Usuarios WHERE nombre = ? AND password = ?";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                usuario = new Usuarios(rs.getString("id"), rs.getString("nombre"), rs.getString("cargo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario; // Retorna el usuario si es válido, null si no lo es.
    }
}
