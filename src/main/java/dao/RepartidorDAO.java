package dao;

import modelo.Repartidor;

import java.util.ArrayList;
/**
 * Define las operaciones de acceso a datos disponibles para la entidad Repartidores.
 */
public interface RepartidorDAO {
    public String create(Repartidor repartidor);
    public ArrayList<Repartidor> readAll();
    public String update(Repartidor repartidor);
    public String delete(Repartidor repartidor);
}
