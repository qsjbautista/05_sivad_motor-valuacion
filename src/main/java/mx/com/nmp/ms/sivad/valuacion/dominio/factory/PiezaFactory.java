/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.PiezaDTO;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link Pieza}.
 *
 * @param <T>
 * @author ngonzalez
 */
public interface PiezaFactory<T extends PiezaDTO> {

    /**
     * Permite crear una entidad de tipo {@link Pieza} con base en los argumentos recibidos.
     *
     * @param piezaDTO DTO con la información de la pieza.
     * @return La entidad creada.
     */
    Pieza create(T piezaDTO);

}
