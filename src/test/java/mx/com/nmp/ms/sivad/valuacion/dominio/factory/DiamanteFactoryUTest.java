/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.DiamanteDTO;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Clase de prueba utilizada para validar el comportamiento de la clase {@link DiamanteFactory}.
 *
 * @author ngonzalez, ecancino
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class DiamanteFactoryUTest {

    private static final int NUM_PIEZAS = 1;
    private static final int NUM_PIEZAS_CERO = 0;
    private static final int NUM_PIEZAS_MENOR_CERO = -1;

    private static final String CERTIFICADO = "ABC";
    private static final String CLARIDAD = "VS1";
    private static final String COLOR = "D";
    private static final String CORTE = "Oval";

    private static final BigDecimal QUILATES =
        new BigDecimal(0.92D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES_CERO =
        new BigDecimal(0.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES_MENOR_CERO =
        new BigDecimal(-1.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final ValorExperto VALOR_EXPERTO = new ValorExperto(
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_VALOR_CERO = new ValorExperto(
        new BigDecimal(0.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_VALOR_MENOR_CERO = new ValorExperto(
        new BigDecimal(-1.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_SIN_VALOR = new ValorExperto(
        null, ValorExperto.TipoEnum.UNITARIO);
    private static final BigDecimal QUILATES_RANGO_INFERIOR =
        new BigDecimal(0.90D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES_RANGO_SUPERIOR =
        new BigDecimal(0.95D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES_RANGO_INFERIOR_CERO =
        new BigDecimal(0.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES_RANGO_INFERIOR_MENOR_CERO =
        new BigDecimal(-1.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES_RANGO_SUPERIOR_CERO =
        new BigDecimal(0.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES_RANGO_SUPERIOR_MENOR_CERO =
        new BigDecimal(-1.00D).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Diamante}.
     */
    @Inject
    private DiamanteFactory diamanteFactory;



    // METODOS

    /**
     * Constructor.
     */
    public DiamanteFactoryUTest() {
        super();
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test
    public void crearDiamanteTest01() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        Diamante diamante = diamanteFactory.create(diamanteDTO);
        assertNotNull(diamante);
        assertEquals(NUM_PIEZAS, diamante.getNumeroDePiezas());
        assertEquals(CORTE, diamante.getCorte());
        assertEquals(COLOR, diamante.getColor());
        assertEquals(CLARIDAD, diamante.getClaridad());
        assertEquals(CERTIFICADO, diamante.getCertificadoDiamante());
        assertEquals(VALOR_EXPERTO.getValor(), diamante.getValorExperto().getValor());
    }

    /**
     * Utilizado para crear una entidad Diamante por medio del builder con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test
    public void crearDiamanteTest02() {
        Diamante diamante =
            diamanteFactory.create(getBuilder(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR));

        assertNotNull(diamante);
        assertEquals(NUM_PIEZAS, diamante.getNumeroDePiezas());
        assertEquals(CORTE, diamante.getCorte());
        assertEquals(COLOR, diamante.getColor());
        assertEquals(CLARIDAD, diamante.getClaridad());
        assertEquals(CERTIFICADO, diamante.getCertificadoDiamante());
        assertEquals(VALOR_EXPERTO.getValor(), diamante.getValorExperto().getValor());
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest03() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, null, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest04() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, null, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest05() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, null, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest06() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, null, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - CERO
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest07() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS_CERO, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - MENOR A CERO
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest08() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS_MENOR_CERO, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO CON VALOR CERO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest09() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES_CERO, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO CON VALOR MENOR A CERO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest10() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES_MENOR_CERO, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test
    public void crearDiamanteTest11() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, null, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        Diamante diamante = diamanteFactory.create(diamanteDTO);
        assertNotNull(diamante);
        assertEquals(NUM_PIEZAS, diamante.getNumeroDePiezas());
        assertEquals(CORTE, diamante.getCorte());
        assertEquals(COLOR, diamante.getColor());
        assertEquals(CLARIDAD, diamante.getClaridad());
        assertNull(diamante.getCertificadoDiamante());
        assertEquals(VALOR_EXPERTO.getValor(), diamante.getValorExperto().getValor());
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test
    public void crearDiamanteTest12() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, null,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        Diamante diamante = diamanteFactory.create(diamanteDTO);
        assertNotNull(diamante);
        assertEquals(NUM_PIEZAS, diamante.getNumeroDePiezas());
        assertEquals(CORTE, diamante.getCorte());
        assertEquals(COLOR, diamante.getColor());
        assertEquals(CLARIDAD, diamante.getClaridad());
        assertEquals(CERTIFICADO, diamante.getCertificadoDiamante());
        assertNull(diamante.getValorExperto());
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO SIN VALOR
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test
    public void crearDiamanteTest13() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO_SIN_VALOR,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        Diamante diamante = diamanteFactory.create(diamanteDTO);
        assertNotNull(diamante);
        assertEquals(NUM_PIEZAS, diamante.getNumeroDePiezas());
        assertEquals(CORTE, diamante.getCorte());
        assertEquals(COLOR, diamante.getColor());
        assertEquals(CLARIDAD, diamante.getClaridad());
        assertEquals(CERTIFICADO, diamante.getCertificadoDiamante());
        assertEquals(VALOR_EXPERTO_SIN_VALOR.getValor(), diamante.getValorExperto().getValor());
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO CON VALOR CERO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest14() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO_VALOR_CERO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO CON VALOR MENOR A CERO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest15() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO_VALOR_MENOR_CERO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NULO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest16() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                null, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest17() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, null);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO CON VALOR CERO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest18() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR_CERO, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO CON VALOR CERO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest19() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR_CERO);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO CON VALOR MENOR A CERO
     * QUILATES HASTA - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest20() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR_MENOR_CERO, QUILATES_RANGO_SUPERIOR);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Utilizado para crear una entidad Diamante por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * CORTE - NO NULO
     * COLOR - NO NULO
     * CLARIDAD - NO NULA
     * QUILATES - NO NULO
     * CERTIFICADO - NO NULO
     * VALOR EXPERTO - NO NULO
     * QUILATES DESDE - NO NULO
     * QUILATES HASTA - NO NULO CON VALOR MENOR A CERO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearDiamanteTest21() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, COLOR, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR_MENOR_CERO);

        diamanteFactory.create(diamanteDTO);
    }

    /**
     * Metodo auxiliar utilizado para crear el builder de Diamante a partir de sus atributos.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param corte El tipo de corte del diamante.
     * @param color El tipo de color del diamante.
     * @param claridad El tipo de claridad del diamante.
     * @param quilates El valor en quilates del diamante.
     * @param certificadoDiamante El valor del certificado del diamante.
     * @param valorExperto El valor experto para la pieza en particular.
     * @param quilatesDesde El valor del rango inferior en quilates del diamante.
     * @param quilatesHasta El valor del rango superior en quilates del diamante.
     * @return El builder creado.
     */
    private Diamante.Builder getBuilder(final int numeroDePiezas,
                                        final String corte,
                                        final String color,
                                        final String claridad,
                                        final BigDecimal quilates,
                                        final String certificadoDiamante,
                                        final ValorExperto valorExperto,
                                        final BigDecimal quilatesDesde,
                                        final BigDecimal quilatesHasta) {
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

            @Override
            public BigDecimal getQuilatesDesde() {
                return quilatesDesde;
            }

            @Override
            public BigDecimal getQuilatesHasta() {
                return quilatesHasta;
            }

        };
    }

}
