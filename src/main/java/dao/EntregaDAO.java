package dao;

import modelo.Entrega;

import java.util.ArrayList;
/**
 * Define las operaciones de acceso a datos disponibles para la entidad Entrega.
 */
public interface EntregaDAO {
    public String create(Entrega entrega);
    public ArrayList<Entrega> readAll();
    public String update(Entrega entrega);
    public String delete(Entrega entrega);

}
