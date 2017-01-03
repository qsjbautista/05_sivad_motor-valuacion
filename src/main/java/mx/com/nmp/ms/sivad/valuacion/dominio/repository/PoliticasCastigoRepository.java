/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.repository;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.PoliticaCastigoNoEncontradaException;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Interface que proporciona el contrato para recuperar las {@link PoliticasCastigo} vigente y actualizar el listado.
 * @see PoliticasCastigo
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface PoliticasCastigoRepository {
    /**
     * Consulta las Políticas de castigo vigentes al momento de la consulta.
     *
     * @return Políticas de castigo vigentes.
     *
     * @throws PoliticaCastigoNoEncontradaException Si no existen Políticas de castigo vigentes.
     */
    PoliticasCastigo consultar();

    /**
     * Permite actualizar el listado de Políticas de castigo.
     *
     * @param entidad Políticas de castigo con la cual se desea reemplazar las vigentes.
     *
     * @throws IllegalArgumentException Si {@code politicasCastigo} es {@literal null}
     */
    void actualizar(@NotNull PoliticasCastigo entidad);

    /**
     * Permite consultar el listado de Políticas de castigo con base en una fecha de vigencia.
     *
     * @param fecha Fecha de vigencia.
     *
     * @return Lista de Políticas de castigo en la fecha especificada.
     *
     * @throws IllegalArgumentException Si {@code fecha} es {@literal null}
     * @throws PoliticaCastigoNoEncontradaException Si no existen Políticas de castigo en la fecha especificada.
     */
    List<PoliticasCastigo> consultar(@NotNull DateTime fecha);
}
