/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector;

import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaDiamanteService;
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
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Pruebas de unidad para la interface {@link TablasDeReferenciaDiamantes}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class TablasDeReferenciaDiamantesUTest {

    private TablasDeReferenciaDiamantes test;

    @Mock
    private ReferenciaDiamanteService mock;

    /**
     * Constructor.
     */
    public TablasDeReferenciaDiamantesUTest() {
        super();

        ReferenciaDiamantesConector conector = new ReferenciaDiamantesConector();
        test = new TablasDeReferenciaDiamantesProxy();

        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(conector, "wsReferenciaDiamante", mock);
        ReflectionTestUtils.setField(test, "referenciaDiamantesConector", conector);
        ReflectionTestUtils.setField(test, "referenciaDiamanteFactory", new ReferenciaDiamanteFactory());
    }


    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaDiamantes#obtenerModificador(CertificadoDiamanteProveedor)
     */
    @Test
    public void obtenerModificadorTest() {
        ObtenerModificadorResponse omr = new ObtenerModificadorResponse();
        omr.setFactor(BigDecimal.ONE);

        when(mock.obtenerModificador(any(ObtenerModificadorRequest.class))).thenReturn(omr);

        BigDecimalConsumidor result = test.obtenerModificador(new CertificadoDiamanteProveedor() {

            @Override
            public String getCertificadoDiamante() {
                return "xXx";
            }
        });

        assertNotNull(result);
        assertNotNull(result.getValor());
        assertEquals(BigDecimal.ONE, result.getValor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaDiamantes#obtenerValorComercial(CaracteristicasDiamanteProveedor)
     */
    @Test
    public void obtenerValorComercialTest() {
        ObtenerValorComercialResponse vcr = new ObtenerValorComercialResponse();
        ValorComercial vc = new ValorComercial();
        vcr.setValorComercial(vc);
        vc.setValorMinimo(BigDecimal.ZERO);
        vc.setValorMedio(BigDecimal.ONE);
        vc.setValorMaximo(BigDecimal.TEN);

        when(mock.obtenerValorComercial(any(ObtenerValorComercialRequest.class))).thenReturn(vcr);

        ValorComercialConsumidor result = test.obtenerValorComercial(new CaracteristicasDiamanteProveedor() {

            @Override
            public String getCorte() {
                return "xXx";
            }

            @Override
            public String getColor() {
                return null;
            }

            @Override
            public String getClaridad() {
                return "xXx";
            }

            @Override
            public BigDecimal getQuilates() {
                return BigDecimal.valueOf(0.8);
            }
        });

        assertNotNull(result);
        assertNotNull(result.getValorMinimo());
        assertNotNull(result.getValorMedio());
        assertNotNull(result.getValorMaximo());

        assertEquals(BigDecimal.ZERO, result.getValorMinimo());
        assertEquals(BigDecimal.ONE, result.getValorMedio());
        assertEquals(BigDecimal.TEN, result.getValorMaximo());
    }
}
