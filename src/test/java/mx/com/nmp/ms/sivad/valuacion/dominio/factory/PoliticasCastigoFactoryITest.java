/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Clase de pruebas para {@link PoliticasCastigoFactory}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class PoliticasCastigoFactoryITest {
    @Inject
    private PoliticasCastigoFactory test;

    @Inject
    private FactorPoliticasCastigoFactory fabricaVo;

    public PoliticasCastigoFactoryITest() {
        super();
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearConNulosTest() {
        test.crearCon(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearConNulos2Test() {
        test.crearCon(new HashMap<Class<? extends Pieza>, BigDecimal>(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearConNulos3Test() {
        test.crearCon(fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearConFechaFuturaTest() {
        test.crearCon(fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE), DateTime.now().plusDays(1));
    }

    @Test
    public void crearConTest() {
        PoliticasCastigo resultado = test
            .crearCon(fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE), DateTime.now());

        assertNotNull(resultado);
        assertNotNull(resultado.getFactores());
        assertNotNull(resultado.getFechaListado());
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleConNulosTest() {
        test.crearPersistibleCon(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleConNulos2Test() {
        test.crearPersistibleCon(new HashMap<Class<? extends Pieza>, BigDecimal>(), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleConNulos3Test() {
        test.crearPersistibleCon(fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleConFechaFuturaTest() {
        test.crearPersistibleCon(fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE),
            DateTime.now().plusDays(1));
    }

    @Test
    public void crearPersistibleConTest() {
        PoliticasCastigo resultado = test
            .crearPersistibleCon(fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE), DateTime.now());

        assertNotNull(resultado);
        assertNotNull(resultado.getFactores());
        assertNotNull(resultado.getFechaListado());
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeNuloTest() {
        test.crearDesde(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeConNulosTest() {
        test.crearDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return null;
            }

            @Override
            public DateTime getFechaListado() {
                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeConNulos2Test() {
        test.crearDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return new HashMap<>();
            }

            @Override
            public DateTime getFechaListado() {
                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeConNulos3Test() {
        test.crearDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
            }

            @Override
            public DateTime getFechaListado() {
                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearDesdeConFechaFuturaTest() {
        test.crearDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
            }

            @Override
            public DateTime getFechaListado() {
                return DateTime.now().plusDays(1);
            }
        });
    }

    @Test
    public void crearDesdeTest() {
        PoliticasCastigo resultado = test.crearDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
            }

            @Override
            public DateTime getFechaListado() {
                return DateTime.now();
            }
        });

        assertNotNull(resultado);
        assertNotNull(resultado.getFactores());
        assertNotNull(resultado.getFechaListado());
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeNuloTest() {
        test.crearPersistibleDesde(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeConNulosTest() {
        test.crearPersistibleDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return null;
            }

            @Override
            public DateTime getFechaListado() {
                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeConNulos2Test() {
        test.crearPersistibleDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return new HashMap<>();
            }

            @Override
            public DateTime getFechaListado() {
                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeConNulos3Test() {
        test.crearPersistibleDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
            }

            @Override
            public DateTime getFechaListado() {
                return null;
            }
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void crearPersistibleDesdeConFechaFuturaTest() {
        test.crearPersistibleDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
            }

            @Override
            public DateTime getFechaListado() {
                return DateTime.now().plusDays(1);
            }
        });
    }

    @Test
    public void crearPersistibleDesdeTest() {
        PoliticasCastigo resultado = test.crearPersistibleDesde(new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return fabricaVo.crearCon(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
            }

            @Override
            public DateTime getFechaListado() {
                return DateTime.now();
            }
        });

        assertNotNull(resultado);
        assertNotNull(resultado.getFactores());
        assertNotNull(resultado.getFechaListado());
    }
}
