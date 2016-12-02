/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo;

import mx.com.nmp.ms.sivad.valuacion.infrastructure.estrategia.RedondeoEstrategiaUtil;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Value Object que contendrá el resultado de la valuación de una pieza.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class Avaluo {
    /**
     * Valor mínimo del avaluó.
     */
    private BigDecimal valorMinimo;

    /**
     * Valor promedio del avaluó.
     */
    private BigDecimal valorPromedio;

    /**
     * Valor máximo del avaluó.
     */
    private BigDecimal valorMaximo;

    /**
     * Constructor.
     *
     * @param valorMinimo Valor mínimo del avaluó.
     * @param valorPromedio Valor promedio del avaluó.
     * @param valorMaximo Valor máximo del avaluó.
     */
    public Avaluo(BigDecimal valorMinimo, BigDecimal valorPromedio, BigDecimal valorMaximo) {
        super();

        this.valorMinimo = valorMinimo;
        this.valorPromedio = valorPromedio;
        this.valorMaximo = valorMaximo;
    }

    /**
     * Recupera el valor mínimo del avaluó.
     *
     * @return Valor mínimo del avaluó.
     */
    public BigDecimal valorMinimo() {
        return RedondeoEstrategiaUtil.get().redondear(valorMinimo);
    }

    /**
     * Recupera el valor promedio del avaluó.
     *
     * @return Valor promedio del avaluó.
     */
    public BigDecimal valorPromedio() {
        return RedondeoEstrategiaUtil.get().redondear(valorPromedio);
    }

    /**
     * Recupera el valor máximo del avaluó.
     *
     * @return Valor máximo del avaluó.
     */
    public BigDecimal valorMaximo() {
        return RedondeoEstrategiaUtil.get().redondear(valorMaximo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Avaluo avaluo = (Avaluo) o;

        return Objects.equals(valorMinimo, avaluo.valorMinimo) &&
            Objects.equals(valorPromedio, avaluo.valorPromedio) &&
            Objects.equals(valorMaximo, avaluo.valorMaximo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(valorMinimo, valorPromedio, valorMaximo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("Avaluo{valorMinimo=%s, valorPromedio=%s, valorMaximo=%s}",
            valorMinimo, valorPromedio, valorMaximo);
    }
}
