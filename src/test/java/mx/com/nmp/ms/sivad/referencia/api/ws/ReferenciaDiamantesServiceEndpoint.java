/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.referencia.api.ws;

import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;
import java.math.BigDecimal;

/**
 * Clase que sirve como mock del WS ReferenciaDiamanteService.
 *
 * @author cachavez, ngonzalez
 */
public class ReferenciaDiamantesServiceEndpoint implements ReferenciaDiamanteService {

    private static final String CERTIFICADO = "ABC";
    private static final String CLARIDAD = "VS1";
    private static final String COLOR_D = "D";
    private static final String CORTE = "Oval";

    private static final BigDecimal FACTOR =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES =
        new BigDecimal(0.92D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_COMERCIAL_MINIMO =
        new BigDecimal(100.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_COMERCIAL_MEDIO =
        new BigDecimal(110.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_COMERCIAL_MAXIMO =
        new BigDecimal(120.00D).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenciaDiamantesServiceEndpoint.class);

    /**
     * Metodo utilizado para obtener el valor comercial del diamante.
     *
     * @param parameters Las características del diamante.
     * @return El valor comercial del diamante.
     */
    @Override
    public ObtenerValorComercialResponse obtenerValorComercial(ObtenerValorComercialRequest parameters) {
        LOGGER.info(">> obtenerValorComercial({})", parameters);

        if (!ObjectUtils.isEmpty(parameters) &&
            !ObjectUtils.isEmpty(parameters.getCorte()) &&
            !ObjectUtils.isEmpty(parameters.getColor()) &&
            !ObjectUtils.isEmpty(parameters.getClaridad()) &&
            !ObjectUtils.isEmpty(parameters.getQuilatesCt()) &&
            parameters.getCorte().equals(CORTE) &&
            parameters.getColor().equals(COLOR_D) &&
            parameters.getClaridad().equals(CLARIDAD) &&
            parameters.getQuilatesCt().equals(QUILATES)) {

            // SE GENERA EL RESPONSE PARA ValuadorDiamantesEndpointSystemTest.valuarPrendaBasico01
            ValorComercial valorComercial = new ValorComercial();
            valorComercial.setValorMinimo(VALOR_COMERCIAL_MINIMO);
            valorComercial.setValorMedio(VALOR_COMERCIAL_MEDIO);
            valorComercial.setValorMaximo(VALOR_COMERCIAL_MAXIMO);

            ObtenerValorComercialResponse response = new ObtenerValorComercialResponse();
            response.setValorComercial(valorComercial);
            return response;
        }

        throw new WebServiceException("Error de comununicacion");
    }

    /**
     * Metodo utilizado para obtener el modificador por certificado.
     *
     * @param parameters Las características del certificado.
     * @return El modificador por certificado
     */
    @Override
    public ObtenerModificadorResponse obtenerModificador(ObtenerModificadorRequest parameters) {
        LOGGER.info(">> obtenerModificador({})", parameters);

        if (!ObjectUtils.isEmpty(parameters) &&
            !ObjectUtils.isEmpty(parameters.getCertificadoDiamante()) &&
            parameters.getCertificadoDiamante().equals(CERTIFICADO)) {

            // SE GENERA EL RESPONSE PARA ValuadorDiamantesEndpointSystemTest.valuarPrendaBasico01
            ObtenerModificadorResponse response = new ObtenerModificadorResponse();
            response.setFactor(FACTOR);
            return response;
        }

        QName faultCode = new QName("", "NMP-TR-010");

        try {
            SOAPFactory factory = SOAPFactory.newInstance();
            throw new SOAPFaultException(factory.createFault("Error Test", faultCode));
        } catch (SOAPException e) {
            LOGGER.warn(e.getMessage(), e);
        }

        throw new WebServiceException("Error de comununicacion");
    }

}
