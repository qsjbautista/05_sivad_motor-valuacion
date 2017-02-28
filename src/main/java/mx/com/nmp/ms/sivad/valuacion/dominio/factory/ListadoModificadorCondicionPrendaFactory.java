/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ListadoModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrenda;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Set;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link ListadoModificadorCondicionPrenda}.
 *
 * @author ngonzalez
 */
public interface ListadoModificadorCondicionPrendaFactory {

    /**
     * Permite crear una entidad de tipo {@link ListadoModificadorCondicionPrenda} con base en los argumentos recibidos.
     *
     * @param ultimaActualizacion La fecha en que se realiza la última actualización.
     * @param fechaListado La fecha de origen de la información.
     * @param modificadoresCondicionPrenda La lista de modificadores - condición prenda.
     * @return La entidad {@link ListadoModificadorCondicionPrenda} creada.
     */
    ListadoModificadorCondicionPrenda crear(DateTime ultimaActualizacion,
                                            LocalDate fechaListado,
                                            Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda);

    /**
     * Permite crear una entidad de tipo {@link ListadoModificadorCondicionPrenda} con base en los argumentos recibidos.
     *
     * @param fechaListado La fecha de origen de la información.
     * @param modificadoresCondicionPrenda La lista de modificadores - condición prenda.
     * @return La entidad {@link ListadoModificadorCondicionPrenda} creada.
     */
    ListadoModificadorCondicionPrenda crear(LocalDate fechaListado,
                                            Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda);

    /**
     * Permite crear una entidad de tipo {@link ListadoModificadorCondicionPrenda} con base en los argumentos recibidos
     * y con la inyección del repositorio de entidades para que se pueda persistir.
     *
     * @param fechaListado La fecha de origen de la información.
     * @param modificadoresCondicionPrenda La lista de modificadores - condición prenda.
     * @return La entidad {@link ListadoModificadorCondicionPrenda} creada.
     */
    ListadoModificadorCondicionPrenda crearPersistible(LocalDate fechaListado,
                                                       Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda);

    /**
     * Permite crear una entidad de tipo {@link ListadoModificadorCondicionPrenda} con base en el builder recibido.
     *
     * @param builder El objeto constructor con la información de la entidad.
     * @return La entidad {@link ListadoModificadorCondicionPrenda} creada.
     */
    ListadoModificadorCondicionPrenda crearDesde(ListadoModificadorCondicionPrenda.Builder builder);

    /**
     * Permite crear una entidad de tipo {@link ListadoModificadorCondicionPrenda} con base en el builder recibido y
     * con la inyección del repositorio de entidades para que se pueda persistir.
     *
     * @param builder El objeto constructor con la información de la entidad.
     * @return La entidad {@link ListadoModificadorCondicionPrenda} creada.
     */
    ListadoModificadorCondicionPrenda crearPersistibleDesde(ListadoModificadorCondicionPrenda.Builder builder);

}
