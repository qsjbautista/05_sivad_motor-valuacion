/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;

/**
 * DTO con la información de una pieza complementaria.
 *
 * @author ngonzalez
 */
public class ComplementarioDTO extends PiezaDTO {



    // METODOS

    /**
     * Constructor.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param valorExperto El valor estimado por un experto.
     */
    public ComplementarioDTO(int numeroDePiezas,
                             ValorExperto valorExperto) {
        super();
        this.numeroDePiezas = numeroDePiezas;
        this.valorExperto = valorExperto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ComplementarioDTO{" +
            "numeroDePiezas='" + numeroDePiezas +
            ", valorExperto='" + ((valorExperto != null) ? valorExperto.toString() : "null") +
            '}';
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComplementarioDTO)) return false;

        ComplementarioDTO that = (ComplementarioDTO) o;

        if (numeroDePiezas != that.numeroDePiezas) return false;
        if (!valorExperto.equals(that.valorExperto)) return false;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int result = valorExperto.hashCode();
        return result;
    }

}
