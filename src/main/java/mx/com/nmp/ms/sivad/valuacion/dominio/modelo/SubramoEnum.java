/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

/**
 * Enum que define los subramos.
 *
 * @author ecancino
 */
public enum SubramoEnum {

    ALHAJAS("AL");

    /**
     * Abreviatura del subramo.
     */
    private String abr;



    // METODOS

    /**
     * Constructor.
     *
     * @param abr Abreviatura del subramo.
     */
    SubramoEnum(String abr) {
        this.abr = abr;
    }



    // GETTERS

    public String getAbr() {
        return abr;
    }

}
