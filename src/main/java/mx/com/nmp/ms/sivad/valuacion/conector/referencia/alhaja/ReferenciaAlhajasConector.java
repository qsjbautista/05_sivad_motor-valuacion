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

        return wsReferenciaAlhaja;
    }

    /**
     * Crea una referencia hacia el Servicio Web Referencia de Alhajas.
     */
    private void crearReferenciaAlhajaService() {
        ReferenciaAlhajaServiceEndpointService ep;
        URL url = getURL();

        if (ObjectUtils.isEmpty(url)) {
            ep = new ReferenciaAlhajaServiceEndpointService();
        } else {
            ep = new ReferenciaAlhajaServiceEndpointService(url);
        }

        wsReferenciaAlhaja = ep.getReferenciaAlhajaServiceEndpointPort();
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
                LOGGER.warn("La URL no es accesible. {}", wsdlLocation, e);
            }
        }

        return url;
    }
}
