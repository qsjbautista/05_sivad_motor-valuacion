/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector;

import com.codahale.metrics.annotation.Timed;
import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerModificadorRequest;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerModificadorResponse;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerValorComercialRequest;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ObtenerValorComercialResponse;
import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ValorComercial;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ValorComercialConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasDiamanteProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CertificadoDiamanteProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.diamante.ReferenciaDiamantesConector;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.diamante.factory.ReferenciaDiamanteFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ConsumidorFactory.crearBigDecimalConsumidor;
import static mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ConsumidorFactory.crearValorComercialConsumidor;

/**
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component("tablasDeReferenciaDiamantes")
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public class TablasDeReferenciaDiamantesProxy implements TablasDeReferenciaDiamantes {
    private Logger LOGGER = LoggerFactory.getLogger(TablasDeReferenciaDiamantesProxy.class);

    /**
     * Referencia al conector hacia el Servicio Web Referencia de Diamantes.
     */
    @Inject
    private ReferenciaDiamantesConector referenciaDiamantesConector;

    /**
     * Referencia a la fabrica de Tipos de Datos del Servicio Web Referencia de Diamantes.
     */
    @Inject
    private ReferenciaDiamanteFactory referenciaDiamanteFactory;

    /**
     * Constructor.
     */
    public TablasDeReferenciaDiamantesProxy() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Timed
    @Override
    @Cacheable(value = "TablasDeReferenciaDiamantes.obtenerModificador.cache", condition = "#proveedor != null ",
        key = "#proveedor.certificadoDiamante")
    public BigDecimalConsumidor obtenerModificador(@NotNull final CertificadoDiamanteProveedor proveedor) {
        LOGGER.info(">> obtenerModificador({})", proveedor);

        ObtenerModificadorRequest certificado = referenciaDiamanteFactory.crearObtenerModificadorRequest(proveedor);
        ObtenerModificadorResponse respuesta = referenciaDiamantesConector.getWsReferenciaDiamante()
            .obtenerModificador(certificado);

        return crearBigDecimalConsumidor(respuesta.getFactor());
    }

    /**
     * {@inheritDoc}
     */
    @Timed
    @Override
    @Cacheable(value = "TablasDeReferenciaDiamantes.obtenerValorComercial.cache", condition = "#proveedor != null ",
        key = "T(java.util.Objects).hash(#proveedor.color, #proveedor.color, #proveedor.claridad, #proveedor.quilates)")
    public ValorComercialConsumidor obtenerValorComercial(@NotNull final CaracteristicasDiamanteProveedor proveedor) {
        LOGGER.info(">> obtenerValorComercial({})", proveedor);

        ObtenerValorComercialRequest valorComercial = referenciaDiamanteFactory
            .crearObtenerValorComercialRequest(proveedor);
        ObtenerValorComercialResponse respuesta = referenciaDiamantesConector.getWsReferenciaDiamante()
            .obtenerValorComercial(valorComercial);
        ValorComercial vc = respuesta.getValorComercial();

        return crearValorComercialConsumidor(vc.getValorMinimo(), vc.getValorMedio(), vc.getValorMaximo());
    }
}
