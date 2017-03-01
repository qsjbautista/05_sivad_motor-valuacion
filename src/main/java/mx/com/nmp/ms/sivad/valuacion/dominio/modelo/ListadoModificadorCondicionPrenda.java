/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import mx.com.nmp.ms.sivad.valuacion.dominio.repository.ModificadorCondicionPrendaRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.Set;

/**
 * Entidad que representa la lista de modificadores - condición prenda.
 *
 * @author ngonzalez
 */
public class ListadoModificadorCondicionPrenda {

    /**
     * Fecha en que se realiza la última actualización (fecha de vigencia).
     */
    private DateTime ultimaActualizacion;

    /**
     * Fecha de origen de la información.
     */
    private LocalDate fechaListado;

    /**
     * Lista de modificadores - condición prenda.
     */
    private Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda;

    /**
     * Referencia al repositorio de {@link ModificadorCondicionPrendaRepository}.
     */
    private ModificadorCondicionPrendaRepository modificadorCondicionPrendaRepository;

    /**
     * Interface que define el contrato para crear entidades de tipo {@link ListadoModificadorCondicionPrenda}.
     */
    public interface Builder {

        /**
         * Permite obtener la fecha en que se realiza la última actualización.
         *
         * @return La fecha en que se realiza la última actualización.
         */
        DateTime getUltimaActualizacion();

        /**
         * Permite obtener la fecha de origen de la información.
         *
         * @return La fecha de origen de la información.
         */
        LocalDate getFechaListado();

        /**
         * Permite obtener la lista de modificadores - condición prenda.
         *
         * @return La lista de modificadores - condición prenda.
         */
        Set<ModificadorCondicionPrenda> getModificadoresCondicionPrenda();

    }



    // METODOS

    /**
     * Constructor.
     *
     * @param builder Referencia al objeto que contiene los datos necesarios para construir la entidad.
     * @param modificadorCondicionPrendaRepository Referencia hacia el repositorio de modificador condición prenda.
     */
    private ListadoModificadorCondicionPrenda(Builder builder,
                                              ModificadorCondicionPrendaRepository modificadorCondicionPrendaRepository) {
        super();

        ultimaActualizacion = builder.getUltimaActualizacion();
        fechaListado = builder.getFechaListado();
        modificadoresCondicionPrenda = builder.getModificadoresCondicionPrenda();

        this.modificadorCondicionPrendaRepository = modificadorCondicionPrendaRepository;
    }

    /**
     * Permite actualizar el listado de modificadores - condición prenda.
     *
     * @return El listado de modificadores - condición prenda actualizado.
     */
    public ListadoModificadorCondicionPrenda actualizar() {
        return modificadorCondicionPrendaRepository.actualizarListado(this);
    }



    // GETTERS

    public DateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public LocalDate getFechaListado() {
        return fechaListado;
    }

    public Set<ModificadorCondicionPrenda> getModificadoresCondicionPrenda() {
        return modificadoresCondicionPrenda;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ListadoModificadorCondicionPrenda{" +
            "ultimaActualizacion=" + ultimaActualizacion +
            ", fechaListado=" + fechaListado +
            ", modificadoresCondicionPrenda=" + modificadoresCondicionPrenda +
            '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ListadoModificadorCondicionPrenda)) return false;

        ListadoModificadorCondicionPrenda that = (ListadoModificadorCondicionPrenda) o;

        if (!ultimaActualizacion.equals(that.ultimaActualizacion)) return false;
        if (!fechaListado.equals(that.fechaListado)) return false;
        if (!modificadoresCondicionPrenda.equals(that.modificadoresCondicionPrenda)) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = ultimaActualizacion.hashCode();
        result = 31 * result + fechaListado.hashCode();
        result = 31 * result + modificadoresCondicionPrenda.hashCode();
        return result;
    }

}
