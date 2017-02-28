/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.exception;

/**
 * Excepción que será lanzada cuando no exista el modificador - condición prenda.
 *
 * @author ngonzalez
 */
public class ModificadorCondicionPrendaNoEncontradoException extends AbstractException {



    // METODOS

    /**
     * Constructor.
     *
     * @param msg Mensaje informativo.
     * @param entidad Entidad objetivo.
     */
    public ModificadorCondicionPrendaNoEncontradoException(String msg, Class<?> entidad) {
        super(msg, entidad);
    }

}
