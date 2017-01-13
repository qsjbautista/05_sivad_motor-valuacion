/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import com.codahale.metrics.annotation.Timed;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.PoliticaCastigoNoEncontradaException;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AvaluoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
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
     * Referencia hacia el repositorio de políticas de castigo.
     */
    private PoliticasCastigoRepository politicasCastigoRepository;

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

    }



    // METODOS

    /**
     * Constructor.
     *
     * @param builder Referencia al objeto que contiene los datos necesarios para construir la entidad.
     * @param politicasCastigoRepository Referencia hacia el repositorio de políticas de castigo.
     */
    private Prenda(Builder builder, PoliticasCastigoRepository politicasCastigoRepository) {
        super();

        this.piezas = builder.getPiezas();
        this.politicasCastigoRepository = politicasCastigoRepository;
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


        // SE CONSULTAN LAS POLÍTICAS DE CASTIGO.
        PoliticasCastigo politicasCastigo = null;

        try {
            politicasCastigo = politicasCastigoRepository.consultar();
        } catch (PoliticaCastigoNoEncontradaException e) {
            LOGGER.warn("No existen políticas de castigo configuradas.");
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error al consultar las políticas de castigo.");
            throw e;
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


        return avaluoTotal;
    }

    /**
     * Metodo auxiliar utilizado para sumar el contenido de dos avalúos, considerando que el avalúo 1 pudiera ser nulo.
     *
     * @param avaluoUno El avalúo uno.
     * @param avaluoDos El avalúo dos.
     * @return Un avalúo con la suma de los dos.
     */
    private Avaluo sumarAvaluos(Avaluo avaluoUno, Avaluo avaluoDos) {
        LOGGER.debug(">> sumarAvaluos. " +
            "Avaluo 1: [" + ((avaluoUno != null) ? avaluoUno.toString() : "null") + "], " +
            "Avaluo 2: [" + ((avaluoDos != null) ? avaluoDos.toString() : "null") + "].");

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

        LOGGER.debug("<< sumarAvaluos. " +
            "Avaluo Result: [" + ((result != null) ? result.toString() : "null") + "].");

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
        LOGGER.debug(">> aplicarPoliticaCastigo. " +
            "Avaluo: [" + ((avaluo != null) ? avaluo.toString() : "null") + "], " +
            "Factor: [" + ((factor != null) ? factor.toString() : "null") + "].");

        Avaluo result = AvaluoFactory.crearCon(
            avaluo.valorMinimo().multiply(factor),
            avaluo.valorPromedio().multiply(factor),
            avaluo.valorMaximo().multiply(factor));

        LOGGER.debug("<< aplicarPoliticaCastigo. " +
            "Avaluo Result: [" + ((result != null) ? result.toString() : "null") + "].");

        return result;
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
