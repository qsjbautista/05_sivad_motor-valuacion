/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.referencia.diamante;

import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaDiamanteService;
import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaDiamantesServiceEndpointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Se encarga de crear una referencia hacia el Servicio Web Referencia de Diamantes.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component
public class ReferenciaDiamantesConector {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenciaDiamantesConector.class);

    /**
     * Contienen la URL donde se encuentra publicado el Servicio Web Referencia de Diamantes.
     */
    @Value("${valuacion.referencia.diamante.wsdlLocation}")
    private String wsdlLocation;

    /**
     * Referencia hacia el Servicio Web Referencia de Diamantes.
     */
    private ReferenciaDiamanteService wsReferenciaDiamante;

    /**
     * Constructor.
     */
    public ReferenciaDiamantesConector() {
        super();
    }

    /**
     * Regresa la referencia hacia el Servicio Web Referencia de Diamantes.
     *
     * @return Referencia hacia el Servicio Web Referencia de Diamantes.
     */
    public ReferenciaDiamanteService getWsReferenciaDiamante() {
        if (ObjectUtils.isEmpty(wsReferenciaDiamante)) {
            crearReferenciaDiamanteService();
        }

        return wsReferenciaDiamante;
    }

    /**
     * Crea una referencia hacia el Servicio Web Referencia de Diamantes.
     */
    private void crearReferenciaDiamanteService() {
        ReferenciaDiamantesServiceEndpointService ep;
        URL url = getURL();

        if (ObjectUtils.isEmpty(url)) {
            ep = new ReferenciaDiamantesServiceEndpointService();
        } else {
            ep = new ReferenciaDiamantesServiceEndpointService(url);
        }

        wsReferenciaDiamante = ep.getReferenciaDiamantesServiceEndpointPort();
    }

    /**
     * Recupera la URL de las propiedades de entorno.
     *
     * @return URL.
     */
    private URL getURL() {
        URL url = null;

        if (!ObjectUtils.isEmpty(wsdlLocation)) {
            try {
                url = new URL(wsdlLocation);
            } catch (MalformedURLException e) {
                LOGGER.warn("La URL no es valida. {}", wsdlLocation, e);
            }
        }

        return url;
    }
}
