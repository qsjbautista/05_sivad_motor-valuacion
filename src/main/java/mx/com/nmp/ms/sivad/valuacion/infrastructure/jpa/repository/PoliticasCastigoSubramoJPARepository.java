/**
 * Proyecto:        NMP - Sistema de Operacion Prendaria Emergente
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository;

import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.PoliticasCastigoSubramoJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Expone los metodos de acceso a datos para la entidad {@link PoliticasCastigoSubramoJPA}.
 *
 * @author ecancino
 */
public interface PoliticasCastigoSubramoJPARepository extends JpaRepository<PoliticasCastigoSubramoJPA, Long> {

    /**
     * Recupera la lista de políticas de castigo de subramo vigentes al momento.
     *
     * @return Polticas de castigo.
     */
    PoliticasCastigoSubramoJPA findFirstByOrderByIdDesc();
}
