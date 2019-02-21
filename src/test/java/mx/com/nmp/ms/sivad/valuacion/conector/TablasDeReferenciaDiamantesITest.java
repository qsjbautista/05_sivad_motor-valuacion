/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaDiamantesServiceEndpoint;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasDiamanteProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CertificadoDiamanteProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.diamante.ReferenciaDiamantesConector;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ValuacionException;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.aop.ConectorExcepcionAspect;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.ws.Endpoint;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Pruebas de integración para la interface {@link TablasDeReferenciaDiamantes}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = MotorValuacionApplication.class, properties = "server.port=10344",
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TablasDeReferenciaDiamantesITest {
    private static Endpoint ep;

    @Inject
    private TablasDeReferenciaDiamantes test;

    @Inject
    private ReferenciaDiamantesConector conector;

    @Inject
    private SpringBus bus;

    /**
     * Constructor.
     */
    public TablasDeReferenciaDiamantesITest() {
        super();
    }

    @PostConstruct
    public void setUp() {
        if (ObjectUtils.isEmpty(ep)) {
            ep = new EndpointImpl(bus, new ReferenciaDiamantesServiceEndpoint());
            ((EndpointImpl) ep).setPublishedEndpointUrl("http://localhost:10344/soap-api/ReferenciaDiamanteService");
            ep.publish("/ReferenciaDiamanteService");
        }
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaDiamantes#obtenerValorComercial(CaracteristicasDiamanteProveedor)
     * @see ConectorExcepcionAspect
     * @see ValuacionException
     */
    @Test(expected = ValuacionException.class)
    public void obtenerValorComercialTest1() {
        test.obtenerValorComercial(getProveedor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaDiamantes#obtenerModificador(CertificadoDiamanteProveedor)
     * @see ConectorExcepcionAspect
     * @see ValuacionException
     */
    @Test(expected = ValuacionException.class)
    public void obtenerValorComercialTest2() {
        test.obtenerModificador(getCertificadoDiamanteProveedor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaDiamantes#obtenerModificador(CertificadoDiamanteProveedor)
     * @see ConectorExcepcionAspect
     * @see ValuacionException
     */
    @Test
    public void obtenerValorComercialTest3() {
        try {
            test.obtenerModificador(getCertificadoDiamanteProveedor());
        } catch (ValuacionException e) {
            assertNotNull(e);
            assertNotNull(e.getCodigo());
            assertEquals("soap:NMP-TR-010", e.getCodigo());
            assertNull(e.getActor());
            return;
        }

        fail();
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaDiamantes#obtenerValorComercial(CaracteristicasDiamanteProveedor)
     * @see ConectorExcepcionAspect
     * @see NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void obtenerValorComercialTest4() {
        ReflectionTestUtils.setField(conector, "wsdlLocation", null);
        ReflectionTestUtils.setField(conector, "wsReferenciaDiamante", null);
        test.obtenerValorComercial(getProveedor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaDiamantes#obtenerValorComercial(CaracteristicasDiamanteProveedor)
     * @see ConectorExcepcionAspect
     * @see NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void obtenerValorComercialTest5() {
        ReflectionTestUtils.setField(conector, "wsdlLocation", "/ReferenciaDiamanteService");
        ReflectionTestUtils.setField(conector, "wsReferenciaDiamante", null);
        test.obtenerValorComercial(getProveedor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaDiamantes#obtenerValorComercial(CaracteristicasDiamanteProveedor)
     * @see ConectorExcepcionAspect
     */
    @Test(expected = NullPointerException.class)
    public void obtenerValorComercialTest6() {
        ReflectionTestUtils.setField(test, "referenciaDiamanteFactory", null);
        test.obtenerValorComercial(getProveedor());
    }

    private CaracteristicasDiamanteProveedor getProveedor() {
        return new CaracteristicasDiamanteProveedor() {
            @Override
            public String getCorte() {
                return "xXx";
            }

            @Override
            public String getSubcorte() {
                return "xXx";
            }

            @Override
            public String getColor() {
                return "xXx";
            }

            @Override
            public String getClaridad() {
                return "xXx";
            }

            @Override
            public BigDecimal getQuilates() {
                return BigDecimal.ONE;
            }

            @Override
            public BigDecimal getQuilatesDesde() {
                return BigDecimal.ONE;
            }

            @Override
            public BigDecimal getQuilatesHasta() {
                return BigDecimal.TEN;
            }
        };
    }

    private CertificadoDiamanteProveedor getCertificadoDiamanteProveedor() {
        return new CertificadoDiamanteProveedor() {
            @Override
            public String getCertificadoDiamante() {
                return "xXx";
            }
        };
    }

    @AfterClass
    public static void afterClass() {
        try {
            ep.stop();
        } catch (Throwable t) {
            System.out.println("Error thrown: " + t.getMessage());
        }
    }
}
