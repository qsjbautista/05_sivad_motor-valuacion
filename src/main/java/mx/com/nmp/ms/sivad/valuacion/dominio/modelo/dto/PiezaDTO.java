/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;

/**
 * DTO abstracto que encapsula las operaciones y atributos comunes de las piezas.
 *
 * @author ngonzalez
 */
public abstract class PiezaDTO {

    /**
     * Número de piezas con características idénticas.
     */
    protected int numeroDePiezas;

    /**
     * El valor experto para la pieza en particular.
     */
    protected ValorExperto valorExperto;



    // GETTERS Y SETTERS

    public int getNumeroDePiezas() {
        return numeroDePiezas;
    }

    public void setNumeroDePiezas(int numeroDePiezas) {
        this.numeroDePiezas = numeroDePiezas;
    }

    public ValorExperto getValorExperto() {
        return valorExperto;
    }

    public void setValorExperto(ValorExperto valorExperto) {
        this.valorExperto = valorExperto;
    }

}
