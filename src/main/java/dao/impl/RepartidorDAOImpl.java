package dao.impl;

import dao.RepartidorDAO;
import modelo.*;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * Implementación de las operaciones de acceso a datos para repartidores.
 * Utiliza JDBC y conexiones a MySQL para realizar las operaciones CRUD
 * sobre la tabla de repartidores.
 */
public class RepartidorDAOImpl implements RepartidorDAO {

    // Método para crear un nuevo repartidor en BBDD
    public String create(Repartidor repartidor){
        String sql = "INSERT INTO repartidores (nombre) VALUES (?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, repartidor.getNombre());
            stmt.executeUpdate();

            return "Éxtio al guardar el repartidor en la base de datos.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al guardar el repartidor en la base de datos.";
        }
    }

    // Método para obtener todos los Repartidores de BBDD en un arreglo
    public ArrayList<Repartidor> readAll(){
        String sql = "SELECT * FROM repartidores";
        ArrayList<Repartidor> repartidores = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                repartidores.add(new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                ));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return repartidores;
    }

    // Método que nos permite actualizar un repartidor
    public String update(Repartidor repartidor){
        String sql = "UPDATE repartidores SET nombre=? WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, repartidor.getNombre());
            stmt.setInt(2, repartidor.getId());

            int cambios = stmt.executeUpdate();

            if (cambios > 0) {
                return "Éxito al actualizar el repartidor en la base de datos.";
            } else {
                return "No se encontró el repartidor en la base de datos.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar el repartidor en la base de datos.";
        }
    }

    // Método que nos permite eliminar un repartidor por su ID
    public String delete(Repartidor repartidor){
        String sql = "DELETE FROM repartidores WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, repartidor.getId());

            int cambios = stmt.executeUpdate();
            if (cambios > 0) {
                return "Éxito al eliminar el repartidor de la base de datos.";
            } else {
                return "No se encontró el repartidor en la base de datos.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar el repartidor en la base de datos.";
        }
    }
}
