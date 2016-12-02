/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.estrategia;

import java.math.BigDecimal;

/**
 * Colección de estrategias para redondear.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public enum RedondeoEstrategias implements RedondeoEstrategia {
    /**
     * Estrategia de redondeo, usa 2 de dígitos decimales y {@link BigDecimal#ROUND_HALF_UP}
     */
    DOS_DECIMALES_ROUND_HALF_UP {
        /**
         * {@inheritDoc}
         */
        @Override
        public BigDecimal redondear(BigDecimal valor) {
            return redondear(valor, 2, BigDecimal.ROUND_HALF_UP);
        }
    },

    /**
     * Estrategia de redondeo, usa 2 de dígitos decimales y {@link BigDecimal#ROUND_HALF_DOWN}
     */
    DOS_DECIMALES_ROUND_HALF_DOWN {
        /**
         * {@inheritDoc}
         */
        @Override
        public BigDecimal redondear(BigDecimal valor) {
            return redondear(valor, 2, BigDecimal.ROUND_HALF_DOWN);
        }
    };

    /**
     * Redondea un {@link BigDecimal}
     *
     * @param valor Valor a redondear.
     * @param decimales Numero de dígitos decimales.
     * @param redondeo Tipo de redondeo.
     *
     * @return Valor redondeado.
     */
    protected BigDecimal redondear(BigDecimal valor, int decimales, int redondeo) {
        return valor.setScale(decimales, redondeo);
    }
}
