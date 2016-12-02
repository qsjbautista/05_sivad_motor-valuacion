/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;

/**
 * Interface que provee el contrato que deben implementar las clases que pretendan ser
 * valoradas por el Motor de Valuación.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface PiezaValuable {
    /**
     * Permite realizar la valuación de una pieza.
     *
     * @return Valor de la pieza valuada.
     */
    Avaluo valuar();
}
