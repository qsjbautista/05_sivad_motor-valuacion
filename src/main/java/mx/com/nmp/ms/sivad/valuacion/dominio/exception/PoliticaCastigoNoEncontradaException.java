/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.exception;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;

/**
 *  Excepción que será lanzada cuando no exista una entidad {@link PoliticasCastigo}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class PoliticaCastigoNoEncontradaException extends RuntimeException {
    private static final long serialVersionUID = 7815555946420442500L;

    /**
     * Constructor.
     *
     * @param mensaje Mensaje que describe la excepción.
     */
    public PoliticaCastigoNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
