/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;

import java.math.BigDecimal;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link Diamante}.
 *
 * @author ngonzalez
 */
public interface DiamanteFactory {

    /**
     * Permite crear una entidad de tipo {@link Diamante} con base en los argumentos recibidos.
     *
     * @param numeroDePiezas El número de piezas de tipo {@link Diamante} con características idénticas.
     * @param corte El tipo de corte del diamante.
     * @param color El tipo de color del diamante.
     * @param claridad El tipo de claridad del diamante.
     * @param quilates El valor en quilates del diamante.
     * @param certificadoDiamante El valor del certificado del diamante.
     * @param valorExperto El valor experto para la pieza en particular.
     * @return La entidad creada.
     */
    public Diamante create(int numeroDePiezas,
                           String corte,
                           String color,
                           String claridad,
                           BigDecimal quilates,
                           String certificadoDiamante,
                           ValorExperto valorExperto);

    /**
     * Permite crear una entidad de tipo {@link Diamante} con base en el builder recibido.
     *
     * @param builder El Builder con los atributos del diamante.
     * @return La entidad creada.
     */
    public Diamante create(Diamante.Builder builder);

}
