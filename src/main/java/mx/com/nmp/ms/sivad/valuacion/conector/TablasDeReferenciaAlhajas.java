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
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasGramoOroProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.MetalCalidadRangoProveedor;

/**
 * Interface que define el contrato para las fachadas hacia el Micro Servicio de Tablas de Referencia.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface TablasDeReferenciaAlhajas {
    /**
     * Permite obtener el valor por gramo de oro correspondiente a las características del metal de una alhaja.
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return BigDecimalConsumidor Objeto que contiene la informción de la respuesta.
     */
    BigDecimalConsumidor obtenerValorGramoOro(@NotNull final CaracteristicasGramoOroProveedor proveedor);

    /**
     * Permite obtener el factor de modificación aplicable a la relación Metal-Calidad-Rango de una alhaja
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return BigDecimalConsumidor Objeto que contiene la informción de la respuesta.
     */
    BigDecimalConsumidor obtenerFactor(@NotNull final MetalCalidadRangoProveedor proveedor);

    /**
     * Permite obtener el valor por gramo de metales distintos a oro.
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return BigDecimalConsumidor Objeto que contiene la informción de la respuesta.
     */
    BigDecimalConsumidor obtenerValorGramoMetal(@NotNull final MetalCalidadRangoProveedor proveedor);
}
