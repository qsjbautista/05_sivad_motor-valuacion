/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object que contiene los factores de castigo aplicables al diamante, alhaja y complemento.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class FactorPoliticasCastigo {
    /**
     * Factor de castigo para diamantes.
     */
    private BigDecimal factorDiamante;

    /**
     * Factor de castigo para alhajas.
     */
    private BigDecimal factorAlhaja;

    /**
     * Factor de castigo para complementos.
     */
    private BigDecimal factorComplemento;

    /**
     * Representa la interface publica usada para crear objetos tipo {@link FactorPoliticasCastigo}
     * Nos brinda el contrato que se debe cumplir para crear una instancia de la clase {@link FactorPoliticasCastigo}
     */
    public interface Builder {
        /**
         * Provee el factor de castigo para diamantes.
         *
         * @return Factor de castigo para diamantes.
         */
        BigDecimal getFactorDiamante();

        /**
         * Provee el factor de castigo para alhajas.
         *
         * @return Factor de castigo para alhajas.
         */
        BigDecimal getFactorAlhaja();

        /**
         * Provee el Factor de castigo para complementos.
         *
         * @return Factor de castigo para complementos.
         */
        BigDecimal getFactorComplemento();
    }

    /**
     * Constructor. Privado para que solo la fabrica pueda crear instancias.
     *
     * @param builder Objeto con los datos necesarios para construir la instancia.
     */
    private FactorPoliticasCastigo(Builder builder) {
        super();

        factorDiamante = builder.getFactorDiamante();
        factorAlhaja = builder.getFactorAlhaja();
        factorComplemento = builder.getFactorComplemento();
    }

    /**
     * Recupera el factor de castigo para diamantes.
     *
     * @return Factor de castigo para diamantes.
     */
    public BigDecimal getFactorDiamante() {
        return factorDiamante;
    }

    /**
     * Recupera el factor de castigo para alhajas.
     *
     * @return Factor de castigo para alhajas.
     */
    public BigDecimal getFactorAlhaja() {
        return factorAlhaja;
    }

    /**
     * Recupera el Factor de castigo para complementos.
     *
     * @return Factor de castigo para complementos.
     */
    public BigDecimal getFactorComplemento() {
        return factorComplemento;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FactorPoliticasCastigo that = (FactorPoliticasCastigo) o;

        return Objects.equals(factorDiamante, that.factorDiamante) &&
            Objects.equals(factorAlhaja, that.factorAlhaja) &&
            Objects.equals(factorComplemento, that.factorComplemento);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(factorDiamante, factorAlhaja, factorComplemento);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("FactorPoliticasCastigo{factorDiamante= %s, factorAlhaja=%s, factorComplemento=%s}",
            factorDiamante, factorAlhaja, factorComplemento);
    }
}
