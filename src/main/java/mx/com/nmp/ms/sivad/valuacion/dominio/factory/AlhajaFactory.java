/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;

import java.math.BigDecimal;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link Alhaja}.
 *
 * @author ngonzalez
 */
public interface AlhajaFactory {

    /**
     * Permite crear una entidad de tipo {@link Alhaja} con base en los argumentos recibidos.
     *
     * @param metal El tipo de metal de la alhaja.
     * @param color El color del metal.
     * @param calidad La calidad de la alhaja.
     * @param rango El rango de la alhaja.
     * @param peso El peso en gramos de la alhaja.
     * @param incremento El incremento por las condiciones físicas de la prenda.
     * @param desplazamiento El desplazamiento comercial.
     * @param valorExperto El valor experto para la pieza en particular.
     * @return La entidad creada.
     */
    public Alhaja create(String metal,
                         String color,
                         String calidad,
                         String rango,
                         BigDecimal peso,
                         BigDecimal incremento,
                         BigDecimal desplazamiento,
                         ValorExperto valorExperto);

    /**
     * Permite crear una entidad de tipo {@link Alhaja} con base en el builder recibido.
     *
     * @param builder El Builder con los atributos de la alhaja.
     * @return La entidad creada.
     */
    public Alhaja create(Alhaja.Builder builder);

}
