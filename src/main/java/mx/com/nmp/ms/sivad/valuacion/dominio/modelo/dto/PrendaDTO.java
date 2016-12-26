/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto;

import java.util.List;

/**
 * DTO con la información de la prenda.
 *
 * @author ngonzalez
 */
public class PrendaDTO {

    /**
     * Lista de piezas de las que se compone la prenda.
     */
    private List<PiezaDTO> piezas;



    // METODOS

    /**
     * Constructor.
     *
     * @param piezas La lista de piezas de las que se compone la prenda.
     */
    public PrendaDTO(List<PiezaDTO> piezas) {
        super();
        this.piezas = piezas;
    }



    // GETTERS Y SETTERS

    public List<PiezaDTO> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<PiezaDTO> piezas) {
        this.piezas = piezas;
    }

}
