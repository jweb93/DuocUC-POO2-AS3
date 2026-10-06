package modelo;
/**
 * Define los posibles estados de un pedido dentro de la aplicación.
 * Cada estado posee una descripción utilizada para su representación.
 */
public enum EstadoPedido {
    PENDIENTE("Pendiente"),
    EN_REPARTO("En reparto"),
    ENTREGADO("Entregado");

    private final String descripcion;

    EstadoPedido(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }

    // Este metodo se agrega para poder convertir de String a EstadoPedido desde BBDD
    public static EstadoPedido desdeTexto(String texto){
        return EstadoPedido.valueOf(texto.toUpperCase());
    }

    /**
     * Devuelve la descripción del estado pedido.
     *
     * @return descripción utilizada para representar el estado
     */
    @Override
    public String toString() {
        return descripcion;
    }
}