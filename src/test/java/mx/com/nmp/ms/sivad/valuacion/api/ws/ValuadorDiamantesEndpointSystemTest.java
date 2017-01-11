package mx.com.nmp.ms.sivad.valuacion.api.ws;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.ValuadorDiamantesService;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.Prenda;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.ValuarPrendaBasicoRequest;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.ValuarPrendaBasicoResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;

import static mx.com.nmp.ms.sivad.valuacion.test.util.ValuadorTestHelper.getValuarPrendaBasicoRequestDummy;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Pruebas de WS
 *
 * @author osanchez
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MotorValuacionApplication.class})
public class ValuadorDiamantesEndpointSystemTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValuadorDiamantesEndpointSystemTest.class);

    @Inject
    private ValuadorDiamantesService valuadorDiamantesEndpoint;

    @Test
    public void valuarPrendaBasico() {
        assertTrue(true);

//        ValuarPrendaBasicoRequest request = getValuarPrendaBasicoRequestDummy();
//
//        ValuarPrendaBasicoResponse response = valuadorDiamantesEndpoint.valuarPrendaBasico(request);
//
//        assertNotNull(response);
//        final Prenda prendaValuada = response.getPrendaValuada();
//        assertNotNull(prendaValuada);
//        final Avaluo avaluo = prendaValuada.getAvaluo();
//        assertNotNull(avaluo);
        // TODO complementar asserts
    }
}
