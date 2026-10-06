package modelo;
/**
 * Representa un pedido que debe ser entregado a una dirección
 */
public class Pedido {
    private int id;
    private Direccion direccionEntrega;
    private TipoPedido tipoPedido;
    private EstadoPedido estado; //PENDIENTE, EN_REPARTO, ENTREGADO

    // Constructor cuando el Pedido está en bbdd con un id definido
    public Pedido(int id, Direccion direccionEntrega, TipoPedido tipoPedido, EstadoPedido estado) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
    }

    // Constructor cuando el Pedido aun no está en BBDD
    public Pedido(Direccion direccionEntrega, TipoPedido tipoPedido, EstadoPedido estado) {
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.estado = estado;
    }

    // Constructir cuando se eliminará un pedido por id


    public Pedido(int id) {
        this.id = id;
    }

    //Getter and Setter
    public int getId() {
        return id;
    }

    public void setIdPedido(int id) {
        this.id = id;
    }

    public Direccion getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(Direccion direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public TipoPedido getTipoPedido() {
        return tipoPedido;
    }

    public void setTipoPedido(TipoPedido tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    /**
     * Devuelve una representación textual resumida del pedido.
     *
     * @return descripción del pedido
     */
    @Override
    public String toString() {
        return "(ID: " + id + ") " + direccionEntrega.toString() + " (" + tipoPedido + " | " + estado + ")";
    }
}
