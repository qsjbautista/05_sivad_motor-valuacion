/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.FechaFuturaException;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ListadoNoEncontradoException;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ListadoSinElementosException;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ModificadorCondicionPrendaNoEncontradoException;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.ListadoModificadorCondicionPrendaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ListadoModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrendaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.CondicionPrendaVO;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.ModificadorCondicionPrendaRepository;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.HistListadoModificadorCondicionPrendaJPA;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.HistModificadorCondicionPrendaJPA;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.ListadoModificadorCondicionPrendaJPA;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.ModificadorCondicionPrendaJPA;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.util.DateUtil;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementación de {@link ModificadorCondicionPrendaRepository}.
 *
 * @author ngonzalez
 */
@Component
public class ModificadorCondicionPrendaRepositoryImpl implements ModificadorCondicionPrendaRepository {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ModificadorCondicionPrendaRepositoryImpl.class);

    /**
     * Referencia al repositorio de {@link HistListadoModificadorCondicionPrendaJPARepository}.
     */
    @Inject
    private HistListadoModificadorCondicionPrendaJPARepository histListadoJpaRepository;

    /**
     * Referencia al repositorio de {@link ListadoModificadorCondicionPrendaJPARepository}
     */
    @Inject
    private ListadoModificadorCondicionPrendaJPARepository listadoJpaRepository;

    /**
     * Referencia al repositorio de {@link ModificadorCondicionPrendaJPARepository}.
     */
    @Inject
    private ModificadorCondicionPrendaJPARepository modificadorCondicionPrendaJPARepository;

    /**
     * Referencia a la fábrica de entidades de tipo {@link ListadoModificadorCondicionPrenda}.
     */
    @Inject
    private ListadoModificadorCondicionPrendaFactory fabricaEntidadesListado;



    // METODOS

    /**
     * {@inheritDoc}
     */
    @Override
    public ModificadorCondicionPrenda consultarModificadorCondicionPrendaVigente(
        @NotNull CondicionPrendaVO condicionPrendaVO) {

        LOGGER.info(">> consultarModificadorCondicionPrendaVigente({})", condicionPrendaVO.toString());

        ModificadorCondicionPrendaJPA modificadorCondicionPrendaJPA =
            modificadorCondicionPrendaJPARepository.findByCondicionPrenda(condicionPrendaVO.getCondicionPrenda());

        if (ObjectUtils.isEmpty(modificadorCondicionPrendaJPA)) {
            String msg = "No existe un modificador - condicion prenda para las caracteristicas solicitadas.";
            LOGGER.warn(msg);
            throw new ModificadorCondicionPrendaNoEncontradoException(msg, ModificadorCondicionPrendaJPA.class);
        }

        return ModificadorCondicionPrendaFactory.crear(
            modificadorCondicionPrendaJPA.getCondicionPrenda(),
            modificadorCondicionPrendaJPA.getFactor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListadoModificadorCondicionPrenda consultarListadoVigente() {
        LOGGER.info(">> consultarListadoVigente()");

        ListadoModificadorCondicionPrendaJPA listadoModificadorCondicionPrendaJPA;

        try {
            listadoModificadorCondicionPrendaJPA = listadoJpaRepository.obtenerListadoVigente();
        } catch (IncorrectResultSizeDataAccessException e) {
            String msg = "Inconsistencia de datos; existe mas de un resultado.";
            LOGGER.error(msg);
            e.printStackTrace();
            throw e;
        }

        if (ObjectUtils.isEmpty(listadoModificadorCondicionPrendaJPA)) {
            String msg = "No existe un listado modificador - condicion prenda vigente.";
            LOGGER.warn(msg);
            throw new ListadoNoEncontradoException(msg, ListadoModificadorCondicionPrendaJPA.class);
        }

        return convertToListadoDeDominio(listadoModificadorCondicionPrendaJPA);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ListadoModificadorCondicionPrenda> consultarListadoPorUltimaActualizacion(
        @NotNull LocalDate ultimaActualizacion) {

        LOGGER.info(">> consultarListadoPorUltimaActualizacion({})", ultimaActualizacion);

        if (DateUtil.isGreaterThanNow(ultimaActualizacion.toDate())) {
            String msg = "La fecha de ultima actualizacion no puede ser una fecha futura.";
            LOGGER.error(msg);
            throw new FechaFuturaException(msg, ListadoModificadorCondicionPrendaJPA.class);
        }

        DateTime fechaInicio = ultimaActualizacion.toDateTimeAtStartOfDay();
        DateTime fechaFin = ultimaActualizacion.toDateTimeAtCurrentTime().millisOfDay().withMaximumValue();

        Set<ListadoModificadorCondicionPrendaJPA> listaVigentes =
            listadoJpaRepository.findByUltimaActualizacionBetween(fechaInicio, fechaFin);
        Set<HistListadoModificadorCondicionPrendaJPA> listaHistoricos =
            histListadoJpaRepository.findByUltimaActualizacionBetween(fechaInicio, fechaFin);

        if (ObjectUtils.isEmpty(listaVigentes) && ObjectUtils.isEmpty(listaHistoricos)) {
            String msg = "No existe un listado modificador - condicion prenda para la fecha indicada.";
            LOGGER.warn(msg);
            throw new ListadoNoEncontradoException(msg, ListadoModificadorCondicionPrendaJPA.class);
        }

        Set<ListadoModificadorCondicionPrenda> result = new HashSet<>();
        if (!ObjectUtils.isEmpty(listaVigentes)) {
            for (ListadoModificadorCondicionPrendaJPA listadoModificadorCondicionPrendaJPA : listaVigentes) {
                result.add(convertToListadoDeDominio(listadoModificadorCondicionPrendaJPA));
            }
        }

        if (!ObjectUtils.isEmpty(listaHistoricos)) {
            for (HistListadoModificadorCondicionPrendaJPA histListadoModificadorCondicionPrendaJPA : listaHistoricos) {
                result.add(convertToListadoDeDominio(histListadoModificadorCondicionPrendaJPA));
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ListadoModificadorCondicionPrenda actualizarListado(
        @NotNull ListadoModificadorCondicionPrenda listado) {

        LOGGER.info(">> actualizarListado({})", listado);

        if (ObjectUtils.isEmpty(listado) ||
            ObjectUtils.isEmpty(listado.getModificadoresCondicionPrenda())) {
            String msg = "El nuevo listado no contiene elementos.";
            LOGGER.error(msg);
            throw new ListadoSinElementosException(msg, ListadoModificadorCondicionPrendaJPA.class);
        }

        ListadoModificadorCondicionPrendaJPA listadoVigente = listadoJpaRepository.obtenerListadoVigente();

        if (!ObjectUtils.isEmpty(listadoVigente)) {

            // SE CONVIERTE EL LISTADO VIGENTE EN HISTÓRICO.
            HistListadoModificadorCondicionPrendaJPA listadoHistorico =
                convertToListadoHistoricoJPA(listadoVigente);
            histListadoJpaRepository.save(listadoHistorico);

            // SE ELIMINA EL LISTADO VIGENTE.
            listadoJpaRepository.delete(listadoVigente.getId());
        }

        // SE CONVIERTE EL LISTADO DE DOMINIO EN VIGENTE.
        ListadoModificadorCondicionPrendaJPA listadoNuevo = convertToListadoVigenteJPA(listado);
        listadoNuevo.setUltimaActualizacion(DateTime.now());
        listadoNuevo.setFechaListado(listado.getFechaListado());
        return convertToListadoDeDominio(listadoJpaRepository.save(listadoNuevo));
    }

    /**
     * Metodo auxiliar utilizado para convertir el listado vigente (JPA) en listado de dominio.
     *
     * @param listado El listado a convertir.
     * @return El listado JPA convertido en listado de dominio.
     */
    private ListadoModificadorCondicionPrenda convertToListadoDeDominio(
        ListadoModificadorCondicionPrendaJPA listado) {

        Set<ModificadorCondicionPrenda> modificadores = new HashSet<>();
        if (!ObjectUtils.isEmpty(listado.getModificadoresCondicionPrenda())) {
            for (ModificadorCondicionPrendaJPA modificador : listado.getModificadoresCondicionPrenda()) {
                modificadores.add(ModificadorCondicionPrendaFactory.crear(
                    modificador.getCondicionPrenda(), modificador.getFactor()));
            }
        }

        return fabricaEntidadesListado.crear(
            listado.getUltimaActualizacion(),
            listado.getFechaListado(),
            modificadores);
    }

    /**
     * Metodo auxiliar utilizado para convertir el listado histórico (JPA) en listado de dominio.
     *
     * @param listado El listado a convertir.
     * @return El listado JPA convertido en listado de dominio.
     */
    private ListadoModificadorCondicionPrenda convertToListadoDeDominio(
        HistListadoModificadorCondicionPrendaJPA listado) {

        Set<ModificadorCondicionPrenda> modificadores = new HashSet<>();
        if (!ObjectUtils.isEmpty(listado.getModificadoresCondicionPrenda())) {
            for (HistModificadorCondicionPrendaJPA modificador : listado.getModificadoresCondicionPrenda()) {
                modificadores.add(ModificadorCondicionPrendaFactory.crear(
                    modificador.getCondicionPrenda(), modificador.getFactor()));
            }
        }

        return fabricaEntidadesListado.crear(
            listado.getUltimaActualizacion(),
            listado.getFechaListado(),
            modificadores);
    }

    /**
     * Metodo auxiliar utilizado para convertir el listado vigente (JPA) en listado histórico (JPA).
     *
     * @param listado El listado a convertir.
     * @return El listado convertido en histórico.
     */
    private HistListadoModificadorCondicionPrendaJPA convertToListadoHistoricoJPA(
        ListadoModificadorCondicionPrendaJPA listado) {

        HistListadoModificadorCondicionPrendaJPA result = new HistListadoModificadorCondicionPrendaJPA();
        result.setUltimaActualizacion(listado.getUltimaActualizacion());
        result.setFechaListado(listado.getFechaListado());

        Set<HistModificadorCondicionPrendaJPA> modificadores = new HashSet<>();
        if (!ObjectUtils.isEmpty(listado.getModificadoresCondicionPrenda())) {
            for (ModificadorCondicionPrendaJPA mcp : listado.getModificadoresCondicionPrenda()) {
                HistModificadorCondicionPrendaJPA mcpHistorico = new HistModificadorCondicionPrendaJPA();
                mcpHistorico.setCondicionPrenda(mcp.getCondicionPrenda());
                mcpHistorico.setFactor(mcp.getFactor());
                modificadores.add(mcpHistorico);
            }
        }

        result.setModificadoresCondicionPrenda(modificadores);
        return result;
    }

    /**
     * Metodo auxiliar utilizado para convertir el listado de dominio en listado vigente (JPA).
     *
     * @param listado El listado a convertir.
     * @return El listado convertido en vigente.
     */
    private ListadoModificadorCondicionPrendaJPA convertToListadoVigenteJPA(
        ListadoModificadorCondicionPrenda listado) {

        ListadoModificadorCondicionPrendaJPA result = new ListadoModificadorCondicionPrendaJPA();
        result.setUltimaActualizacion(listado.getUltimaActualizacion());
        result.setFechaListado(listado.getFechaListado());

        Set<ModificadorCondicionPrendaJPA> modificadores = new HashSet<>();
        if (!ObjectUtils.isEmpty(listado.getModificadoresCondicionPrenda())) {
            for (ModificadorCondicionPrenda mcp : listado.getModificadoresCondicionPrenda()) {
                ModificadorCondicionPrendaJPA mcpVigente = new ModificadorCondicionPrendaJPA();
                mcpVigente.setCondicionPrenda(mcp.getCondicionPrenda());
                mcpVigente.setFactor(mcp.getFactor());
                modificadores.add(mcpVigente);
            }
        }

        result.setModificadoresCondicionPrenda(modificadores);
        return result;
    }

}
