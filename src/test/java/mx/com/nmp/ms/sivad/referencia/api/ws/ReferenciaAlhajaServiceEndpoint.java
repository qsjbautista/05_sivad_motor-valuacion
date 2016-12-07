package mx.com.nmp.ms.sivad.referencia.api.ws;

import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerDesplazamientoRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerDesplazamientoResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerLimitesIncrementoRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerLimitesIncrementoResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

public class ReferenciaAlhajaServiceEndpoint implements ReferenciaAlhajaService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReferenciaAlhajaServiceEndpoint.class);
    @Override
    public ObtenerValorGramoOroResponse obtenerValorGramoOro(ObtenerValorGramoOroRequest parameters) {
        LOGGER.info(">> obtenerValorGramoOro({})", parameters);

        ObtenerValorGramoOroResponse response = new ObtenerValorGramoOroResponse();
        response.setPrecioPorGramo(BigDecimal.valueOf(104.31));
        return response;
    }

    @Override
    public ObtenerValorGramoMetalResponse obtenerValorGramoMetal(ObtenerValorGramoMetalRequest parameters) {
        LOGGER.info(">> obtenerValorGramoMetal({})", parameters);

        ObtenerValorGramoMetalResponse response = new ObtenerValorGramoMetalResponse();
        response.setPrecioPorGramo(BigDecimal.ONE);
        return response;
    }

    @Override
    public ObtenerFactorResponse obtenerFactor(ObtenerFactorRequest parameters) {
        LOGGER.info(">> obtenerFactor({})", parameters);

        ObtenerFactorResponse response = new ObtenerFactorResponse();
        response.setFactor(BigDecimal.valueOf(1.23));
        return response;
    }

    @Override
    public ObtenerDesplazamientoResponse obtenerDesplazamiento(ObtenerDesplazamientoRequest parameters) {
        return new ObtenerDesplazamientoResponse();
    }

    @Override
    public ObtenerLimitesIncrementoResponse obtenerLimitesIncremento(ObtenerLimitesIncrementoRequest parameters) {
        return new ObtenerLimitesIncrementoResponse();
    }
}
