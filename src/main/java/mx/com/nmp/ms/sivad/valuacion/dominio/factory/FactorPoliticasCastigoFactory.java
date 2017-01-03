/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Interface que define el contrato para la fabricación de Value Object tipo FactorPoliticasCastigo
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
    Map<Class<? extends Pieza>, BigDecimal> crearCon(
        @NotNull BigDecimal factorDiamante, @NotNull BigDecimal factorAlhaja, @NotNull BigDecimal factorComplemento);
}
