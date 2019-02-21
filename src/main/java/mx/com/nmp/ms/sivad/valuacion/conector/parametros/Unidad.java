package mx.com.nmp.ms.sivad.valuacion.conector.parametros;

/**
 * @author cachavez
 */
public class Unidad {

    /**
     * Abreviatura o clave de la unidad.
     */
    private String abreviatura;

    /**
     * Descripción breve de la unidad.
     */
    private String descripcion;

    /**
     * Constructor de la clase.
     */
    public Unidad() {
        super();
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
}
