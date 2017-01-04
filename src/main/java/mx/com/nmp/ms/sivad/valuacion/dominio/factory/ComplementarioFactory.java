/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.ComplementarioDTO;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link Complementario}.
 *
 * @author ngonzalez
 */
public interface ComplementarioFactory extends PiezaFactory<ComplementarioDTO> {

    /**
     * Permite crear una entidad de tipo {@link Complementario} con base en los argumentos recibidos.
     *
     * @param complementarioDTO DTO con la información de la pieza complementaria.
     * @return La entidad creada.
     */
    public Complementario create(ComplementarioDTO complementarioDTO);

    /**
     * Permite crear una entidad de tipo {@link Complementario} con base en el builder recibido.
     *
     * @param builder El Builder con los atributos del complementario.
     * @return La entidad creada.
     */
    public Complementario create(Complementario.Builder builder);

}
