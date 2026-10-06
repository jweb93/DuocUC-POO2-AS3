package modelo;

/**
 * Representa una dirección física donde se entregará un pedido
 */

public class Direccion {
    private String calle;
    private int numero;
    private String comuna;

    /**
     * Crea una dirección con sus datos principales.
     *
     * @param calle nombre de la calle
     * @param numero número de la dirección
     * @param comuna comuna a la que pertenece la dirección
     */
    public Direccion(String calle, int numero, String comuna) {
        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
    }

    // Getter and Setters
    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    /**
     * Devuelve una representación textual legible de la dirección.
     *
     * @return representación de la dirección
     */
    @Override
    public String toString() {
        return calle + ' ' + numero + ", " + comuna;
    }

    /**
     * Convierte la dirección a la representación de texto utilizada para
     * almacenarla en la base de datos.
     *
     * @return dirección serializada en formato calle|numero|comuna
     */
    public String serializar(){
        return calle + "|" + numero + "|" + comuna;
    }
}
