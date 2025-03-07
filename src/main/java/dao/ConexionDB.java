
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Datos de conexión (ajusta según tu configuración)
    private static final String URL = "jdbc:mysql://localhost:3306/tryplep_db"; // Cambia "tu_base_de_datos"
    private static final String USUARIO = "root"; // Usuario de MySQL
    private static final String PASSWORD = ""; // Contraseña de MySQL (déjala vacía si no usas contraseña)

    // Método para obtener la conexión
    public static Connection getConexion() {
        Connection conexion = null;
        try {
            // Cargar el driver JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Error: No se encontró el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error de conexión a la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }

    // Método para cerrar la conexión
    public static void cerrarConexion(Connection conexion) {
        try {
            if (conexion != null) {
                conexion.close();
                System.out.println("✅ Conexión cerrada.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al cerrar la conexión.");
            e.printStackTrace();
        }
    }

}
