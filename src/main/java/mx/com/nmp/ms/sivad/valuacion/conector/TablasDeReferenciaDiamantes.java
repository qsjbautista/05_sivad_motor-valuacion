/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ValorComercialConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasDiamanteProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CertificadoDiamanteProveedor;

/**
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface TablasDeReferenciaDiamantes {
    /**
     * Permite obtener el porcentaje de modificación de valor correspondiente a cada tipo de certificado de diamantes.
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return BigDecimalConsumidor Objeto que contiene la informción de la respuesta.
     */
    BigDecimalConsumidor obtenerModificador(@NotNull final CertificadoDiamanteProveedor proveedor);

    /**
     * Permite obtener el valor comercial de un diamante con base en las características:
     *
     * <lu>
     *  <li>Corte</li>
     *	<li>Color</li>
     *	<li>Claridad</li>
     *	<li>Quilates (Ct.)</li>
     *	<li>Quilates Desde</li>
     *  <li>Quilates Hasta</li>
     * </lu>
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return ValorComercialConsumidor Objeto que contiene la informción de la respuesta.
     */
    ValorComercialConsumidor obtenerValorComercial(@NotNull final CaracteristicasDiamanteProveedor proveedor);
}
