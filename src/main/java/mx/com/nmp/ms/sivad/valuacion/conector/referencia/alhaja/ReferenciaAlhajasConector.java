/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja;

import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaAlhajaService;
import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaAlhajaServiceEndpointService;
import mx.com.nmp.ms.sivad.valuacion.security.WSSecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Se encarga de crear una referencia hacia el Servicio Web Referencia de Alhajas.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component
public class ReferenciaAlhajasConector {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenciaAlhajasConector.class);

    /**
     * Contienen la URL donde se encuentra publicado el Servicio Web Referencia de Alhajas.
     */
    @Value("${valuacion.referencia.alhaja.wsdlLocation}")
    private String wsdlLocation;

    /**
     * Header name
     */
    @Value("${valuacion.referencia.header.api.name}")
    private String apiName;

    /**
     * Token value
     */
    @Value("${valuacion.referencia.header.api.key}")
    private String apiKey;

    /**
     * Referencia hacia el Servicio Web Referencia de Alhajas.
     */
    private ReferenciaAlhajaService wsReferenciaAlhaja;

    /**
     * Constructor.
     */
    public ReferenciaAlhajasConector() {
        super();
    }

    /**
     * Regresa la referencia hacia el Servicio Web Referencia de Alhajas.
     *
     * @return Referencia hacia el Servicio Web Referencia de Alhajas.
     */
    public ReferenciaAlhajaService getWsReferenciaAlhaja() {
        if (ObjectUtils.isEmpty(wsReferenciaAlhaja)) {
            crearReferenciaAlhajaService();
        }

        LOGGER.info("Recuperando referencia al WS Referencia Alhajas.");
        return wsReferenciaAlhaja;
    }

    /**
     * Crea una referencia hacia el Servicio Web Referencia de Alhajas.
     */
    private void crearReferenciaAlhajaService() {
        ReferenciaAlhajaServiceEndpointService ep;
        URL url = getURL();

        if (ObjectUtils.isEmpty(url)) {
            LOGGER.info("Creando referencia al WS Referencia Alhajas. con valores por defecto");
            ep = new ReferenciaAlhajaServiceEndpointService();
        } else {
            LOGGER.info("Creando referencia al WS Referencia Alhajas. con URL {}", url);
            ep = new ReferenciaAlhajaServiceEndpointService(url);
        }

        wsReferenciaAlhaja = ep.getReferenciaAlhajaServiceEndpointPort();
        
        WSSecurityUtils.addHttpAPIKeyHeader(wsReferenciaAlhaja, apiName, apiKey, "http://ws.api.referencia.sivad.ms.nmp.com.mx/");
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
                LOGGER.info("Creando URL con {}", wsdlLocation);
                url = new URL(wsdlLocation);
            } catch (MalformedURLException e) {
                LOGGER.warn("La URL no es accesible. {}", wsdlLocation, e);
            }
        }

        return url;
    }
}
