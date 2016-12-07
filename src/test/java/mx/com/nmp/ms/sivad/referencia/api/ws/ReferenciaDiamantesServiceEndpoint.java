package mx.com.nmp.ms.sivad.referencia.api.ws;

import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerModificadorRequest;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerModificadorResponse;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerValorComercialRequest;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerValorComercialResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.soap.SOAPFaultException;

public class ReferenciaDiamantesServiceEndpoint implements ReferenciaDiamanteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenciaDiamantesServiceEndpoint.class);

    @Override
    public ObtenerValorComercialResponse obtenerValorComercial(ObtenerValorComercialRequest parameters) {
        LOGGER.info(">> obtenerValorComercial({})", parameters);

        throw new WebServiceException("Error de comununicacion");
    }

    @Override
    public ObtenerModificadorResponse obtenerModificador(ObtenerModificadorRequest parameters) {
        LOGGER.info(">> obtenerModificador({})", parameters);

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
