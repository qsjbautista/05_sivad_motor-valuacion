/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

/**
 * Enum que define los tipos de metal.
 *
 * @author ngonzalez
 */
public enum TipoMetalEnum {

    ORO("AU");

    /**
     * Tipo de metal.
     */
    private String tipo;



    // METODOS

    /**
     * Constructor.
     *
     * @param tipo El tipo de metal.
     */
    TipoMetalEnum(String tipo) {
        this.tipo = tipo;
    }



    // GETTERS

    public String getTipo() {
        return tipo;
    }

}
