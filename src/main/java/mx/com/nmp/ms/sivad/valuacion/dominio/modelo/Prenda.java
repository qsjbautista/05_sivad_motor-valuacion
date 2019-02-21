/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import com.codahale.metrics.annotation.Timed;
import mx.com.nmp.ms.sivad.valuacion.conector.parametros.ParametrosConector;
import mx.com.nmp.ms.sivad.valuacion.conector.parametros.FiltroParametro;
import mx.com.nmp.ms.sivad.valuacion.conector.parametros.TipoParametro;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ModificadorCondicionPrendaNoEncontradoException;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.PoliticaCastigoNoEncontradaException;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AvaluoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.CondicionPrendaVO;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.ModificadorCondicionPrendaRepository;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que implementa la interface {@link PiezaValuable}, ésta clase representa una Prenda y encapsula
 * la lógica para valuarse.
 *
 * @author ngonzalez
 */
public class Prenda implements PiezaValuable {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Prenda.class);

    /**
     * Lista de piezas de tipo {@link Pieza}.
     */
    private List<Pieza> piezas;

    /**
     * Avalúo de la prenda.
     */
    private Avaluo avaluo;

    /**
     * Identificador de la condición fisica de la prenda.
     */
    private CondicionPrendaVO condicionFisica;

    /**
     * Referencia hacia el repositorio de políticas de castigo.
     */
    private PoliticasCastigoRepository politicasCastigoRepository;

    /**
     * Referencia hacia el repositorio modificador por condiciones fisicas de la prenda.
     */
    private ModificadorCondicionPrendaRepository condicionPrendaRepository;

    /**
     * Referencia hacia el repositorio de parametros.
     */
    private ParametrosConector parametrosConector;

    /**
     * Abreviatura del subramo
     */
    private String subramo;

    /**
     * Identificador de la sucursal
     */
    private Long sucursal;

    /**
     * Interface que define el contrato para crear entidades de tipo {@link Prenda}.
     */
    public interface Builder {

        /**
         * Permite obtener la lista de piezas de tipo {@link Pieza}.
         *
         * @return La lista de piezas de tipo {@link Pieza}.
         */
        List<Pieza> getPiezas();

        /**
         * Recupera el valor de {@code condicionFisica}
         *
         * @return Valor de {@code condicionFisica}
         */
        CondicionPrendaVO getCondicionFisica();

        /**
         * Recupera el valor de {@code subramo}
         *
         * @return Valor de {@code subramo}
         */
        String getSubramo();

        /**
         * Recupera el valor de {@code sucursal}
         *
         * @return Valor de {@code sucursal}
         */
        Long getSucursal();
    }



    // METODOS

    /**
     * Constructor.
     *
     * @param builder Referencia al objeto que contiene los datos necesarios para construir la entidad.
     * @param politicasCastigoRepository Referencia hacia el repositorio de políticas de castigo.
     */
    private Prenda(Builder builder, PoliticasCastigoRepository politicasCastigoRepository,
                   ModificadorCondicionPrendaRepository condicionPrendaRepository, ParametrosConector parametrosConector) {
        super();

        this.piezas = builder.getPiezas();
        this.condicionFisica = builder.getCondicionFisica();
        this.politicasCastigoRepository = politicasCastigoRepository;
        this.condicionPrendaRepository = condicionPrendaRepository;
        this.parametrosConector = parametrosConector;
        this.subramo = builder.getSubramo();
        this.sucursal = builder.getSucursal();
    }

    /**
     * Permite realizar la valuación de la {@link Prenda}.
     *
     * @return El avalúo de la pieza del tipo {@link Prenda}.
     */
    @Override
    @Timed
    public Avaluo valuar() {
        LOGGER.info(">> valuar");

        // MAPA DE ESTRATEGIA DE AVALÚOS POR TIPO DE PIEZA.
        Map<Class<? extends Pieza>, Avaluo> mapaEstrategiaAvaluos = new HashMap<>();


        // SE VALÚAN LAS PIEZAS.
        for (Pieza pieza : piezas) {
            mapaEstrategiaAvaluos.put(pieza.getClass(),
                sumarAvaluos(mapaEstrategiaAvaluos.get(pieza.getClass()), pieza.valuar()));
        }


        // SE VERIFICA PARAMETRO POR SUBRAMO QUE DETERMINA SI APLICA POLITICA
        Float aplicarPoliticasCastigo = parametrosConector.recuperarValorParametro(TipoParametro.SUCURSAL_SUBRAMO,
            "AP", new FiltroParametro("subramo", subramo), new FiltroParametro("sucursal", sucursal.toString()));

        PoliticasCastigo politicasCastigo = null;

        if (aplicarPoliticasCastigo != null && aplicarPoliticasCastigo == 1F) {

            // SE CONSULTAN LAS POLÍTICAS DE CASTIGO.

            try {
                politicasCastigo = politicasCastigoRepository.consultar(subramo);
            } catch (PoliticaCastigoNoEncontradaException e) {
                LOGGER.warn("No existen políticas de castigo configuradas.");
            } catch (Exception e) {
                LOGGER.error("Ocurrió un error al consultar las políticas de castigo.");
                throw e;
            }
        }


        // SE CREA EL AVALÚO CON BASE EN LOS VALORES DEFINITIVOS.
        Avaluo avaluoTotal = null;

        for (Map.Entry<Class<? extends Pieza>, Avaluo> entry : mapaEstrategiaAvaluos.entrySet()) {

            // EN CASO DE QUE EXISTAN POLÍTICAS DE CASTIGO.
            if (!ObjectUtils.isEmpty(politicasCastigo) && !ObjectUtils.isEmpty(politicasCastigo.getFactores())) {
                mapaEstrategiaAvaluos.put(entry.getKey(),
                    aplicarPoliticaCastigo(entry.getValue(), politicasCastigo.getFactores().get(entry.getKey())));
            }

            avaluoTotal = sumarAvaluos(avaluoTotal, entry.getValue());
        }

        for (Pieza pieza : piezas) {
            if (!ObjectUtils.isEmpty(politicasCastigo) && !ObjectUtils.isEmpty(politicasCastigo.getFactores())) {
                pieza.setAvaluoPoliticas(aplicarPoliticaCastigo(pieza.getAvaluo(), politicasCastigo.getFactores().get(pieza.getClass())));
            }
        }

        if (!ObjectUtils.isEmpty(condicionFisica)) {
            return aplicarPorcentajeCondidicionesFisicas(avaluoTotal);
        } else {
            return avaluoTotal;
        }
    }

    /**
     * Metodo auxiliar utilizado para sumar el contenido de dos avalúos, considerando que el avalúo 1 pudiera ser nulo.
     *
     * @param avaluoUno El avalúo uno.
     * @param avaluoDos El avalúo dos.
     * @return Un avalúo con la suma de los dos.
     */
    private Avaluo sumarAvaluos(Avaluo avaluoUno, Avaluo avaluoDos) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">> sumarAvaluos. " +
                "Avaluo 1: [" + ((avaluoUno != null) ? avaluoUno.toString() : "null") + "], " +
                "Avaluo 2: [" + ((avaluoDos != null) ? avaluoDos.toString() : "null") + "].");
        }

        if (avaluoUno == null) {
            return AvaluoFactory.crearCon(
                avaluoDos.valorMinimo(),
                avaluoDos.valorPromedio(),
                avaluoDos.valorMaximo());
        }

        Avaluo result = AvaluoFactory.crearCon(
            avaluoUno.valorMinimo().add(avaluoDos.valorMinimo()),
            avaluoUno.valorPromedio().add(avaluoDos.valorPromedio()),
            avaluoUno.valorMaximo().add(avaluoDos.valorMaximo()));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("<< sumarAvaluos. " +
                "Avaluo Result: [" + ((result != null) ? result.toString() : "null") + "].");
        }

        return result;
    }

    /**
     * Metodo auxiliar utilizado para aplicar la política de castigo al avalúo.
     *
     * @param avaluo El avalúo.
     * @param factor El factor de la política de castigo que aplica para el avalúo.
     * @return El avalúo con el factor de castigo aplicado.
     */
    private Avaluo aplicarPoliticaCastigo(Avaluo avaluo, BigDecimal factor) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(">> aplicarPoliticaCastigo. " +
                "Avaluo: [" + ((avaluo != null) ? avaluo.toString() : "null") + "], " +
                "Factor: [" + ((factor != null) ? factor.toString() : "null") + "].");
        }

        Avaluo result = AvaluoFactory.crearCon(
            avaluo.valorMinimo().multiply(factor),
            avaluo.valorPromedio().multiply(factor),
            avaluo.valorMaximo().multiply(factor));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("<< aplicarPoliticaCastigo. " +
                "Avaluo Result: [" + ((result != null) ? result.toString() : "null") + "].");
        }

        return result;
    }

    /**
     * Metodo auxiliar utilizado para aplicar el factor correspondiente por condiciones fisicas de la prenda.
     *
     * @param avaluoTotal El avalúo.
     *
     * @return El avalúo con el factor aplicado.
     */
    private Avaluo aplicarPorcentajeCondidicionesFisicas(Avaluo avaluoTotal) {
        LOGGER.debug(">> aplicarPorcentajeCondidicionesFisicas. [{}]", avaluoTotal);
        BigDecimal factor = BigDecimal.ONE;

        try {
            ModificadorCondicionPrenda modificador = condicionPrendaRepository
                .consultarModificadorCondicionPrendaVigente(condicionFisica);
            factor = modificador.getFactor();
            LOGGER.info("Factor por condiciones recuperado [{}]", factor);
        } catch (ModificadorCondicionPrendaNoEncontradoException e) {
            LOGGER.warn(String
                    .format("No existe el modificador condicion prenda [%s]", condicionFisica.getCondicionPrenda()),
                e);
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error inesperado al consultar el modificador de codición fisica de la prenda", e);
            throw e;
        }

        BigDecimal valorMinimo = avaluoTotal.valorMinimo().multiply(factor);
        LOGGER.info("Aplicando factor {} * {} = {} ", avaluoTotal.valorMinimo(), factor, valorMinimo);

        BigDecimal valorPromedio = avaluoTotal.valorPromedio().multiply(factor);
        LOGGER.info("Aplicando factor {} * {} = {} ", avaluoTotal.valorPromedio(), factor, valorPromedio);

        BigDecimal valorMaximo = avaluoTotal.valorMaximo().multiply(factor);
        LOGGER.info("Aplicando factor {} * {} = {} ", avaluoTotal.valorMaximo(), factor, valorMaximo);


        Avaluo resultado = AvaluoFactory.crearCon(valorMinimo, valorPromedio, valorMaximo);

        LOGGER.debug("<< aplicarPorcentajeCondidicionesFisicas. Result: [{}]", resultado);

        return resultado;
    }



    // GETTERS

    public List<Pieza> getPiezas() {
        return piezas;
    }

    public Avaluo getAvaluo() {
        if (avaluo == null) {
            avaluo = valuar();
        }

        return avaluo;
    }

}
