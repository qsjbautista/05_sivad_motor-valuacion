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

    /**
     * Identificador de la condición fisica de la prenda.
     */
    private String condicionFisica;

    /**
     * Abreviatura del subramo
     */
    private String subramo;

    /**
     * Identificador de la sucursal
     */
    private Long sucursal;

    // METODOS

    /**
     * Constructor.
     *
     * @param piezas La lista de piezas de las que se compone la prenda.
     * @param  condicionFisica Identificador de la condición fisica de la prenda.
     * @param subramo Abreviatura del subramo
     * @param sucursal Identificador de la sucursal
     */
    public PrendaDTO(List<PiezaDTO> piezas, String condicionFisica, String subramo,
                     Long sucursal) {
        super();
        this.piezas = piezas;
        this.condicionFisica = condicionFisica;
        this.subramo = subramo;
        this.sucursal = sucursal;
    }



    // GETTERS Y SETTERS

    public List<PiezaDTO> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<PiezaDTO> piezas) {
        this.piezas = piezas;
    }

    /**
     * Recupera el valor de {@code condicionFisica}
     *
     * @return Valor de {@code condicionFisica}
     */
    public String getCondicionFisica() {
        return condicionFisica;
    }

    /**
     * Establece el nuevo valor de {@code condicionFisica}
     *
     * @param condicionFisica Nuevo valor de {@code condicionFisica}
     */
    public void setCondicionFisica(String condicionFisica) {
        this.condicionFisica = condicionFisica;
    }

    public String getSubramo() {
        return subramo;
    }

    public void setSubramo(String subramo) {
        this.subramo = subramo;
    }

    public Long getSucursal() {
        return sucursal;
    }

    public void setSucursal(Long sucursal) {
        this.sucursal = sucursal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "PrendaDTO{" +
            "piezas=" + ((piezas != null) ? piezas.size() : "null") +
            '}';
    }

}
