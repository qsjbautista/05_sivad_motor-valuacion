/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.exception;

/**
 * Excepción que será lanzada cuando ocurra un error el proceso de valuación.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class ValuacionException extends RuntimeException {
    /**
     * Texto empleado para indicar el tipo de error.
     */
    private String codigo;

    /**
     * Texto empleado para indicar quien causo el error.
     */
    private String actor;

    /**
     * Constructor.
     *
     * @param codigo Texto empleado para indicar el tipo de error.
     * @param mensaje Texto empleado para explicar el tipo de error.
     * @param actor Texto empleado para indicar quien causo el error.
     */
    public ValuacionException(String codigo, String mensaje, String actor) {
        super(mensaje);

        this.codigo = codigo;
        this.actor = actor;
    }

    /**
     * Recupera el tipo de error.
     *
     * @return Texto empleado para indicar el tipo de error.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Recupera la causa del error.
     *
     * @return Texto empleado para indicar quien causo el error.
     */
    public String getActor() {
        return actor;
    }
}
