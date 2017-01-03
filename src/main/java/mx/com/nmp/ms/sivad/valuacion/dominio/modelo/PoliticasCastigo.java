/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

/**
 * Entidad de dominio (DDD) usada para administrar (actualizar y recuperar) la lista de políticas de castigo.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class PoliticasCastigo {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoliticasCastigo.class);

    /**
     * Value Object que contiene los factores de castigo
     */
    private Map<Class<? extends Pieza>, BigDecimal> factores;

    /**
     * Fecha en la que se actualiza la lista de políticas.
     */
    private DateTime fechaListado;

    /**
     * Referencia al repositorio de datos.
     */
    private PoliticasCastigoRepository repositorio;

    /**
     * Representa la interface publica usada para crear objetos tipo {@link PoliticasCastigo}
     * Nos brinda el contrato que se debe cumplir para crear una instancia de la clase {@link PoliticasCastigo}
     */
    public interface Builder {
        /**
         * Provee el value object que representa una politica de castigo.
         *
         * @return Value Object que representa una politica de castigo.
         */
        Map<Class<? extends Pieza>, BigDecimal> getFactores();

        /**
         * Provee la fecha en la que se actualiza la lista de políticas.
         *
         * @return Fecha en la que se actualiza la lista de políticas.
         */
        DateTime getFechaListado();
    }

    /**
     * Constructor. Privado para que solo la fabrica pueda crear instancias.
     *
     * @param builder Objeto con los datos necesarios para construir la instancia.
     * @param repositorio Referencia al repositorio de datos.
     */
    private PoliticasCastigo(Builder builder, PoliticasCastigoRepository repositorio) {
        super();

        factores = builder.getFactores();
        fechaListado = builder.getFechaListado();

        this.repositorio = repositorio;
    }

    /**
     * Actualiza el listado de políticas de castigo.
     */
    public void actualizar() {
        LOGGER.info(">> actualizar()");


        if (ObjectUtils.isEmpty(repositorio)) {
            LOGGER.info("No se puede actualizar el litado de politicas. " +
                "Ya se encuentra almacenda la entidad o no contiene referencia al repositorio de datos.");
        } else {
            LOGGER.debug("Actualizando listado de politicas de castigo con {}", this);
            repositorio.actualizar(this);
        }
    }

    /**
     * Recupera el value object que representa una politica de castigo.
     *
     * @return Value Object que representa una politica de castigo.
     */
    public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
        return factores;
    }

    /**
     * Recupera la fecha en la que se actualiza la lista de políticas.
     *
     * @return Fecha en la que se actualiza la lista de políticas.
     */
    public DateTime getFechaListado() {
        return fechaListado;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PoliticasCastigo that = (PoliticasCastigo) o;

        return Objects.equals(fechaListado, that.fechaListado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(fechaListado);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("PoliticasCastigo{factorPoliticasCastigo=%s, fechaListado=%s}",
            factores, fechaListado);
    }
}
