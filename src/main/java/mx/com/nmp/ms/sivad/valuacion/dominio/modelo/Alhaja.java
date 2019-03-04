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

    private static final BigDecimal FACTOR = BigDecimal.valueOf(100);

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
     * Importe del gramo
     */
    private BigDecimal importeGramo;

    /**
     * Monto del avalúo complementario
     */
    private BigDecimal avaluoComplementario;

    /**
     * Abreviatura del subramo
     */
    private String subramo;

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

        /**
         * Recupera el monto del avalúo complementario.
         *
         * @return Monto del avalúo complementario
         */
        BigDecimal getAvaluoComplementario();

        /**
         * Recupera la abreviatura del subramo de la alhaja
         *
         * @return Abreviatura del subramo
         */
        String getSubramo();
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

        avaluoComplementario = builder.getAvaluoComplementario();
        subramo = builder.getSubramo();

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
            if (subramo.equals(SubramoEnum.ALHAJAS.getAbr())) {
                realizarValuacionAlhaja();
            } else {
                realizarValuacion();
            }
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
     * {@inheritDoc}
     */
    public BigDecimal getImporteGramo() {
        return importeGramo;
    }

    /**
     * Realiza el cálculo para obtener el precio de ésta alhaja.
     */
    private void realizarValuacion() {
        BigDecimal avaluoAlhaja;

        if (ObjectUtils.isEmpty(valorExperto)) {
            BigDecimal precioGramo = recuperarPrecioGramoMetal();
            LOGGER.debug("Valor por gramo de {} = {}", metal, precioGramo);

            BigDecimal precioMetal = precioGramo.multiply(peso);
            LOGGER.debug("Aplicando peso {}gr = {}", peso, precioMetal);

            BigDecimal inc = recuperarIncremento();
            LOGGER.debug("Incremento a aplicar {}", inc);

            BigDecimal desp = recuperarDesplazamiento();
            LOGGER.debug("Desplazamiento a aplicar {}", desp);

            BigDecimal inc_desp = inc.add(desp);
            BigDecimal div_inc_desp = inc_desp
                .divide(FACTOR, 4, BigDecimal.ROUND_HALF_UP);
            BigDecimal fac_inc_des = BigDecimal.ONE.add(div_inc_desp);
            BigDecimal conIncDes = precioMetal.multiply(fac_inc_des);
            LOGGER.debug("Aplicando incremento desplazamiento {} = {}", fac_inc_des, conIncDes);

            // EN EL CASO DE AVALÚO NORMAL O VALOR MONTE, NO APLICA EL FACTOR, SOLO APLICA PARA EL AVALÚO COMERCIAL
            //BigDecimal factor = recuperarFactor();
            //avaluoAlhaja = conIncDes.multiply(factor);
            //LOGGER.debug("Aplicando factor {} = {}", factor, avaluoAlhaja);

            avaluoAlhaja = conIncDes;
        } else {
            avaluoAlhaja = recuperarValorExperto();
            LOGGER.debug("No se valua se usa valor experto {}", avaluoAlhaja);
        }

        avaluo = AvaluoFactory.crearCon(avaluoAlhaja, avaluoAlhaja, avaluoAlhaja);
    }

    /**
     * Realiza el cálculo para obtener el precio de está alhaja
     * para el subramo Alhajas.
     */
    private void realizarValuacionAlhaja() {

        BigDecimal precioGramo = recuperarPrecioGramoMetal();
        LOGGER.debug("Valor por gramo de {} = {}", metal, precioGramo);

        //PRECIO DE 1 GRAMO DE ORO (NO APLICA REDONDEO)
        importeGramo = precioGramo;


        BigDecimal precioOro = redondearEntero(precioGramo.multiply(peso));
        LOGGER.debug("Aplicando peso {}gr = {}", peso, precioOro);

        // EL PRECIO APLICADO AL GRAMAJE NOS DA EL AVALÚO TÉCNICO
        avaluoTecnico = precioOro;


        BigDecimal inc = recuperarIncremento();
        LOGGER.debug("Incremento a aplicar {}", inc);

        BigDecimal desp = recuperarDesplazamiento();
        LOGGER.debug("Desplazamiento a aplicar {}", desp);

        BigDecimal inc_desp = inc.add(desp);
        BigDecimal div_inc_desp = inc_desp
            .divide(FACTOR, 4, BigDecimal.ROUND_HALF_UP);
        BigDecimal precioMetal = BigDecimal.ONE.add(div_inc_desp);

        LOGGER.debug("Aplicando incremento desplazamiento {} = {}", div_inc_desp, precioMetal);

        // EL AVALUO SE OBTIENE MULTIPLICANDO EL AVALUO TECNICO YA REDONDEADO
        precioMetal = redondearEntero(precioOro.multiply(precioMetal));


        BigDecimal avaluoComplementario = recuperarAvaluoComplementario();

        // SE SUMA EL AVALUO COMPLEMENTARIO AL PRECIO METAL, PARA OBTENER EL AVALUO
        int avaluoSinRedondeo = precioMetal.intValue() + avaluoComplementario.intValue();
        BigDecimal avaluoAlhaja = redondearEntero(new BigDecimal(avaluoSinRedondeo));
        LOGGER.debug("Aplicando avaluo complementario {} = {}", avaluoComplementario, avaluoAlhaja);


        // SE APLICA EL FACTOR PARA OBTENER EL AVALÚO COMERCIAL
        BigDecimal factor = recuperarFactor();
        avaluoComercial = redondearEntero(avaluoAlhaja.multiply(factor));
        LOGGER.debug("Aplicando factor {} = {}", factor, avaluoComercial);

        avaluo = AvaluoFactory.crearCon(avaluoAlhaja, avaluoAlhaja, avaluoAlhaja);
    }

    /**
     * Recupera el precio de 1 gramo de Oro u otro metal.
     *
     * @return Precio del metal.
     */
    private BigDecimal recuperarPrecioGramoMetal() {
        BigDecimal precioGramoMetal;

        if (metal.equals(TipoMetalEnum.ORO.getTipo())) {
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
        return !(ObjectUtils.isEmpty(metal) || ObjectUtils.isEmpty(rango));
    }

    /**
     * Recupera el avaluó que capturo el experto.
     *
     * @return Avaluó del experto.
     */
    private BigDecimal recuperarValorExperto() {
        /*if (ObjectUtils.isEmpty(valorExperto) || metal.equals(TipoMetalEnum.ORO.getTipo())) {
            return BigDecimal.ZERO;
        } else {
            return valorExperto.getValor();
        }*/
        if (ValorExperto.TipoEnum.UNITARIO == valorExperto.getTipo()) {
            return valorExperto.getValor().multiply(BigDecimal.valueOf(numeroDePiezas));
        } else {
            return valorExperto.getValor();
        }
    }

    /**
     * Recupera el incremento a aplicar.
     *
     * @return Incremento a aplicar.
     */
    private BigDecimal recuperarIncremento() {
        if (ObjectUtils.isEmpty(incremento)) {
            return BigDecimal.ZERO;
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
        if (ObjectUtils.isEmpty(desplazamiento)) {
            return BigDecimal.ZERO;
        } else {
            return desplazamiento;
        }
    }

    /**
     * Recupera el monto del avalúo complementario a aplicar
     *
     * @return Avalúo complementario a aplicar
     */
    private BigDecimal recuperarAvaluoComplementario() {
        if (ObjectUtils.isEmpty(avaluoComplementario)) {
            return BigDecimal.ZERO;
        } else {
            return avaluoComplementario;
        }
    }

    /**
     * Aplica el redondeo con la función Math.ceil, que redondea al
     * entero mas pequeño mayor o igual al número dado.
     *
     * @param monto Monto a redondear
     * @return Monto redondeado
     */
    private BigDecimal redondearEntero(BigDecimal monto) {
        return new BigDecimal(Math.ceil(monto.doubleValue()));
    }
}
