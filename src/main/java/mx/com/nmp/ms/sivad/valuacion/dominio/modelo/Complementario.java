/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Clase que implementa la interface {@link PiezaValuable}, ésta clase representa un valor
 * complementario y encapsula la lógica para valuar este tipo de piezas.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class Complementario implements PiezaValuable {
    /**
     * Contiene el valor estimado por un experto.
     */
    private BigDecimal valorExperto;

    /**
     * Valor de éste Valor Complementario.
     */
    private Avaluo avaluo;

    /**
     * Representa la interface publica usada para crear objetos tipo {@link Complementario}
     * Nos brinda el contrato que se debe cumplir para crear una instancia de la clase {@link Complementario}
     */
    public interface Builder {
        /**
         * Recupera el valor estimado por un experto.
         *
         * @return Valor estimado por un experto.
         */
        BigDecimal getValorExperto();
    }

    /**
     * Constructor.
     *
     * @param builder Objeto con los datos necesarios para construir la instancia.
     */
    public Complementario(Builder builder) {
        super();

        valorExperto = builder.getValorExperto();
    }

    /**
     * Permite realizar la valuación de un pieza del tipo {@link Complementario}
     *
     * @return Valor del Valor Complementario valuado.
     */
    @Override
    public Avaluo valuar() {
        avaluo = new Avaluo(valorExperto, valorExperto, valorExperto);

        return avaluo;
    }

    /**
     * Recupera el valor del Valor Complementario valuado.
     *
     * @return Valor del Valor Complementario valuado.
     */
    public Avaluo avaluo() {
        return avaluo;
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

        Complementario that = (Complementario) o;

        return Objects.equals(valorExperto, that.valorExperto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(valorExperto);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format("Complementario{valorExperto=%s}", valorExperto);
    }
}
