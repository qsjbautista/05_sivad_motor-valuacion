/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector;

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
    @Override
    @Cacheable(value = "tablasDeReferenciaDiamantesCache", condition = "#proveedor != null ",
        key = "#proveedor.certificadoDiamante")
    public BigDecimalConsumidor obtenerModificador(@NotNull final CertificadoDiamanteProveedor proveedor) {
        ObtenerModificadorRequest certificado = referenciaDiamanteFactory.crearObtenerModificadorRequest(proveedor);
        ObtenerModificadorResponse respuesta = referenciaDiamantesConector.getWsReferenciaDiamante()
            .obtenerModificador(certificado);

        return crearBigDecimalConsumidor(respuesta.getFactor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable(value = "tablasDeReferenciaDiamantesCache", condition = "#proveedor != null ",
        key = "T(java.util.Objects).hash(#proveedor.color, #proveedor.color, #proveedor.claridad, #proveedor.quilates)")
    public ValorComercialConsumidor obtenerValorComercial(@NotNull final CaracteristicasDiamanteProveedor proveedor) {
        ObtenerValorComercialRequest valorComercial = referenciaDiamanteFactory
            .crearObtenerValorComercialRequest(proveedor);
        ObtenerValorComercialResponse respuesta = referenciaDiamantesConector.getWsReferenciaDiamante()
            .obtenerValorComercial(valorComercial);
        ValorComercial vc = respuesta.getValorComercial();

        return crearValorComercialConsumidor(vc.getValorMinimo(), vc.getValorMedio(), vc.getValorMaximo());
    }
}