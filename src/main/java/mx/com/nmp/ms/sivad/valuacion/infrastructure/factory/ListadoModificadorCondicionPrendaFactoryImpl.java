/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.arquetipo.journal.util.ApplicationContextProvider;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.DomainExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.ListadoModificadorCondicionPrendaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ListadoModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.ModificadorCondicionPrendaRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.util.Set;

import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getConstructor;
import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getInstancia;

/**
 * Fábrica que se encarga de crear objetos de tipo {@link ListadoModificadorCondicionPrenda}.
 *
 * @author ngonzalez
 */
@Component
public class ListadoModificadorCondicionPrendaFactoryImpl implements ListadoModificadorCondicionPrendaFactory {

    /**
     * Referencia al constructor de la entidad.
     */
    private final Constructor<ListadoModificadorCondicionPrenda> constructor;

    /**
     * Referencia al repositorio de {@link ModificadorCondicionPrendaRepository}.
     */
    private ModificadorCondicionPrendaRepository modificadorCondicionPrendaRepository;



    // METODOS

    /**
     * Constructor.
     */
    public ListadoModificadorCondicionPrendaFactoryImpl() {
        super();

        constructor = getConstructor(ListadoModificadorCondicionPrenda.class,
            ListadoModificadorCondicionPrenda.Builder.class,
            ModificadorCondicionPrendaRepository.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListadoModificadorCondicionPrenda crear(
        DateTime ultimaActualizacion,
        LocalDate fechaListado,
        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda) {

        Assert.notNull(ultimaActualizacion,
            DomainExceptionCodes.MODIFICADOR_CONDICION_PRENDA_ULTIMA_ACTUALIZACION_NULA.getMessageException());

        final ListadoModificadorCondicionPrenda.Builder builder =
            getBuilder(ultimaActualizacion, fechaListado, modificadoresCondicionPrenda);

        return crearDesde(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListadoModificadorCondicionPrenda crear(
        LocalDate fechaListado,
        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda) {

        final ListadoModificadorCondicionPrenda.Builder builder =
            getBuilder(null, fechaListado, modificadoresCondicionPrenda);

        return crearDesde(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListadoModificadorCondicionPrenda crearPersistible(
        LocalDate fechaListado,
        Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda) {

        final ListadoModificadorCondicionPrenda.Builder builder =
            getBuilder(null, fechaListado, modificadoresCondicionPrenda);

        return crearPersistibleDesde(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListadoModificadorCondicionPrenda crearDesde(
        ListadoModificadorCondicionPrenda.Builder builder) {

        return crear(builder, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListadoModificadorCondicionPrenda crearPersistibleDesde(
        ListadoModificadorCondicionPrenda.Builder builder) {

        return crear(builder, getRepositorio());
    }

    /**
     * Metodo auxiliar utilizado crear la entidad de tipo {@link ListadoModificadorCondicionPrenda}.
     *
     * @param builder El objeto constructor con la información de la entidad.
     * @param repositorio La referencia al repositorio de {@link ModificadorCondicionPrendaRepository}.
     * @return La entidad {@link ListadoModificadorCondicionPrenda} creada.
     */
    private ListadoModificadorCondicionPrenda crear(
        final ListadoModificadorCondicionPrenda.Builder builder,
        ModificadorCondicionPrendaRepository repositorio) {

        validarBuilder(builder);
        return getInstancia(constructor, builder, repositorio);
    }

    /**
     * Permite obtener la referencia al repositorio de {@link ModificadorCondicionPrendaRepository}.
     *
     * @return La referencia al repositorio de {@link ModificadorCondicionPrendaRepository}.
     */
    private ModificadorCondicionPrendaRepository getRepositorio() {
        if (ObjectUtils.isEmpty(modificadorCondicionPrendaRepository)) {
            modificadorCondicionPrendaRepository = ApplicationContextProvider.get().getBean(ModificadorCondicionPrendaRepository.class);
        }

        return modificadorCondicionPrendaRepository;
    }

    /**
     * Crea un objeto constructor de la entidad de tipo {@link ListadoModificadorCondicionPrenda} a partir del
     * valor de los argumentos.
     *
     * @param ultimaActualizacion La fecha en que se realiza la última actualización.
     * @param fechaListado La fecha de origen de la información.
     * @param modificadoresCondicionPrenda La lista de modificadores - condición prenda.
     * @return El objeto constructor creado.
     */
    private static ListadoModificadorCondicionPrenda.Builder getBuilder(
        final DateTime ultimaActualizacion,
        final LocalDate fechaListado,
        final Set<ModificadorCondicionPrenda> modificadoresCondicionPrenda) {

        return new ListadoModificadorCondicionPrenda.Builder() {

            /**
             * {@inheritDoc}
             */
            @Override
            public DateTime getUltimaActualizacion() {
                return ultimaActualizacion;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public LocalDate getFechaListado() {
                return fechaListado;
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public Set<ModificadorCondicionPrenda> getModificadoresCondicionPrenda() {
                return modificadoresCondicionPrenda;
            }

        };
    }

    /**
     * Utilizado para validar los valores con los que se quiere crear la entidad.
     *
     * @param builder El objeto constructor con la información de la entidad.
     */
    private static void validarBuilder(
        final ListadoModificadorCondicionPrenda.Builder builder) {

        Assert.notNull(builder,
            DomainExceptionCodes.BUILDER_NULO.getMessageException());

        Assert.notNull(builder.getFechaListado(),
            DomainExceptionCodes.MODIFICADOR_CONDICION_PRENDA_FECHA_LISTADO_NULA.getMessageException());

        Assert.notNull(builder.getModificadoresCondicionPrenda(),
            DomainExceptionCodes.MODIFICADOR_CONDICION_PRENDA_LISTA_MODIFICADORES_NULA.getMessageException());

        if (ObjectUtils.isEmpty(builder.getModificadoresCondicionPrenda())) {
            throw new IllegalArgumentException(
                DomainExceptionCodes.MODIFICADOR_CONDICION_PRENDA_LISTA_MODIFICADORES_VACIA.getMessageException());
        }

    }

}
