/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ListadoModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrendaFactory;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Clase de prueba utilizada para validar el comportamiento de {@link ListadoModificadorCondicionPrendaFactory}.
 *
 * @author ngonzalez
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class ListadoModificadorCondicionPrendaFactoryUTest {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ListadoModificadorCondicionPrendaFactoryUTest.class);

    private static final String CONDICION_PRENDA_NUEVA_1 = "AX";
    private static final String CONDICION_PRENDA_NUEVA_2 = "AY";
    private static final String CONDICION_PRENDA_NUEVA_3 = "AZ";

    private static final DateTime ULTIMA_ACTUALIZACION = DateTime.now();

    private static final LocalDate FECHA_LISTADO = LocalDate.now();

    private static final BigDecimal FACTOR_NUEVO_1 = new BigDecimal(1.30).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal FACTOR_NUEVO_2 = new BigDecimal(1.15).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal FACTOR_NUEVO_3 = new BigDecimal(1.00).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Referencia a la fábrica {@link ListadoModificadorCondicionPrendaFactory}.
     */
    @Inject
    private ListadoModificadorCondicionPrendaFactory listadoModificadorCondicionPrendaFactory;



    // METODOS

    /**
     * Constructor.
     */
    public ListadoModificadorCondicionPrendaFactoryUTest() {
        super();
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA ÚLTIMA ACTUALIZACIÓN - NULA
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearListadoModificadorCondicionPrendaTest01() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest01()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        listadoModificadorCondicionPrendaFactory.crear(
            null, FECHA_LISTADO, modificadoresCondicionPrenda);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest01()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     * FECHA LISTADO - NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearListadoModificadorCondicionPrendaTest02() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest02()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        listadoModificadorCondicionPrendaFactory.crear(
            ULTIMA_ACTUALIZACION, null, modificadoresCondicionPrenda);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest02()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearListadoModificadorCondicionPrendaTest03() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest03()");

        listadoModificadorCondicionPrendaFactory.crear(
            ULTIMA_ACTUALIZACION, FECHA_LISTADO, null);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest03()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y SIN ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearListadoModificadorCondicionPrendaTest04() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest04()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        listadoModificadorCondicionPrendaFactory.crear(
            ULTIMA_ACTUALIZACION, FECHA_LISTADO, modificadoresCondicionPrenda);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest04()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test
    public void crearListadoModificadorCondicionPrendaTest05() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest05()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        ModificadorCondicionPrenda mcp2 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_2, FACTOR_NUEVO_2);
        ModificadorCondicionPrenda mcp3 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_3, FACTOR_NUEVO_3);
        modificadoresCondicionPrenda.add(mcp1);
        modificadoresCondicionPrenda.add(mcp2);
        modificadoresCondicionPrenda.add(mcp3);

        ListadoModificadorCondicionPrenda result = listadoModificadorCondicionPrendaFactory.crear(
            ULTIMA_ACTUALIZACION, FECHA_LISTADO, modificadoresCondicionPrenda);

        assertNotNull(result);
        assertNotNull(result.getUltimaActualizacion());
        assertNotNull(result.getFechaListado());
        assertNotNull(result.getModificadoresCondicionPrenda());
        assertEquals(ULTIMA_ACTUALIZACION, result.getUltimaActualizacion());
        assertEquals(FECHA_LISTADO, result.getFechaListado());
        assertFalse(result.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(result.getModificadoresCondicionPrenda().size() == 3);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest05()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA LISTADO - NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearListadoModificadorCondicionPrendaTest06() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest06()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        listadoModificadorCondicionPrendaFactory.crear(null, modificadoresCondicionPrenda);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest06()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearListadoModificadorCondicionPrendaTest07() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest07()");

        listadoModificadorCondicionPrendaFactory.crear(LocalDate.now(), null);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest07()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y SIN ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearListadoModificadorCondicionPrendaTest08() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest08()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        listadoModificadorCondicionPrendaFactory.crear(LocalDate.now(), modificadoresCondicionPrenda);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest08()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda con las siguientes características:
     *
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test
    public void crearListadoModificadorCondicionPrendaTest09() {
        LOGGER.info(">> crearListadoModificadorCondicionPrendaTest09()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        ModificadorCondicionPrenda mcp2 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_2, FACTOR_NUEVO_2);
        ModificadorCondicionPrenda mcp3 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_3, FACTOR_NUEVO_3);
        modificadoresCondicionPrenda.add(mcp1);
        modificadoresCondicionPrenda.add(mcp2);
        modificadoresCondicionPrenda.add(mcp3);

        ListadoModificadorCondicionPrenda result = listadoModificadorCondicionPrendaFactory.crear(
            LocalDate.now(), modificadoresCondicionPrenda);

        assertNotNull(result);
        assertNotNull(result.getFechaListado());
        assertNotNull(result.getModificadoresCondicionPrenda());
        assertEquals(FECHA_LISTADO, result.getFechaListado());
        assertFalse(result.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(result.getModificadoresCondicionPrenda().size() == 3);

        LOGGER.info("<< crearListadoModificadorCondicionPrendaTest09()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda (persistible) con las siguientes
     * características:
     *
     * FECHA LISTADO - NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleListadoModificadorCondicionPrendaTest06() {
        LOGGER.info(">> crearPersistibleListadoModificadorCondicionPrendaTest06()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        listadoModificadorCondicionPrendaFactory.crearPersistible(null, modificadoresCondicionPrenda);

        LOGGER.info("<< crearPersistibleListadoModificadorCondicionPrendaTest06()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda (persistible) con las siguientes
     * características:
     *
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleListadoModificadorCondicionPrendaTest07() {
        LOGGER.info(">> crearPersistibleListadoModificadorCondicionPrendaTest07()");

        listadoModificadorCondicionPrendaFactory.crearPersistible(LocalDate.now(), null);

        LOGGER.info("<< crearPersistibleListadoModificadorCondicionPrendaTest07()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda (persistible) con las siguientes
     * características:
     *
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y SIN ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleListadoModificadorCondicionPrendaTest08() {
        LOGGER.info(">> crearPersistibleListadoModificadorCondicionPrendaTest08()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        listadoModificadorCondicionPrendaFactory.crearPersistible(LocalDate.now(), modificadoresCondicionPrenda);

        LOGGER.info("<< crearPersistibleListadoModificadorCondicionPrendaTest08()");
    }

    /**
     * Utilizado para crear un listado de modificadores - condición prenda (persistible) con las siguientes
     * características:
     *
     * FECHA LISTADO - NO NULA
     * LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test
    public void crearPersistibleListadoModificadorCondicionPrendaTest09() {
        LOGGER.info(">> crearPersistibleListadoModificadorCondicionPrendaTest09()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        ModificadorCondicionPrenda mcp2 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_2, FACTOR_NUEVO_2);
        ModificadorCondicionPrenda mcp3 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_3, FACTOR_NUEVO_3);
        modificadoresCondicionPrenda.add(mcp1);
        modificadoresCondicionPrenda.add(mcp2);
        modificadoresCondicionPrenda.add(mcp3);

        ListadoModificadorCondicionPrenda result =
            listadoModificadorCondicionPrendaFactory.crearPersistible(
                LocalDate.now(), modificadoresCondicionPrenda);

        assertNotNull(result);
        assertNotNull(result.getFechaListado());
        assertNotNull(result.getModificadoresCondicionPrenda());
        assertEquals(FECHA_LISTADO, result.getFechaListado());
        assertFalse(result.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(result.getModificadoresCondicionPrenda().size() == 3);

        LOGGER.info("<< crearPersistibleListadoModificadorCondicionPrendaTest09()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda con las
     * siguientes características:
     *
     * BUILDER - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeListadoModificadorCondicionPrendaTest01() {
        LOGGER.info(">> crearDesdeListadoModificadorCondicionPrendaTest01()");

        listadoModificadorCondicionPrendaFactory.crearDesde(null);

        LOGGER.info("<< crearDesdeListadoModificadorCondicionPrendaTest01()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda con las
     * siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NULA
     *      FECHA LISTADO - NO NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test
    public void crearDesdeListadoModificadorCondicionPrendaTest02() {
        LOGGER.info(">> crearDesdeListadoModificadorCondicionPrendaTest02()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        ListadoModificadorCondicionPrenda result =
            listadoModificadorCondicionPrendaFactory.crearDesde(
                getBuilder(null, FECHA_LISTADO, modificadoresCondicionPrenda));

        assertNotNull(result);
        assertNull(result.getUltimaActualizacion());
        assertNotNull(result.getFechaListado());
        assertNotNull(result.getModificadoresCondicionPrenda());
        assertEquals(FECHA_LISTADO, result.getFechaListado());
        assertFalse(result.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(result.getModificadoresCondicionPrenda().size() == 1);

        LOGGER.info("<< crearDesdeListadoModificadorCondicionPrendaTest02()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda con las
     * siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     *      FECHA LISTADO - NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeListadoModificadorCondicionPrendaTest03() {
        LOGGER.info(">> crearDesdeListadoModificadorCondicionPrendaTest03()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        listadoModificadorCondicionPrendaFactory.crearDesde(
            getBuilder(ULTIMA_ACTUALIZACION, null, modificadoresCondicionPrenda));

        LOGGER.info("<< crearDesdeListadoModificadorCondicionPrendaTest03()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda con las
     * siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     *      FECHA LISTADO - NO NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeListadoModificadorCondicionPrendaTest04() {
        LOGGER.info(">> crearDesdeListadoModificadorCondicionPrendaTest04()");

        listadoModificadorCondicionPrendaFactory.crearDesde(
            getBuilder(ULTIMA_ACTUALIZACION, FECHA_LISTADO, null));

        LOGGER.info("<< crearDesdeListadoModificadorCondicionPrendaTest04()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda con las
     * siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     *      FECHA LISTADO - NO NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y SIN ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeListadoModificadorCondicionPrendaTest05() {
        LOGGER.info(">> crearDesdeListadoModificadorCondicionPrendaTest05()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        listadoModificadorCondicionPrendaFactory.crearDesde(
            getBuilder(ULTIMA_ACTUALIZACION, FECHA_LISTADO, modificadoresCondicionPrenda));

        LOGGER.info("<< crearDesdeListadoModificadorCondicionPrendaTest05()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda con las
     * siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     *      FECHA LISTADO - NO NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test
    public void crearDesdeListadoModificadorCondicionPrendaTest06() {
        LOGGER.info(">> crearDesdeListadoModificadorCondicionPrendaTest06()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        ListadoModificadorCondicionPrenda result =
            listadoModificadorCondicionPrendaFactory.crearDesde(
                getBuilder(ULTIMA_ACTUALIZACION, FECHA_LISTADO, modificadoresCondicionPrenda));

        assertNotNull(result);
        assertNotNull(result.getUltimaActualizacion());
        assertNotNull(result.getFechaListado());
        assertNotNull(result.getModificadoresCondicionPrenda());
        assertEquals(ULTIMA_ACTUALIZACION, result.getUltimaActualizacion());
        assertEquals(FECHA_LISTADO, result.getFechaListado());
        assertFalse(result.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(result.getModificadoresCondicionPrenda().size() == 1);

        LOGGER.info("<< crearDesdeListadoModificadorCondicionPrendaTest06()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda (persistible)
     * con las siguientes características:
     *
     * BUILDER - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeListadoModificadorCondicionPrendaTest01() {
        LOGGER.info(">> crearPersistibleDesdeListadoModificadorCondicionPrendaTest01()");

        listadoModificadorCondicionPrendaFactory.crearPersistibleDesde(null);

        LOGGER.info("<< crearPersistibleDesdeListadoModificadorCondicionPrendaTest01()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda (persistible)
     * con las siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NULA
     *      FECHA LISTADO - NO NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test
    public void crearPersistibleDesdeListadoModificadorCondicionPrendaTest02() {
        LOGGER.info(">> crearPersistibleDesdeListadoModificadorCondicionPrendaTest02()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        ListadoModificadorCondicionPrenda result =
            listadoModificadorCondicionPrendaFactory.crearPersistibleDesde(
                getBuilder(null, FECHA_LISTADO, modificadoresCondicionPrenda));

        assertNotNull(result);
        assertNull(result.getUltimaActualizacion());
        assertNotNull(result.getFechaListado());
        assertNotNull(result.getModificadoresCondicionPrenda());
        assertEquals(FECHA_LISTADO, result.getFechaListado());
        assertFalse(result.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(result.getModificadoresCondicionPrenda().size() == 1);

        LOGGER.info("<< crearPersistibleDesdeListadoModificadorCondicionPrendaTest02()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda (persistible)
     * con las siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     *      FECHA LISTADO - NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeListadoModificadorCondicionPrendaTest03() {
        LOGGER.info(">> crearPersistibleDesdeListadoModificadorCondicionPrendaTest03()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        listadoModificadorCondicionPrendaFactory.crearPersistibleDesde(
            getBuilder(ULTIMA_ACTUALIZACION, null, modificadoresCondicionPrenda));

        LOGGER.info("<< crearPersistibleDesdeListadoModificadorCondicionPrendaTest03()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda (persistible)
     * con las siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     *      FECHA LISTADO - NO NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeListadoModificadorCondicionPrendaTest04() {
        LOGGER.info(">> crearPersistibleDesdeListadoModificadorCondicionPrendaTest04()");

        listadoModificadorCondicionPrendaFactory.crearPersistibleDesde(
            getBuilder(ULTIMA_ACTUALIZACION, FECHA_LISTADO, null));

        LOGGER.info("<< crearPersistibleDesdeListadoModificadorCondicionPrendaTest04()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda (persistible)
     * con las siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     *      FECHA LISTADO - NO NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y SIN ELEMENTOS
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeListadoModificadorCondicionPrendaTest05() {
        LOGGER.info(">> crearPersistibleDesdeListadoModificadorCondicionPrendaTest05()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        listadoModificadorCondicionPrendaFactory.crearPersistibleDesde(
            getBuilder(ULTIMA_ACTUALIZACION, FECHA_LISTADO, modificadoresCondicionPrenda));

        LOGGER.info("<< crearPersistibleDesdeListadoModificadorCondicionPrendaTest05()");
    }

    /**
     * Utilizado para crear (a través del builder) un listado de modificadores - condición prenda (persistible)
     * con las siguientes características:
     *
     * BUILDER - NO NULO CON:
     *      FECHA ÚLTIMA ACTUALIZACIÓN - NO NULA
     *      FECHA LISTADO - NO NULA
     *      LISTADO MODIFICADORES-CONDICIÓN PRENDA - NO NULO Y CON ELEMENTOS
     */
    @Test
    public void crearPersistibleDesdeListadoModificadorCondicionPrendaTest06() {
        LOGGER.info(">> crearPersistibleDesdeListadoModificadorCondicionPrendaTest06()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ModificadorCondicionPrenda mcp1 =
            ModificadorCondicionPrendaFactory.crear(CONDICION_PRENDA_NUEVA_1, FACTOR_NUEVO_1);
        modificadoresCondicionPrenda.add(mcp1);

        ListadoModificadorCondicionPrenda result =
            listadoModificadorCondicionPrendaFactory.crearPersistibleDesde(
                getBuilder(ULTIMA_ACTUALIZACION, FECHA_LISTADO, modificadoresCondicionPrenda));

        assertNotNull(result);
        assertNotNull(result.getUltimaActualizacion());
        assertNotNull(result.getFechaListado());
        assertNotNull(result.getModificadoresCondicionPrenda());
        assertEquals(ULTIMA_ACTUALIZACION, result.getUltimaActualizacion());
        assertEquals(FECHA_LISTADO, result.getFechaListado());
        assertFalse(result.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(result.getModificadoresCondicionPrenda().size() == 1);

        LOGGER.info("<< crearPersistibleDesdeListadoModificadorCondicionPrendaTest06()");
    }

    /**
     * Metodo auxiliar utilizado para crear el builder de {@link ListadoModificadorCondicionPrenda} a partir
     * de sus atributos.
     *
     * @param ultimaActualizacion La fecha en que se realiza la última actualización.
     * @param fechaListado La fecha de origen de la información.
     * @param modificadoresCondicionPrenda La lista de modificadores - condición prenda.
     * @return El builder creado.
     */
    private ListadoModificadorCondicionPrenda.Builder getBuilder(
        final DateTime ultimaActualizacion,
        final LocalDate fechaListado,
        final Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda) {

        return new ListadoModificadorCondicionPrenda.Builder() {

            /**
             * {@inheritDoc}
             */
            @Override
            public DateTime getUltimaActualizacion() {
                return ultimaActualizacion;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public LocalDate getFechaListado() {
                return fechaListado;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Set<ModificadorCondicionPrenda> getModificadoresCondicionPrenda() {
                return modificadoresCondicionPrenda;
            }

        };

    }

}
