package mx.com.nmp.ms.sivad.valuacion.conector.parametros;

/**
 * @author cachavez
 */
public class FiltroParametro {

    /**
     * Nombre del filtro
     */
    private String nombre;

    /**
     * Valor del filtro
     */
    private String valor;

    /**
     * Constructor de la clase
     */
    public FiltroParametro() {
        super();
    }

    /**
     * Constructor de la clase
     *
     * @param nombre Nombre del filtro
     * @param valor Valor del filtro
     */
    public FiltroParametro(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    /**
     * Recupera el valor de {@code nombre}
     *
     * @return Valor de {@code nombre}
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor de {@code nombre}
     *
     * @param nombre Valor de {@code nombre}
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Recupera el valor de {@code valor}
     *
     * @return Valor de {@code valor}
     */
    public String getValor() {
        return valor;
    }

    /**
     * Establece el valor de {@code valor}
     *
     * @param valor Valor de {@code valor}
     */
    public void setValor(String valor) {
        this.valor = valor;
    }
}
