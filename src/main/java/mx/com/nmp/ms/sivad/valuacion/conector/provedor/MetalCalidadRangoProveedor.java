/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.provedor;

/**
 * Contrato que deben seguir los proveedores de datos Metal-Calidad-Rango.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface MetalCalidadRangoProveedor {
    /**
     * Recupera el Metal.
     *
     * @return Metal
     */
    String getMetal();

    /**
     * Recupera la Calidad.
     *
     * @return Calidad.
     */
    String getCalidad();

    /**
     * Recupera el Rango.
     *
     * @return Rango
     */
    String getRango();
}
