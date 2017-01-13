/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.referencia.api.ws;

import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * Clase que sirve como mock del WS ReferenciaAlhajaService.
 *
 * @author cachavez, ngonzalez
 */
public class ReferenciaAlhajaServiceEndpoint implements ReferenciaAlhajaService {

    private static final String CALIDAD = "14";
    private static final String COLOR_A = "Amarillo";
    private static final String METAL = "AG";
    private static final String RANGO = "F1";

    private static final BigDecimal DESPLAZAMIENTO =
        new BigDecimal(0.80D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal FACTOR_CALIDAD_ALHAJA =
        new BigDecimal(1.05D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal LIMITE_INFERIOR =
        new BigDecimal(0.75D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal LIMITE_SUPERIOR =
        new BigDecimal(0.95D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PRECIO_X_GRAMO_METAL =
        new BigDecimal(50.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PRECIO_X_GRAMO_ORO =
        new BigDecimal(100.00D).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenciaAlhajaServiceEndpoint.class);

    /**
     * Metodo utilizado para obtener el precio por gramo del oro.
     *
     * @param parameters Las características del oro.
     * @return El precio por gramo del oro.
     */
    @Override
    public ObtenerValorGramoOroResponse obtenerValorGramoOro(ObtenerValorGramoOroRequest parameters) {
        LOGGER.info(">> obtenerValorGramoOro({})", parameters);

        if (!ObjectUtils.isEmpty(parameters) &&
            !ObjectUtils.isEmpty(parameters.getColor()) &&
            !ObjectUtils.isEmpty(parameters.getCalidad()) &&
            parameters.getColor().equals(COLOR_A) &&
            parameters.getCalidad().equals(CALIDAD)) {

            // SE GENERA EL RESPONSE PARA ValuadorDiamantesEndpointSystemTest.valuarPrendaBasico01
            ObtenerValorGramoOroResponse response = new ObtenerValorGramoOroResponse();
            response.setPrecioPorGramo(PRECIO_X_GRAMO_ORO);
            return response;
        }

        ObtenerValorGramoOroResponse response = new ObtenerValorGramoOroResponse();
        response.setPrecioPorGramo(BigDecimal.valueOf(104.31));
        return response;
    }

    /**
     * Metodo utilizado para obtener el precio por gramo del metal.
     *
     * @param parameters Las características del metal.
     * @return El precio por gramo del metal.
     */
    @Override
    public ObtenerValorGramoMetalResponse obtenerValorGramoMetal(ObtenerValorGramoMetalRequest parameters) {
        LOGGER.info(">> obtenerValorGramoMetal({})", parameters);

        if (!ObjectUtils.isEmpty(parameters) &&
            !ObjectUtils.isEmpty(parameters.getMetal()) &&
            !ObjectUtils.isEmpty(parameters.getCalidad()) &&
            !ObjectUtils.isEmpty(parameters.getRango()) &&
            parameters.getMetal().equals(METAL) &&
            parameters.getCalidad().equals(CALIDAD) &&
            parameters.getRango().equals(RANGO)) {

            // SE GENERA EL RESPONSE PARA ValuadorDiamantesEndpointSystemTest.valuarPrendaBasico01
            ObtenerValorGramoMetalResponse response = new ObtenerValorGramoMetalResponse();
            response.setPrecioPorGramo(PRECIO_X_GRAMO_METAL);
            return response;
        }

        ObtenerValorGramoMetalResponse response = new ObtenerValorGramoMetalResponse();
        response.setPrecioPorGramo(BigDecimal.ONE);
        return response;
    }

    /**
     * Metodo utilizado para obtener el factor calidad alhaja.
     *
     * @param parameters Las características del metal.
     * @return El factor calidad alhaja.
     */
    @Override
    public ObtenerFactorResponse obtenerFactor(ObtenerFactorRequest parameters) {
        LOGGER.info(">> obtenerFactor({})", parameters);

        if (!ObjectUtils.isEmpty(parameters) &&
            !ObjectUtils.isEmpty(parameters.getMetal()) &&
            !ObjectUtils.isEmpty(parameters.getCalidad()) &&
            !ObjectUtils.isEmpty(parameters.getRango()) &&
            parameters.getMetal().equals(METAL) &&
            parameters.getCalidad().equals(CALIDAD) &&
            parameters.getRango().equals(RANGO)) {

            // SE GENERA EL RESPONSE PARA ValuadorDiamantesEndpointSystemTest.valuarPrendaBasico01
            ObtenerFactorResponse response = new ObtenerFactorResponse();
            response.setFactor(FACTOR_CALIDAD_ALHAJA);
            return response;
        }

        ObtenerFactorResponse response = new ObtenerFactorResponse();
        response.setFactor(BigDecimal.valueOf(1.23));
        return response;
    }

    /**
     * Metodo utilizado para obtener el factor de desplazamiento.
     *
     * @param parameters Las características del metal.
     * @return El factor de desplazamiento.
     */
    @Override
    public ObtenerDesplazamientoResponse obtenerDesplazamiento(ObtenerDesplazamientoRequest parameters) {
        LOGGER.info(">> obtenerDesplazamiento({})", parameters);

        if (!ObjectUtils.isEmpty(parameters) &&
            !ObjectUtils.isEmpty(parameters.getMetal()) &&
            !ObjectUtils.isEmpty(parameters.getCalidad()) &&
            !ObjectUtils.isEmpty(parameters.getRango()) &&
            parameters.getMetal().equals(METAL) &&
            parameters.getCalidad().equals(CALIDAD) &&
            parameters.getRango().equals(RANGO)) {

            // SE GENERA EL RESPONSE PARA ValuadorDiamantesEndpointSystemTest.valuarPrendaBasico01
            ObtenerDesplazamientoResponse response = new ObtenerDesplazamientoResponse();
            response.setDesplazamiento(DESPLAZAMIENTO);
            return response;
        }

        return new ObtenerDesplazamientoResponse();
    }

    /**
     * Metodo utilizado para obtener los límites de incremento.
     *
     * @param parameters Las características del metal.
     * @return Los límites de incremento.
     */
    @Override
    public ObtenerLimitesIncrementoResponse obtenerLimitesIncremento(ObtenerLimitesIncrementoRequest parameters) {
        LOGGER.info(">> obtenerLimitesIncremento({})", parameters);

        if (!ObjectUtils.isEmpty(parameters) &&
            !ObjectUtils.isEmpty(parameters.getMetal()) &&
            !ObjectUtils.isEmpty(parameters.getCalidad()) &&
            !ObjectUtils.isEmpty(parameters.getRango()) &&
            parameters.getMetal().equals(METAL) &&
            parameters.getCalidad().equals(CALIDAD) &&
            parameters.getRango().equals(RANGO)) {

            // SE GENERA EL RESPONSE PARA ValuadorDiamantesEndpointSystemTest.valuarPrendaBasico01
            RangoLimite rangoLimite = new RangoLimite();
            rangoLimite.setLimiteInferior(LIMITE_INFERIOR);
            rangoLimite.setLimiteSuperior(LIMITE_SUPERIOR);

            ObtenerLimitesIncrementoResponse response = new ObtenerLimitesIncrementoResponse();
            response.setLimitesIncremento(rangoLimite);
            return response;
        }

        return new ObtenerLimitesIncrementoResponse();
    }

}
