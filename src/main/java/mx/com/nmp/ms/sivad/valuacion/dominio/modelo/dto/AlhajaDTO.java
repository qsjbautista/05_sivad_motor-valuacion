/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;

import java.math.BigDecimal;

/**
 * DTO con la información de una pieza de tipo alhaja.
 *
 * @author ngonzalez
 */
public class AlhajaDTO extends PiezaDTO {

    /**
     * Metal de la alhaja.
     */
    private String metal;

    /**
     * Color del metal.
     */
    private String color;

    /**
     * Calidad de la alhaja.
     */
    private String calidad;

    /**
     * Rango de la alhaja.
     */
    private String rango;

    /**
     * Peso en gramos de la alhaja.
     */
    private BigDecimal peso;

    /**
     * Incremento por condiciones físicas de la prenda.
     */
    private BigDecimal incremento;

    /**
     * Desplazamiento comercial.
     */
    private BigDecimal desplazamiento;



    // METODOS

    /**
     * Constructor.
     *
     * @param metal El tipo de metal de la alhaja.
     * @param color El color del metal.
     * @param calidad La calidad de la alhaja.
     * @param rango El rango de la alhaja.
     * @param peso El peso en gramos de la alhaja.
     * @param incremento El incremento por las condiciones físicas de la prenda.
     * @param desplazamiento El desplazamiento comercial.
     * @param valorExperto El valor experto para la pieza en particular.
     */
    public AlhajaDTO(String metal,
                     String color,
                     String calidad,
                     String rango,
                     BigDecimal peso,
                     BigDecimal incremento,
                     BigDecimal desplazamiento,
                     ValorExperto valorExperto) {
        super();
        this.metal = metal;
        this.color = color;
        this.calidad = calidad;
        this.rango = rango;
        this.peso = peso;
        this.incremento = incremento;
        this.desplazamiento = desplazamiento;
        this.valorExperto = valorExperto;
    }



    // GETTERS Y SETTERS

    public String getMetal() {
        return metal;
    }

    public void setMetal(String metal) {
        this.metal = metal;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCalidad() {
        return calidad;
    }

    public void setCalidad(String calidad) {
        this.calidad = calidad;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public BigDecimal getIncremento() {
        return incremento;
    }

    public void setIncremento(BigDecimal incremento) {
        this.incremento = incremento;
    }

    public BigDecimal getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(BigDecimal desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

}
