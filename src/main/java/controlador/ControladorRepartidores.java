package controlador;

import dao.impl.RepartidorDAOImpl;
import modelo.Repartidor;

import java.util.ArrayList;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con repartidores.
 * Actúa como intermediario entre las vistas de la aplicación y la capa de
 * acceso a datos de repartidores.
 */
public class ControladorRepartidores {
    private RepartidorDAOImpl repartidorDAOImpl;

    /**
     * Crea un controlador de repartidores e inicializa su objeto de acceso a datos.
     */
    public ControladorRepartidores(){
        repartidorDAOImpl = new RepartidorDAOImpl();
    }

    /**
     * Obtiene todos los repartidores registrados en la base de datos.
     *
     * @return lista con los repartidores registrados
     */
    public ArrayList<Repartidor> getRepartidores() {
        return repartidorDAOImpl.readAll();
    }

    /**
     * Solicita el registro de un nuevo repartidor.
     *
     * @param repartidor repartidor que se desea registrar
     * @return mensaje con el resultado de la operación
     */
    public String agregarNuevoRepartidor(Repartidor repartidor){
        return repartidorDAOImpl.create(repartidor);
    }

    /**
     * Solicita la actualización de un repartidor existente.
     *
     * @param repartidor repartidor con los datos actualizados
     * @return mensaje con el resultado de la operación
     */
    public String actualizarRepartidor(Repartidor repartidor){
        return repartidorDAOImpl.update(repartidor);
    }

    /**
     * Solicita la eliminación de un repartidor.
     *
     * @param repartidor repartidor que identifica el registro que se desea eliminar
     * @return mensaje con el resultado de la operación
     */

    public String eliminaRepartidor(Repartidor repartidor){
        return repartidorDAOImpl.delete(repartidor);
    }

}
