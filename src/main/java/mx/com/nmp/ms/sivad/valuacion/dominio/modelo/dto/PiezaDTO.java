package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;

/**
 * Created by Quarksoft on 25/12/2016.
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
