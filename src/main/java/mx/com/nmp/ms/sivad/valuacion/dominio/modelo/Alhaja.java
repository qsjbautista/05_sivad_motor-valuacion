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
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AvaluoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
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
public class Alhaja extends Pieza implements CaracteristicasGramoOroProveedor, MetalCalidadRangoProveedor {
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
     * Valor experto para la pieza en particular.
     */
    private ValorExperto valorExperto;

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

        /**
         * Recupera el valor experto para la pieza en particular.
         *
         * @return El valor experto para la pieza en particular.
         */
        ValorExperto getValorExperto();
    }

    /**
     * Constructor.
     *
     * @param builder Objeto con los datos necesarios para construir la instancia.
     * @param conector Referencia hacia el conector con el sistema de tablas de referencia.
     */
    private Alhaja(Builder builder, TablasDeReferenciaAlhajas conector) {
        super();

        metal = builder.getMetal();
        color = builder.getColor();
        calidad = builder.getCalidad();
        rango = builder.getRango();

        peso = builder.getPeso();
        incremento = builder.getIncremento();
        desplazamiento = builder.getDesplazamiento();
        valorExperto = builder.getValorExperto();

        this.conector = conector;
    }

    /**
     * Permite realizar la valuación de una pieza del tipo Alhaja.
     *
     * @return Valor de la Alhaja valuada.
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
        LOGGER.debug("Valor por gramo de {} = {}", metal, precioGramo);

        BigDecimal precioMetal = precioGramo.multiply(peso);
        LOGGER.debug("Aplicando peso {}gr = {}", peso, precioMetal);

        BigDecimal factor = recuperarFactor();
        BigDecimal conFactor = precioMetal.multiply(factor);
        LOGGER.debug("Aplicando factor {} = {}", factor, conFactor);

        BigDecimal vl = recuperarValorExperto();
        BigDecimal conValorExperto = conFactor.add(vl);
        LOGGER.debug("Aplicando valor experto {} = {}", vl, conValorExperto);

        BigDecimal inc = recuperarIncremento();
        BigDecimal conIncremento = conValorExperto.multiply(inc);
        LOGGER.debug("Aplicando incremento {} = {}", inc, conIncremento);

        BigDecimal desp = recuperarDesplazamiento();
        BigDecimal avaluoAlhaja = conIncremento.multiply(desp);
        LOGGER.debug("Aplicando desplazamiento {} = {}", desp, avaluoAlhaja);

        avaluo = AvaluoFactory.crearCon(avaluoAlhaja, avaluoAlhaja, avaluoAlhaja);
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

    /**
     * Recupera el factor a aplicar a la alhaja.
     *
     * @return Factor a aplicar.
     */
    private BigDecimal recuperarFactor() {
        if (isValidFactorRequest()) {
            return conector.obtenerFactor(this).getValor();
        } else {
            return BigDecimal.ONE;
        }
    }

    /**
     * Verifica si se tienen los datos necesario para solicitar el factor.
     *
     * @return Verdadero si se tienen los datos para solicitar el factor, falso si no.
     */
    private boolean isValidFactorRequest() {
        return !(ObjectUtils.isEmpty(metal) || (ObjectUtils.isEmpty(calidad) || ObjectUtils.isEmpty(rango)));
    }

    /**
     * Recupera el avaluó que capturo el experto.
     *
     * @return Avaluó del experto.
     */
    private BigDecimal recuperarValorExperto() {
        if (ObjectUtils.isEmpty(valorExperto) || metal.equals(IDENTIFICADOR_METAL_ORO)) {
            return BigDecimal.ZERO;
        } else {
            if (ValorExperto.TipoEnum.UNITARIO.equals(valorExperto.getTipo())) {
                throw new IllegalArgumentException("Valor experto tipo unitario no soportado para alhajas.");
            } else {
                return valorExperto.getValor();
            }
        }
    }

    /**
     * Recupera el incremento a aplicar.
     *
     * @return Incremento a aplicar.
     */
    private BigDecimal recuperarIncremento() {
        if (ObjectUtils.isEmpty(incremento) || incremento.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ONE;
        } else {
            return incremento;
        }
    }

    /**
     * Recupera el desplazamiento a aplicar.
     *
     * @return Desplazamiento a aplicar.
     */
    private BigDecimal recuperarDesplazamiento() {
        if (ObjectUtils.isEmpty(desplazamiento) || desplazamiento.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ONE;
        } else {
            return desplazamiento;
        }
    }
}
