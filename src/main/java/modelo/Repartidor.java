package modelo;

/**
 * Representa un repartidor que recibirá pedido y los despachará
 */

public class Repartidor {
    private int id;
    private String nombre;

    // Constructor cuando el objeto ya existe en bbdd
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Constructor cuando el objeto se va a crear en bdd
    public Repartidor(String nombre) {
        this.nombre = nombre;
    }

    // Constructor cuado se eliminará un objeto por id

    // Getter and setter
    public Repartidor(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve una representación textual resumida del repartidor.
     */
    @Override
    public String toString() {
        return "(ID: " + id + ") " + nombre;
    }

}
