/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Map;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static org.hibernate.annotations.CacheConcurrencyStrategy.NONSTRICT_READ_WRITE;

/**
 * Entidad JPA que permite mapear las lista de políticas de castigo a una tabla de unidades persistentes.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Entity
@Cache(usage = NONSTRICT_READ_WRITE)
@Table(name = "tc_politica_castigo_pieza",
    indexes = {
        @Index(name = "idx_tc_politica_castigo_pieza_id", columnList = "id", unique = true),
        @Index(name = "idx_tc_politica_castigo_pieza_fecha_listado", columnList = "fecha_listado")
    }
)
public class PoliticasCastigoJpa implements PoliticasCastigo.Builder {

    /**
     * Identificador.
     */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ElementCollection(fetch = EAGER)
    @MapKeyColumn(name = "pieza")
    @Column(name = "factor")
    @CollectionTable(name = "tc_politica_castigo_pieza_factores", joinColumns = @JoinColumn(name = "politica"))
    private Map<Class<? extends Pieza>, BigDecimal> factores;

    /**
     * Fecha en la que se actualiza la lista de políticas.
     */
    @Column(name = "fecha_listado", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime fechaListado;

    /**
     * Constructor.
     */
    public PoliticasCastigoJpa() {
        super();

        fechaListado = DateTime.now();
    }

    /**
     * Regresa el identificador.
     *
     * @return Identificador.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador.
     *
     * @param id Identificador.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Regresa el listado de factores de castigo.
     *
     * @return Listado de factores de castigo.
     */
    @Override
    public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
        return factores;
    }

    /**
     * Establece el listado de factores de castigo.
     *
     * @param factores Listado de factores de castigo.
     */
    public void setFactores(Map<Class<? extends Pieza>, BigDecimal> factores) {
        this.factores = factores;
    }

    /**
     * Regresa la fecha en la que se actualiza la lista de políticas.
     *
     * @return Fecha en la que se actualiza la lista de políticas.
     */
    public DateTime getFechaListado() {
        return fechaListado;
    }

    /**
     * Establece la fecha en la que se actualiza la lista de políticas.
     *
     * @param fechaListado Fecha en la que se actualiza la lista de políticas.
     */
    public void setFechaListado(DateTime fechaListado) {
        this.fechaListado = fechaListado;
    }
}
