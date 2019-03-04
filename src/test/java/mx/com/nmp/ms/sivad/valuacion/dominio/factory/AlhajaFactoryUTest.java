/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.AlhajaDTO;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Clase de prueba utilizada para validar el comportamiento de la clase {@link AlhajaFactory}.
 *
 * @author ngonzalez
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class AlhajaFactoryUTest {

    private static final String CALIDAD = "14";
    private static final String COLOR = "Amarillo";
    private static final String METAL_ORO = "AU";
    private static final String METAL_PLATA = "AG";
    private static final String RANGO = "F1";
    private static final String SUBRAMO = "DI";

    private static final BigDecimal DESPLAZAMIENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal INCREMENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PESO =
        new BigDecimal(25.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final ValorExperto VALOR_EXPERTO = new ValorExperto(
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.TOTAL);
    private static final ValorExperto VALOR_EXPERTO_TIPO_UNITARIO = new ValorExperto(
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_VALOR_CERO = new ValorExperto(
        new BigDecimal(0.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.TOTAL);
    private static final ValorExperto VALOR_EXPERTO_VALOR_MENOR_CERO = new ValorExperto(
        new BigDecimal(-1.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.TOTAL);
    private static final ValorExperto VALOR_EXPERTO_SIN_VALOR = new ValorExperto(
        null, ValorExperto.TipoEnum.TOTAL);
    private static final BigDecimal VALOR_MENOR_ZERO =
        new BigDecimal(-1.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal VALOR_ZERO =
        new BigDecimal(0.00D).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Alhaja}.
     */
    @Inject
    private AlhajaFactory alhajaFactory;



    // METODOS

    /**
     * Constructor.
     */
    public AlhajaFactoryUTest() {
        super();
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test
    public void crearAlhajaTest01() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO,
                VALOR_EXPERTO,null, SUBRAMO);

        Alhaja alhaja = alhajaFactory.create(alhajaDTO);
        assertNotNull(alhaja);
        assertEquals(METAL_PLATA, alhaja.getMetal());
        assertEquals(COLOR, alhaja.getColor());
        assertEquals(CALIDAD, alhaja.getCalidad());
        assertEquals(RANGO, alhaja.getRango());
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio del builder con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test
    public void crearAlhajaTest02() {
        Alhaja alhaja =
            alhajaFactory.create(getBuilder(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO, null, SUBRAMO));

        assertNotNull(alhaja);
        assertEquals(METAL_PLATA, alhaja.getMetal());
        assertEquals(COLOR, alhaja.getColor());
        assertEquals(CALIDAD, alhaja.getCalidad());
        assertEquals(RANGO, alhaja.getRango());
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest03() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(null, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO, null, SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest04() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, null, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO ("ORO" - Color y Calidad requeridos)
     * COLOR - NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest05() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_ORO, null, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO ("ORO" - Color y Calidad requeridos)
     * COLOR - NO NULO
     * CALIDAD - NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest06() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_ORO, COLOR, null, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO, null, SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO CON VALOR CERO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest07() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, VALOR_ZERO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO CON VALOR MENOR A CERO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest08() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, VALOR_MENOR_ZERO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test
    public void crearAlhajaTest09() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, null, DESPLAZAMIENTO, VALOR_EXPERTO, null,SUBRAMO);

        Alhaja alhaja = alhajaFactory.create(alhajaDTO);
        assertNotNull(alhaja);
        assertEquals(METAL_PLATA, alhaja.getMetal());
        assertEquals(COLOR, alhaja.getColor());
        assertEquals(CALIDAD, alhaja.getCalidad());
        assertEquals(RANGO, alhaja.getRango());
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO CON VALOR CERO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest10() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, VALOR_ZERO, VALOR_ZERO, VALOR_EXPERTO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO CON VALOR MENOR CERO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest11() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, VALOR_MENOR_ZERO, VALOR_MENOR_ZERO, VALOR_EXPERTO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NULO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test
    public void crearAlhajaTest12() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, null, VALOR_EXPERTO, null,SUBRAMO);

        Alhaja alhaja = alhajaFactory.create(alhajaDTO);
        assertNotNull(alhaja);
        assertEquals(METAL_PLATA, alhaja.getMetal());
        assertEquals(COLOR, alhaja.getColor());
        assertEquals(CALIDAD, alhaja.getCalidad());
        assertEquals(RANGO, alhaja.getRango());
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO CON VALOR CERO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest13() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, VALOR_ZERO, VALOR_EXPERTO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO CON VALOR MENOR A CERO
     * VALOR EXPERTO - NO NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest14() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, VALOR_MENOR_ZERO, VALOR_EXPERTO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NULO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test
    public void crearAlhajaTest15() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, null, null,SUBRAMO);

        Alhaja alhaja = alhajaFactory.create(alhajaDTO);
        assertNotNull(alhaja);
        assertEquals(METAL_PLATA, alhaja.getMetal());
        assertEquals(COLOR, alhaja.getColor());
        assertEquals(CALIDAD, alhaja.getCalidad());
        assertEquals(RANGO, alhaja.getRango());
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO SIN VALOR
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test
    public void crearAlhajaTest16() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO_TIPO_UNITARIO, null,SUBRAMO);

        Alhaja alhaja = alhajaFactory.create(alhajaDTO);
        assertNotNull(alhaja);
        assertEquals(METAL_PLATA, alhaja.getMetal());
        assertEquals(COLOR, alhaja.getColor());
        assertEquals(CALIDAD, alhaja.getCalidad());
        assertEquals(RANGO, alhaja.getRango());
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO CON VALOR CERO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest17() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO_VALOR_CERO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO CON VALOR MENOR A CERO
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest18() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO_VALOR_MENOR_CERO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Utilizado para crear una entidad Alhaja por medio de un DTO con las siguientes características:
     *
     * METAL - NO NULO
     * COLOR - NO NULO
     * CALIDAD - NO NULA
     * RANGO - NO NULO
     * PESO - NO NULO
     * INCREMENTO - NO NULO
     * DESPLAZAMIENTO - NO NULO
     * VALOR EXPERTO - NO NULO (Tipo "UNITARIO" - No soportado)
     * AVALUO COMPLEMENTARIO - NULO
     * SUBRAMO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearAlhajaTest19() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL_PLATA, COLOR, CALIDAD, RANGO, PESO, INCREMENTO, VALOR_MENOR_ZERO, VALOR_EXPERTO_TIPO_UNITARIO, null,SUBRAMO);

        alhajaFactory.create(alhajaDTO);
    }

    /**
     * Metodo auxiliar utilizado para crear el builder de Alhaja a partir de sus atributos.
     *
     * @param metal El metal de la alhaja.
     * @param color El color del metal.
     * @param calidad La calidad de la alhaja.
     * @param rango El rango de la alhaja.
     * @param peso El peso en gramos de la alhaja.
     * @param incremento El incremento por condiciones físicas de la prenda.
     * @param desplazamiento El desplazamiento comercial.
     * @param valorExperto El valor experto para la pieza en particular.
     * @param avaluoComplementario Monto de avalúo complementario
     * @param subramo Abreviatura del subramo
     * @return El builder creado.
     */
    private Alhaja.Builder getBuilder(final String metal,
                                      final String color,
                                      final String calidad,
                                      final String rango,
                                      final BigDecimal peso,
                                      final BigDecimal incremento,
                                      final BigDecimal desplazamiento, final
                                      ValorExperto valorExperto,
                                      final BigDecimal avaluoComplementario,
                                      final String subramo) {
        return new Alhaja.Builder() {

            @Override
            public String getMetal() {
                return metal;
            }

            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getCalidad() {
                return calidad;
            }

            @Override
            public String getRango() {
                return rango;
            }

            @Override
            public BigDecimal getPeso() {
                return peso;
            }

            @Override
            public BigDecimal getIncremento() {
                return incremento;
            }

            @Override
            public BigDecimal getDesplazamiento() {
                return desplazamiento;
            }

            @Override
            public ValorExperto getValorExperto() {
                return valorExperto;
            }

            @Override
            public BigDecimal getAvaluoComplementario() {
                return avaluoComplementario;
            }

            @Override
            public String getSubramo() {
                return subramo;
            }
        };
    }

}
