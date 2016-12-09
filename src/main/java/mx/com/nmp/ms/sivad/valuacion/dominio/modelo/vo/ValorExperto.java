/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo;

import java.math.BigDecimal;

/**
 * Value Object con la información del valor experto.
 *
 * @author ngonzalez
 */
public class ValorExperto {

    /**
     * El valor del experto.
     */
    private BigDecimal valorExperto;

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
     * @param valorExperto El valor del experto.
     * @param tipo El tipo de valor experto.
     */
    public ValorExperto(BigDecimal valorExperto, ValorExperto.TipoEnum tipo) {
        this.valorExperto = valorExperto;
        this.tipo = tipo;
    }



    // GETTERS

    public BigDecimal getValorExperto() {
        return valorExperto;
    }

    public TipoEnum getTipo() {
        return tipo;
    }

}
