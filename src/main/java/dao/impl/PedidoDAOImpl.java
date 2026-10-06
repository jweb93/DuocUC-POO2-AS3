package dao.impl;

import dao.PedidoDAO;
import modelo.Direccion;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.TipoPedido;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementación de las operaciones de acceso a datos para pedidos.
 * Utiliza JDBC y conexiones a MySQL para realizar las operaciones CRUD
 * sobre la tabla de pedidos.
 */
public class PedidoDAOImpl implements PedidoDAO {

    // Método para crear un nuevo pedido en BBDD
    public String create(Pedido pedido){
        String sql = "INSERT INTO pedidos (direccion, tipo, estado) VALUES (?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccionEntrega().serializar());
            stmt.setString(2, pedido.getTipoPedido().name()); // Con name() obtenemos el valor del Enum
            stmt.setString(3, pedido.getEstado().name());

            stmt.executeUpdate();
            return "Éxito al guardar el pedido en la base de datos.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al guardar el pedido en la base de datos.";
        }
    }

    // Método para obtener todos los Pedidos de BBDD en un arreglo
    public ArrayList<Pedido> readAll(){
        String sql = "SELECT * FROM pedidos";
        ArrayList<Pedido> pedidos = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Direccion direccion;
                try{
                    String[] datos = rs.getString("direccion").split("\\|");
                    direccion = new Direccion(datos[0], Integer.parseInt(datos[1]), datos[2]);

                    pedidos.add(new Pedido (
                            rs.getInt("id"),
                            direccion,
                            TipoPedido.valueOf(rs.getString("tipo")),
                            EstadoPedido.valueOf(rs.getString("estado")))
                    );
                }catch (NumberFormatException e){
                    System.out.println("Error en el formato numérico de direccion");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    // Método que nos permite actualizar un pedido
    public String update(Pedido pedido){
        String sql = "UPDATE pedidos SET direccion=?, tipo=?, estado=? WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pedido.getDireccionEntrega().serializar());
            stmt.setString(2, pedido.getTipoPedido().name()); // Con name() obtenemos el valor del Enum
            stmt.setString(3, pedido.getEstado().name());
            stmt.setInt(4, pedido.getId());

            int cambios = stmt.executeUpdate();

            if (cambios > 0) {
                return "Éxito al actualizar el pedido en la base de datos.";
            } else {
                return "No se encontró el pedido en la base de datos.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar el pedido en la base de datos.";
        }
    }

    // Método que nos permite eliminar un pedio por su ID
    public String delete(Pedido pedido){
        String sql = "DELETE FROM pedidos WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getId());

            int cambios = stmt.executeUpdate();
            if (cambios > 0) {
                return "Éxito al eliminar el pedido de la base de datos.";
            } else {
                return "No se encontró el pedido en la base de datos.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar el pedido en la base de datos.";
        }
    }

}
