/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.DiamanteDTO;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link Diamante}.
 *
 * @author ngonzalez
 */
public interface DiamanteFactory extends PiezaFactory<DiamanteDTO> {

    /**
     * Permite crear una entidad de tipo {@link Diamante} con base en los argumentos recibidos.
     *
     * @param diamanteDTO DTO con la información de la pieza de tipo diamante.
     * @return La entidad creada.
     */
    public Diamante create(DiamanteDTO diamanteDTO);

    /**
     * Permite crear una entidad de tipo {@link Diamante} con base en el builder recibido.
     *
     * @param builder El Builder con los atributos del diamante.
     * @return La entidad creada.
     */
    public Diamante create(Diamante.Builder builder);

}
