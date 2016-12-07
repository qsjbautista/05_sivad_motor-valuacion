/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.modelo;

import com.codahale.metrics.annotation.Timed;
import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaDiamantes;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.ValorComercialConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasDiamanteProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CertificadoDiamanteProveedor;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * Clase que implementa la interface {@link PiezaValuable}, ésta clase representa una Diamante y encapsula
 * la lógica para valuar este tipo de piezas.
 *
 * @author ngonzalez
 */
public class Diamante implements PiezaValuable, CaracteristicasDiamanteProveedor, CertificadoDiamanteProveedor {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Diamante.class);

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

    /**
     * El valor experto para la pieza en particular.
     */
    private BigDecimal valorExperto;

    /**
     * Avalúo del diamante.
     */
    private Avaluo avaluo;

    /**
     * Referencia hacia el conector con el sistema de tablas de referencia.
     */
    private TablasDeReferenciaDiamantes conector;

    /**
     * Interface que define el contrato para crear entidades de tipo {@link Diamante}.
     */
    public interface Builder {

        /**
         * Permite obtener el tipo de corte del diamante.
         *
         * @return El tipo de corte del diamante.
         */
        public String getCorte();

        /**
         * Permite obtener el tipo de color del diamante.
         *
         * @return El tipo de color del diamante.
         */
        public String getColor();

        /**
         * Permite obtener el tipo de claridad del diamante.
         *
         * @return El tipo de claridad del diamante.
         */
        public String getClaridad();

        /**
         * Permite obtener el valor en quilates del diamante.
         *
         * @return El valor en quilates del diamante.
         */
        public BigDecimal getQuilates();

        /**
         * Permite obtener el valor del certificado del diamante.
         *
         * @return El valor del certificado del diamante.
         */
        public String getCertificadoDiamante();

        /**
         * Permite obtener el valor experto para la pieza en particular.
         *
         * @return El valor experto para la pieza en particular.
         */
        public BigDecimal getValorExperto();

    }



    // METODOS

    /**
     * Constructor.
     *
     * @param builder Referencia al objeto que contiene los datos necesarios para construir la entidad.
     * @param conector Referencia hacia el conector con el sistema de tablas de referencia.
     */
    private Diamante(Builder builder, TablasDeReferenciaDiamantes conector) {
        super();

        this.corte = builder.getCorte();
        this.color = builder.getColor();
        this.claridad = builder.getClaridad();
        this.quilates = builder.getQuilates();
        this.certificadoDiamante = builder.getCertificadoDiamante();
        this.valorExperto = builder.getValorExperto();
        this.conector = conector;
    }

    /**
     * Permite realizar la valuación de la pieza del tipo Diamante.
     *
     * @return El avalúo de la pieza del tipo Diamante.
     */
    @Override
    @Timed
    public Avaluo valuar() {
        LOGGER.info(">> valuar");

        BigDecimal valorComercialMinimo;
        BigDecimal valorComercialMedio;
        BigDecimal valorComercialMaximo;

        // SE DETERMINA SI EXISTE VALOR DE EXPERTO.
        if (ObjectUtils.isEmpty(this.valorExperto)) {

            // SE OBTIENE EL VALOR COMERCIAL DEL DIAMANTE CON BASE EN SUS CARACTERÍSTICAS.
            ValorComercialConsumidor valorComercialConsumidor = conector.obtenerValorComercial(this);

            valorComercialMinimo = valorComercialConsumidor.getValorMinimo();
            valorComercialMedio = valorComercialConsumidor.getValorMedio();
            valorComercialMaximo = valorComercialConsumidor.getValorMaximo();
        } else {
            valorComercialMinimo = valorExperto;
            valorComercialMedio = valorExperto;
            valorComercialMaximo = valorExperto;
        }

        LOGGER.debug("Valor Comercial Minimo: [{}]", valorComercialMinimo);
        LOGGER.debug("Valor Comercial Medio: [{}]", valorComercialMedio);
        LOGGER.debug("Valor Comercial Maximo: [{}]", valorComercialMaximo);

        // EN CASO DE EXISTIR CERTIFICADO SE APLICA EL INCREMENTO.
        if (!ObjectUtils.isEmpty(this.certificadoDiamante)) {
            BigDecimal incrementoPorCertificado = conector.obtenerModificador(this).getValor();

            if (incrementoPorCertificado.compareTo(BigDecimal.ZERO) > 0) {
                LOGGER.debug("Incremento Por Certificado: [{}]", incrementoPorCertificado);

                valorComercialMinimo = valorComercialMinimo.add(
                    valorComercialMinimo.multiply(incrementoPorCertificado));
                valorComercialMedio = valorComercialMedio.add(
                    valorComercialMedio.multiply(incrementoPorCertificado));
                valorComercialMaximo = valorComercialMaximo.add(
                    valorComercialMaximo.multiply(incrementoPorCertificado));

                LOGGER.debug("Valor Comercial Minimo: [{}]", valorComercialMinimo);
                LOGGER.debug("Valor Comercial Medio: [{}]", valorComercialMedio);
                LOGGER.debug("Valor Comercial Maximo: [{}]", valorComercialMaximo);
            }
        }

        // SE CREA EL AVALÚO CON BASE EN LOS VALORES COMERCIALES DEFINITIVOS.
        avaluo = new Avaluo(valorComercialMinimo, valorComercialMedio, valorComercialMaximo);
        return avaluo;
    }



    // GETTERS

    public String getCorte() {
        return corte;
    }

    public String getColor() {
        return color;
    }

    public String getClaridad() {
        return claridad;
    }

    public BigDecimal getQuilates() {
        return quilates;
    }

    public String getCertificadoDiamante() {
        return certificadoDiamante;
    }

    public BigDecimal getValorExperto() {
        return valorExperto;
    }

    public Avaluo getAvaluo() {
        return avaluo;
    }

}
