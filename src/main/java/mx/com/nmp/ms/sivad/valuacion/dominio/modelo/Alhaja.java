/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaAlhajas;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasGramoOroProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.MetalCalidadRangoProveedor;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * Clase que implementa la interface {@link PiezaValuable}, ésta clase representa una Alhaja y encapsula
 * la lógica para valuar este tipo de piezas.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class Alhaja implements PiezaValuable,
        CaracteristicasGramoOroProveedor, MetalCalidadRangoProveedor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Alhaja.class);

    /**
     * Identificador del metal Oro.
     */
    private static final String IDENTIFICADOR_METAL_ORO = "AU";

    /**
     * Referencia hacia el conector con el sistema de tablas de referencia.
     */
    private TablasDeReferenciaAlhajas conector;

    /**
     * Metal de la alhaja.
     */
    private String metal;

    /**
     * Color del metal.
     */
    private String color;

    /**
     * Calidad de la alhaja.
     */
    private String calidad;

    /**
     * Rango de la alhaja.
     */
    private String rango;

    /**
     * Peso en gramos de la alhaja.
     */
    private BigDecimal peso;

    /**
     * Incremento por condiciones fisicas de la prenda.
     */
    private BigDecimal incremento;

    /**
     * Desplazamiento comercial.
     */
    private BigDecimal desplazamiento;

    /**
     * Valor de ésta alhaja.
     */
    private Avaluo avaluo;

    /**
     * Representa la interface publica usada para crear objetos tipo {@link Alhaja}
     * Nos brinda el contrato que se debe cumplir para crear una instancia de la clase {@link Alhaja}
     */
    public interface Builder {
        /**
         * Recupera el metal de la alhaja.
         *
         * @return Metal de la alhaja.
         */
        String getMetal();

        /**
         * Recupera el color del metal.
         *
         * @return Color del metal.
         */
        String getColor();

        /**
         * Recupera la calidad de la alhaja.
         *
         * @return Calidad de la alhaja.
         */
        String getCalidad();

        /**
         * Recupera el rango de la alhaja.
         *
         * @return Rango de la alhaja.
         */
        String getRango();

        /**
         * Recupera el incremento por condiciones fisicas de la prenda.
         *
         * @return Incremento por condiciones fisicas de la prenda.
         */
        BigDecimal getPeso();

        /**
         * Recupera el peso en gramos de la alhaja.
         *
         * @return Peso en gramos de la alhaja.
         */
        BigDecimal getIncremento();

        /**
         * Recupera el desplazamiento comercial.
         *
         * @return Desplazamiento comercial.
         */
        BigDecimal getDesplazamiento();
    }

    /**
     * Constructor.
     *
     * @param builder Objeto con los datos necesarios para construir la instancia.
     * @param conector Referencia hacia el conector con el sistema de tablas de referencia.
     */
    public Alhaja(Builder builder, TablasDeReferenciaAlhajas conector) {
        super();

        metal = builder.getMetal();
        color = builder.getColor();
        calidad = builder.getCalidad();
        rango = builder.getRango();

        peso = builder.getPeso();
        incremento = builder.getIncremento();
        desplazamiento = builder.getDesplazamiento();

        this.conector = conector;
    }

    /**
     * Permite realizar la valuación de una pieza del tipo Alhaja.
     *
     * @return Valor de la Alhaja valuada.
     */
    @Override
    public Avaluo valuar() {
        if (!ObjectUtils.isEmpty(avaluo)) {
            LOGGER.debug("Alhaja ya valuada. Se regresa resultado existente.");
            return avaluo;
        }

        synchronized (Alhaja.class) {
            if (ObjectUtils.isEmpty(avaluo)) {
                realizarValuacion();
            } else {
                LOGGER.debug("Alhaja ya valuada. Se regresa resultado existente.");
            }
        }

        return avaluo;
    }

    /**
     * Recupera el valor de la alhaja valuada.
     *
     * @return Valor de la alhaja valuada.
     */
    public Avaluo avaluo() {
        return avaluo;
    }

    /**
     * {@inheritDoc}
     */
    public String getMetal() {
        return metal;
    }

    /**
     * {@inheritDoc}
     */
    public String getColor() {
        return color;
    }

    /**
     * {@inheritDoc}
     */
    public String getCalidad() {
        return calidad;
    }

    /**
     * {@inheritDoc}
     */
    public String getRango() {
        return rango;
    }

    /**
     * Realiza el cálculo para obtener el precio de ésta alhaja.
     */
    private void realizarValuacion() {
        BigDecimal precioGramo = recuperarPrecioGramoMetal();
        BigDecimal factor = conector.obtenerFactor(this).getValor();
        BigDecimal precioMetal = precioGramo.multiply(peso);
        BigDecimal precioMetalFactor = precioMetal.multiply(factor);
        BigDecimal avaluoAlahaja = precioMetalFactor;

        if (incremento.compareTo(BigDecimal.ZERO) > 0) {
            avaluoAlahaja = precioMetalFactor.multiply(incremento);
        }

        if (desplazamiento.compareTo(BigDecimal.ZERO) > 0) {
            avaluoAlahaja = avaluoAlahaja.multiply(desplazamiento);
        }

        avaluo = new Avaluo(avaluoAlahaja, avaluoAlahaja, avaluoAlahaja);
    }

    /**
     * Recupera el precio de 1 gramo de Oro u otro metal.
     *
     * @return Precio del metal.
     */
    private BigDecimal recuperarPrecioGramoMetal() {
        BigDecimal precioGramoMetal;

        if (metal.equals(IDENTIFICADOR_METAL_ORO)) {
            precioGramoMetal = conector.obtenerValorGramoOro(this).getValor();
        } else {
            precioGramoMetal = conector.obtenerValorGramoMetal(this).getValor();
        }

        return precioGramoMetal;
    }
}
