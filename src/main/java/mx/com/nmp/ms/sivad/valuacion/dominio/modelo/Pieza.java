/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;

import java.math.BigDecimal;

/**
 * Clase abstracta que implementa la interface {@link PiezaValuable}, la cual encapsula las operaciones y
 * atributos comunes de las piezas.
 *
 * @author ngonzalez
 */
public abstract class Pieza implements PiezaValuable {

    /**
     * Número de piezas con características idénticas.
     */
    protected int numeroDePiezas = 1;

    /**
     * Avalúo de la pieza.
     */
    protected Avaluo avaluo;

    /**
     * Avalúo de la pieza con las politicas de castigo o factor de participacion aplicado.
     */
    protected Avaluo avaluoPoliticas;

    /**
     * Avalúo técnico o costo del metal
     */
    protected BigDecimal avaluoTecnico;

    /**
     * Avalúo comercial o valor comercial
     */
    protected BigDecimal avaluoComercial;

    // GETTERS

    public int getNumeroDePiezas() {
        return numeroDePiezas;
    }

    public Avaluo getAvaluo() {
        if (avaluo == null) {
            avaluo = valuar();
        }

        return avaluo;
    }

    public void setAvaluoPoliticas(Avaluo avaluoPoliticas) {
        this.avaluoPoliticas = avaluoPoliticas;
    }

    public Avaluo getAvaluoPoliticas() {
        return avaluoPoliticas;
    }

    public void setAvaluoTecnico(BigDecimal avaluoTecnico) {
        this.avaluoTecnico = avaluoTecnico;
    }

    public BigDecimal getAvaluoTecnico() {
        return avaluoTecnico;
    }

    public void setAvaluoComercial(BigDecimal avaluoComercial) {
        this.avaluoComercial = avaluoComercial;
    }

    public BigDecimal getAvaluoComercial() {
        return avaluoComercial;
    }
}
