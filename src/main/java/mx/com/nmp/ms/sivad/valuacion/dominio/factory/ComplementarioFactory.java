/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link Complementario}.
 *
 * @author ngonzalez
 */
public interface ComplementarioFactory {

    /**
     * Permite crear una entidad de tipo {@link Complementario} con base en los argumentos recibidos.
     *
     * @param numeroDePiezas El número de piezas de tipo {@link Complementario} con características idénticas.
     * @param valorExperto El valor estimado por un experto.
     * @return La entidad creada.
     */
    public Complementario create(int numeroDePiezas,
                                 ValorExperto valorExperto);

    /**
     * Permite crear una entidad de tipo {@link Complementario} con base en el builder recibido.
     *
     * @param builder El Builder con los atributos del complementario.
     * @return La entidad creada.
     */
    public Complementario create(Complementario.Builder builder);

}
