/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.AlhajaDTO;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link Alhaja}.
 *
 * @author ngonzalez
 */
public interface AlhajaFactory extends PiezaFactory<AlhajaDTO> {

    /**
     * Permite crear una entidad de tipo {@link Alhaja} con base en los argumentos recibidos.
     *
     * @param alhajaDTO DTO con la información de la pieza de tipo alhaja.
     * @return La entidad creada.
     */
    public Alhaja create(AlhajaDTO alhajaDTO);

    /**
     * Permite crear una entidad de tipo {@link Alhaja} con base en el builder recibido.
     *
     * @param builder El Builder con los atributos de la alhaja.
     * @return La entidad creada.
     */
    public Alhaja create(Alhaja.Builder builder);

}
