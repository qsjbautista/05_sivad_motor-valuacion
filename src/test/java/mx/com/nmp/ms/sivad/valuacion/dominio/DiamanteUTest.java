/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaDiamantes;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ValorComercialConsumidor;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private static final BigDecimal VALOR_EXPERTO =
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_EXPERTO_INCREMENTO =
        new BigDecimal(550.00D).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Referencia al conector TablasDeReferenciaDiamantes.
     */
    @Mock
    private TablasDeReferenciaDiamantes conector;



    // METODOS

    /**
     * Constructor.
     */
    public DiamanteUTest() {
        super();

        MockitoAnnotations.initMocks(this);
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
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
            new Diamante(getBuilder(CORTE, COLOR, CLARIDAD, QUILATES, null, VALOR_EXPERTO), conector);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_EXPERTO, avaluo.valorMinimo());
        assertEquals(VALOR_EXPERTO, avaluo.valorPromedio());
        assertEquals(VALOR_EXPERTO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
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
            new Diamante(getBuilder(CORTE, COLOR, CLARIDAD, QUILATES, "", VALOR_EXPERTO), conector);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_EXPERTO, avaluo.valorMinimo());
        assertEquals(VALOR_EXPERTO, avaluo.valorPromedio());
        assertEquals(VALOR_EXPERTO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Diamante con las siguientes características:
     *
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
            new Diamante(getBuilder(CORTE, COLOR, CLARIDAD, QUILATES, null, null), conector);

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
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - VACÍO
     * VALOR EXPERTO - NULO
     */
    @Test
    public void crearDiamanteTest04() {
        Diamante diamante =
            new Diamante(getBuilder(CORTE, COLOR, CLARIDAD, QUILATES, "", null), conector);

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
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NULO
     */
    @Test
    public void crearDiamanteTest05() {
        Diamante diamante =
            new Diamante(getBuilder(CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, null), conector);

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
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     */
    @Test
    public void crearDiamanteTest06() {
        Diamante diamante =
            new Diamante(getBuilder(CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO), conector);

        BigDecimalConsumidor bigDecimalConsumidor = getBigDecimalConsumidor(PORCENTAJE_INCREMENTO);
        when(conector.obtenerModificador(any(Diamante.class))).thenReturn(bigDecimalConsumidor);

        Avaluo avaluo = diamante.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(VALOR_EXPERTO_INCREMENTO, avaluo.valorMinimo());
        assertEquals(VALOR_EXPERTO_INCREMENTO, avaluo.valorPromedio());
        assertEquals(VALOR_EXPERTO_INCREMENTO, avaluo.valorMaximo());
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
    private Diamante.Builder getBuilder(final String corte, final String color,
                                        final String claridad, final BigDecimal quilates,
                                        final String certificadoDiamante, final BigDecimal valorExperto) {
        return new Diamante.Builder() {

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
            public BigDecimal getValorExperto() {
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
