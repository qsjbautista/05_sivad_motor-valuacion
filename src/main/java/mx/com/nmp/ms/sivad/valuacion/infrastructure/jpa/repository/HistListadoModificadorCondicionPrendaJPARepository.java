/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository;

import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.HistListadoModificadorCondicionPrendaJPA;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Expone los metodos de acceso a datos para los históricos de los listados de modificadores - condición prenda.
 *
 * @author ngonzalez
 */
@Repository
public interface HistListadoModificadorCondicionPrendaJPARepository extends
    JpaRepository<HistListadoModificadorCondicionPrendaJPA, Long> {

    /**
     * Permite obtener los históricos de los listados de modificadores - condición prenda cuya fecha de última
     * actualización se encuentre dentro del rango de fechas indicadas.
     *
     * @param fechaInicial La fecha de inicio del rango de fechas.
     * @param fechaFinal La fecha de fin del rango de fechas.
     * @return Los históricos de los listados de modificadores - condición prenda que coincidieron.
     */
    Set<HistListadoModificadorCondicionPrendaJPA> findByUltimaActualizacionBetween(
        DateTime fechaInicial, DateTime fechaFinal);

}
