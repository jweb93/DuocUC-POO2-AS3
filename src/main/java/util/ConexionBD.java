package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Proporciona la configuración necesaria para establecer conexiones
 * con la base de datos MySQL utilizada por SpeedFast.
 */

public class ConexionBD {

    // Estos valores deben ser actualizados a la configuración local de bbdd
    private static final String URL = "jdbc:mysql://localhost:3306/speedfast_db";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }
}
