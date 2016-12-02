/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.vo;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.estrategia.RedondeoEstrategiaUtil;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

/**
 * Pruebas de integración para la clase {@link Avaluo}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class AvaluoITest {
    /**
     * (non-Javadoc)
     * @see RedondeoEstrategiaUtil#redondear(BigDecimal)
     */
    @Test
    public void redondeoDefaultAvaluoTest() {
        BigDecimal valorExperto = BigDecimal.valueOf(12.335);
        Avaluo avaluo =  new Avaluo(valorExperto, valorExperto, valorExperto);

        assertEquals(avaluo.valorMinimo(), BigDecimal.valueOf(12.34));
        assertEquals(avaluo.valorPromedio(), BigDecimal.valueOf(12.34));
        assertEquals(avaluo.valorMaximo(), BigDecimal.valueOf(12.34));
    }
}
