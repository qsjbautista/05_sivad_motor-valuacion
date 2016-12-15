/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.arquetipo.journal.util.ApplicationContextProvider;
import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaAlhajas;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.DomainExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AlhajaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.TipoMetalEnum;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import mx.com.nmp.ms.sivad.valuacion.dominio.validador.ValidadorNumero;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getConstructor;
import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getInstancia;

/**
 * Fábrica para crear entidades tipo {@link Alhaja}.
 *
 * @author ngonzalez
 */
@Component
public class AlhajaFactoryImpl implements AlhajaFactory {

    /**
     * Referencia al constructor de la entidad.
     */
    private final Constructor<Alhaja> constructor;

    /**
     * Referencia hacia el conector con el sistema de "tablas de referencia".
     */
    private TablasDeReferenciaAlhajas tablasDeReferenciaAlhajas;



    // METODOS

    /**
     * Constructor.
     */
    public AlhajaFactoryImpl() {
        super();

        constructor = getConstructor(Alhaja.class, Alhaja.Builder.class,
            TablasDeReferenciaAlhajas.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Alhaja create(String metal,
                         String color,
                         String calidad,
                         String rango,
                         BigDecimal peso,
                         BigDecimal incremento,
                         BigDecimal desplazamiento,
                         ValorExperto valorExperto) {
        final Alhaja.Builder builder =
            getBuilder(metal, color, calidad, rango, peso, incremento, desplazamiento, valorExperto);
        return create(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Alhaja create(Alhaja.Builder builder) {
        validarBuilder(builder);
        return getInstancia(constructor, builder, getTablasDeReferenciaAlhajas());
    }

    /**
     * Crea un objeto constructor a partir del valor de los argumentos.
     *
     * @param metal El tipo de metal de la alhaja.
     * @param color El color del metal.
     * @param calidad La calidad de la alhaja.
     * @param rango El rango de la alhaja.
     * @param peso El peso en gramos de la alhaja.
     * @param incremento El incremento por las condiciones físicas de la prenda.
     * @param desplazamiento El desplazamiento comercial.
     * @param valorExperto El valor experto para la pieza en particular.
     * @return El objeto constructor creado.
     */
    private static Alhaja.Builder getBuilder(final String metal,
                                             final String color,
                                             final String calidad,
                                             final String rango,
                                             final BigDecimal peso,
                                             final BigDecimal incremento,
                                             final BigDecimal desplazamiento,
                                             final ValorExperto valorExperto) {
        return new Alhaja.Builder() {

            @Override
            public String getMetal() {
                return metal;
            }

            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getCalidad() {
                return calidad;
            }

            @Override
            public String getRango() {
                return rango;
            }

            @Override
            public BigDecimal getPeso() {
                return peso;
            }

            @Override
            public BigDecimal getIncremento() {
                return incremento;
            }

            @Override
            public BigDecimal getDesplazamiento() {
                return desplazamiento;
            }

            @Override
            public ValorExperto getValorExperto() {
                return valorExperto;
            }
        };
    }

    /**
     * Utilizado para validar los valores con los que se quiere crear la entidad.
     *
     * @param builder Objeto constructor de la entidad.
     */
    private static void validarBuilder(final Alhaja.Builder builder) {
        Assert.notNull(builder, DomainExceptionCodes.BUILDER_NULO.getMessageException());
        Assert.notNull(builder.getMetal(), DomainExceptionCodes.ALHAJA_METAL_NULO.getMessageException());
        Assert.notNull(builder.getPeso(), DomainExceptionCodes.ALHAJA_PESO_NULO.getMessageException());

        if (builder.getMetal().equals(TipoMetalEnum.ORO.getTipo())) {
            Assert.notNull(builder.getColor(), DomainExceptionCodes.ALHAJA_COLOR_NULO.getMessageException());
            Assert.notNull(builder.getCalidad(), DomainExceptionCodes.ALHAJA_CALIDAD_NULA.getMessageException());
        }

        ValidadorNumero.validarPositivo(builder.getPeso());

        if (!ObjectUtils.isEmpty(builder.getIncremento())) {
            ValidadorNumero.validarPositivo(builder.getIncremento());
        }

        if (!ObjectUtils.isEmpty(builder.getDesplazamiento())) {
            ValidadorNumero.validarPositivo(builder.getDesplazamiento());
        }

        if (!ObjectUtils.isEmpty(builder.getValorExperto()) &&
            !ObjectUtils.isEmpty(builder.getValorExperto().getValor())) {
            ValidadorNumero.validarPositivo(builder.getValorExperto().getValor());

            if (builder.getValorExperto().getTipo().equals(ValorExperto.TipoEnum.UNITARIO)) {
                throw new IllegalArgumentException(DomainExceptionCodes.ALHAJA_VALOR_EXPERTO_NO_SOPORTADO.getMessageException());
            }
        }
    }

    /**
     * Permite obtener la referencia hacia el conector con el sistema de "tablas de referencia".
     *
     * @return Referencia hacia el conector con el sistema de "tablas de referencia".
     */
    public TablasDeReferenciaAlhajas getTablasDeReferenciaAlhajas() {
        if (ObjectUtils.isEmpty(tablasDeReferenciaAlhajas)) {
            tablasDeReferenciaAlhajas =
                ApplicationContextProvider.get().getBean(TablasDeReferenciaAlhajas.class);
        }

        return tablasDeReferenciaAlhajas;
    }

}
