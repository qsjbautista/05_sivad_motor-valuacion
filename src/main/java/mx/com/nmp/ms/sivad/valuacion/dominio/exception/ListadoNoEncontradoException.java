/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.exception;

/**
 * Excepción de uso general que será lanzada cuando no existan listados (de cualquier tipo de entidad que se
 * registre en forma de lista).
 *
 * @author ngonzalez
 */
public class ListadoNoEncontradoException extends AbstractException {



    // METODOS

    /**
     * Constructor.
     *
     * @param msg Mensaje informativo.
     * @param entidad Entidad objetivo.
     */
    public ListadoNoEncontradoException(String msg, Class<?> entidad) {
        super(msg, entidad);
    }

}
