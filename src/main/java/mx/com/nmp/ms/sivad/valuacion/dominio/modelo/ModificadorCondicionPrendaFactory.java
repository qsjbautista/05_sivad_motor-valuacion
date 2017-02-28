/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import java.math.BigDecimal;

/**
 * Fábrica que se encarga de crear objetos de tipo {@link ModificadorCondicionPrenda}.
 *
 * @author ngonzalez
 */
public class ModificadorCondicionPrendaFactory {



    // METODOS

    /**
     * Permite crear una entidad de tipo {@link ModificadorCondicionPrenda} con base en los argumentos recibidos.
     *
     * @param condicionPrenda La condición física de la prenda.
     * @param factor El factor.
     * @return La entidad {@link ModificadorCondicionPrenda} creada.
     */
    public static ModificadorCondicionPrenda crear(String condicionPrenda, BigDecimal factor) {
        return new ModificadorCondicionPrenda(condicionPrenda, factor);
    }

}
