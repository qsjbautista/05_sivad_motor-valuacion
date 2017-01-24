/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.PoliticaCastigoNoEncontradaException;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.PoliticasCastigoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.PoliticasCastigoJpa;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementación en JPA de la interface {@link PoliticasCastigoRepository} permite recuperar de las unidades
 * persistentes la entidad {@link PoliticasCastigo} con las políticas vigentes.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Repository
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public class PoliticasCastigoRepositoryImpl implements PoliticasCastigoRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoliticasCastigoRepositoryImpl.class);

    /**
     * Referencia al repositorio JPA.
     */
    @Inject
    private PoliticasCastigoJpaRepository repositorio;

    /**
     * Referencia a la fabrica de entidades.
     */
    @Inject
    private PoliticasCastigoFactory fabrica;

    /**
     * Constructor.
     */
    public PoliticasCastigoRepositoryImpl() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PoliticasCastigo consultar() {
        LOGGER.info(">> consultar()");

        PoliticasCastigoJpa politica = repositorio.findFirstByOrderByFechaListadoDesc();

        if (ObjectUtils.isEmpty(politica)) {
            throw new PoliticaCastigoNoEncontradaException("No existen politicas de castigo vigentes");
        }

        LOGGER.debug("creando entidad {} desde {}", PoliticasCastigo.class.getSimpleName(), politica);

        return fabrica.crearDesde(politica);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @CacheEvict(cacheNames = "PoliticasCastigoJpaRepository.findFirstByOrderByFechaListadoDesc", allEntries = true)
    public void actualizar(@NotNull PoliticasCastigo entidad) {
        LOGGER.info(">> actualizar({})", entidad);

        Map<Class<? extends Pieza>, BigDecimal> vo = entidad.getFactores();
        PoliticasCastigoJpa politica = new PoliticasCastigoJpa();

        politica.setFactores(vo);
        politica.setFechaListado(entidad.getFechaListado());

        repositorio.saveAndFlush(politica);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PoliticasCastigo> consultar(@NotNull DateTime fecha) {
        validarFechaFutura(fecha);

        LOGGER.info(">> consultar({})", fecha);

        DateTime fechaFinal = fecha.millisOfDay().withMaximumValue();
        List<PoliticasCastigoJpa> politicas = repositorio
            .findByFechaListadoBetweenOrderByFechaListadoDesc(fecha, fechaFinal);

        if (ObjectUtils.isEmpty(politicas)) {
            throw new PoliticaCastigoNoEncontradaException("No existen politicas de castigo para la fecha de vigencia");
        }

        List<PoliticasCastigo> result = new ArrayList<>(politicas.size());

        for (PoliticasCastigoJpa p : politicas) {
            LOGGER.debug("creando entidad {} desde {}", PoliticasCastigo.class.getSimpleName(), p);
            result.add(fabrica.crearDesde(p));
        }


        return result;
    }

    /**
     * Verifica que la fecha no sea del futuro.
     *
     * @param fecha Fecha a validar.
     */
    private void validarFechaFutura(DateTime fecha) {
        if (fecha.isAfterNow()) {
            throw new PoliticaCastigoNoEncontradaException("La fecha de vigencia no puede ser una fecha futura");
        }
    }
}
