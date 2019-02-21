/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository;

import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.PoliticasCastigoJpa;
import org.joda.time.DateTime;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositorio que brinda la funcionalidad para administrar la entidad JPA {@link PoliticasCastigoJpa}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface PoliticasCastigoJpaRepository extends JpaRepository<PoliticasCastigoJpa, Long> {
    /**
     * Recupera la lista de políticas de castigo vigentes al momento.
     *
     * @return Polticas de castigo.
     */
    @Cacheable("PoliticasCastigoJpaRepository.findFirstByOrderByFechaListadoDesc")
    PoliticasCastigoJpa findFirstByOrderByFechaListadoDesc();

    /**
     * Recupera la lista de politicas de castigo vigentes al momento del subramo especificado.
     *
     * @param subramo Abreviatura del subramo de la prenda
     * @return Politicas de castigo.
     */
    @Query(value = "SELECT " +
        "MAX(pcs.politicasCastigo) " +
        "FROM PoliticasCastigoSubramoJPA pcs " +
        "WHERE pcs.subramo = :subramo " +
        "GROUP BY pcs.politicasCastigo.fechaListado " +
        "ORDER BY pcs.politicasCastigo.fechaListado DESC")
	@Cacheable("PoliticasCastigoJpaRepository.findFirstBySubramoByOrderByFechaListadoDesc")
    PoliticasCastigoJpa findFirstBySubramoByOrderByFechaListadoDesc(@Param("subramo") String subramo);

    /**
     * Recupera la lista de políticas de castigo con base en una fecha de vigencia.
     *
     * @param fechaInicial Fecha de vigencia inicial (ej. 2016-11-24T00:00:00.000).
     * @param fechaFinal Fecha de vigencia final (ej. 2016-11-24T23:59:59.999).
     *
     * @return Listado de políticas de castigo.
     */
    List<PoliticasCastigoJpa> findByFechaListadoBetweenOrderByFechaListadoDesc(DateTime fechaInicial, DateTime fechaFinal);
}
