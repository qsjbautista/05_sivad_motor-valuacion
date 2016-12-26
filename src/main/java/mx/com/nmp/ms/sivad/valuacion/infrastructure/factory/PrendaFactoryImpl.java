/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.arquetipo.journal.util.ApplicationContextProvider;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.DomainExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AlhajaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.ComplementarioFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.DiamanteFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.PrendaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Prenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.*;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getConstructor;
import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getInstancia;

/**
 * Fábrica para crear entidades tipo {@link Prenda}.
 *
 * @author ngonzalez
 */
@Component
public class PrendaFactoryImpl implements PrendaFactory {

    /**
     * Referencia al constructor de la entidad.
     */
    private final Constructor<Prenda> constructor;

    /**
     * Referencia hacia el repositorio de políticas de castigo.
     */
    private PoliticasCastigoRepository politicasCastigoRepository;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Alhaja}.
     */
    @Inject
    private AlhajaFactory alhajaFactory;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Diamante}.
     */
    @Inject
    private DiamanteFactory diamanteFactory;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Complementario}.
     */
    @Inject
    private ComplementarioFactory complementarioFactory;



    // METODOS

    /**
     * Constructor.
     */
    public PrendaFactoryImpl() {
        super();

        constructor = getConstructor(Prenda.class, Prenda.Builder.class,
            PoliticasCastigoRepository.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Prenda create(PrendaDTO prendaDTO) {
        List<Pieza> piezas = new ArrayList<>();

        if (!ObjectUtils.isEmpty(prendaDTO) && !ObjectUtils.isEmpty(prendaDTO.getPiezas())) {
            for (PiezaDTO piezaDTO : prendaDTO.getPiezas()) {
                if (piezaDTO instanceof AlhajaDTO) {
                    piezas.add(alhajaFactory.create((AlhajaDTO) piezaDTO));
                }

                if (piezaDTO instanceof DiamanteDTO) {
                    piezas.add(diamanteFactory.create((DiamanteDTO) piezaDTO));
                }

                if (piezaDTO instanceof ComplementarioDTO) {
                    piezas.add(complementarioFactory.create((ComplementarioDTO) piezaDTO));
                }
            }
        }

        final Prenda.Builder builder =
            getBuilder(piezas);
        return create(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Prenda create(Prenda.Builder builder) {
        validarBuilder(builder);
        return getInstancia(constructor, builder, getPoliticasCastigoRepository());
    }

    /**
     * Crea un objeto constructor a partir del valor de los argumentos.
     *
     * @param piezas Lista de piezas de las que se compone la prenda.
     * @return El objeto constructor creado.
     */
    private static Prenda.Builder getBuilder(final List<Pieza> piezas) {
        return new Prenda.Builder() {

            @Override
            public List<Pieza> getPiezas() {
                return piezas;
            }
        };
    }

    /**
     * Utilizado para validar los valores con los que se quiere crear la entidad.
     *
     * @param builder Objeto constructor de la entidad.
     */
    private static void validarBuilder(final Prenda.Builder builder) {
        Assert.notNull(builder, DomainExceptionCodes.BUILDER_NULO.getMessageException());
        Assert.notNull(builder.getPiezas(), DomainExceptionCodes.LISTA_PIEZAS_NULA.getMessageException());
        Assert.notEmpty(builder.getPiezas(), DomainExceptionCodes.LISTA_PIEZAS_VACIA.getMessageException());
    }

    /**
     * Permite obtener la referencia del repositorio de políticas de castigo.
     *
     * @return Referencia hacia el repositorio de políticas de castigo.
     */
    public PoliticasCastigoRepository getPoliticasCastigoRepository() {
        if (ObjectUtils.isEmpty(politicasCastigoRepository)) {
            politicasCastigoRepository =
                ApplicationContextProvider.get().getBean(PoliticasCastigoRepository.class);
        }

        return politicasCastigoRepository;
    }

}
