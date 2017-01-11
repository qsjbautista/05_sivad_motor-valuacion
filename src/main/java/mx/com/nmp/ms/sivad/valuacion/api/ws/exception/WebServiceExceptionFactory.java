/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.api.ws.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;

/**
 * Fábrica para crear excepciones tipo {@link WebServiceException}.
 *
 * @author ngonzalez
 */
public class WebServiceExceptionFactory {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceExceptionFactory.class);



    // METODOS

    /**
     * Constructor.
     *
     * Privado para que no se puedan crear objetos.
     */
    private WebServiceExceptionFactory() {
        super();
    }

    /**
     * Permite crear una excepción tipo {@link WebServiceException} con base en los argumentos recibidos.
     *
     * @param codigoError El código de error de la excepción.
     * @param mensaje El mensaje de error de la excepción.
     * @return La excepción {@link WebServiceException} creada.
     */
    public static WebServiceException crearWebServiceExceptionCon(String codigoError,
                                                                  String mensaje) {
        return new WebServiceException(getFalla(codigoError, mensaje));
    }

    /**
     * Permite crear una excepción tipo {@link WebServiceException} con base en los argumentos recibidos.
     *
     * @param codigoError El código de error de la excepción.
     * @param mensaje El mensaje de error de la excepción.
     * @param actor Texto empleado para indicar quién o qué causó el error.
     * @return La excepción {@link WebServiceException} creada.
     */
    public static WebServiceException crearWebServiceExceptionCon(String codigoError,
                                                                  String mensaje,
                                                                  Throwable actor) {
        return new WebServiceException(getFalla(codigoError, mensaje, actor.getMessage()));
    }

    /**
     * Permite crear una excepción tipo {@link WebServiceException} con base en los argumentos recibidos.
     *
     * @param codigoError El código de error de la excepción.
     * @param mensaje El mensaje de error de la excepción.
     * @param actor Texto empleado para indicar quién o qué causó el error.
     * @return La excepción {@link WebServiceException} creada.
     */
    public static WebServiceException crearWebServiceExceptionCon(String codigoError,
                                                                  String mensaje,
                                                                  String actor) {
        return new WebServiceException(getFalla(codigoError, mensaje, actor));
    }

    /**
     * Metodo auxiliar utilizado para crear la falla con base en los argumentos recibidos.
     *
     * @param codigoError El código de error de la excepción.
     * @param mensaje El mensaje de error de la excepción.
     * @return <code>SOAPFault</code> representando la falla ocurrida.
     */
    private static SOAPFault getFalla(String codigoError,
                                      String mensaje) {
        QName faultCode = new QName("", codigoError);
        SOAPFault falla = null;

        try {
            SOAPFactory factory = SOAPFactory.newInstance();
            falla = factory.createFault(mensaje, faultCode);
        } catch (SOAPException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return falla;
    }

    /**
     * Metodo auxiliar utilizado para crear la falla con base en los argumentos recibidos.
     *
     * @param codigoError El código de error de la excepción.
     * @param mensaje El mensaje de error de la excepción.
     * @param actor Texto empleado para indicar quién o qué causó el error.
     * @return <code>SOAPFault</code> representando la falla ocurrida.
     */
    private static SOAPFault getFalla(String codigoError,
                                      String mensaje,
                                      String actor) {
        SOAPFault falla = getFalla(codigoError, mensaje);

        try {
            falla.setFaultActor(actor);
        } catch (SOAPException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        return falla;
    }

}
