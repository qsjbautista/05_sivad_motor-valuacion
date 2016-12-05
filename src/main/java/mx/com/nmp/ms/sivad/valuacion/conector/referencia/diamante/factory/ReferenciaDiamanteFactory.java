/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.referencia.diamante.factory;

import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObjectFactory;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerModificadorRequest;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerValorComercialRequest;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasDiamanteProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CertificadoDiamanteProveedor;
import org.springframework.stereotype.Component;

/**
 * Fabrica que se encarga de crear los Tipos de Datos del Servicio Web Referencia de Diamantes.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component
public class ReferenciaDiamanteFactory {
    /**
     * Referencia al creador de objetos.
     * @see ObjectFactory
     */
    private ObjectFactory objectFactory;

    /**
     * Constructor.
     */
    public ReferenciaDiamanteFactory() {
        super();

        objectFactory = new ObjectFactory();
    }

    /**
     * Crea una instancia de {@link ObtenerValorComercialRequest}
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return La instancia de {@link ObtenerValorComercialRequest}
     */
    public ObtenerValorComercialRequest crearObtenerValorComercialRequest(final CaracteristicasDiamanteProveedor proveedor) {
        ObtenerValorComercialRequest valorComercial =  objectFactory.createObtenerValorComercialRequest();

        valorComercial.setCorte(proveedor.getCorte());
        valorComercial.setColor(proveedor.getColor());
        valorComercial.setClaridad(proveedor.getClaridad());
        valorComercial.setQuilatesCt(proveedor.getQuilates());

        return valorComercial;
    }

    /**
     * Crea una instancia de {@link ObtenerModificadorRequest}
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return La instancia de {@link ObtenerModificadorRequest}
     */
    public ObtenerModificadorRequest crearObtenerModificadorRequest(final CertificadoDiamanteProveedor proveedor) {
        ObtenerModificadorRequest certificado =  objectFactory.createObtenerModificadorRequest();

        certificado.setCertificadoDiamante(proveedor.getCertificadoDiamante());

        return certificado;
    }

}
