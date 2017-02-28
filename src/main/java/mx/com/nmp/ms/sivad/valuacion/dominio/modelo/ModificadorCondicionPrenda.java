/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import java.math.BigDecimal;

/**
 * Entidad que representa la relación entre la condición física de la prenda y el factor que influye dicha
 * condición en el cálculo del avalúo.
 *
 * @author ngonzalez
 */
public class ModificadorCondicionPrenda {

    /**
     * Condición física de la prenda.
     */
    private String condicionPrenda;

    /**
     * Factor.
     */
    private BigDecimal factor;



    // METODOS

    /**
     * Constructor.
     *
     * @param condicionPrenda La condición física de la prenda.
     * @param factor El factor.
     */
    public ModificadorCondicionPrenda(String condicionPrenda, BigDecimal factor) {
        this.condicionPrenda = condicionPrenda;
        this.factor = factor;
    }



    // GETTERS

    public String getCondicionPrenda() {
        return condicionPrenda;
    }

    public BigDecimal getFactor() {
        return factor;
    }

}
