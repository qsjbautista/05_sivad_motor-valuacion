/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.*;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.*;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.CondicionPrendaVO;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Clase de prueba utilizada para validar el comportamiento de la clase {@link PrendaFactory}.
 *
 * @author ngonzalez, ecancino
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class PrendaFactoryUTest {

    private static final int NUM_PIEZAS = 1;

    private static final String CALIDAD = "14";
    private static final String CERTIFICADO = "ABC";
    private static final String CLARIDAD = "VS1";
    private static final String COLOR_A = "Amarillo";
    private static final String COLOR_D = "D";
    private static final String CORTE = "Oval";
    private static final String SUBCORTE = "Brillante";
    private static final String METAL = "AU";
    private static final String RANGO = "F1";

    private static final BigDecimal QUILATES =
        new BigDecimal(0.92D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal DESPLAZAMIENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal INCREMENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PESO =
        new BigDecimal(25.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final ValorExperto VALOR_EXPERTO = new ValorExperto(
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.TOTAL);
    private static final BigDecimal QUILATES_RANGO_INFERIOR =
        new BigDecimal(0.90D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES_RANGO_SUPERIOR =
        new BigDecimal(0.95D).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Alhaja}.
     */
    @Inject
    private AlhajaFactory alhajaFactory;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Complementario}.
     */
    @Inject
    private ComplementarioFactory complementarioFactory;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Diamante}.
     */
    @Inject
    private DiamanteFactory diamanteFactory;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Prenda}.
     */
    @Inject
    private PrendaFactory prendaFactory;



    // METODOS

    /**
     * Constructor.
     */
    public PrendaFactoryUTest() {
        super();
    }

    /**
     * Utilizado para crear una entidad Prenda por medio de un DTO con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 1 - Alhaja
     * 1 - Diamante
     * 1 - Complementario
     */
    @Test
    public void crearPrendaTest01() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL, COLOR_A, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO);

        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, SUBCORTE, COLOR_D, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS, VALOR_EXPERTO);

        List<PiezaDTO> piezas = new ArrayList<>();
        piezas.add(alhajaDTO);
        piezas.add(diamanteDTO);
        piezas.add(complementarioDTO);

        PrendaDTO prendaDTO = new PrendaDTO(piezas, "EX");
        Prenda prenda = prendaFactory.create(prendaDTO);

        assertNotNull(prenda);
        assertNotNull(prenda.getPiezas());
        assertEquals(3, prenda.getPiezas().size());
    }

    /**
     * Utilizado para crear una entidad Prenda por medio del builder con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 1 - Alhaja
     * 1 - Diamante
     * 1 - Complementario
     */
    @Test
    public void crearPrendaTest02() {
        Alhaja alhaja =
            alhajaFactory.create(getBuilderAlhaja(METAL, COLOR_A, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO));

        Diamante diamante =
            diamanteFactory.create(getBuilderDiamante(NUM_PIEZAS, CORTE, SUBCORTE, COLOR_D, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR));

        Complementario complementario =
            complementarioFactory.create(getBuilderComplementario(NUM_PIEZAS, VALOR_EXPERTO));

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(alhaja);
        piezas.add(diamante);
        piezas.add(complementario);

        Prenda prenda =
            prendaFactory.create(getBuilder(piezas, "RE"));

        assertNotNull(prenda);
        assertNotNull(prenda.getPiezas());
        assertEquals(3, prenda.getPiezas().size());
    }

    /**
     * Utilizado para crear una entidad Prenda por medio de un DTO con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 1 - Alhaja
     * 0 - Diamante
     * 0 - Complementario
     */
    @Test
    public void crearPrendaTest03() {
        AlhajaDTO alhajaDTO =
            new AlhajaDTO(METAL, COLOR_A, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO);

        List<PiezaDTO> piezas = new ArrayList<>();
        piezas.add(alhajaDTO);

        PrendaDTO prendaDTO = new PrendaDTO(piezas, "EX");
        Prenda prenda = prendaFactory.create(prendaDTO);

        assertNotNull(prenda);
        assertNotNull(prenda.getPiezas());
        assertEquals(1, prenda.getPiezas().size());
    }

    /**
     * Utilizado para crear una entidad Prenda por medio de un DTO con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 0 - Alhaja
     * 1 - Diamante
     * 0 - Complementario
     */
    @Test
    public void crearPrendaTest04() {
        DiamanteDTO diamanteDTO =
            new DiamanteDTO(NUM_PIEZAS, CORTE, SUBCORTE, COLOR_D, CLARIDAD, QUILATES, CERTIFICADO, VALOR_EXPERTO,
                QUILATES_RANGO_INFERIOR, QUILATES_RANGO_SUPERIOR);

        List<PiezaDTO> piezas = new ArrayList<>();
        piezas.add(diamanteDTO);

        PrendaDTO prendaDTO = new PrendaDTO(piezas, "BN");
        Prenda prenda = prendaFactory.create(prendaDTO);

        assertNotNull(prenda);
        assertNotNull(prenda.getPiezas());
        assertEquals(1, prenda.getPiezas().size());
    }

    /**
     * Utilizado para crear una entidad Prenda por medio de un DTO con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 0 - Alhaja
     * 0 - Diamante
     * 1 - Complementario
     */
    @Test
    public void crearPrendaTest05() {
        ComplementarioDTO complementarioDTO =
            new ComplementarioDTO(NUM_PIEZAS, VALOR_EXPERTO);

        List<PiezaDTO> piezas = new ArrayList<>();
        piezas.add(complementarioDTO);

        PrendaDTO prendaDTO = new PrendaDTO(piezas, "RE");
        Prenda prenda = prendaFactory.create(prendaDTO);

        assertNotNull(prenda);
        assertNotNull(prenda.getPiezas());
        assertEquals(1, prenda.getPiezas().size());
    }

    /**
     * Utilizado para crear una entidad Prenda por medio de un DTO con las siguientes características:
     *
     * LISTA DE PIEZAS - NULA
     * 0 - Alhaja
     * 0 - Diamante
     * 0 - Complementario
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPrendaTest06() {
        List<PiezaDTO> piezas = null;

        PrendaDTO prendaDTO = new PrendaDTO(piezas, "EX");
        prendaFactory.create(prendaDTO);
    }

    /**
     * Utilizado para crear una entidad Prenda por medio de un DTO con las siguientes características:
     *
     * LISTA DE PIEZAS - VACIA
     * 0 - Alhaja
     * 0 - Diamante
     * 0 - Complementario
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPrendaTest07() {
        List<PiezaDTO> piezas = new ArrayList<>();

        PrendaDTO prendaDTO = new PrendaDTO(piezas, "BN");
        prendaFactory.create(prendaDTO);
    }

    /**
     * Metodo auxiliar utilizado para crear el builder de Prenda a partir de sus atributos.
     *
     * @param piezas Lista de piezas de las que se compone la prenda.
     * @return El builder creado.
     */
    private Prenda.Builder getBuilder(final List<Pieza> piezas, final String condicionFisica) {
        return new Prenda.Builder() {

            @Override
            public List<Pieza> getPiezas() {
                return piezas;
            }

            @Override
            public CondicionPrendaVO getCondicionFisica() {
                CondicionPrendaVO condicionPrendaVO = null;
                if (!ObjectUtils.isEmpty(condicionFisica)) {
                    condicionPrendaVO = new CondicionPrendaVO(condicionFisica);
                }
                return condicionPrendaVO;
            }

        };
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
     * @return El builder creado.
     */
    private Alhaja.Builder getBuilderAlhaja(final String metal,
                                      final String color,
                                      final String calidad,
                                      final String rango,
                                      final BigDecimal peso,
                                      final BigDecimal incremento,
                                      final BigDecimal desplazamiento, final
                                      ValorExperto valorExperto) {
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

        };
    }

    /**
     * Metodo auxiliar utilizado para crear el builder de Diamante a partir de sus atributos.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param corte El tipo de corte del diamante.
     * @param subcorte El tipo de corte hijo del diamante.
     * @param color El tipo de color del diamante.
     * @param claridad El tipo de claridad del diamante.
     * @param quilates El valor en quilates del diamante.
     * @param certificadoDiamante El valor del certificado del diamante.
     * @param valorExperto El valor experto para la pieza en particular.
     * @param quilatesDesde El valor del rango inferior en quilates del diamante.
     * @param quilatesHasta El valor del rango superior en quilates del diamante.
     * @return El builder creado.
     */
    private Diamante.Builder getBuilderDiamante(final int numeroDePiezas,
                                        final String corte,
                                        final String subcorte,
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
            public String getSubcorte() {
                return subcorte;
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

    /**
     * Metodo auxiliar utilizado para crear el builder de Complementario a partir de sus atributos.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param valorExperto El valor experto para la pieza en particular.
     * @return El builder creado.
     */
    private Complementario.Builder getBuilderComplementario(final int numeroDePiezas,
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
