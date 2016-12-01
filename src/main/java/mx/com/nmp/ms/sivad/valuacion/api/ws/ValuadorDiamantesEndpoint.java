package mx.com.nmp.ms.sivad.valuacion.api.ws;

import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.ValuadorDiamantesService;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author osanchez
 */
public class ValuadorDiamantesEndpoint implements ValuadorDiamantesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValuadorDiamantesEndpoint.class);

    /**
     * @param parameters
     * @return returns mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.ValuarPrendaBasicoResponse
     */
    @Override
    public ValuarPrendaBasicoResponse valuarPrendaBasico(ValuarPrendaBasicoRequest parameters) {
        LOGGER.info(">> valuarPrendaBasico({})", parameters);

        Prenda prenda = parameters.getPrenda();
        prenda.getAvaluo().setValorMinimo(BigDecimal.ONE);
        prenda.getAvaluo().setValorPromedio(BigDecimal.ONE);
        prenda.getAvaluo().setValorMaximo(BigDecimal.TEN);

        ValuarPrendaBasicoResponse response = new ValuarPrendaBasicoResponse();
        response.setPrendaValuada(prenda);

        return response;
    }

    /**
     * @param parameters
     * @return returns mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.ValuarPrendaNMPResponse
     */
    @Override
    public ValuarPrendaNMPResponse valuarPrendaNMP(ValuarPrendaNMPRequest parameters) {
        LOGGER.info(">> valuarPrendaNMP({})", parameters);

        Prenda prenda = parameters.getPrenda();
        prenda.getAvaluo().setValorMinimo(BigDecimal.ZERO);
        prenda.getAvaluo().setValorPromedio(BigDecimal.TEN);
        prenda.getAvaluo().setValorMaximo(BigDecimal.TEN);

        ValuarPrendaNMPResponse response = new ValuarPrendaNMPResponse();
        response.setPrendaValuada(prenda);

        return response;
    }
}
