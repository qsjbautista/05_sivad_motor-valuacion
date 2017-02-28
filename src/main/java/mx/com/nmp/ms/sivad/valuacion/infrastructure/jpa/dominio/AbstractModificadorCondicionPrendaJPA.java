/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Clase abstracta que factoriza los atributos comunes de los modificadores - condición prenda.
 *
 * @author ngonzalez
 */
@MappedSuperclass
public abstract class AbstractModificadorCondicionPrendaJPA {

    /**
     * Identificador del registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    /**
     * Condición física de la prenda.
     */
    @Column(name = "condicion_prenda", nullable = false)
    protected String condicionPrenda;

    /**
     * Factor.
     */
    @Column(name = "factor", precision = 6, scale = 2, nullable = false)
    protected BigDecimal factor;



    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondicionPrenda() {
        return condicionPrenda;
    }

    public void setCondicionPrenda(String condicionPrenda) {
        this.condicionPrenda = condicionPrenda;
    }

    public BigDecimal getFactor() {
        return factor;
    }

    public void setFactor(BigDecimal factor) {
        this.factor = factor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractModificadorCondicionPrendaJPA)) return false;

        AbstractModificadorCondicionPrendaJPA that = (AbstractModificadorCondicionPrendaJPA) o;

        if (!id.equals(that.id)) return false;
        if (!condicionPrenda.equals(that.condicionPrenda)) return false;
        if (!factor.equals(that.factor)) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, condicionPrenda, factor);
    }

}
