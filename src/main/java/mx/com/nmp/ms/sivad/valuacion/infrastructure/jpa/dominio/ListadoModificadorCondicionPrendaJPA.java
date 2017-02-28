/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.Set;

/**
 * Entidad utilizada para representar la lista de modificadores - condición prenda.
 *
 * @author ngonzalez
 */
@Entity
@Table(name = "cfg_diamante_listado_modificador_condicion_prenda")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ListadoModificadorCondicionPrendaJPA extends AbstractListadoModificadorCondicionPrendaJPA {

    /**
     * Lista de modificadores - condición prenda.
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "listado")
    private Set<ModificadorCondicionPrendaJPA> modificadoresCondicionPrenda;



    // GETTERS Y SETTERS

    public Set<ModificadorCondicionPrendaJPA> getModificadoresCondicionPrenda() {
        return modificadoresCondicionPrenda;
    }

    public void setModificadoresCondicionPrenda(Set<ModificadorCondicionPrendaJPA> modificadoresCondicionPrenda) {
        this.modificadoresCondicionPrenda = modificadoresCondicionPrenda;
    }

}
