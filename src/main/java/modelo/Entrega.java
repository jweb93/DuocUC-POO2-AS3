package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Entrega {
    private int id;
    private Pedido pedido;
    private Repartidor repartidor;
    private LocalDate fecha;       // LocalDate.of(2026, 9, 28);
    private LocalTime hora;    // LocalTime.of(20, 45);

    // Constructor cuando la entrega está en bbdd con un id definido
    public Entrega(int id, Pedido pedido, Repartidor repartidor, LocalDate fecha, LocalTime hora) {
        this.id = id;
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Constructor cuando la entrega aun no está en BBDD
    public Entrega(Pedido pedido, Repartidor repartidor, LocalDate fecha, LocalTime hora) {
        this.pedido = pedido;
        this.repartidor = repartidor;
        this.fecha = fecha;
        this.hora = hora;
    }

    // Constructor para cuando se eliminará una entrega. Solo se necesita su id.
    public Entrega(int id) {
        this.id = id;
    }

    //Getter and Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(Repartidor repartidor) {
        this.repartidor = repartidor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
