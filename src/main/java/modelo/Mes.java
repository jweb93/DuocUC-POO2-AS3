package modelo;
/**
 * Representa los meses del año y relaciona cada constante con su
 * correspondiente número de mes.
 */
public enum Mes {
    ENERO(1, "Enero"),
    FEBRERO(2, "Febrero"),
    MARZO(3, "Marzo"),
    ABRIL(4, "Abril"),
    MAYO(5, "Mayo"),
    JUNIO(6, "Junio"),
    JULIO(7, "Julio"),
    AGOSTO(8, "Agosto"),
    SEPTIEMBRE(9, "Septiembre"),
    OCTUBRE(10, "Octubre"),
    NOVIEMBRE(11, "Noviembre"),
    DICIEMBRE(12, "Diciembre");

    private final int numero;
    private final String nombre;

    Mes(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Busca un mes a partir de su número.
     *
     * @param i número del mes que se desea buscar
     * @return mes correspondiente al número indicado, o null si no existe
     */
    public Mes mesPorID(int i){
        for (Mes mes : Mes.values()) {
            if (mes.getNumero() == i) {
                return mes;
            }
        }
        return null;
    }
}