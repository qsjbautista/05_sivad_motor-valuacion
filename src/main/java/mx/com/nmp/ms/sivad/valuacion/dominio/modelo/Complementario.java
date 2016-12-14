/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AvaluoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Clase que implementa la interface {@link PiezaValuable}, ésta clase representa un valor
 * complementario y encapsula la lógica para valuar este tipo de piezas.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class Complementario extends Pieza {
    private static final Logger LOGGER = LoggerFactory.getLogger(Complementario.class);

    /**
     * Contiene el valor estimado por un experto.
     */
    private ValorExperto valorExperto;

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
        ValorExperto getValorExperto();
    }

    /**
     * Constructor.
     *
     * @param builder Objeto con los datos necesarios para construir la instancia.
     */
    private Complementario(Builder builder) {
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
        LOGGER.info(">> valuar()");

        if (ObjectUtils.isEmpty(avaluo)) {
            realizarValuacion();
        }

        LOGGER.debug("<< {}", avaluo);

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

    /**
     * Realiza el cálculo para obtener el precio de éste complemento.
     */
    private void realizarValuacion() {
        BigDecimal avaluoComplemento;

        if (ValorExperto.TipoEnum.UNITARIO.equals(valorExperto.getTipo())) {
            BigDecimal numeroComplementos = BigDecimal.valueOf(numeroDePiezas);
            avaluoComplemento = valorExperto.getValor().multiply(numeroComplementos);
        } else {
            avaluoComplemento = valorExperto.getValor();
        }

        avaluo = AvaluoFactory.crearCon(avaluoComplemento, avaluoComplemento, avaluoComplemento);
    }
}
