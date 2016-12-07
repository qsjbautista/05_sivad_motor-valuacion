/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector;

import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaAlhajaService;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroResponse;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasGramoOroProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.MetalCalidadRangoProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja.ReferenciaAlhajasConector;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja.factory.ReferenciaAlhajaFactory;
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
 * Pruebas de unidad para la interface {@link TablasDeReferenciaAlhajas}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class TablasDeReferenciaAlhajasUTest {
    private TablasDeReferenciaAlhajas test;

    @Mock
    private ReferenciaAlhajaService mock;

    /**
     * Constructor.
     */
    public TablasDeReferenciaAlhajasUTest() {
        super();

        ReferenciaAlhajasConector conector = new ReferenciaAlhajasConector();
        test = new TablasDeReferenciaAlhajasProxy();

        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(conector, "wsReferenciaAlhaja", mock);
        ReflectionTestUtils.setField(test, "referenciaAlhajasConector", conector);
        ReflectionTestUtils.setField(test, "referenciaAlhajaFactory", new ReferenciaAlhajaFactory());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaAlhajas#obtenerValorGramoOro(CaracteristicasGramoOroProveedor)
     */
    @Test
    public void obtenerValorGramoOroTest() {
        ObtenerValorGramoOroResponse vgr = new ObtenerValorGramoOroResponse();
        vgr.setPrecioPorGramo(BigDecimal.ONE);

        when(mock.obtenerValorGramoOro(any(ObtenerValorGramoOroRequest.class))).thenReturn(vgr);

        BigDecimalConsumidor result = test.obtenerValorGramoOro(new CaracteristicasGramoOroProveedor() {

            @Override
            public String getColor() {
                return "xXx";
            }

            @Override
            public String getCalidad() {
                return "xXx";
            }
        });

        assertNotNull(result);
        assertNotNull(result.getValor());
        assertEquals(BigDecimal.ONE, result.getValor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaAlhajas#obtenerFactor(MetalCalidadRangoProveedor)
     */
    @Test
    public void obtenerFactorTest() {
        ObtenerFactorResponse ofr = new ObtenerFactorResponse();
        ofr.setFactor(BigDecimal.ONE);

        when(mock.obtenerFactor(any(ObtenerFactorRequest.class))).thenReturn(ofr);

        BigDecimalConsumidor result = test.obtenerFactor(new MetalCalidadRangoProveedor() {
            @Override
            public String getMetal() {
                return "xXx";
            }

            @Override
            public String getCalidad() {
                return "xXx";
            }

            @Override
            public String getRango() {
                return "xXx";
            }
        });

        assertNotNull(result);
        assertNotNull(result.getValor());
        assertEquals(BigDecimal.ONE, result.getValor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaAlhajas#obtenerValorGramoMetal(MetalCalidadRangoProveedor)
     */
    @Test
    public void obtenerValorGramoMetalTest() {
        ObtenerValorGramoMetalResponse vgm = new ObtenerValorGramoMetalResponse();
        vgm.setPrecioPorGramo(BigDecimal.ONE);

        when(mock.obtenerValorGramoMetal(any(ObtenerValorGramoMetalRequest.class))).thenReturn(vgm);

        BigDecimalConsumidor result = test.obtenerValorGramoMetal(new MetalCalidadRangoProveedor() {
            @Override
            public String getMetal() {
                return "xXx";
            }

            @Override
            public String getCalidad() {
                return "xXx";
            }

            @Override
            public String getRango() {
                return "xXx";
            }
        });

        assertNotNull(result);
        assertNotNull(result.getValor());
        assertEquals(BigDecimal.ONE, result.getValor());
    }
}
