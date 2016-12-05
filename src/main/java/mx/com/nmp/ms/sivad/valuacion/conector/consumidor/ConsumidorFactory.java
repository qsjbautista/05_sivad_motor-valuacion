/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.consumidor;

import mx.com.nmp.ms.sivad.referencia.ws.diamantes.datatypes.ValorComercial;

import java.math.BigDecimal;

/**
 * Fabrica para crear consumidores de resultados.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public final class ConsumidorFactory {
    /**
     * Constructor. Privado para que no se puedan crear instancias.
     */
    private ConsumidorFactory() {
        super();
    }

    /**
     * Crea un objeto para consumir las respuestas tipo {@link BigDecimal}
     *
     * @param valor Valor que será consumido por los componentes interesados.
     *
     * @return BigDecimalConsumidor Objeto que contiene la informción de la respuesta.
     */
    public static BigDecimalConsumidor crearBigDecimalConsumidor(final BigDecimal valor) {
        return new BigDecimalConsumidor() {
            @Override
            public BigDecimal getValor() {
                return valor;
            }
        };
    }

    /**
     * Crea un objeto para consumir las respuestas tipo {@link ValorComercial}
     *
     * @param valorMinimo Valor que será consumido por los componentes interesados.
     * @param valorMedio Valor que será consumido por los componentes interesados.
     * @param valorMaximo Valor que será consumido por los componentes interesados.
     *
     * @return ValorComercialConsumidor Objeto que contiene la informción de la respuesta.
     */
    public static ValorComercialConsumidor crearValorComercialConsumidor(
        final BigDecimal valorMinimo, final BigDecimal valorMedio, final BigDecimal valorMaximo) {
        return new ValorComercialConsumidor() {
            @Override
            public BigDecimal getValorMinimo() {
                return valorMinimo;
            }

            @Override
            public BigDecimal getValorMedio() {
                return valorMedio;
            }

            @Override
            public BigDecimal getValorMaximo() {
                return valorMaximo;
            }
        };
    }
}
