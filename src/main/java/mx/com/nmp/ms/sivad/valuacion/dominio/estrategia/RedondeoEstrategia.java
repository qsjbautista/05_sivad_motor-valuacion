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
 * Interface que define el contrato para redondear un {@link BigDecimal}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface RedondeoEstrategia {
    /**
     * Contrato del método que redondea {@link BigDecimal}
     *
     * @param valor Valor a redondear.
     *
     * @return Valor redondeado.
     */
    BigDecimal redondear(BigDecimal valor);
}
