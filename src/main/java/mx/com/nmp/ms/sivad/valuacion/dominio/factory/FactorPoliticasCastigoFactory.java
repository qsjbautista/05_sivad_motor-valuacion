/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.FactorPoliticasCastigo;

import java.math.BigDecimal;

/**
 * Interface que define el contrato para la fabricación de Value Object tipo {@link FactorPoliticasCastigo}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface FactorPoliticasCastigoFactory {
    /**
     * Crea un Value Object a partir del valor de los argumentos.
     *
     * @param factorDiamante Factor de castigo para diamantes.
     * @param factorAlhaja Factor de castigo para alhajas.
     * @param factorComplemento Factor de castigo para complementos.
     *
     * @return El Value Object creado.
     *
     * @throws IllegalArgumentException  Cuando algún argumento, {@code factorDiamante}, {@code factorAlhaja},
     * {@code factorComplemento} sea {@literal null} o cero.
     */
    FactorPoliticasCastigo crearCon(@NotNull BigDecimal factorDiamante,
                                    @NotNull BigDecimal factorAlhaja, @NotNull BigDecimal factorComplemento);

    /**
     * Crea una Value Object a partir de un objeto constructor.
     *
     * @param builder Objeto que contiene la información necesaria para crear el Value Object.
     *
     * @return El Value Object creado.
     *
     * @throws IllegalArgumentException Cuando {@link FactorPoliticasCastigo.Builder} es {@literal null} o algún valor
     * sea negativo o cero.
     */
    FactorPoliticasCastigo crearDesde(@NotNull FactorPoliticasCastigo.Builder builder);
}
