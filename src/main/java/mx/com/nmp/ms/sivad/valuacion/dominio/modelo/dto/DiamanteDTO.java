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
 * @author ngonzalez, ecancino
 */
public class DiamanteDTO extends PiezaDTO {

    /**
     * El tipo de corte del diamante con base en el catálogo de cortes.
     */
    private String corte;

    /**
     * El tipo de corte hijo del diamante con base en el catálogo de cortes.
     */
    private String subcorte;

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

    /**
     * Rango inferior del peso del diamante.
     */
    private BigDecimal quilatesDesde;

    /**
     * Rango superior del peso del diamante.
     */
    private BigDecimal quilatesHasta;



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

    /**
     * Constructor.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param corte El tipo de corte del diamante.
     * @param subcorte El tipo de corte hijo del diamante.
     * @param color El tipo de color del diamante.
     * @param claridad El tipo de claridad del diamante.
     * @param quilates El valor en quilates del diamante.
     * @param certificadoDiamante El valor del certificado del diamante.
     * @param valorExperto El valor experto para la pieza en particular.
     * @param quilatesDesde Rango inferior del peso del diamante.
     * @param quilatesHasta Rango superior del peso del diamante.
     */
    public DiamanteDTO(int numeroDePiezas,
                       String corte,
                       String subcorte,
                       String color,
                       String claridad,
                       BigDecimal quilates,
                       String certificadoDiamante,
                       ValorExperto valorExperto,
                       BigDecimal quilatesDesde,
                       BigDecimal quilatesHasta) {
        super();
        this.numeroDePiezas = numeroDePiezas;
        this.corte = corte;
        this.subcorte = subcorte;
        this.color = color;
        this.claridad = claridad;
        this.quilates = quilates;
        this.certificadoDiamante = certificadoDiamante;
        this.valorExperto = valorExperto;
        this.quilatesDesde = quilatesDesde;
        this.quilatesHasta = quilatesHasta;
    }



    // GETTERS Y SETTERS

    public String getCorte() {
        return corte;
    }

    public void setCorte(String corte) {
        this.corte = corte;
    }

    public String getSubcorte() {
        return subcorte;
    }

    public void setSubcorte(String subcorte) {
        this.subcorte = subcorte;
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

    public BigDecimal getQuilatesDesde() {
        return quilatesDesde;
    }

    public void setQuilatesDesde(BigDecimal quilatesDesde) {
        this.quilatesDesde = quilatesDesde;
    }

    public BigDecimal getQuilatesHasta() {
        return quilatesHasta;
    }

    public void setQuilatesHasta(BigDecimal quilatesHasta) {
        this.quilatesHasta = quilatesHasta;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "DiamanteDTO{" +
            "numeroDePiezas='" + numeroDePiezas +
            ", corte='" + corte + '\'' +
            ", subcorte='" + subcorte + '\'' +
            ", color='" + color + '\'' +
            ", claridad='" + claridad + '\'' +
            ", quilates=" + ((quilates != null) ? quilates.toString() : "null") +
            ", certificadoDiamante='" + certificadoDiamante + '\'' +
            ", valorExperto='" + ((valorExperto != null) ? valorExperto.toString() : "null") +
            ", quilatesDesde=" + ((quilatesDesde != null) ? quilatesDesde.toString() : "null") +
            ", quilatesHasta=" + ((quilatesHasta != null) ? quilatesHasta.toString() : "null") +
            '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiamanteDTO)) return false;

        DiamanteDTO that = (DiamanteDTO) o;

        if (numeroDePiezas != that.numeroDePiezas) return false;
        if (!certificadoDiamante.equals(that.certificadoDiamante)) return false;
        if (!claridad.equals(that.claridad)) return false;
        if (!color.equals(that.color)) return false;
        if (!corte.equals(that.corte)) return false;
        if (!subcorte.equals(that.subcorte)) return false;
        if (!quilates.equals(that.quilates)) return false;
        if (!quilatesDesde.equals(that.quilatesDesde)) return false;
        if (!quilatesHasta.equals(that.quilatesHasta)) return false;
        if (!valorExperto.equals(that.valorExperto)) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = corte.hashCode();
        result = 31 * result + subcorte.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + claridad.hashCode();
        result = 31 * result + quilates.hashCode();
        result = 31 * result + certificadoDiamante.hashCode();
        result = 31 * result + quilatesDesde.hashCode();
        result = 31 * result + quilatesHasta.hashCode();
        result = 31 * result + valorExperto.hashCode();
        return result;
    }

}
