/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.repository;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.PoliticaCastigoNoEncontradaException;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.FactorPoliticasCastigoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.PoliticasCastigoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.PoliticasCastigoJpa;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Clase de pruebas para {@link PoliticasCastigoRepository}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class PoliticasCastigoRepositoryITest {
    @Inject
    private PoliticasCastigoRepository test;

    @Inject
    private PoliticasCastigoFactory fabrica;

    @Inject
    private FactorPoliticasCastigoFactory fabricaVo;

    public PoliticasCastigoRepositoryITest() {
        super();
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoJpa
     */
    @Test
    public void entidadTest() {
        PoliticasCastigoJpa jpa = new PoliticasCastigoJpa();

        jpa.setId(1L);
        jpa.setFactores(new HashMap<Class<? extends Pieza>, BigDecimal>());
        jpa.setFechaListado(DateTime.now());

        assertEquals(Long.valueOf(1L), jpa.getId());
        assertNotNull(jpa.getFactores());
        assertNotNull(jpa.getFechaListado());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar()
     */
    @Test(expected = PoliticaCastigoNoEncontradaException.class)
    public void consultarVigentesNoDatosTest() {
        test.consultar();
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Test(expected = PoliticaCastigoNoEncontradaException.class)
    public void consultarPorFechaSinDatosTest() {
        test.consultar(DateTime.now());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Test(expected = PoliticaCastigoNoEncontradaException.class)
    public void consultarPorFechaFuturaSinDatosTest() {
        test.consultar(DateTime.now());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar()
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-politicas-castigo-h2.sql")
    public void consultarVigentesTest() {
        PoliticasCastigo resultado = test.consultar();

        assertNotNull(resultado);
        assertNotNull(resultado.getFechaListado());
        assertNotNull(resultado.getFactores());
        assertEquals(3, resultado.getFactores().size());
        assertEquals(BigDecimal.valueOf(0.4237), resultado.getFactores().get(Complementario.class));
        assertEquals(BigDecimal.valueOf(0.3321), resultado.getFactores().get(Diamante.class));
        assertEquals(BigDecimal.valueOf(0.4173), resultado.getFactores().get(Alhaja.class));
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-politicas-castigo-h2.sql")
    public void consultarPorFechaTest() {
        List<PoliticasCastigo> resultado = test.consultar(DateTime.parse("2017-01-04"));

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertNotNull(resultado.get(0).getFechaListado());
        assertNotNull(resultado.get(0).getFactores());
        assertEquals(3, resultado.get(0).getFactores().size());
        assertEquals(BigDecimal.valueOf(0.4000).setScale(4, BigDecimal.ROUND_HALF_UP),
            resultado.get(0).getFactores().get(Complementario.class));
        assertEquals(BigDecimal.valueOf(0.3000).setScale(4, BigDecimal.ROUND_HALF_UP),
            resultado.get(0).getFactores().get(Diamante.class));
        assertEquals(BigDecimal.valueOf(0.4000).setScale(4, BigDecimal.ROUND_HALF_UP),
            resultado.get(0).getFactores().get(Alhaja.class));
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Transactional
    @Sql("/bd/test-data-politicas-castigo-h2.sql")
    @Test(expected = PoliticaCastigoNoEncontradaException.class)
    public void consultarPorFechaNoexisteTest() {
        test.consultar(DateTime.parse("2017-01-01"));
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Transactional
    @Sql("/bd/test-data-politicas-castigo-h2.sql")
    @Test(expected = PoliticaCastigoNoEncontradaException.class)
    public void consultarPorFechaFuturaTest() {
        test.consultar(DateTime.now().plusHours(1));
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-politicas-castigo2-h2.sql")
    public void consultarPorFecha2Test() {
        List<PoliticasCastigo> resultado = test.consultar(DateTime.parse("2017-01-02"));

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertNotNull(resultado.get(0).getFechaListado());
        assertNotNull(resultado.get(1).getFechaListado());
        assertNotNull(resultado.get(0).getFactores());
        assertNotNull(resultado.get(1).getFactores());
        assertEquals(3, resultado.get(0).getFactores().size());
        assertEquals(3, resultado.get(1).getFactores().size());
        assertEquals(BigDecimal.valueOf(0.4000).setScale(4, BigDecimal.ROUND_HALF_UP),
            resultado.get(0).getFactores().get(Complementario.class));
        assertEquals(BigDecimal.valueOf(0.3000).setScale(4, BigDecimal.ROUND_HALF_UP),
            resultado.get(0).getFactores().get(Diamante.class));
        assertEquals(BigDecimal.valueOf(0.4000).setScale(4, BigDecimal.ROUND_HALF_UP),
            resultado.get(0).getFactores().get(Alhaja.class));
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-politicas-castigo2-h2.sql")
    public void consultarPorFecha3Test() {
        List<PoliticasCastigo> resultado = test.consultar(DateTime.parse("2017-01-02T16"));

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-politicas-castigo2-h2.sql")
    public void consultarPorFecha4Test() {
        List<PoliticasCastigo> resultado = test.consultar(DateTime.parse("2017-01-02T16:1"));

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Test
    @Transactional
    @Sql("/bd/test-data-politicas-castigo2-h2.sql")
    public void consultarPorFecha5Test() {
        List<PoliticasCastigo> resultado = test.consultar(DateTime.parse("2017-01-02T16:1:40"));

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#actualizar(PoliticasCastigo)
     */
    @Test
    @Transactional
    public void actualizarTest() {
        Map<Class<? extends Pieza>, BigDecimal> vo =
            fabricaVo.crearCon(BigDecimal.valueOf(0.3), BigDecimal.valueOf(0.4), BigDecimal.valueOf(0.4));
        DateTime fecha = DateTime.now();
        PoliticasCastigo pc = fabrica.crearCon(vo, fecha);

        test.actualizar(pc);
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#actualizar(PoliticasCastigo)
     */
    @Test(expected = IllegalArgumentException.class)
    public void actualizarNullTest() {
        test.actualizar(null);
    }

    /**
     * (non-Javadoc)
     * @see PoliticasCastigoRepository#consultar(DateTime)
     */
    @Test(expected = IllegalArgumentException.class)
    public void consultarPorFechaNullTest() {
        test.consultar(null);
    }
}
