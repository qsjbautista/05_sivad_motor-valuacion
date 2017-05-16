/**
* Proyecto:        NMP - Microservicio de Motor de Valuación
* Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
*/
package mx.com.nmp.ms.sivad.valuacion.dominio;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaAlhajas;
import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaDiamantes;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ValorComercialConsumidor;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.*;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.*;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.CondicionPrendaVO;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import org.joda.time.DateTime;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
* Clase de prueba utilizada para validar el comportamiento de la clase {@link Prenda}
*
* @author ngonzalez
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class PrendaUTest {

    private static final int NUM_PIEZAS_1 = 1;
    private static final int NUM_PIEZAS_2 = 2;

    private static final String CALIDAD = "14";
    private static final String CERTIFICADO = "ABC";
    private static final String CLARIDAD = "VS1";
    private static final String COLOR_A = "Amarillo";
    private static final String COLOR_D = "D";
    private static final String CORTE = "Oval";
    private static final String METAL = "AU";
    private static final String RANGO = "F1";

    private static final BigDecimal QUILATES =
        new BigDecimal(0.92D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal DESPLAZAMIENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal INCREMENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PC_FACTOR_ALHAJA =
        new BigDecimal(1.20D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PC_FACTOR_COMPLEMENTARIO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PC_FACTOR_DIAMANTE =
        new BigDecimal(1.30D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PESO =
        new BigDecimal(25.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final ValorExperto VALOR_EXPERTO_TOTAL = new ValorExperto(
        new BigDecimal(100.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.TOTAL);
    private static final ValorExperto VALOR_EXPERTO_UNITARIO = new ValorExperto(
        new BigDecimal(100.00D).setScale(2, BigDecimal.ROUND_HALF_UP), ValorExperto.TipoEnum.UNITARIO);


    // UTILIZADAS PARA EL AVALÚO DE ALHAJAS.
    private static final BigDecimal AV_ALHAJA_FACTOR =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal AV_ALHAJA_VALOR_GRAMO_ORO =
        new BigDecimal(100.00D).setScale(3, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal AV_ALHAJA =
        new BigDecimal(120).setScale(2, BigDecimal.ROUND_HALF_UP);


    // UTILIZADAS PARA EL AVALÚO DE DIAMANTES.
    private static final BigDecimal AV_DIAMANTE_PORCENTAJE_INCREMENTO =
        new BigDecimal(1.10D).setScale(3, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal AV_DIAMANTE_VALOR_COMERCIAL_MINIMO =
        new BigDecimal(400.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal AV_DIAMANTE_VALOR_COMERCIAL_MEDIO =
        new BigDecimal(500.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal AV_DIAMANTE_VALOR_COMERCIAL_MAXIMO =
        new BigDecimal(600.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal AV_DIAMANTE_VALOR_MINIMO =
        new BigDecimal(572.00D).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(QUILATES).setScale(2);
    private static final BigDecimal AV_DIAMANTE_VALOR_MEDIO =
        new BigDecimal(715.00D).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(QUILATES).setScale(2);
    private static final BigDecimal AV_DIAMANTE_VALOR_MAXIMO =
        new BigDecimal(858.00D).setScale(2, BigDecimal.ROUND_HALF_UP).multiply(QUILATES).setScale(2);


    // UTILIZADAS PARA EL AVALÚO DE PIEZAS COMPLEMENTARIAS.
    private static final BigDecimal AV_COMPLEMENTARIO =
        new BigDecimal(110.00D).setScale(2, BigDecimal.ROUND_HALF_UP);


    /**
     * Referencia hacia el repositorio de políticas de castigo.
     */
    @Mock
    private PoliticasCastigoRepository politicasCastigoRepository;

    /**
     * Referencia al conector TablasDeReferenciaAlhajas.
     */
    @Mock
    private TablasDeReferenciaAlhajas tablasDeReferenciaAlhajas;

    /**
     * Referencia al conector TablasDeReferenciaDiamantes.
     */
    @Mock
    private TablasDeReferenciaDiamantes tablasDeReferenciaDiamantes;

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
     * Referencia a la fábrica de {@link Prenda}.
     */
    @Inject
    private PrendaFactory prendaFactory;

    /**
     * Referencia a la fábrica de {@link PoliticasCastigo}.
     */
    @Inject
    private PoliticasCastigoFactory politicasCastigoFactory;

    /**
     * Referencia a la fábrica de factores de políticas de castigo.
     */
    @Inject
    private FactorPoliticasCastigoFactory factorPoliticasCastigoFactory;



    // METODOS

    /**
     * Constructor.
     */
    public PrendaUTest() {
        super();
    }

    /**
     * Configuración inicial.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(alhajaFactory, "tablasDeReferenciaAlhajas", tablasDeReferenciaAlhajas);
        ReflectionTestUtils.setField(diamanteFactory, "tablasDeReferenciaDiamantes", tablasDeReferenciaDiamantes);
        ReflectionTestUtils.setField(prendaFactory, "politicasCastigoRepository", politicasCastigoRepository);
    }

    /**
     * Utilizado para crear una entidad Prenda con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 1 - Alhaja
     * 1 - Diamante
     * 1 - Complementario
     */
    @Test
    public void crearPrendaTest01() {
        Alhaja alhaja =
            alhajaFactory.create(getBuilderAlhaja(METAL, COLOR_A, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO_TOTAL));

        Diamante diamante =
            diamanteFactory.create(getBuilderDiamante(NUM_PIEZAS_1, CORTE, COLOR_D, CLARIDAD, QUILATES, CERTIFICADO, null));

        Complementario complementario =
            complementarioFactory.create(getBuilderComplementario(NUM_PIEZAS_1, VALOR_EXPERTO_TOTAL));

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(alhaja);
        piezas.add(diamante);
        piezas.add(complementario);

        Prenda prenda =
            prendaFactory.create(getBuilder(piezas, "EX"));

        BigDecimalConsumidor valorGramoOro = getBigDecimalConsumidor(
            AV_ALHAJA_VALOR_GRAMO_ORO);
        when(tablasDeReferenciaAlhajas.obtenerValorGramoOro(any(Alhaja.class))).thenReturn(valorGramoOro);

        BigDecimalConsumidor factor = getBigDecimalConsumidor(
            AV_ALHAJA_FACTOR);
        when(tablasDeReferenciaAlhajas.obtenerFactor(any(Alhaja.class))).thenReturn(factor);

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            AV_DIAMANTE_VALOR_COMERCIAL_MINIMO, AV_DIAMANTE_VALOR_COMERCIAL_MEDIO, AV_DIAMANTE_VALOR_COMERCIAL_MAXIMO);
        when(tablasDeReferenciaDiamantes.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        BigDecimalConsumidor porcentajeIncremento = getBigDecimalConsumidor(
            AV_DIAMANTE_PORCENTAJE_INCREMENTO);
        when(tablasDeReferenciaDiamantes.obtenerModificador(any(Diamante.class))).thenReturn(porcentajeIncremento);

        PoliticasCastigo politicasCastigo = getPoliticasCastigo(
            PC_FACTOR_DIAMANTE, PC_FACTOR_ALHAJA, PC_FACTOR_COMPLEMENTARIO);
        when(politicasCastigoRepository.consultar()).thenReturn(politicasCastigo);

        Avaluo avaluo = prenda.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(AV_ALHAJA
            .add(AV_DIAMANTE_VALOR_MINIMO)
            .add(AV_COMPLEMENTARIO), avaluo.valorMinimo());
        assertEquals(AV_ALHAJA
            .add(AV_DIAMANTE_VALOR_MEDIO)
            .add(AV_COMPLEMENTARIO), avaluo.valorPromedio());
        assertEquals(AV_ALHAJA
            .add(AV_DIAMANTE_VALOR_MAXIMO)
            .add(AV_COMPLEMENTARIO), avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Prenda con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 1 - Alhaja
     * 0 - Diamante
     * 0 - Complementario
     */
    @Test
    public void crearPrendaTest02() {
        Alhaja alhaja =
            alhajaFactory.create(getBuilderAlhaja(METAL, COLOR_A, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO_TOTAL));

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(alhaja);

        Prenda prenda =
            prendaFactory.create(getBuilder(piezas, "BN"));

        BigDecimalConsumidor valorGramoOro = getBigDecimalConsumidor(
            AV_ALHAJA_VALOR_GRAMO_ORO);
        when(tablasDeReferenciaAlhajas.obtenerValorGramoOro(any(Alhaja.class))).thenReturn(valorGramoOro);

        BigDecimalConsumidor factor = getBigDecimalConsumidor(
            AV_ALHAJA_FACTOR);
        when(tablasDeReferenciaAlhajas.obtenerFactor(any(Alhaja.class))).thenReturn(factor);

        PoliticasCastigo politicasCastigo = getPoliticasCastigo(
            PC_FACTOR_DIAMANTE, PC_FACTOR_ALHAJA, PC_FACTOR_COMPLEMENTARIO);
        when(politicasCastigoRepository.consultar()).thenReturn(politicasCastigo);

        Avaluo avaluo = prenda.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(AV_ALHAJA, avaluo.valorMinimo());
        assertEquals(AV_ALHAJA, avaluo.valorPromedio());
        assertEquals(AV_ALHAJA, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Prenda con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 0 - Alhaja
     * 1 - Diamante
     * 0 - Complementario
     */
    @Test
    public void crearPrendaTest03() {
        Diamante diamante =
            diamanteFactory.create(getBuilderDiamante(NUM_PIEZAS_1, CORTE, COLOR_D, CLARIDAD, QUILATES, CERTIFICADO, null));

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(diamante);

        Prenda prenda =
            prendaFactory.create(getBuilder(piezas, "RE"));

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            AV_DIAMANTE_VALOR_COMERCIAL_MINIMO, AV_DIAMANTE_VALOR_COMERCIAL_MEDIO, AV_DIAMANTE_VALOR_COMERCIAL_MAXIMO);
        when(tablasDeReferenciaDiamantes.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        BigDecimalConsumidor porcentajeIncremento = getBigDecimalConsumidor(
            AV_DIAMANTE_PORCENTAJE_INCREMENTO);
        when(tablasDeReferenciaDiamantes.obtenerModificador(any(Diamante.class))).thenReturn(porcentajeIncremento);

        PoliticasCastigo politicasCastigo = getPoliticasCastigo(
            PC_FACTOR_DIAMANTE, PC_FACTOR_ALHAJA, PC_FACTOR_COMPLEMENTARIO);
        when(politicasCastigoRepository.consultar()).thenReturn(politicasCastigo);

        Avaluo avaluo = prenda.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(AV_DIAMANTE_VALOR_MINIMO, avaluo.valorMinimo());
        assertEquals(AV_DIAMANTE_VALOR_MEDIO, avaluo.valorPromedio());
        assertEquals(AV_DIAMANTE_VALOR_MAXIMO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Prenda con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 0 - Alhaja
     * 0 - Diamante
     * 1 - Complementario
     */
    @Test
    public void crearPrendaTest04() {
        Complementario complementario =
            complementarioFactory.create(getBuilderComplementario(NUM_PIEZAS_1, VALOR_EXPERTO_TOTAL));

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(complementario);

        Prenda prenda =
            prendaFactory.create(getBuilder(piezas, "EX"));

        PoliticasCastigo politicasCastigo = getPoliticasCastigo(
            PC_FACTOR_DIAMANTE, PC_FACTOR_ALHAJA, PC_FACTOR_COMPLEMENTARIO);
        when(politicasCastigoRepository.consultar()).thenReturn(politicasCastigo);

        Avaluo avaluo = prenda.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(AV_COMPLEMENTARIO, avaluo.valorMinimo());
        assertEquals(AV_COMPLEMENTARIO, avaluo.valorPromedio());
        assertEquals(AV_COMPLEMENTARIO, avaluo.valorMaximo());
    }

    /**
     * Utilizado para crear una entidad Prenda con las siguientes características:
     *
     * LISTA DE PIEZAS - NULA
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPrendaTest05() {
        List<Pieza> piezas = null;

        prendaFactory.create(getBuilder(piezas, "BN"));
    }

    /**
     * Utilizado para crear una entidad Prenda con las siguientes características:
     *
     * LISTA DE PIEZAS - VACIA
     */
    @Test(expected = IllegalArgumentException.class)
    public void crearPrendaTest06() {
        List<Pieza> piezas = new ArrayList<>();

        prendaFactory.create(getBuilder(piezas, "RE"));
    }

    /**
     * Utilizado para crear una entidad Prenda con las siguientes características:
     *
     * LISTA DE PIEZAS - NO NULA
     * 1 - Alhaja
     * 2 - Diamante
     * 2 - Complementario
     */
    @Test
    public void crearPrendaTest07() {
        Alhaja alhaja =
            alhajaFactory.create(getBuilderAlhaja(METAL, COLOR_A, CALIDAD, RANGO, PESO, INCREMENTO, DESPLAZAMIENTO, VALOR_EXPERTO_TOTAL));

        Diamante diamante =
            diamanteFactory.create(getBuilderDiamante(NUM_PIEZAS_2, CORTE, COLOR_D, CLARIDAD, QUILATES, CERTIFICADO, null));

        Complementario complementario =
            complementarioFactory.create(getBuilderComplementario(NUM_PIEZAS_2, VALOR_EXPERTO_UNITARIO));

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(alhaja);
        piezas.add(diamante);
        piezas.add(complementario);

        Prenda prenda =
            prendaFactory.create(getBuilder(piezas, "EX"));

        BigDecimalConsumidor valorGramoOro = getBigDecimalConsumidor(
            AV_ALHAJA_VALOR_GRAMO_ORO);
        when(tablasDeReferenciaAlhajas.obtenerValorGramoOro(any(Alhaja.class))).thenReturn(valorGramoOro);

        BigDecimalConsumidor factor = getBigDecimalConsumidor(
            AV_ALHAJA_FACTOR);
        when(tablasDeReferenciaAlhajas.obtenerFactor(any(Alhaja.class))).thenReturn(factor);

        ValorComercialConsumidor valorComercial = getValorComercialConsumidor(
            AV_DIAMANTE_VALOR_COMERCIAL_MINIMO, AV_DIAMANTE_VALOR_COMERCIAL_MEDIO, AV_DIAMANTE_VALOR_COMERCIAL_MAXIMO);
        when(tablasDeReferenciaDiamantes.obtenerValorComercial(any(Diamante.class))).thenReturn(valorComercial);

        BigDecimalConsumidor porcentajeIncremento = getBigDecimalConsumidor(
            AV_DIAMANTE_PORCENTAJE_INCREMENTO);
        when(tablasDeReferenciaDiamantes.obtenerModificador(any(Diamante.class))).thenReturn(porcentajeIncremento);

        PoliticasCastigo politicasCastigo = getPoliticasCastigo(
            PC_FACTOR_DIAMANTE, PC_FACTOR_ALHAJA, PC_FACTOR_COMPLEMENTARIO);
        when(politicasCastigoRepository.consultar()).thenReturn(politicasCastigo);

        Avaluo avaluo = prenda.valuar();
        assertNotNull(avaluo);
        assertNotNull(avaluo.valorMinimo());
        assertNotNull(avaluo.valorPromedio());
        assertNotNull(avaluo.valorMaximo());
        assertEquals(AV_ALHAJA
            .add(AV_DIAMANTE_VALOR_MINIMO.multiply(new BigDecimal(NUM_PIEZAS_2)))
            .add(AV_COMPLEMENTARIO.multiply(new BigDecimal(NUM_PIEZAS_2))), avaluo.valorMinimo());
        assertEquals(AV_ALHAJA
            .add(AV_DIAMANTE_VALOR_MEDIO.multiply(new BigDecimal(NUM_PIEZAS_2)))
            .add(AV_COMPLEMENTARIO.multiply(new BigDecimal(NUM_PIEZAS_2))), avaluo.valorPromedio());
        assertEquals(AV_ALHAJA
            .add(AV_DIAMANTE_VALOR_MAXIMO.multiply(new BigDecimal(NUM_PIEZAS_2)))
            .add(AV_COMPLEMENTARIO.multiply(new BigDecimal(NUM_PIEZAS_2))), avaluo.valorMaximo());
    }

    /**
     * Metodo auxiliar utilizado para crear el builder de Prenda a partir de sus atributos.
     *
     * @param piezas Lista de piezas de las que se compone la prenda.
     * @return El builder creado.
     */
    private Prenda.Builder getBuilder(final List<Pieza> piezas, final String condicionesFisicas) {
        return new Prenda.Builder() {

            @Override
            public List<Pieza> getPiezas() {
                return piezas;
            }

            @Override
            public CondicionPrendaVO getCondicionFisica() {
                return new CondicionPrendaVO(condicionesFisicas);
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
     * @param color El tipo de color del diamante.
     * @param claridad El tipo de claridad del diamante.
     * @param quilates El valor en quilates del diamante.
     * @param certificadoDiamante El valor del certificado del diamante.
     * @param valorExperto El valor experto para la pieza en particular.
     * @return El builder creado.
     */
    private Diamante.Builder getBuilderDiamante(final int numeroDePiezas,
                                                final String corte,
                                                final String color,
                                                final String claridad,
                                                final BigDecimal quilates,
                                                final String certificadoDiamante,
                                                final ValorExperto valorExperto) {
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

    /**
     * Metodo auxiliar utilizado para crear el valor comercial que devolvería el conector TablasDeReferenciaDiamantes.
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
     * Metodo auxiliar utilizado para crear un BigDecimalConsumidor.
     *
     * @param valor El valor del BigDecimalConsumidor.
     * @return El BigDecimalConsumidor creado.
     */
    private BigDecimalConsumidor getBigDecimalConsumidor(final BigDecimal valor) {
        return new BigDecimalConsumidor() {

            @Override
            public BigDecimal getValor() {
                return valor;
            }

        };
    }

    /**
     * Metodo auxiliar utilizado para crear una PoliticasCastigo.
     *
     * @param factorDiamante El factor para la pieza de tipo Diamante.
     * @param factorAlhaja El factor para la pieza de tipo Alhaja.
     * @param factorComplemento El factor para la pieza de tipo Complemento.
     * @return La PoliticasCastigo creada.
     */
    private PoliticasCastigo getPoliticasCastigo(
        BigDecimal factorDiamante,
        BigDecimal factorAlhaja,
        BigDecimal factorComplemento) {
        return politicasCastigoFactory.crearCon(getFactoresPoliticasCastigo(
            factorDiamante, factorAlhaja, factorComplemento), DateTime.now());
    }

    /**
     * Metodo auxiliar utilizado para crear los factores de políticas de castigo.
     *
     * @param factorDiamante El factor para la pieza de tipo Diamante.
     * @param factorAlhaja El factor para la pieza de tipo Alhaja.
     * @param factorComplemento El factor para la pieza de tipo Complemento.
     * @return Los factores de políticas de castigo.
     */
    private Map<Class<? extends Pieza>, BigDecimal> getFactoresPoliticasCastigo(
        BigDecimal factorDiamante,
        BigDecimal factorAlhaja,
        BigDecimal factorComplemento) {
        return factorPoliticasCastigoFactory.crearCon(factorDiamante, factorAlhaja, factorComplemento);
    }

}
