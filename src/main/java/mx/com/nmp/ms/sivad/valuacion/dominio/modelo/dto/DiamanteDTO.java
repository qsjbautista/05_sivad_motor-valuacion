/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;

import java.math.BigDecimal;

/**
 * DTO con la información de una pieza de tipo diamante.
 *
 * @author ngonzalez
 */
public class DiamanteDTO extends PiezaDTO {

    /**
     * El tipo de corte del diamante con base en el catálogo de cortes.
     */
    private String corte;

    /**
     * El tipo de color del diamante con base en la clasificación GIA.
     */
    private String color;

    /**
     * El tipo de claridad del diamante con base en la clasificación GIA.
     */
    private String claridad;

    /**
     * El valor en quilates del diamante.
     */
    private BigDecimal quilates;

    /**
     * El valor del certificado del diamante.
     */
    private String certificadoDiamante;



    // METODOS

    /**
     * Constructor.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param corte El tipo de corte del diamante.
     * @param color El tipo de color del diamante.
     * @param claridad El tipo de claridad del diamante.
     * @param quilates El valor en quilates del diamante.
     * @param certificadoDiamante El valor del certificado del diamante.
     * @param valorExperto El valor experto para la pieza en particular.
     */
    public DiamanteDTO(int numeroDePiezas,
                       String corte,
                       String color,
                       String claridad,
                       BigDecimal quilates,
                       String certificadoDiamante,
                       ValorExperto valorExperto) {
        super();
        this.numeroDePiezas = numeroDePiezas;
        this.corte = corte;
        this.color = color;
        this.claridad = claridad;
        this.quilates = quilates;
        this.certificadoDiamante = certificadoDiamante;
        this.valorExperto = valorExperto;
    }



    // GETTERS Y SETTERS

    public String getCorte() {
        return corte;
    }

    public void setCorte(String corte) {
        this.corte = corte;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getClaridad() {
        return claridad;
    }

    public void setClaridad(String claridad) {
        this.claridad = claridad;
    }

    public BigDecimal getQuilates() {
        return quilates;
    }

    public void setQuilates(BigDecimal quilates) {
        this.quilates = quilates;
    }

    public String getCertificadoDiamante() {
        return certificadoDiamante;
    }

    public void setCertificadoDiamante(String certificadoDiamante) {
        this.certificadoDiamante = certificadoDiamante;
    }

}
