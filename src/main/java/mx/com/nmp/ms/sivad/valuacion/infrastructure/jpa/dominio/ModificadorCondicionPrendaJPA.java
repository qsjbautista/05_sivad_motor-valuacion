/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entidad utilizada para representar la relación entre la condición física de la prenda y el factor que influye
 * dicha condición en el cálculo del avalúo.
 *
 * @author ngonzalez
 */
@Entity
@Table(name = "cfg_diamante_modificador_condicion_prenda")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ModificadorCondicionPrendaJPA extends AbstractModificadorCondicionPrendaJPA {

}
