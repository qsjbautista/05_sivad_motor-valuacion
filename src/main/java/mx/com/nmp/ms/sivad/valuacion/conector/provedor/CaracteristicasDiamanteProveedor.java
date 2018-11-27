/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.provedor;

import java.math.BigDecimal;

/**
 * Contrato que deben seguir los proveedores de datos de Características del diamante.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface CaracteristicasDiamanteProveedor {
    /**
     * Recupera el Corte del diamante.
     *
     * @return Corte del diamante.
     */
    String getCorte();

    /**
     * Recupera el suborte del diamante.
     *
     * @return Subcorte del diamante.
     */
    String getSubcorte();

    /**
     * Recupera el Color del diamante.
     *
     * @return Color del diamante.
     */
    String getColor();

    /**
     * Recupera la Claridad del diamante.
     *
     * @return Claridad del diamante.
     */
    String getClaridad();

    /**
     * Recupera los Quilates del diamante.
     *
     * @return Quilates del diamante.
     */
    BigDecimal getQuilates();

    /**
     * Recupera los Quilates inferior del diamante.
     *
     * @return Quilates inferior del diamante.
     */
    BigDecimal getQuilatesDesde();

    /**
     * Recupera los Quilates superior del diamante.
     *
     * @return Quilates superior del diamante.
     */
    BigDecimal getQuilatesHasta();
}
