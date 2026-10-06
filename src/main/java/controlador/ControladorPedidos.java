package controlador;

import dao.impl.PedidoDAOImpl;
import modelo.Pedido;

import java.util.ArrayList;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con pedidos.
 * Actúa como intermediario entre las vistas de la aplicación y la capa de
 * acceso a datos de pedidos.
 */

public class ControladorPedidos {
    private PedidoDAOImpl pedidoDAOImpl;

    /**
     * Crea un controlador de pedidos e inicializa su objeto de acceso a datos.
     */
    public ControladorPedidos(){
        pedidoDAOImpl = new PedidoDAOImpl();
    }

    /**
     * Obtiene todos los pedidos registrados en la base de datos.
     *
     * @return lista con los pedidos registrados
     */
    public ArrayList<Pedido> getPedidos() {
        return pedidoDAOImpl.readAll();
    }

    /**
     * Solicita el registro de un nuevo pedido en la base de datos.
     *
     * @param pedido pedido que se desea registrar
     * @return mensaje con el resultado de la operación
     */
    public String agregarNuevoPedido(Pedido pedido){
        return pedidoDAOImpl.create(pedido);
    }

    /**
     * Solicita la actualización de un pedido existente.
     *
     * @param pedido pedido con los datos que se desean actualizar
     * @return mensaje con el resultado de la operación
     */

    public String actualizarPedido(Pedido pedido){
        return pedidoDAOImpl.update(pedido);
    }

    /**
     * Solicita la eliminación de un pedido de la base de datos.
     *
     * @param pedido pedido que identifica el registro que se desea eliminar
     * @return mensaje con el resultado de la operación
     */
    public String eliminaPedido(Pedido pedido){
        return pedidoDAOImpl.delete(pedido);
    }
}
