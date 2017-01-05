/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.FactorPoliticasCastigoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.PoliticasCastigoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Clase de pruebas para {@link PoliticasCastigo}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class PoliticasCastigoITest {
    @Inject
    private FactorPoliticasCastigoFactory fabricaVo;

    @Inject
    private PoliticasCastigoFactory fabrica;

    public PoliticasCastigoITest() {
        super();
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigo#actualizar()
     */
    @Test
    public void actualizarSinRepositorioTest() {
        Map<Class<? extends Pieza>, BigDecimal> vo = fabricaVo
            .crearCon(BigDecimal.valueOf(30), BigDecimal.valueOf(40), BigDecimal.valueOf(40));
        assertNotNull(vo);

        DateTime fecha = DateTime.now();

        PoliticasCastigo test = fabrica.crearCon(vo, fecha);

        assertNotNull(test);

        test.actualizar();

        assertEquals(vo, test.getFactores());
        assertEquals(fecha, test.getFechaListado());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigo#actualizar()
     */
    @Test
    @Transactional
    public void actualizarTest() {
        Map<Class<? extends Pieza>, BigDecimal> vo = fabricaVo
            .crearCon(BigDecimal.valueOf(0.30), BigDecimal.valueOf(0.40), BigDecimal.valueOf(0.40));
        assertNotNull(vo);

        DateTime fecha = DateTime.now();

        PoliticasCastigo test = fabrica.crearPersistibleCon(vo, fecha);

        assertNotNull(test);

        test.actualizar();

        assertEquals(vo, test.getFactores());
        assertEquals(fecha, test.getFechaListado());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigo#toString()
     */
    @Test
    public void toStringTest() {
        final Map<Class<? extends Pieza>, BigDecimal> vo = fabricaVo
            .crearCon(BigDecimal.valueOf(30), BigDecimal.valueOf(40), BigDecimal.valueOf(40));
        assertNotNull(vo);

        final DateTime fecha = DateTime.now();

        PoliticasCastigo test = fabrica.crearDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return vo;
            }

            @Override
            public DateTime getFechaListado() {
                return fecha;
            }
        });

        assertNotNull(test);
        assertEquals(vo, test.getFactores());
        assertEquals(fecha, test.getFechaListado());
        assertNotNull(test.toString());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigo#hashCode()
     */
    @Test
    public void hashCodeTest() {
        final Map<Class<? extends Pieza>, BigDecimal> vo = fabricaVo
            .crearCon(BigDecimal.valueOf(30), BigDecimal.valueOf(40), BigDecimal.valueOf(40));
        assertNotNull(vo);

        final DateTime fecha = DateTime.now();

        PoliticasCastigo test = fabrica.crearPersistibleDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return vo;
            }

            @Override
            public DateTime getFechaListado() {
                return fecha;
            }
        });

        assertNotNull(test);
        assertEquals(vo, test.getFactores());
        assertEquals(fecha, test.getFechaListado());
        assertNotNull(test.hashCode());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigo#equals(Object)
     */
    @Test
    @SuppressWarnings("ObjectEqualsNull")
    public void equalsTest() {
        Map<Class<? extends Pieza>, BigDecimal> vo = fabricaVo
            .crearCon(BigDecimal.valueOf(30), BigDecimal.valueOf(40), BigDecimal.valueOf(40));

        DateTime fecha = DateTime.now();

        PoliticasCastigo test = fabrica.crearCon(vo, fecha);
        PoliticasCastigo test3 = fabrica.crearCon(vo, fecha);
        PoliticasCastigo test4 = fabrica.crearCon(vo, fecha.minusHours(1));

        assertEquals(test, test);
        assertFalse(test.equals(null));
        assertFalse(test.equals(new Object()));
        assertEquals(test, test3);
        assertNotEquals(test, test4);
    }
}
