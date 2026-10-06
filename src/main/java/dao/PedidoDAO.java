package dao;

import modelo.Pedido;

import java.util.ArrayList;
/**
 * Define las operaciones de acceso a datos disponibles para la entidad Pedido.
 */
public interface PedidoDAO {

    public String create(Pedido pedido);
    public ArrayList<Pedido> readAll();
    public String update(Pedido pedido);
    public String delete(Pedido pedido);
}
