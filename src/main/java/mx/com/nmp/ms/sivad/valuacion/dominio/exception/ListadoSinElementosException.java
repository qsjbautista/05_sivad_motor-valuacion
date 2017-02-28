/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.exception;

/**
 * Excepción de uso general que será lanzada cuando se intente actualizar el listado con una lista de valores
 * vacía.
 *
 * @author ngonzalez
 */
public class ListadoSinElementosException extends AbstractException {



    // METODOS

    /**
     * Constructor.
     *
     * @param msg Mensaje informativo.
     * @param entidad Entidad objetivo.
     */
    public ListadoSinElementosException(String msg, Class<?> entidad) {
        super(msg, entidad);
    }

}
