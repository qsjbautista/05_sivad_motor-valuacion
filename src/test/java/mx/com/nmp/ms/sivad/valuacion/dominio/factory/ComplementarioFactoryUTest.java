/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.ComplementarioDTO;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Clase de prueba utilizada para validar el comportamiento de la clase {@link ComplementarioFactory}.
 *
 * @author ngonzalez
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class ComplementarioFactoryUTest {

    private static final int NUM_PIEZAS = 1;
    private static final int NUM_PIEZAS_CERO = 0;
    private static final int NUM_PIEZAS_MENOR_CERO = -1;
    
    private static final ValorExperto VALOR_EXPERTO = new ValorExperto(
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_VALOR_CERO = new ValorExperto(
        new BigDecimal(0.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_VALOR_MENOR_CERO = new ValorExperto(
        new BigDecimal(-1.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);
    private static final ValorExperto VALOR_EXPERTO_SIN_VALOR = new ValorExperto(
        null, ValorExperto.TipoEnum.UNITARIO);

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Complementario}.
     */
    @Inject
    private ComplementarioFactory complementarioFactory;



    // METODOS

    /**
     * Constructor.
     */
    public ComplementarioFactoryUTest() {
        super();
    }

    /**
     * Utilizado para crear una entidad Complementario por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * VALOR EXPERTO - NO NULO
     */
    @Test()
    public void crearComplementarioTest01() {
        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS, VALOR_EXPERTO);

        Complementario complementario = complementarioFactory.create(complementarioDTO);
        assertNotNull(complementario);
        assertEquals(NUM_PIEZAS, complementario.getNumeroDePiezas());
        assertEquals(VALOR_EXPERTO.getValor(), complementario.getValorExperto().getValor());
    }

    /**
     * Utilizado para crear una entidad Complementario por medio del builder con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * VALOR EXPERTO - NO NULO
     */
    @Test
    public void crearComplementarioTest02() {
        Complementario complementario =
            complementarioFactory.create(getBuilder(NUM_PIEZAS, VALOR_EXPERTO));

        assertNotNull(complementario);
        assertEquals(NUM_PIEZAS, complementario.getNumeroDePiezas());
        assertEquals(VALOR_EXPERTO.getValor(), complementario.getValorExperto().getValor());
    }

    /**
     * Utilizado para crear una entidad Complementario por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - CERO
     * VALOR EXPERTO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearComplementarioTest03() {
        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS_CERO, VALOR_EXPERTO);

        complementarioFactory.create(complementarioDTO);
    }

    /**
     * Utilizado para crear una entidad Complementario por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - MENOR A CERO
     * VALOR EXPERTO - NO NULO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearComplementarioTest04() {
        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS_MENOR_CERO, VALOR_EXPERTO);

        complementarioFactory.create(complementarioDTO);
    }

    /**
     * Utilizado para crear una entidad Complementario por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * VALOR EXPERTO - NULO
     */
    @Test()
    public void crearComplementarioTest05() {
        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS, null);

        Complementario complementario = complementarioFactory.create(complementarioDTO);
        assertNotNull(complementario);
        assertEquals(NUM_PIEZAS, complementario.getNumeroDePiezas());
        assertNull(complementario.getValorExperto());
    }

    /**
     * Utilizado para crear una entidad Complementario por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * VALOR EXPERTO - NO NULO SIN VALOR
     */
    @Test()
    public void crearComplementarioTest06() {
        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS, VALOR_EXPERTO_SIN_VALOR);

        Complementario complementario = complementarioFactory.create(complementarioDTO);
        assertNotNull(complementario);
        assertEquals(NUM_PIEZAS, complementario.getNumeroDePiezas());
        assertEquals(VALOR_EXPERTO_SIN_VALOR.getValor(), complementario.getValorExperto().getValor());
    }

    /**
     * Utilizado para crear una entidad Complementario por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * VALOR EXPERTO - NO NULO CON VALOR CERO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearComplementarioTest07() {
        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS, VALOR_EXPERTO_VALOR_CERO);

        complementarioFactory.create(complementarioDTO);
    }

    /**
     * Utilizado para crear una entidad Complementario por medio de un DTO con las siguientes características:
     *
     * NÚMERO DE PIEZAS - 1
     * VALOR EXPERTO - NO NULO CON VALOR MENOR A CERO
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearComplementarioTest08() {
        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS, VALOR_EXPERTO_VALOR_MENOR_CERO);

        complementarioFactory.create(complementarioDTO);
    }

    /**
     * Metodo auxiliar utilizado para crear el builder de Complementario a partir de sus atributos.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param valorExperto El valor experto para la pieza en particular.
     * @return El builder creado.
     */
    private Complementario.Builder getBuilder(final int numeroDePiezas,
                                              final ValorExperto valorExperto) {
        return new Complementario.Builder() {

            @Override
            public int getNumeroDePiezas() {
                return numeroDePiezas;
            }

            @Override
            public ValorExperto getValorExperto() {
                return valorExperto;
            }

        };
    }

}
