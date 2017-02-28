/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.exception;

/**
 * Excepción de uso general que será lanzada cuando se indique una fecha posterior a la fecha actual en una
 * consulta.
 *
 * @author ngonzalez
 */
public class FechaFuturaException extends AbstractException {



    // METODOS

    /**
     * Constructor.
     *
     * @param msg Mensaje informativo.
     * @param entidad Entidad objetivo.
     */
    public FechaFuturaException(String msg, Class<?> entidad) {
        super(msg, entidad);
    }

}
