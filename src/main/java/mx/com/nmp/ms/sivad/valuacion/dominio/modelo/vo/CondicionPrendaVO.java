/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo;

/**
 * Value Object con la información de la condición de la prenda.
 *
 * @author ngonzalez
 */
public class CondicionPrendaVO {

    /**
     * Condición física de la prenda.
     */
    private String condicionPrenda;



    // METODOS

    /**
     * Constructor.
     *
     * @param condicionPrenda La condición física de la prenda.
     */
    public CondicionPrendaVO(String condicionPrenda) {
        this.condicionPrenda = condicionPrenda;
    }



    // GETTERS

    public String getCondicionPrenda() {
        return condicionPrenda;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "CondicionPrendaVO{" +
            "condicionPrenda='" + condicionPrenda + '\'' +
            '}';
    }

}
