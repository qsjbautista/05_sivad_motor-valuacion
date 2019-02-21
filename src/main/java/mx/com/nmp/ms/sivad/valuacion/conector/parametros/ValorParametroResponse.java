package mx.com.nmp.ms.sivad.valuacion.conector.parametros;

/**
 * @author cachavez
 */
public class ValorParametroResponse {

    /**
     * Nombre del tipo de parámetro.
     */
    private String tipoParametro;

    /**
     * Abreviatura del parámetro.
     */
    private String abreviatura;

    /**
     * Descripción del parámetro.
     */
    private String descripcion;

    /**
     * Unidad de medida del valor.
     */
    private Unidad unidad;

    /**
     * Valor del parametro según el tipo y la unidad.
     */
    private Float valor;

    /**
     * Constructor de la clase
     */
    public ValorParametroResponse() {
        super();
    }

    /**
     * Recupera el valor de {@code tipoParametro}
     *
     * @return Valor de {@code tipoParametro}
     */
    public String getTipoParametro() {
        return tipoParametro;
    }

    /**
     * Establece el valor de {@code tipoParametro}
     *
     * @param tipoParametro Valor de {@code tipoParametro}
     */
    public void setTipoParametro(String tipoParametro) {
        this.tipoParametro = tipoParametro;
    }

    /**
     * Recupera el valor de {@code abreviatura}
     *
     * @return Valor de {@code abreviatura}
     */
    public String getAbreviatura() {
        return abreviatura;
    }

    /**
     * Establece el valor de {@code abreviatura}
     *
     * @param abreviatura Valor de {@code abreviatura}
     */
    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    /**
     * Recupera el valor de {@code descripcion}
     *
     * @return Valor de {@code descripcion}
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece el valor de {@code descripcion}
     *
     * @param descripcion Valor de {@code descripcion}
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Recupera el valor de {@code unidad}
     *
     * @return Valor de {@code unidad}
     */
    public Unidad getUnidad() {
        return unidad;
    }

    /**
     * Establece el valor de {@code unidad}
     *
     * @param unidad Valor de {@code unidad}
     */
    public void setUnidad(Unidad unidad) {
        this.unidad = unidad;
    }

    /**
     * Recupera el valor de {@code valor}
     *
     * @return Valor de {@code valor}
     */
    public Float getValor() {
        return valor;
    }

    /**
     * Establece el valor de {@code valor}
     *
     * @param valor Valor de {@code valor}
     */
    public void setValor(Float valor) {
        this.valor = valor;
    }
}
