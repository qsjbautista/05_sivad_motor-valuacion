/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.api.ws.exception;

import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;

/**
 * Excepción que será lanzada en el momento que ocurra algún error en la obtención de información del servicio
 * de motor de valuación.
 *
 * @author ngonzalez
 */
public class WebServiceException extends SOAPFaultException {

    private static final long serialVersionUID = -3148483076881584849L;



    // METODOS

    /**
     * Constructor.
     *
     * @param falla <code>SOAPFault</code> representando la falla ocurrida.
     */
    public WebServiceException(SOAPFault falla) {
        super(falla);
    }

}
