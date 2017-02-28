/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.repository;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.FechaFuturaException;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ListadoNoEncontradoException;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ModificadorCondicionPrendaNoEncontradoException;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.ListadoModificadorCondicionPrendaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ListadoModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrendaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.CondicionPrendaVO;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.HistListadoModificadorCondicionPrendaJPA;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.ListadoModificadorCondicionPrendaJPA;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository.HistListadoModificadorCondicionPrendaJPARepository;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository.ListadoModificadorCondicionPrendaJPARepository;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Clase de prueba utilizada para validar el comportamiento de {@link ModificadorCondicionPrendaRepository}.
 *
 * @author ngonzalez
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class ModificadorCondicionPrendaRepositoryITest {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ModificadorCondicionPrendaRepositoryITest.class);

    private static final String CONDICION_PRENDA = "EX";
    private static final String CONDICION_PRENDA_NO_EXISTE = "XX";
    private static final String CONDICION_PRENDA_NUEVA_1 = "AX";
    private static final String CONDICION_PRENDA_NUEVA_2 = "AY";
    private static final String CONDICION_PRENDA_NUEVA_3 = "AZ";
    private static final String FORMATO_FECHA = "yyyy-MM-dd";
    private static final String ULTIMA_ACTUALIZACION_HISTORICOS = "2017-02-21";
    private static final String ULTIMA_ACTUALIZACION_VIGENTES = "2017-02-24";

    private static final BigDecimal FACTOR = new BigDecimal(1.10).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal FACTOR_NUEVO_1 = new BigDecimal(1.30).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal FACTOR_NUEVO_2 = new BigDecimal(1.15).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal FACTOR_NUEVO_3 = new BigDecimal(1.00).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Referencia al repositorio de {@link ModificadorCondicionPrendaRepository}.
     */
    @Inject
    private ModificadorCondicionPrendaRepository modificadorCondicionPrendaRepository;

    /**
     * Referencia a la fábrica {@link ListadoModificadorCondicionPrendaFactory}.
     */
    @Inject
    private ListadoModificadorCondicionPrendaFactory listadoModificadorCondicionPrendaFactory;

    /**
     * Referencia al repositorio de {@link ListadoModificadorCondicionPrendaJPARepository}.
     */
    @Inject
    private ListadoModificadorCondicionPrendaJPARepository jpaRepository;

    /**
     * Referencia al repositorio de {@link HistListadoModificadorCondicionPrendaJPARepository}.
     */
    @Inject
    private HistListadoModificadorCondicionPrendaJPARepository histJPARepository;



    // METODOS

    /**
     * Constructor.
     */
    public ModificadorCondicionPrendaRepositoryITest() {
        super();
    }

    /**
     * Utilizado para consultar un modificador - condición prenda con un argumento nulo.
     */
    @Test(expected = IllegalArgumentException.class)
    public void consultarModificadorCondicionPrendaVigenteTest01() {
        LOGGER.info(">> consultarModificadorCondicionPrendaVigenteTest01()");

        modificadorCondicionPrendaRepository.consultarModificadorCondicionPrendaVigente(null);

        LOGGER.info("<< consultarModificadorCondicionPrendaVigenteTest01()");
    }

    /**
     * Utilizado para consultar un modificador - condición prenda cuya condición no existe.
     */
    @Test(expected = ModificadorCondicionPrendaNoEncontradoException.class)
    @Transactional
    @Sql("/bd/test-data-modificador-condicion-prenda-01-h2.sql")
    public void consultarModificadorCondicionPrendaVigenteTest02() {
        LOGGER.info(">> consultarModificadorCondicionPrendaVigenteTest02()");

        CondicionPrendaVO condicionPrendaVO = new CondicionPrendaVO(CONDICION_PRENDA_NO_EXISTE);
        modificadorCondicionPrendaRepository.consultarModificadorCondicionPrendaVigente(condicionPrendaVO);

        LOGGER.info("<< consultarModificadorCondicionPrendaVigenteTest02()");
    }

    /**
     * Utilizado para consultar un modificador - condición prenda cuya condición existe.
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-modificador-condicion-prenda-01-h2.sql")
    public void consultarModificadorCondicionPrendaVigenteTest03() {
        LOGGER.info(">> consultarModificadorCondicionPrendaVigenteTest03()");

        CondicionPrendaVO condicionPrendaVO = new CondicionPrendaVO(CONDICION_PRENDA);
        ModificadorCondicionPrenda result =
            modificadorCondicionPrendaRepository.consultarModificadorCondicionPrendaVigente(condicionPrendaVO);

        assertNotNull(result);
        assertNotNull(result.getCondicionPrenda());
        assertNotNull(result.getFactor());
        assertEquals(CONDICION_PRENDA, result.getCondicionPrenda());
        assertEquals(FACTOR, result.getFactor());

        LOGGER.info("<< consultarModificadorCondicionPrendaVigenteTest03()");
    }

    /**
     * Utilizado para consultar un modificador - condición prenda cuya condición existe.
     *
     * Existe más de un registro en la BD con las mismas características.
     */
    @Test(expected = IncorrectResultSizeDataAccessException.class)
    @Transactional
    @Sql("/bd/test-data-modificador-condicion-prenda-02-h2.sql")
    public void consultarModificadorCondicionPrendaVigenteTest04() {
        LOGGER.info(">> consultarModificadorCondicionPrendaVigenteTest04()");

        CondicionPrendaVO condicionPrendaVO = new CondicionPrendaVO(CONDICION_PRENDA);
        modificadorCondicionPrendaRepository.consultarModificadorCondicionPrendaVigente(condicionPrendaVO);

        LOGGER.info("<< consultarModificadorCondicionPrendaVigenteTest04()");
    }

    /**
     * Utilizado para consultar el listado de modificadores - condición prenda vigente (sin datos).
     */
    @Test(expected = ListadoNoEncontradoException.class)
    @Transactional
    public void consultarListadoVigenteTest01() {
        LOGGER.info(">> consultarListadoVigenteTest01()");

        modificadorCondicionPrendaRepository.consultarListadoVigente();

        LOGGER.info("<< consultarListadoVigenteTest01()");
    }

    /**
     * Utilizado para consultar el listado de modificadores - condición prenda vigente (con datos).
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-modificador-condicion-prenda-01-h2.sql")
    public void consultarListadoVigenteTest02() {
        LOGGER.info(">> consultarListadoVigenteTest02()");

        ListadoModificadorCondicionPrenda result =
            modificadorCondicionPrendaRepository.consultarListadoVigente();

        assertNotNull(result);
        assertNotNull(result.getUltimaActualizacion());
        assertNotNull(result.getFechaListado());
        assertNotNull(result.getModificadoresCondicionPrenda());
        assertFalse(result.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(result.getModificadoresCondicionPrenda().size() == 3);

        LOGGER.info("<< consultarListadoVigenteTest02()");
    }

    /**
     * Utilizado para consultar el listado de modificadores - condición prenda vigente (con datos).
     */
    @Test(expected = IncorrectResultSizeDataAccessException.class)
    @Transactional
    @Sql("/bd/test-data-modificador-condicion-prenda-02-h2.sql")
    public void consultarListadoVigenteTest03() {
        LOGGER.info(">> consultarListadoVigenteTest03()");

        modificadorCondicionPrendaRepository.consultarListadoVigente();

        LOGGER.info("<< consultarListadoVigenteTest03()");
    }

    /**
     * Utilizado para consultar los listados de modificadores - condición prenda que correspondan a
     * la fecha de última actualización indicada (con un argumento nulo).
     */
    @Test(expected = IllegalArgumentException.class)
    public void consultarListadoPorUltimaActualizacionTest01() {
        LOGGER.info(">> consultarListadoPorUltimaActualizacionTest01()");

        modificadorCondicionPrendaRepository.consultarListadoPorUltimaActualizacion(null);

        LOGGER.info("<< consultarListadoPorUltimaActualizacionTest01()");
    }

    /**
     * Utilizado para consultar los listados de modificadores - condición prenda que correspondan a
     * la fecha de última actualización indicada (con una fecha posterior a la fecha actual).
     */
    @Test(expected = FechaFuturaException.class)
    @Transactional
    public void consultarListadoPorUltimaActualizacionTest02() {
        LOGGER.info(">> consultarListadoPorUltimaActualizacionTest02()");

        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaActual);
        calendar.add(Calendar.DATE, 1);
        LocalDate fechaFutura = LocalDate.fromDateFields(calendar.getTime());
        modificadorCondicionPrendaRepository.consultarListadoPorUltimaActualizacion(fechaFutura);

        LOGGER.info("<< consultarListadoPorUltimaActualizacionTest02()");
    }

    /**
     * Utilizado para consultar los listados de modificadores - condición prenda que correspondan a
     * la fecha de última actualización indicada (con una fecha anterior a la fecha actual y sin
     * datos).
     */
    @Test(expected = ListadoNoEncontradoException.class)
    @Transactional
    public void consultarListadoPorUltimaActualizacionTest03() {
        LOGGER.info(">> consultarListadoPorUltimaActualizacionTest03()");

        LocalDate fecha = new LocalDate();
        modificadorCondicionPrendaRepository.consultarListadoPorUltimaActualizacion(fecha);

        LOGGER.info("<< consultarListadoPorUltimaActualizacionTest03()");
    }

    /**
     * Utilizado para consultar los listados de modificadores - condición prenda que correspondan a
     * la fecha de última actualización indicada (con una fecha anterior a la fecha actual y con
     * datos).
     *
     * La coincidencia se presenta en los históricos.
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-modificador-condicion-prenda-01-h2.sql")
    public void consultarListadoPorUltimaActualizacionTest04() {
        LOGGER.info(">> consultarListadoPorUltimaActualizacionTest04()");

        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(ULTIMA_ACTUALIZACION_HISTORICOS));
        } catch (ParseException e) {
            LOGGER.error("Ocurrio una excepcion inesperada al realizar la operacion. {}", e.getMessage());
            LOGGER.info("<< consultarListadoPorUltimaActualizacionTest04()");
            fail();
        }

        LocalDate ultimaActualizacion = LocalDate.fromDateFields(calendar.getTime());
        Set<ListadoModificadorCondicionPrenda> result =
            modificadorCondicionPrendaRepository.consultarListadoPorUltimaActualizacion(ultimaActualizacion);

        assertNotNull(result);
        assertTrue(result.size() == 2);

        LOGGER.info("<< consultarListadoPorUltimaActualizacionTest04()");
    }

    /**
     * Utilizado para consultar los listados de modificadores - condición prenda que correspondan a
     * la fecha de última actualización indicada (con una fecha anterior a la fecha actual y con
     * datos).
     *
     * La coincidencia se presenta en los vigentes.
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-modificador-condicion-prenda-01-h2.sql")
    public void consultarListadoPorUltimaActualizacionTest05() {
        LOGGER.info(">> consultarListadoPorUltimaActualizacionTest05()");

        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_FECHA);
        Calendar calendar = Calendar.getInstance();

        try {
            calendar.setTime(sdf.parse(ULTIMA_ACTUALIZACION_VIGENTES));
        } catch (ParseException e) {
            LOGGER.error("Ocurrio una excepcion inesperada al realizar la operacion. {}", e.getMessage());
            LOGGER.info("<< consultarListadoPorUltimaActualizacionTest05()");
            fail();
        }

        LocalDate ultimaActualizacion = LocalDate.fromDateFields(calendar.getTime());
        Set<ListadoModificadorCondicionPrenda> result =
            modificadorCondicionPrendaRepository.consultarListadoPorUltimaActualizacion(ultimaActualizacion);

        assertNotNull(result);
        assertTrue(result.size() == 1);

        LOGGER.info("<< consultarListadoPorUltimaActualizacionTest05()");
    }

    /**
     * Utilizado para actualizar el listado de modificadores - condición prenda (con un argumento nulo).
     */
    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void actualizarListadoTest01() {
        LOGGER.info(">> actualizarListadoTest01()");

        modificadorCondicionPrendaRepository.actualizarListado(null);

        LOGGER.info("<< actualizarListadoTest01()");
    }

    /**
     * Utilizado para actualizar el listado de modificadores - condición prenda (con un listado vacío).
     */
    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void actualizarListadoTest02() {
        LOGGER.info(">> actualizarListadoTest02()");

        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda = new HashSet<>();
        ListadoModificadorCondicionPrenda listado =
            listadoModificadorCondicionPrendaFactory.crearPersistible(
                LocalDate.now(), modificadoresCondicionPrenda);
        modificadorCondicionPrendaRepository.actualizarListado(listado);

        LOGGER.info("<< actualizarListadoTest02()");
    }

    /**
     * Utilizado para actualizar el listado de modificadores - condición prenda (sin datos iniciales).
     */
    @Test
    @Transactional
    public void actualizarListadoTest03() {
        LOGGER.info(">> actualizarListadoTest03()");

        List<ListadoModificadorCondicionPrendaJPA> listadoInicialIda = jpaRepository.findAll();
        assertNotNull(listadoInicialIda);
        assertTrue(listadoInicialIda.size() == 0);

        List<HistListadoModificadorCondicionPrendaJPA> histListadoInicialIda = histJPARepository.findAll();
        assertNotNull(histListadoInicialIda);
        assertTrue(histListadoInicialIda.size() == 0);

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

        ListadoModificadorCondicionPrenda listado =
            listadoModificadorCondicionPrendaFactory.crearPersistible(LocalDate.now(), modificadoresCondicionPrenda);
        ListadoModificadorCondicionPrenda result =
            modificadorCondicionPrendaRepository.actualizarListado(listado);
        assertNotNull(result);

        ListadoModificadorCondicionPrenda resultListadoVigente =
            modificadorCondicionPrendaRepository.consultarListadoVigente();

        assertNotNull(resultListadoVigente);
        assertNotNull(resultListadoVigente.getUltimaActualizacion());
        assertNotNull(resultListadoVigente.getFechaListado());
        assertNotNull(resultListadoVigente.getModificadoresCondicionPrenda());
        assertFalse(resultListadoVigente.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(resultListadoVigente.getModificadoresCondicionPrenda().size() == 3);

        CondicionPrendaVO condicionPrendaVO = new CondicionPrendaVO(CONDICION_PRENDA_NUEVA_1);
        ModificadorCondicionPrenda mcpVigente =
            modificadorCondicionPrendaRepository.consultarModificadorCondicionPrendaVigente(condicionPrendaVO);

        assertNotNull(mcpVigente);
        assertEquals(CONDICION_PRENDA_NUEVA_1, mcpVigente.getCondicionPrenda());
        assertEquals(FACTOR_NUEVO_1, mcpVigente.getFactor());

        List<ListadoModificadorCondicionPrendaJPA> listadoInicialVuelta = jpaRepository.findAll();
        assertNotNull(listadoInicialVuelta);
        assertTrue(listadoInicialVuelta.size() > 0);
        assertEquals(1, listadoInicialVuelta.size());

        List<HistListadoModificadorCondicionPrendaJPA> histListadoInicialVuelta = histJPARepository.findAll();
        assertNotNull(histListadoInicialVuelta);
        assertTrue(histListadoInicialVuelta.size() == 0);

        LOGGER.info("<< actualizarListadoTest03()");
    }

    /**
     * Utilizado para actualizar el listado de modificadores - condición prenda (con datos iniciales).
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-modificador-condicion-prenda-01-h2.sql")
    public void actualizarListadoTest04() {
        LOGGER.info(">> actualizarListadoTest04()");

        List<ListadoModificadorCondicionPrendaJPA> listadoInicialIda = jpaRepository.findAll();
        assertNotNull(listadoInicialIda);
        assertTrue(listadoInicialIda.size() > 0);

        List<HistListadoModificadorCondicionPrendaJPA> histListadoInicialIda = histJPARepository.findAll();
        assertNotNull(histListadoInicialIda);
        assertTrue(histListadoInicialIda.size() > 0);
        int tamanioHistListadoInicial = histListadoInicialIda.size();

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

        ListadoModificadorCondicionPrenda listado =
            listadoModificadorCondicionPrendaFactory.crearPersistible(LocalDate.now(), modificadoresCondicionPrenda);
        ListadoModificadorCondicionPrenda result =
            modificadorCondicionPrendaRepository.actualizarListado(listado);
        assertNotNull(result);

        ListadoModificadorCondicionPrenda resultListadoVigente =
            modificadorCondicionPrendaRepository.consultarListadoVigente();

        assertNotNull(resultListadoVigente);
        assertNotNull(resultListadoVigente.getUltimaActualizacion());
        assertNotNull(resultListadoVigente.getFechaListado());
        assertNotNull(resultListadoVigente.getModificadoresCondicionPrenda());
        assertFalse(resultListadoVigente.getModificadoresCondicionPrenda().isEmpty());
        assertTrue(resultListadoVigente.getModificadoresCondicionPrenda().size() == 3);

        CondicionPrendaVO condicionPrendaVO = new CondicionPrendaVO(CONDICION_PRENDA_NUEVA_3);
        ModificadorCondicionPrenda mcpVigente =
            modificadorCondicionPrendaRepository.consultarModificadorCondicionPrendaVigente(condicionPrendaVO);

        assertNotNull(mcpVigente);
        assertEquals(CONDICION_PRENDA_NUEVA_3, mcpVigente.getCondicionPrenda());
        assertEquals(FACTOR_NUEVO_3, mcpVigente.getFactor());

        List<ListadoModificadorCondicionPrendaJPA> listadoInicialVuelta = jpaRepository.findAll();
        assertNotNull(listadoInicialVuelta);
        assertTrue(listadoInicialVuelta.size() > 0);
        assertEquals(1, listadoInicialVuelta.size());

        List<HistListadoModificadorCondicionPrendaJPA> histListadoInicialVuelta = histJPARepository.findAll();
        assertNotNull(histListadoInicialVuelta);
        assertTrue(histListadoInicialVuelta.size() > 0);
        assertEquals(tamanioHistListadoInicial + 1, histListadoInicialVuelta.size());

        LOGGER.info("<< actualizarListadoTest04()");
    }

}
