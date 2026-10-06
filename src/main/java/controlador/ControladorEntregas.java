package controlador;

import dao.impl.EntregaDAOImpl;
import modelo.Entrega;

import java.util.ArrayList;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con entregas.
 * Actúa como intermediario entre las vistas de la aplicación y la capa de
 * acceso a datos de entregas.
 */

public class ControladorEntregas {
    private EntregaDAOImpl entregaDAOImpl;

    /**
     * Crea un controlador de entregas e inicializa su objeto de acceso a datos.
     */
    public ControladorEntregas(){
        entregaDAOImpl = new EntregaDAOImpl();
    }

    /**
     * Obtiene todas las entregas registradas en la base de datos.
     *
     * @return lista con las entregas registradas
     */
    public ArrayList<Entrega> getEntregas() {
        return entregaDAOImpl.readAll();
    }

    /**
     * Solicita el registro de una nueva entrega.
     *
     * @param entrega entrega que se desea registrar
     * @return mensaje con el resultado de la operación
     */
    public String agregarNuevaEntrega(Entrega entrega){
        return entregaDAOImpl.create(entrega);
    }

    /**
     * Solicita la actualización de una entrega existente.
     *
     * @param entrega entrega con los datos actualizados
     * @return mensaje con el resultado de la operación
     */
    public String actualizarEntrega(Entrega entrega){
        return entregaDAOImpl.update(entrega);
    }

    /**
     * Solicita la eliminación de una entrega.
     *
     * @param entrega entrega que identifica el registro que se desea eliminar
     * @return mensaje con el resultado de la operación
     */
    public String eliminaEntrega(Entrega entrega){
        return entregaDAOImpl.delete(entrega);
    }
}
