/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object con la información del valor experto.
 *
 * @author ngonzalez
 */
public class ValorExperto {

    /**
     * El valor del experto.
     */
    private BigDecimal valor;

    /**
     * El tipo de valor experto (Unitario o Total).
     */
    private TipoEnum tipo;

    /**
     * Enum que define los tipos para el valor experto.
     */
    public enum TipoEnum {

        UNITARIO("Unitario"),
        TOTAL("Total");

        /**
         * Tipo de valor experto.
         */
        private String tipo;


        // METODOS

        /**
         * Constructor.
         *
         * @param tipo El tipo de valor experto.
         */
        TipoEnum(String tipo) {
            this.tipo = tipo;
        }


        // GETTERS

        public String getTipo() {
            return tipo;
        }
    }



    // METODOS

    /**
     * Constructor.
     *
     * @param valor El valor del experto.
     * @param tipo El tipo de valor experto.
     */
    public ValorExperto(BigDecimal valor, ValorExperto.TipoEnum tipo) {
        this.valor = valor;
        this.tipo = tipo;
    }



    // GETTERS

    public BigDecimal getValor() {
        return valor;
    }

    public TipoEnum getTipo() {
        return tipo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ValorExperto{" +
            "valor=" + valor != null ? valor.toString() : "null" +
            ", tipo=" + tipo != null ? tipo.getTipo() : "null" +
            '}';
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

        ValorExperto that = (ValorExperto) o;

        return Objects.equals(valor, that.valor) &&
            tipo == that.tipo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(valor, tipo);
    }
}
