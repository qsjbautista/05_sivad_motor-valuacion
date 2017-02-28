/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository;

import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.ListadoModificadorCondicionPrendaJPA;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Expone los metodos de acceso a datos para el listado de modificadores - condición prenda.
 *
 * @author ngonzalez
 */
@Repository
public interface ListadoModificadorCondicionPrendaJPARepository extends
    JpaRepository<ListadoModificadorCondicionPrendaJPA, Long> {

    /**
     * Permite obtener el listado de modificadores - condición prenda vigente.
     *
     * @return Listado vigente.
     */
    @Query("SELECT lmcp FROM ListadoModificadorCondicionPrendaJPA lmcp")
    ListadoModificadorCondicionPrendaJPA obtenerListadoVigente();

    /**
     * Permite obtener los listados de modificadores - condición prenda cuya fecha de última
     * actualización se encuentre dentro del rango de fechas indicadas.
     *
     * @param fechaInicial La fecha de inicio del rango de fechas.
     * @param fechaFinal La fecha de fin del rango de fechas.
     * @return Los listados de modificadores - condición prenda que coincidieron.
     */
    Set<ListadoModificadorCondicionPrendaJPA> findByUltimaActualizacionBetween(
        DateTime fechaInicial, DateTime fechaFinal);

}
