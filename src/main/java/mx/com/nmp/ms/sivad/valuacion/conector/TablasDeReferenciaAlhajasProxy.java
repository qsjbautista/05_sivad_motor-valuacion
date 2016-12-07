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
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroResponse;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja.factory.ReferenciaAlhajaFactory;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja.ReferenciaAlhajasConector;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasGramoOroProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.MetalCalidadRangoProveedor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

import static mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ConsumidorFactory.crearBigDecimalConsumidor;

/**
 * Conector hacia el servicio de valores de referencia basados en datos de la industria y el mercado.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component("tablasDeReferenciaAlhajas")
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public class TablasDeReferenciaAlhajasProxy implements TablasDeReferenciaAlhajas {
    private Logger LOGGER = LoggerFactory.getLogger(TablasDeReferenciaAlhajasProxy.class);

    /**
     * Referencia al conector hacia el Servicio Web Referencia de Alhajas.
     */
    @Inject
    private ReferenciaAlhajasConector referenciaAlhajasConector;

    /**
     * Referencia a la fabrica de Tipos de Datos del Servicio Web Referencia de Alhajas.
     */
    @Inject
    private ReferenciaAlhajaFactory referenciaAlhajaFactory;



    /**
     * Constructor.
     */
    public TablasDeReferenciaAlhajasProxy() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Timed
    @Override
    @Cacheable(value = "TablasDeReferenciaAlhajas.obtenerValorGramoOro.cache", condition = "#proveedor != null ",
        key = "T(java.util.Objects).hash(#proveedor.color, #proveedor.calidad)")
    public BigDecimalConsumidor obtenerValorGramoOro(@NotNull final CaracteristicasGramoOroProveedor proveedor) {
        LOGGER.info(">> obtenerValorGramoOro({})", proveedor);

        ObtenerValorGramoOroRequest gramoOro = referenciaAlhajaFactory.crearObtenerValorGramoOroRequest(proveedor);
        ObtenerValorGramoOroResponse respuesta = referenciaAlhajasConector.getWsReferenciaAlhaja()
            .obtenerValorGramoOro(gramoOro);

        return crearBigDecimalConsumidor(respuesta.getPrecioPorGramo());
    }

    /**
     * {@inheritDoc}
     */
    @Timed
    @Override
    @Cacheable(value = "TablasDeReferenciaAlhajas.obtenerFactor.cache", condition = "#proveedor != null ",
        key = "T(java.util.Objects).hash(#proveedor.metal, #proveedor.calidad, #proveedor.rango)")
    public BigDecimalConsumidor obtenerFactor(@NotNull final MetalCalidadRangoProveedor proveedor) {
        LOGGER.info(">> obtenerFactor({})", proveedor);

        ObtenerFactorRequest factor = referenciaAlhajaFactory.crearObtenerFactorRequest(proveedor);
        ObtenerFactorResponse respuesta = referenciaAlhajasConector.getWsReferenciaAlhaja().obtenerFactor(factor);

        return crearBigDecimalConsumidor(respuesta.getFactor());
    }

    /**
     * {@inheritDoc}
     */
    @Timed
    @Override
    @Cacheable(value = "TablasDeReferenciaAlhajas.obtenerValorGramoMetal.cache", condition = "#proveedor != null ",
        key = "T(java.util.Objects).hash(#proveedor.metal, #proveedor.calidad)")
    public BigDecimalConsumidor obtenerValorGramoMetal(@NotNull final MetalCalidadRangoProveedor proveedor) {
        LOGGER.info(">> obtenerValorGramoMetal({})", proveedor);

        ObtenerValorGramoMetalRequest gramoMetal =
            referenciaAlhajaFactory.crearObtenerValorGramoMetalRequest(proveedor);
        ObtenerValorGramoMetalResponse respuesta = referenciaAlhajasConector.getWsReferenciaAlhaja()
            .obtenerValorGramoMetal(gramoMetal);

        return crearBigDecimalConsumidor(respuesta.getPrecioPorGramo());
    }
}
