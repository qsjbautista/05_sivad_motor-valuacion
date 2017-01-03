/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.arquetipo.journal.util.ApplicationContextProvider;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.PoliticasCastigoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import mx.com.nmp.ms.sivad.valuacion.dominio.validador.ValidadorNumero;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Fábrica para crear entidades tipo {@link PoliticasCastigo}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component
public class PoliticasCastigoFactoryImpl implements PoliticasCastigoFactory {
    /**
     * Referencia al constructor de la entidad.
     */
    private Constructor<PoliticasCastigo> constructor;

    /**
     * Referencia al repositorio de entidades.
     */
    private PoliticasCastigoRepository repositorio;

    /**
     * Constructor.
     */
    public PoliticasCastigoFactoryImpl() {
        super();

        constructor = ConstructorUtil
            .getConstructor(PoliticasCastigo.class, PoliticasCastigo.Builder.class, PoliticasCastigoRepository.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PoliticasCastigo crearCon(
            @NotNull Map<Class<? extends Pieza>, BigDecimal> factorPoliticasCastigo, @NotNull DateTime fechaListado) {
        final PoliticasCastigo.Builder builder = getBuilder(factorPoliticasCastigo, fechaListado);

        return crearDesde(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PoliticasCastigo crearPersistibleCon(
            @NotNull Map<Class<? extends Pieza>, BigDecimal> factorPoliticasCastigo, @NotNull DateTime fechaListado) {
        final PoliticasCastigo.Builder builder = getBuilder(factorPoliticasCastigo, fechaListado);

        return crearPersistibleDesde(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PoliticasCastigo crearDesde(@NotNull PoliticasCastigo.Builder builder) {
        return crear(builder, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PoliticasCastigo crearPersistibleDesde(@NotNull PoliticasCastigo.Builder builder) {
        return crear(builder, getRepositorio());
    }

    /**
     * Se encarga de construir el {@link PoliticasCastigo.Builder} para crear la instancia de la entidad.
     *
     * @param factorPoliticasCastigo Objeto Value Object con los factores aplicables
     *                               a diamantes, alhajas y complementario.
     * @param fechaListado Fecha de vigencia de las políticas de castigo.
     *
     * @return @link PoliticasCastigo.Builder}
     */
    private PoliticasCastigo.Builder getBuilder(
            final Map<Class<? extends Pieza>, BigDecimal> factorPoliticasCastigo, final DateTime fechaListado) {
        return new PoliticasCastigo.Builder() {
            @Override
            public Map<Class<? extends Pieza>, BigDecimal> getFactores() {
                return factorPoliticasCastigo;
            }

            @Override
            public DateTime getFechaListado() {
                return fechaListado;
            }
        };
    }

    /**
     * Crea una entidad {@link PoliticasCastigo} a partir del valor de los argumentos.
     *
     * @param builder Objeto que contiene la información necesaria para crear la entidad.
     * @param repositorio Referencia al repositorio de entidades.
     *
     * @return {@link PoliticasCastigo}
     */
    private PoliticasCastigo crear(PoliticasCastigo.Builder builder, PoliticasCastigoRepository repositorio) {
        validarBuilder(builder);

        return ConstructorUtil.getInstancia(constructor, builder, repositorio);
    }

    /**
     * Valida que la información contenida en {@link PoliticasCastigo.Builder} sea correcta.
     *
     * @param builder {@link PoliticasCastigo.Builder} a validar.
     *
     * @throws IllegalArgumentException Cuando algún valor del builder sea {@literal null}
     */
    private void validarBuilder(PoliticasCastigo.Builder builder) {
        validarFactores(builder.getFactores());
        Assert.notNull(builder.getFechaListado());
    }

    /**
     * Valida si el mapa de factores contiene información valida.
     *
     * @param factores Mapa con los factores de politicas de castigo
     */
    private void validarFactores(Map<Class<? extends Pieza>, BigDecimal> factores) {
        Assert.notNull(factores);
        Assert.isTrue(!factores.containsKey(null));
        Assert.isTrue(!factores.containsValue(null));

        for (BigDecimal v : factores.values()) {
            ValidadorNumero.validarPositivo(v);
        }
    }

    /**
     * Recupera el repositorio de la entidad. Se recupera de esta manera para evitar un error de referencia circular.
     *
     * @return Repositorio de la entidad.
     */
    private PoliticasCastigoRepository getRepositorio() {
        if (ObjectUtils.isEmpty(repositorio)) {
            repositorio = ApplicationContextProvider.get().getBean(PoliticasCastigoRepository.class);
        }

        return repositorio;
    }
}
