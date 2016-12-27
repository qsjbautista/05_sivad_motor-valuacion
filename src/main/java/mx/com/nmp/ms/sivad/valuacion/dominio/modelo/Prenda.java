/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import com.codahale.metrics.annotation.Timed;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AvaluoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.List;

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

        // SE INICIALIZAN LOS AVALÚOS.
        Avaluo avaluoTotal = AvaluoFactory.crearCon(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        Avaluo avaluoAlhajas = AvaluoFactory.crearCon(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        Avaluo avaluoDiamantes = AvaluoFactory.crearCon(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        Avaluo avaluoComplementarios = AvaluoFactory.crearCon(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);

        // SE VALÚAN LAS PIEZAS.
        if (!ObjectUtils.isEmpty(piezas)) {
            for (Pieza pieza : piezas) {
                if (pieza instanceof Alhaja) {
                    avaluoAlhajas = sumarAvaluos(avaluoAlhajas, pieza.valuar());
                }

                if (pieza instanceof Diamante) {
                    avaluoDiamantes = sumarAvaluos(avaluoDiamantes, pieza.valuar());
                }

                if (pieza instanceof Complementario) {
                    avaluoComplementarios = sumarAvaluos(avaluoComplementarios, pieza.valuar());
                }
            }

            // EN CASO DE QUE EXISTAN POLÍTICAS DE CASTIGO.
            PoliticasCastigo politicasCastigo = politicasCastigoRepository.consultar();

            if (!ObjectUtils.isEmpty(politicasCastigo) &&
                !ObjectUtils.isEmpty(politicasCastigo.getFactorPoliticasCastigo())) {

                if (!ObjectUtils.isEmpty(politicasCastigo.getFactorPoliticasCastigo().getFactorAlhaja())) {
                    avaluoAlhajas = aplicarPoliticaCastigo(avaluoAlhajas,
                        politicasCastigo.getFactorPoliticasCastigo().getFactorAlhaja());
                }

                if (!ObjectUtils.isEmpty(politicasCastigo.getFactorPoliticasCastigo().getFactorDiamante())) {
                    avaluoDiamantes = aplicarPoliticaCastigo(avaluoDiamantes,
                        politicasCastigo.getFactorPoliticasCastigo().getFactorDiamante());
                }

                if (!ObjectUtils.isEmpty(politicasCastigo.getFactorPoliticasCastigo().getFactorComplemento())) {
                    avaluoComplementarios = aplicarPoliticaCastigo(avaluoComplementarios,
                        politicasCastigo.getFactorPoliticasCastigo().getFactorComplemento());
                }
            }

            // SE CREA EL AVALÚO CON BASE EN LOS VALORES DEFINITIVOS.
            avaluoTotal = sumarAvaluos(avaluoTotal, avaluoAlhajas);
            avaluoTotal = sumarAvaluos(avaluoTotal, avaluoDiamantes);
            avaluoTotal = sumarAvaluos(avaluoTotal, avaluoComplementarios);
        }

        return avaluoTotal;
    }

    /**
     * Metodo auxiliar utilizado para sumar el contenido de dos avalúos.
     *
     * @param avaluoUno El avalúo uno.
     * @param avaluoDos El avalúo dos.
     * @return Un avalúo con la suma de los dos.
     */
    private Avaluo sumarAvaluos(Avaluo avaluoUno, Avaluo avaluoDos) {
        avaluoUno.valorMinimo().add(avaluoDos.valorMinimo());
        avaluoUno.valorPromedio().add(avaluoDos.valorPromedio());
        avaluoUno.valorMaximo().add(avaluoDos.valorMaximo());

        return avaluoUno;
    }

    /**
     * Metodo auxiliar utilizado para aplicar la política de castigo al avalúo.
     *
     * @param avaluo El avalúo.
     * @param factor El factor de la política de castigo que aplica para el avalúo.
     * @return El avalúo con el factor de castigo aplicado.
     */
    private Avaluo aplicarPoliticaCastigo(Avaluo avaluo, BigDecimal factor) {
        avaluo.valorMinimo().multiply(factor);
        avaluo.valorPromedio().multiply(factor);
        avaluo.valorMaximo().multiply(factor);

        return avaluo;
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
