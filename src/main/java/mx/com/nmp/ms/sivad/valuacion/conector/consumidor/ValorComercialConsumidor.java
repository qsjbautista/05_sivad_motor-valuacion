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
 * Contrato se usará para consumir el valor comercial obtenido del Servicio Web.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface ValorComercialConsumidor {
    /**
     * Recupera el Valor comercial mínimo.
     *
     * @return Valor comercial mínimo.
     */
    BigDecimal getValorMinimo();

    /**
     * Recupera el Valor comercial medio.
     *
     * @return Valor comercial medio.
     */
    BigDecimal getValorMedio();

    /**
     * Recupera el Valor comercial máximo.
     *
     * @return Valor comercial máximo.
     */
    BigDecimal getValorMaximo();
}
