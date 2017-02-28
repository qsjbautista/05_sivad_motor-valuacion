/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Clase de prueba utilizada para validar el comportamiento de {@link ModificadorCondicionPrendaFactory}.
 *
 * @author ngonzalez
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class ModificadorCondicionPrendaFactoryUTest {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ModificadorCondicionPrendaFactoryUTest.class);

    private static final String CONDICION_PRENDA = "EX";

    private static final BigDecimal FACTOR = new BigDecimal(1.10).setScale(2, BigDecimal.ROUND_HALF_UP);



    // METODOS

    /**
     * Constructor.
     */
    public ModificadorCondicionPrendaFactoryUTest() {
        super();
    }

    /**
     * Utilizado para crear un modificador - condición prenda con las siguientes características:
     *
     * CONDICIÓN PRENDA - NULA
     * FACTOR - NULO
     */
    @Test
    public void crearModificadorCondicionPrendaTest01() {
        LOGGER.info(">> crearModificadorCondicionPrendaTest01()");

        ModificadorCondicionPrenda result =
            ModificadorCondicionPrendaFactory.crear(null, null);

        assertNotNull(result);
        assertNull(result.getCondicionPrenda());
        assertNull(result.getFactor());

        LOGGER.info("<< crearModificadorCondicionPrendaTest01()");
    }

    /**
     * Utilizado para crear un modificador - condición prenda con las siguientes características:
     *
     * CONDICIÓN PRENDA - NULA
     * FACTOR - NO NULO
     */
    @Test
    public void crearModificadorCondicionPrendaTest02() {
        LOGGER.info(">> crearModificadorCondicionPrendaTest02()");

        ModificadorCondicionPrenda result =
            ModificadorCondicionPrendaFactory.crear(null, FACTOR);

        assertNotNull(result);
        assertNull(result.getCondicionPrenda());
        assertNotNull(result.getFactor());
        assertEquals(FACTOR, result.getFactor());

        LOGGER.info("<< crearModificadorCondicionPrendaTest02()");
    }

    /**
     * Utilizado para crear un modificador - condición prenda con las siguientes características:
     *
     * CONDICIÓN PRENDA - NO NULA
     * FACTOR - NULO
     */
    @Test
    public void crearModificadorCondicionPrendaTest03() {
        LOGGER.info(">> crearModificadorCondicionPrendaTest03()");

        ModificadorCondicionPrenda result =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA, null);

        assertNotNull(result);
        assertNotNull(result.getCondicionPrenda());
        assertNull(result.getFactor());
        assertEquals(CONDICION_PRENDA, result.getCondicionPrenda());

        LOGGER.info("<< crearModificadorCondicionPrendaTest03()");
    }

    /**
     * Utilizado para crear un modificador - condición prenda con las siguientes características:
     *
     * CONDICIÓN PRENDA - NO NULA
     * FACTOR - NO NULO
     */
    @Test
    public void crearModificadorCondicionPrendaTest04() {
        LOGGER.info(">> crearModificadorCondicionPrendaTest04()");

        ModificadorCondicionPrenda result =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA, FACTOR);

        assertNotNull(result);
        assertNotNull(result.getCondicionPrenda());
        assertNotNull(result.getFactor());
        assertEquals(CONDICION_PRENDA, result.getCondicionPrenda());
        assertEquals(FACTOR, result.getFactor());

        LOGGER.info("<< crearModificadorCondicionPrendaTest04()");
    }

}
