/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.FactorPoliticasCastigo;
import org.joda.time.DateTime;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link PoliticasCastigo}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface PoliticasCastigoFactory {
    /**
     * Crea una entidad a partir del valor de los argumentos.
     *
     * @param factorPoliticasCastigo Objeto Value Object con los factores aplicables
     *                               a diamantes, alhajas y complementario.
     * @param fechaListado Fecha de vigencia de las políticas de castigo.
     *
     * @return La entidad creada.
     *
     * @throws IllegalArgumentException Cuando algún argumento sea {@literal null}
     */
    PoliticasCastigo crearCon(@NotNull FactorPoliticasCastigo factorPoliticasCastigo, @NotNull DateTime fechaListado);

    /**
     * Crea una entidad a partir del valor de los argumentos. a esta entidad le será inyectado
     * el repositorio de entidades para que se pueda persistir.
     *
     * @param factorPoliticasCastigo Objeto Value Object con los factores aplicables
     *                               a diamantes, alhajas y complementario.
     * @param fechaListado Fecha de vigencia de las políticas de castigo.
     *
     * @return La entidad creada.
     *
     * @throws IllegalArgumentException Cuando algún argumento sea {@literal null}
     */
    PoliticasCastigo crearPersistibleCon(
        @NotNull FactorPoliticasCastigo factorPoliticasCastigo, @NotNull DateTime fechaListado);

    /**
     * Crea una entidad a partir de un objeto constructor.
     *
     * @param builder Objeto que contiene la información necesaria para crear la entidad.
     *
     * @return La entidad creada.
     *
     * @throws IllegalArgumentException Cuando {@link PoliticasCastigo.Builder} sea {@literal null} o algún
     * valor sea {@literal null}
     */
    PoliticasCastigo crearDesde(@NotNull PoliticasCastigo.Builder builder);

    /**
     * Crea una entidad a partir de un objeto constructor. a esta entidad le será inyectado
     * el repositorio de entidades para que se pueda persistir.
     *
     * @param builder Objeto que contiene la información necesaria para crear la entidad.
     *
     * @return La entidad creada.
     *
     * @throws IllegalArgumentException Cuando {@link PoliticasCastigo.Builder} sea {@literal null} o algún
     * valor sea {@literal null}
     */
    PoliticasCastigo crearPersistibleDesde(@NotNull PoliticasCastigo.Builder builder);
}
