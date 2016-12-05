/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.aop;

import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ValuacionException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.xml.soap.SOAPFault;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

/**
 * Interceptor para manejar las excepciones que puedan ser lanzadas durante la comunicación
 * con el Micro Servicio Tabla de Referencia.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Aspect
@Component
public class ConectorExcepcionAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConectorExcepcionAspect.class);

    /**
     * Permite interceptar los métodos de los conectores hacia el Micro Servicio Tabla de Referencia.
     *
     * @param jp Punto de unión.
     * @param excepcion Excepción lanzada por el método.
     *
     * @throws ValuacionException Se lanza cunado el Micro Servicio Tabla de Referencia no encuentra
     *                            alguna característica de la petición.
     * @throws Throwable Se relanza la excepción, si no es manejada por el interceptor.
     */
    @AfterThrowing(value = "execution (public * mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferencia*.*(..))",
        throwing = "excepcion")
    public void manejarExcepcion(JoinPoint jp, Throwable excepcion) throws Throwable {
        LOGGER.info(">> manejarExcepcion({})", jp);

        if (SOAPFaultException.class.isAssignableFrom(excepcion.getClass())) {
            SOAPFaultException ex = (SOAPFaultException) excepcion;
            LOGGER.warn(ex.getMessage(), ex);
            throw getValuacionExceptionFromSOAPFaultException(ex);
        }

        if (WebServiceException.class.isAssignableFrom(excepcion.getClass())) {
            WebServiceException ex = (WebServiceException) excepcion;
            LOGGER.warn(ex.getMessage(), ex);
            throw  getValuacionExceptionFromWebServiceException(ex);
        }

        LOGGER.warn("Relanzando excepción no manejada", excepcion);

        throw excepcion;
    }

    /**
     * Convierte una excepción tipo {@link SOAPFaultException} a {@link ValuacionException}
     *
     * @param ex Excepción a convertir.
     *
     * @return Excepcion convertida.
     */
    private ValuacionException getValuacionExceptionFromSOAPFaultException(SOAPFaultException ex) {
        SOAPFault falla = ex.getFault();
        String codigo = falla.getFaultCode();
        String mensaje = falla.getFaultString();
        String actor = falla.getFaultActor();

        return new ValuacionException(codigo, mensaje, actor);
    }

    /**
     * Convierte una excepción tipo {@link WebServiceException} a {@link ValuacionException}
     *
     * @param ex Excepción a convertir.
     *
     * @return Excepcion convertida.
     */
    private ValuacionException getValuacionExceptionFromWebServiceException(WebServiceException ex) {
        final String mensaje = "Ocurrió un error de comunicación con el Micro Servicio Tablas de Referencia.";
        return new ValuacionException("MV003", mensaje, ex.getMessage());
    }
}
