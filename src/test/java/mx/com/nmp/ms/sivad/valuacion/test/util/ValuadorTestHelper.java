package mx.com.nmp.ms.sivad.valuacion.test.util;

import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.Prenda;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.ValuarPrendaBasicoRequest;

/**
 * Clase de ayuda: Datos de prueba
 *
 * @author osanchez
 */
public final class ValuadorTestHelper {

    public static ValuarPrendaBasicoRequest getValuarPrendaBasicoRequestDummy() {
        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();

        final Prenda prenda = new Prenda();
        prenda.setAvaluo(new Avaluo());
        request.setPrenda(prenda);

        return request;
    }

}
