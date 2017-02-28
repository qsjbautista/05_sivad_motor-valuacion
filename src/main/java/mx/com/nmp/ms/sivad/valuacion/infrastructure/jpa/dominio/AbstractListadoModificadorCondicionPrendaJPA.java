/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.util.Objects;

/**
 * Clase abstracta que factoriza los atributos comunes de los listados de modificadores - condición prenda.
 *
 * @author ngonzalez
 */
@MappedSuperclass
public abstract class AbstractListadoModificadorCondicionPrendaJPA {

    /**
     * Identificador del registro.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    protected Long id;

    /**
     * Fecha en que se realiza la última actualización (fecha de vigencia).
     */
    @Column(name = "ultima_actualizacion", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime ultimaActualizacion;

    /**
     * La fecha de origen de la información.
     */
    @Column(name = "fecha_listado", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    protected LocalDate fechaListado;



    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(DateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public LocalDate getFechaListado() {
        return fechaListado;
    }

    public void setFechaListado(LocalDate fechaListado) {
        this.fechaListado = fechaListado;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractListadoModificadorCondicionPrendaJPA)) return false;

        AbstractListadoModificadorCondicionPrendaJPA that = (AbstractListadoModificadorCondicionPrendaJPA) o;

        if (!id.equals(that.id)) return false;
        if (!ultimaActualizacion.equals(that.ultimaActualizacion)) return false;
        if (!fechaListado.equals(that.fechaListado)) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, ultimaActualizacion, fechaListado);
    }

}
