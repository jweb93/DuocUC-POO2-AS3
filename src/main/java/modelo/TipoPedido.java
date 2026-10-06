package modelo;

/**
 * Define los tipos de pedido disponibles en la aplicación.
 * Cada constante posee una descripción utilizada para su representación
 * en la interfaz de usuario.
 */
public enum TipoPedido {
    COMIDA("Comida"),
    ENCOMIENDA("Encomienda"),
    EXPRESS("Express");

    private final String descripcion;

    TipoPedido(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }

    // Este metodo se agrega para poder convertir de String a TipoPedido desde BBDD
    public static TipoPedido desdeTexto(String texto){
        return TipoPedido.valueOf(texto.toUpperCase());
    }

    /**
     * Devuelve la descripción del tipo de pedido.
     *
     * @return descripción utilizada para representar el tipo
     */
    @Override
    public String toString() {
        return descripcion;
    }
}
