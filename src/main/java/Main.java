import java.sql.Connection;
import java.sql.SQLException;

import controlador.ControladorEntregas;
import controlador.ControladorPedidos;
import controlador.ControladorRepartidores;
import util.ConexionBD;
import vista.VentanaPrincipal;

import javax.swing.*;
/**
 * Clase principal de la aplicación SpeedFast.
 * Inicializa los controladores del sistema y muestra la ventana principal
 * de la aplicación mediante el hilo de eventos de Swing.
 */
public class Main {
    public static void main(String[] args) {
        ControladorPedidos controladorPedidos = new ControladorPedidos();
        ControladorRepartidores controladorRepartidores = new ControladorRepartidores();
        ControladorEntregas controladorEntregas = new ControladorEntregas();

        SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal(controladorPedidos, controladorRepartidores, controladorEntregas).setVisible(true);
        });

        // Validación de conexión a BBDD creada con archivo speedfast_db.sql
        try (Connection conn = ConexionBD.obtenerConexion()) {
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar con la base de datos:");
            e.printStackTrace();
        }
    }
}

