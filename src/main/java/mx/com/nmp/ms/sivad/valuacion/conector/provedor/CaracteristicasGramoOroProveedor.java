/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.provedor;

/**
 * Contrato que deben seguir los proveedores de datos de Características Gramo de Oro.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface CaracteristicasGramoOroProveedor {
    /**
     * Recupera el Color del oro.
     *
     * @return Color del oro.
     */
    String getColor();

    /**
     * Recupera la Calidad del oro.
     *
     * @return Calidad del oro.
     */
    String getCalidad();
}
