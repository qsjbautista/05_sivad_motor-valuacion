/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.vo;

import mx.com.nmp.ms.sivad.valuacion.infrastructure.estrategia.RedondeoEstrategiaUtil;
import mx.com.nmp.ms.sivad.valuacion.dominio.estrategia.RedondeoEstrategias;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Pruebas de unidad para la clase {@link Avaluo}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class AvaluoUTest {
    /**
     * Constructor.
     */
    public AvaluoUTest() {
        super();
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     */
    @Test
    public void crearAvaluoTest() {
        Avaluo avaluo =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

        assertNotNull(avaluo);
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     * @see Avaluo#valorMinimo()
     * @see Avaluo#valorPromedio()
     * @see Avaluo#valorMaximo()
     */
    @Test
    public void obtenerValoresAvaluoTest() {
        BigDecimal valorExperto = BigDecimal.ONE;
        Avaluo avaluo =  new Avaluo(valorExperto, valorExperto, valorExperto);

        valorExperto = RedondeoEstrategiaUtil.get().redondear(valorExperto);

        assertNotNull(avaluo);
        assertEquals(avaluo.valorMinimo(), valorExperto);
        assertEquals(avaluo.valorPromedio(), valorExperto);
        assertEquals(avaluo.valorMaximo(), valorExperto);
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     * @see Avaluo#equals(Object)
     */
    @Test
    public void equalsAvaluoTest() {
        Avaluo avaluo =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

        assertEquals(avaluo, avaluo);
        assertFalse(avaluo.equals(null));
        assertFalse(avaluo.equals(new Object()));
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     * @see Avaluo#equals(Object)
     */
    @Test
    public void equalsFalseMinimoAvaluoTest() {
        Avaluo avaluo =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        Avaluo avaluo2 =  new Avaluo(BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ONE);

        assertFalse(avaluo.equals(avaluo2));
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     * @see Avaluo#equals(Object)
     */
    @Test
    public void equalsFalsePromedioAvaluoTest() {
        Avaluo avaluo =  new Avaluo(BigDecimal.ONE, BigDecimal.TEN, BigDecimal.ONE);
        Avaluo avaluo2 =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

        assertFalse(avaluo.equals(avaluo2));
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     * @see Avaluo#equals(Object)
     */
    @Test
    public void equalsFalseMaximoAvaluoTest() {
        Avaluo avaluo =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        Avaluo avaluo2 =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.TEN);

        assertFalse(avaluo.equals(avaluo2));
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     * @see Avaluo#equals(Object)
     */
    @Test
    public void equalsTrueAvaluoTest() {
        Avaluo avaluo =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        Avaluo avaluo2 =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

        assertEquals(avaluo, avaluo2);
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     * @see Avaluo#toString()
     */
    @Test
    public void toStringTrueAvaluoTest() {
        Avaluo avaluo =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

        assertNotNull(avaluo.toString());
    }

    /**
     * (non-Javadoc)
     * @see Avaluo
     * @see Avaluo#hashCode()
     */
    @Test
    public void hashCodeTrueAvaluoTest() {
        Avaluo avaluo =  new Avaluo(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);

        assertNotNull(avaluo.hashCode());
    }

    /**
     * (non-Javadoc)
     * @see RedondeoEstrategiaUtil#redondear(BigDecimal)
     */
    @Test
    public void redondeoROUND_HALF_DOWNAvaluoTest() {
        BigDecimal valorExperto = BigDecimal.valueOf(12.335);
        Avaluo avaluo =  new Avaluo(valorExperto, valorExperto, valorExperto);

        RedondeoEstrategiaUtil.get();
        ReflectionTestUtils.setField(RedondeoEstrategiaUtil.class, "INSTANCIA", null);
        RedondeoEstrategiaUtil redondeoEstrategiaUtil = RedondeoEstrategiaUtil.get();

        ReflectionTestUtils.setField(redondeoEstrategiaUtil, "estrategiaRedondeo",
            RedondeoEstrategias.DOS_DECIMALES_ROUND_HALF_DOWN.name());

        assertEquals(avaluo.valorMinimo(), BigDecimal.valueOf(12.33));
        assertEquals(avaluo.valorPromedio(), BigDecimal.valueOf(12.33));
        assertEquals(avaluo.valorMaximo(), BigDecimal.valueOf(12.33));
    }

    /**
     * (non-Javadoc)
     * @see RedondeoEstrategiaUtil#redondear(BigDecimal)
     */
    @Test
    public void redondeoROUND_HALF_UPAvaluoTest() {
        BigDecimal valorExperto = BigDecimal.valueOf(12.335);
        Avaluo avaluo =  new Avaluo(valorExperto, valorExperto, valorExperto);

        RedondeoEstrategiaUtil.get();
        ReflectionTestUtils.setField(RedondeoEstrategiaUtil.class, "INSTANCIA", null);
        RedondeoEstrategiaUtil redondeoEstrategiaUtil = RedondeoEstrategiaUtil.get();

        ReflectionTestUtils.setField(redondeoEstrategiaUtil, "estrategiaRedondeo",
            RedondeoEstrategias.DOS_DECIMALES_ROUND_HALF_UP.name());

        assertEquals(avaluo.valorMinimo(), BigDecimal.valueOf(12.34));
        assertEquals(avaluo.valorPromedio(), BigDecimal.valueOf(12.34));
        assertEquals(avaluo.valorMaximo(), BigDecimal.valueOf(12.34));
    }

    /**
     * (non-Javadoc)
     * @see RedondeoEstrategiaUtil#redondear(BigDecimal)
     */
    @Test
    public void redondeoDefaultAvaluoTest() {
        BigDecimal valorExperto = BigDecimal.valueOf(12.335);
        Avaluo avaluo =  new Avaluo(valorExperto, valorExperto, valorExperto);

        RedondeoEstrategiaUtil.get();
        ReflectionTestUtils.setField(RedondeoEstrategiaUtil.class, "INSTANCIA", null);

        assertEquals(avaluo.valorMinimo(), BigDecimal.valueOf(12.34));
        assertEquals(avaluo.valorPromedio(), BigDecimal.valueOf(12.34));
        assertEquals(avaluo.valorMaximo(), BigDecimal.valueOf(12.34));
    }
}
