/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaDiamantes;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ValorComercialConsumidor;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.DiamanteFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Clase de prueba utilizada para validar el comportamiento de la clase {@link Diamante}
 *
 * @author ngonzalez
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class DiamanteUTest {

    private static final int NUM_PIEZAS_MENOR_CERO = -1;
    private static final int NUM_PIEZAS_CERO = 0;
    private static final int NUM_PIEZAS_UNO = 1;
    private static final int NUM_PIEZAS_DOS = 2;
    private static final String CERTIFICADO = "ABC";
    private static final String CLARIDAD = "VS1";
    private static final String COLOR = "D";
    private static final String CORTE = "Oval";
    private static final BigDecimal QUILATES =
        new BigDecimal(0.92D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PORCENTAJE_INCREMENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_MINIMO =
        new BigDecimal(400.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_MINIMO_INCREMENTO =
        new BigDecimal(440.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_MEDIO =
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_MEDIO_INCREMENTO =
        new BigDecimal(550.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_MAXIMO =
        new BigDecimal(600.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_MAXIMO_INCREMENTO =
        new BigDecimal(660.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final ValorExperto VALOR_EXPERTO = new ValorExperto(
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_VALOR_NULO = new ValorExperto(
        null, ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_INCREMENTO = new ValorExperto(
        new BigDecimal(550.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);

    /**
     * Referencia al conector TablasDeReferenciaDiamantes.
     */
    @Mock
    private TablasDeReferenciaDiamantes conector;

    /**
     * Referencia a la fábrica de {@link Diamante}.
     */
    @Inject
    private DiamanteFactory diamanteFactory;



    // METODOS

    /**
     * Constructor.
     */
    public DiamanteUTest() {
        super();
    }

    /**
     * Configuración inicial.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(diamanteFactory, "tablasDeReferenciaDiamantes", conector);
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NULO
     * VALOR EXPERTO - NO NULO
     */
    @Test
    public void crearDiamanteTest01() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_UNO, CORTE, COLOR, CLARIDAD, QUILATES, null, VALOR_EXPERTO));

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_EXPERTO.getValor(), avaluo.valorMinimo());
        assertEquals(VALOR_EXPERTO.getValor(), avaluo.valorPromedio());
        assertEquals(VALOR_EXPERTO.getValor(), avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - VACÍO
     * VALOR EXPERTO - NO NULO
     */
    @Test
    public void crearDiamanteTest02() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_UNO, CORTE, COLOR, CLARIDAD, QUILATES, "", VALOR_EXPERTO));

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_EXPERTO.getValor(), avaluo.valorMinimo());
        assertEquals(VALOR_EXPERTO.getValor(), avaluo.valorPromedio());
        assertEquals(VALOR_EXPERTO.getValor(), avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NULO
     * VALOR EXPERTO - NULO
     */
    @Test
    public void crearDiamanteTest03() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_UNO, CORTE, COLOR, CLARIDAD, QUILATES, null, null));

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            VALOR_MINIMO, VALOR_MEDIO, VALOR_MAXIMO);
        when(conector.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_MINIMO, avaluo.valorMinimo());
        assertEquals(VALOR_MEDIO, avaluo.valorPromedio());
        assertEquals(VALOR_MAXIMO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NULO
     * VALOR EXPERTO - NO NULO (Valor nulo)
     */
    @Test
    public void crearDiamanteTest04() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_UNO, CORTE, COLOR, CLARIDAD, QUILATES, null, VALOR_EXPERTO_VALOR_NULO));

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            VALOR_MINIMO, VALOR_MEDIO, VALOR_MAXIMO);
        when(conector.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_MINIMO, avaluo.valorMinimo());
        assertEquals(VALOR_MEDIO, avaluo.valorPromedio());
        assertEquals(VALOR_MAXIMO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - VACÍO
     * VALOR EXPERTO - NULO
     */
    @Test
    public void crearDiamanteTest05() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_UNO, CORTE, COLOR, CLARIDAD, QUILATES, "", null));

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            VALOR_MINIMO, VALOR_MEDIO, VALOR_MAXIMO);
        when(conector.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_MINIMO, avaluo.valorMinimo());
        assertEquals(VALOR_MEDIO, avaluo.valorPromedio());
        assertEquals(VALOR_MAXIMO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - VACÍO
     * VALOR EXPERTO - NO NULO (Valor nulo)
     */
    @Test
    public void crearDiamanteTest06() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_UNO, CORTE, COLOR, CLARIDAD, QUILATES, "", VALOR_EXPERTO_VALOR_NULO));

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            VALOR_MINIMO, VALOR_MEDIO, VALOR_MAXIMO);
        when(conector.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_MINIMO, avaluo.valorMinimo());
        assertEquals(VALOR_MEDIO, avaluo.valorPromedio());
        assertEquals(VALOR_MAXIMO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NULO
     */
    @Test
    public void crearDiamanteTest07() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_UNO, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, null));

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            VALOR_MINIMO, VALOR_MEDIO, VALOR_MAXIMO);
        when(conector.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        BigDecimalConsumidor bigDecimalConsumidor = getBigDecimalConsumidor(PORCENTAJE_INCREMENTO);
        when(conector.obtenerModificador(any(Diamante.class))).thenReturn(bigDecimalConsumidor);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_MINIMO_INCREMENTO, avaluo.valorMinimo());
        assertEquals(VALOR_MEDIO_INCREMENTO, avaluo.valorPromedio());
        assertEquals(VALOR_MAXIMO_INCREMENTO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - MENOR A CERO
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest08() {
        diamanteFactory.create(getBuilder(NUM_PIEZAS_MENOR_CERO, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, null));
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - CERO
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest09() {
        diamanteFactory.create(getBuilder(NUM_PIEZAS_CERO, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, null));
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 2
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NULO
     */
    @Test
    public void crearDiamanteTest10() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_DOS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, null));

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            VALOR_MINIMO, VALOR_MEDIO, VALOR_MAXIMO);
        when(conector.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        BigDecimalConsumidor bigDecimalConsumidor = getBigDecimalConsumidor(PORCENTAJE_INCREMENTO);
        when(conector.obtenerModificador(any(Diamante.class))).thenReturn(bigDecimalConsumidor);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_MINIMO_INCREMENTO.multiply(new BigDecimal(NUM_PIEZAS_DOS)), avaluo.valorMinimo());
        assertEquals(VALOR_MEDIO_INCREMENTO.multiply(new BigDecimal(NUM_PIEZAS_DOS)), avaluo.valorPromedio());
        assertEquals(VALOR_MAXIMO_INCREMENTO.multiply(new BigDecimal(NUM_PIEZAS_DOS)), avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     */
    @Test
    public void crearDiamanteTest11() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_UNO, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO));

        BigDecimalConsumidor bigDecimalConsumidor = getBigDecimalConsumidor(PORCENTAJE_INCREMENTO);
        when(conector.obtenerModificador(any(Diamante.class))).thenReturn(bigDecimalConsumidor);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_EXPERTO_INCREMENTO.getValor(), avaluo.valorMinimo());
        assertEquals(VALOR_EXPERTO_INCREMENTO.getValor(), avaluo.valorPromedio());
        assertEquals(VALOR_EXPERTO_INCREMENTO.getValor(), avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - MENOR A CERO
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest12() {
        diamanteFactory.create(getBuilder(NUM_PIEZAS_MENOR_CERO, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO));
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - CERO
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest13() {
        diamanteFactory.create(getBuilder(NUM_PIEZAS_CERO, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO));
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 2
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     */
    @Test
    public void crearDiamanteTest14() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS_DOS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO));

        BigDecimalConsumidor bigDecimalConsumidor = getBigDecimalConsumidor(PORCENTAJE_INCREMENTO);
        when(conector.obtenerModificador(any(Diamante.class))).thenReturn(bigDecimalConsumidor);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_EXPERTO_INCREMENTO.getValor().multiply(new BigDecimal(NUM_PIEZAS_DOS)), avaluo.valorMinimo());
        assertEquals(VALOR_EXPERTO_INCREMENTO.getValor().multiply(new BigDecimal(NUM_PIEZAS_DOS)), avaluo.valorPromedio());
        assertEquals(VALOR_EXPERTO_INCREMENTO.getValor().multiply(new BigDecimal(NUM_PIEZAS_DOS)), avaluo.valorMaximo());
    }

    /**
     * Metodo auxiliar utilizado para crear el builder de Diamante a partir de sus atributos.
     *
     * @param corte El tipo de corte del diamante.
     * @param color El tipo de color del diamante.
     * @param claridad El tipo de claridad del diamante.
     * @param quilates El valor en quilates del diamante.
     * @param certificadoDiamante El valor del certificado del diamante.
     * @param valorExperto El valor experto para la pieza en particular.
     * @return El builder creado.
     */
    private Diamante.Builder getBuilder(final int numeroDePiezas, final String corte, final String color,
                                        final String claridad, final BigDecimal quilates,
                                        final String certificadoDiamante, final ValorExperto valorExperto) {
        return new Diamante.Builder() {

            @Override
            public int getNumeroDePiezas() {
                return numeroDePiezas;
            }

            @Override
            public String getCorte() {
                return corte;
            }

            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getClaridad() {
                return claridad;
            }

            @Override
            public BigDecimal getQuilates() {
                return quilates;
            }

            @Override
            public String getCertificadoDiamante() {
                return certificadoDiamante;
            }

            @Override
            public ValorExperto getValorExperto() {
                return valorExperto;
            }

        };
    }

    /**
     * Metodo auxiliar utilizado para crear el valor comercial que devolvería el conector.
     *
     * @param valorMinimo El valor comercial mínimo.
     * @param valorMedio El valor comercial medio.
     * @param valorMaximo El valor comercial máximo.
     * @return El valor comercial creado.
     */
    private ValorComercialConsumidor getValorComercialConsumidor(
        final BigDecimal valorMinimo,
        final BigDecimal valorMedio,
        final BigDecimal valorMaximo) {

        return new ValorComercialConsumidor() {

            @Override
            public BigDecimal getValorMinimo() {
                return valorMinimo;
            }

            @Override
            public BigDecimal getValorMedio() {
                return valorMedio;
            }

            @Override
            public BigDecimal getValorMaximo() {
                return valorMaximo;
            }

        };
    }

    /**
     * Metodo auxiliar utilizado para crear el porcentaje de incremento por certificado que devolvería el conector.
     *
     * @param valor El valor del porcentaje de incremento por certificado.
     * @return El porcentaje de incremento por certificado creado.
     */
    private BigDecimalConsumidor getBigDecimalConsumidor(final BigDecimal valor) {
        return new BigDecimalConsumidor() {

            @Override
            public BigDecimal getValor() {
                return valor;
            }

        };
    }

}
