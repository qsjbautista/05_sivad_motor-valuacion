/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.exception.DomainExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.*;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.*;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.*;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * Mapa de estrategia de fábricas por tipo de pieza.
     */
    private Map<Class<? extends PiezaDTO>, PiezaFactory> mapaEstrategiaFactory;

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

    /**
     * Referencia hacia el repositorio de políticas de castigo.
     */
    @Inject
    private PoliticasCastigoRepository politicasCastigoRepository;



    // METODOS

    /**
     * Constructor.
     */
    public PrendaFactoryImpl() {
        super();

        constructor = getConstructor(Prenda.class, Prenda.Builder.class,
            PoliticasCastigoRepository.class);

        mapaEstrategiaFactory = new HashMap<>();
    }

    /**
     * Configuración inicial.
     */
    @PostConstruct
    private void inicializar() {
        mapaEstrategiaFactory.put(AlhajaDTO.class, alhajaFactory);
        mapaEstrategiaFactory.put(DiamanteDTO.class, diamanteFactory);
        mapaEstrategiaFactory.put(ComplementarioDTO.class, complementarioFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Prenda create(PrendaDTO prendaDTO) {
        List<Pieza> piezas = new ArrayList<>();

        if (!ObjectUtils.isEmpty(prendaDTO) && !ObjectUtils.isEmpty(prendaDTO.getPiezas())) {
            for (PiezaDTO piezaDTO : prendaDTO.getPiezas()) {
                piezas.add(mapaEstrategiaFactory.get(piezaDTO.getClass()).create(piezaDTO));
            }
        }

        final Prenda.Builder builder = getBuilder(piezas);
        return create(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Prenda create(List<Pieza> piezas) {
        final Prenda.Builder builder = getBuilder(piezas);
        return create(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Prenda create(Prenda.Builder builder) {
        validarBuilder(builder);
        return getInstancia(constructor, builder, politicasCastigoRepository);
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

}
