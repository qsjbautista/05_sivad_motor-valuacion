/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.consumidor;

import java.math.BigDecimal;

/**
 * Contrato que se usará para consumir los resultados tipo {@link BigDecimal} obtenidos del Servicio Web.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface BigDecimalConsumidor {
    /**
     * Recupera el valor resultante.
     *
     * @return Valor resultante.
     */
    BigDecimal getValor();
}
