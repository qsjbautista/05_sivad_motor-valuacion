/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector;

import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaAlhajaServiceEndpoint;
import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasGramoOroProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja.ReferenciaAlhajasConector;
import mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja.factory.ReferenciaAlhajaFactory;
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


/**
 * Pruebas de integración para la interface {@link TablasDeReferenciaAlhajas}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = MotorValuacionApplication.class, properties = "server.port=10344",
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TablasDeReferenciaAlhajasITest {
    private static Endpoint ep;

    @Inject
    private TablasDeReferenciaAlhajas test;

    @Inject
    private ReferenciaAlhajasConector conector;

    @Inject
    private ReferenciaAlhajaFactory factory;

    @Inject
    private SpringBus bus;

    public TablasDeReferenciaAlhajasITest() {
        super();
    }

    @PostConstruct
    public void setUp() {
        if (ObjectUtils.isEmpty(ep)) {
            ep = new EndpointImpl(bus, new ReferenciaAlhajaServiceEndpoint());
            ((EndpointImpl) ep).setPublishedEndpointUrl("http://localhost:10344/soap-api/ReferenciaAlhajaService?wsdl");
            ep.publish("/ReferenciaAlhajaService");
        }
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaAlhajas#obtenerValorGramoOro(CaracteristicasGramoOroProveedor)
     * @see ConectorExcepcionAspect
     * @see NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void obtenerValorGramoOroTest() {
        ReflectionTestUtils.setField(conector, "wsdlLocation", null);
        ReflectionTestUtils.setField(conector, "wsReferenciaAlhaja", null);
        test.obtenerValorGramoOro(getProveedor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaAlhajas#obtenerValorGramoOro(CaracteristicasGramoOroProveedor)
     * @see ConectorExcepcionAspect
     * @see NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void obtenerValorGramoOroTest2() {
        ReflectionTestUtils.setField(conector, "wsdlLocation", "/ReferenciaDiamanteService");
        ReflectionTestUtils.setField(conector, "wsReferenciaAlhaja", null);
        test.obtenerValorGramoOro(getProveedor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaAlhajas#obtenerValorGramoOro(CaracteristicasGramoOroProveedor)
     * @see ConectorExcepcionAspect
     */
    @Test(expected = NullPointerException.class)
    public void obtenerValorGramoOroTest3() {
        ReflectionTestUtils.setField(test, "referenciaAlhajaFactory", null);
        test.obtenerValorGramoOro(getProveedor());
    }

    /**
     * (non-Javadoc)
     * @see TablasDeReferenciaAlhajas#obtenerValorGramoOro(CaracteristicasGramoOroProveedor)
     */
    @Test
    public void obtenerValorGramoOroTest4() {
        ReflectionTestUtils.setField(conector, "wsdlLocation",
            "http://localhost:10344/soap-api/ReferenciaAlhajaService?wsdl");
        ReflectionTestUtils.setField(conector, "wsReferenciaAlhaja", null);
        ReflectionTestUtils.setField(test, "referenciaAlhajaFactory", factory);
        BigDecimalConsumidor resultado = test.obtenerValorGramoOro(getProveedor());

        assertNotNull(resultado);
        assertNotNull(resultado.getValor());
        assertEquals(BigDecimal.valueOf(104.31), resultado.getValor());
    }

    private CaracteristicasGramoOroProveedor getProveedor() {
        return new CaracteristicasGramoOroProveedor() {
            @Override
            public String getColor() {
                return "xXx";
            }

            @Override
            public String getCalidad() {
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
