/**
 * Proyecto:        NMP - Sistema de Operacion Prendaria Emergente
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Entidad utilizada para representar la relacion de las politicas de castigo con un subramo.
 *
 * @author ecancino
 */
@Entity
@Table(name = "tc_politicas_castigo_subramo")
public class PoliticasCastigoSubramoJPA {

    /**
     * Identificador del registro
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    /**
     * Politicas de castigo
     */
    @OneToOne
    @JoinColumn(name = "politica", nullable = false)
    private PoliticasCastigoJpa politicasCastigo;

    /**
     * Abreviatura del subramo de la prenda.
     */
    @Column(name="subramo", length=20, nullable = false)
    private String subramo;



    // GETTERS Y SETTERS

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PoliticasCastigoJpa getPoliticasCastigo() {
        return this.politicasCastigo;
    }

    public void setPoliticasCastigo(PoliticasCastigoJpa politicasCastigo) {
        this.politicasCastigo = politicasCastigo;
    }

    public String getSubramo() {
        return this.subramo;
    }

    public void setSubramo(String subramo) {
        this.subramo = subramo;
    }



    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PoliticasCastigoSubramoJPA)) return false;

        PoliticasCastigoSubramoJPA that = (PoliticasCastigoSubramoJPA) o;

        if (!id.equals(that.id)) return false;
        if (!politicasCastigo.equals(that.politicasCastigo)) return false;
        return subramo.equals(that.subramo);
    }

    public int hashCode() {
        return Objects.hash(id, politicasCastigo, subramo);
    }

    public String toString() {
        return "PoliticasCastigoSubramoJPA{" +
            "id=" + id + '\'' +
            ", politicasCastigo='" + politicasCastigo + '\'' +
            ", subramo='" + subramo +
            '}';
    }
}
