package dao.impl;

import dao.EntregaDAO;
import modelo.*;
import util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
/**
 * Implementación de las operaciones de acceso a datos para entregas.
 * Utiliza JDBC y conexiones a MySQL para realizar las operaciones CRUD
 * sobre la tabla de entregas.
 */
public class EntregaDAOImpl implements EntregaDAO {

    // Para crear una nueva entrega en BBDD se necesita pedido, repartidor, fecha y hora
    public String create(Entrega entrega){
        String sql = "INSERT INTO entregas (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getPedido().getId());
            stmt.setInt(2, entrega.getRepartidor().getId());
            stmt.setObject(3, entrega.getFecha()); // setObject permite pasar la fecha en su formato LocalDate de java al formato date de mySQL
            stmt.setObject(4, entrega.getHora());  // setObject permite pasar la hora en su formato LocalTime de java al formato date de mySQL

            stmt.executeUpdate();
            return "Éxito al guardar la entrega en la base de datos.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al guardar la entrega en la base de datos."; // Por ejemplo, si no existe el pedido asociado en bbdd
        }
    }

    // Método para obtener todas las Entregas de BBDD en un arreglo
    public ArrayList<Entrega> readAll(){
        String sql = "select \n" +
                "\te.id,\n" +
                "\te.id_pedido,\n" +
                "\tp.direccion,\n" +
                "\tp.tipo,\n" +
                "\tp.estado,\n" +
                "\te.id_repartidor,\n" +
                "\tr.nombre,\n" +
                "\te.fecha,\n" +
                "\te.hora\n" +
                "from (entregas as e left join pedidos as p on e.id_pedido = p.id) left join repartidores as r on e.id_repartidor = r.id ;";
        ArrayList<Entrega> entregas = new ArrayList<>();

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pedido pedido;
                Repartidor repartidor;
                Direccion direccion;

                try{
                    String[] datos = rs.getString("p.direccion").split("\\|");
                    direccion = new Direccion(datos[0], Integer.parseInt(datos[1]), datos[2]);

                    pedido = new Pedido(
                            rs.getInt("e.id_pedido"),
                            direccion,
                            TipoPedido.valueOf(rs.getString("p.tipo")),
                            EstadoPedido.valueOf(rs.getString("p.estado"))
                    );

                    repartidor = new Repartidor(
                            rs.getInt("e.id_repartidor"),
                            rs.getString("r.nombre")
                    );

                    entregas.add(new Entrega(
                            rs.getInt("e.id"),
                            pedido,
                            repartidor,
                            rs.getObject("e.fecha", LocalDate.class),
                            rs.getObject("e.hora", LocalTime.class)
                    ));

                }catch (NumberFormatException e){
                    System.out.println("Error en el formato numérico de direccion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entregas;
    }

    // Método que nos permite actualizar una entrega (solo a nivel de fecha, hora, id pedido o id repartidor
    public String update(Entrega entrega){
        String sql = "UPDATE entregas SET id_pedido=?, id_repartidor=?, fecha=?, hora=? WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getPedido().getId());
            stmt.setInt(2, entrega.getRepartidor().getId());
            stmt.setObject(3, entrega.getFecha());
            stmt.setObject(4, entrega.getHora());
            stmt.setInt(5, entrega.getId());

            int cambios = stmt.executeUpdate();

            if (cambios > 0) {
                return "Éxito al actualizar la entrega en la base de datos.";
            } else {
                return "No se encontró la entrega en la base de datos.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al actualizar la entrega en la base de datos.";
        }
    }

    // Método que nos permite eliminar una entrega por su ID
    public String delete(Entrega entrega){
        String sql = "DELETE FROM entregas WHERE id=?";

        try (Connection conn = ConexionBD.obtenerConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entrega.getId());

            int cambios = stmt.executeUpdate();
            if (cambios > 0) {
                return "Éxito al eliminar la entrega de la base de datos.";
            } else {
                return "No se encontró la entrega en la base de datos.";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al eliminar la entrega en la base de datos.";
        }
    }
}
