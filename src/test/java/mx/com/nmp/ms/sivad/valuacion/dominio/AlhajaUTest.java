/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio;

import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaAlhajaService;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalResponse;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroResponse;
import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaAlhajas;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja.ReferenciaAlhajasConector;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ValuacionException;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AlhajaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AvaluoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.TipoMetalEnum;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.inject.Inject;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Clase de pruebas para {@link Alhaja}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class AlhajaUTest {

    @Inject
    private AlhajaFactory fabrica;

    @Inject
    private TablasDeReferenciaAlhajas proxy;

    @Inject
    private ReferenciaAlhajasConector conector;

    @Mock
    private ReferenciaAlhajaService mock;

    /**
     * Constructor
     */
    public AlhajaUTest() {
        super();
    }

    @Before
    public void inicializa() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(conector, "wsReferenciaAlhaja", mock);
        ReflectionTestUtils.setField(proxy, "referenciaAlhajasConector", conector);
        ReflectionTestUtils.setField(fabrica, "tablasDeReferenciaAlhajas", proxy);
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalOroPesoTest() {
        addComportamientoGramoOro(BigDecimal.valueOf(312.500));
        BigDecimal valor = BigDecimal.valueOf(662.5);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder(TipoMetalEnum.ORO.getTipo(), "AU_AM", "10_Q", null,
            BigDecimal.valueOf(2.12), null, null, null));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalOroPesoFactorTest() {
        addComportamientoGramoOro(BigDecimal.valueOf(312.500));
        addComportamientoFactor(BigDecimal.valueOf(1.25));
        BigDecimal valor = BigDecimal.valueOf(828.125);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder(TipoMetalEnum.ORO.getTipo(), "AU_AM", "10_Q", "F2",
            BigDecimal.valueOf(2.12), null, null, null));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalOroPesoFactorIncrementoTest() {
        addComportamientoGramoOro(BigDecimal.valueOf(312.500));
        addComportamientoFactor(BigDecimal.valueOf(1.25));
        BigDecimal valor = BigDecimal.valueOf(1076.5625);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder(TipoMetalEnum.ORO.getTipo(), "AU_AM", "10_Q", "F2",
            BigDecimal.valueOf(2.12), BigDecimal.valueOf(1.30), null, null));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalOroPesoFactorIncrementoDesplazamientoTest() {
        addComportamientoGramoOro(BigDecimal.valueOf(312.500));
        addComportamientoFactor(BigDecimal.valueOf(1.25));
        BigDecimal valor = BigDecimal.valueOf(1345.703125);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder(TipoMetalEnum.ORO.getTipo(), "AU_AM", "10_Q", "F2",
            BigDecimal.valueOf(2.12), BigDecimal.valueOf(1.30), BigDecimal.valueOf(1.25), null));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalPlataPesoTest() {
        addComportamientoGramoMetal(BigDecimal.valueOf(600.000));
        BigDecimal valor = BigDecimal.valueOf(1272);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder("PT", null, null, null, BigDecimal.valueOf(2.12), null, null, null));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalPlataPesoIncrementoTest() {
        addComportamientoGramoMetal(BigDecimal.valueOf(600.000));
        BigDecimal valor = BigDecimal.valueOf(1653.6);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder("PT", null, null, null, BigDecimal.valueOf(2.12),
            BigDecimal.valueOf(1.30), null, null));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalPlataPesoIncrementoDesplazamientoTest() {
        addComportamientoGramoMetal(BigDecimal.valueOf(600.000));
        BigDecimal valor = BigDecimal.valueOf(2067);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder("PT", null, null, null, BigDecimal.valueOf(2.12),
            BigDecimal.valueOf(1.30), BigDecimal.valueOf(1.25), null));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalPlataPesoIncrementoDesplazamientoValorExpertoTest() {
        addComportamientoGramoMetal(BigDecimal.valueOf(600.000));
        BigDecimal valor = BigDecimal.valueOf(3692);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder("PT", null, null, null, BigDecimal.valueOf(2.12),
            BigDecimal.valueOf(1.30), BigDecimal.valueOf(1.25),
            new ValorExperto(BigDecimal.valueOf(1000), ValorExperto.TipoEnum.TOTAL)));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test
    public void valuarMetalAGPesoIncrementoDesplazamientoValorExpertoTest() {
        addComportamientoGramoMetal(BigDecimal.valueOf(8.000));
        BigDecimal valor = BigDecimal.valueOf(76.31);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder("AG", null, "CL_999", null, BigDecimal.valueOf(2.12),
            BigDecimal.valueOf(1.30), BigDecimal.valueOf(1.25),
            new ValorExperto(BigDecimal.valueOf(30), ValorExperto.TipoEnum.TOTAL)));

        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
        assertTrue(avaluo == test.valuar());
    }

    /**
     * (non-Javadoc)
     * @see Alhaja#valuar()
     */
    @Test(expected = ValuacionException.class)
    public void valuarMetalAGFactorTest() {
        addComportamientoGramoMetal(BigDecimal.valueOf(8.000));
        addComportamientoFactorError();
        BigDecimal valor = BigDecimal.valueOf(76.31);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Alhaja test = fabrica.create(getBuilder("AG", null, "CL_999", "F1", BigDecimal.valueOf(2.12),
            BigDecimal.valueOf(1.30), BigDecimal.valueOf(1.25),
            new ValorExperto(BigDecimal.valueOf(30), ValorExperto.TipoEnum.TOTAL)));

        assertNotNull(test);

        test.valuar();
    }

    private void addComportamientoGramoOro(BigDecimal retorno) {
        ObtenerValorGramoOroResponse respuesta = new ObtenerValorGramoOroResponse();
        respuesta.setPrecioPorGramo(retorno);

        when(mock.obtenerValorGramoOro(any(ObtenerValorGramoOroRequest.class)))
            .thenReturn(respuesta);
    }

    private void addComportamientoGramoMetal(BigDecimal retorno) {
        ObtenerValorGramoMetalResponse respuesta = new ObtenerValorGramoMetalResponse();
        respuesta.setPrecioPorGramo(retorno);

        when(mock.obtenerValorGramoMetal(any(ObtenerValorGramoMetalRequest.class)))
            .thenReturn(respuesta);
    }

    private void addComportamientoFactor(BigDecimal retorno) {
        ObtenerFactorResponse respuesta = new ObtenerFactorResponse();
        respuesta.setFactor(retorno);

        when(mock.obtenerFactor(any(ObtenerFactorRequest.class)))
            .thenReturn(respuesta);
    }

    private void addComportamientoFactorError() {
        SOAPFaultException excepcion = new SOAPFaultException(getFalla("NMPR007", "No existe el rango solicitado."));

        when(mock.obtenerFactor(any(ObtenerFactorRequest.class)))
            .thenThrow(excepcion);
    }

    private static SOAPFault getFalla(String codigoError, String mensaje) {
        QName faultCode = new QName("", codigoError);
        SOAPFault falla = null;

        try {
            SOAPFactory factory = SOAPFactory.newInstance();
            falla = factory.createFault(mensaje, faultCode);
        } catch (SOAPException e) {
            e.printStackTrace();
        }

        return falla;
    }

    private static Alhaja.Builder getBuilder(final String met, final String col, final String cal, final String ran,
            final BigDecimal pes, final BigDecimal inc, final BigDecimal des, final ValorExperto ve) {
        return new Alhaja.Builder() {
            @Override
            public String getMetal() {
                return met;
            }

            @Override
            public String getColor() {
                return col;
            }

            @Override
            public String getCalidad() {
                return cal;
            }

            @Override
            public String getRango() {
                return ran;
            }

            @Override
            public BigDecimal getPeso() {
                return pes;
            }

            @Override
            public BigDecimal getIncremento() {
                return inc;
            }

            @Override
            public BigDecimal getDesplazamiento() {
                return des;
            }

            @Override
            public ValorExperto getValorExperto() {
                return ve;
            }
        };
    }
}
