/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository;

import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.ModificadorCondicionPrendaJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Expone los metodos de acceso a datos para la entidad {@link ModificadorCondicionPrendaJPA}.
 *
 * @author ngonzalez
 */
@Repository
public interface ModificadorCondicionPrendaJPARepository extends
    JpaRepository<ModificadorCondicionPrendaJPA, Long> {

    /**
     * Utilizado para obtener la entidad que coincida exactamente con el atributo "condicionPrenda" indicado.
     *
     * @param condicionPrenda La condición física de la prenda.
     * @return La entidad que coincida con el valor del atributo indicado.
     */
    ModificadorCondicionPrendaJPA findByCondicionPrenda(String condicionPrenda);

}
