/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaAlhajas;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.DomainExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AlhajaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.TipoMetalEnum;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.AlhajaDTO;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import mx.com.nmp.ms.sivad.valuacion.dominio.validador.ValidadorNumero;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.inject.Inject;
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
    @Inject
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
    public Alhaja create(AlhajaDTO alhajaDTO) {
        final Alhaja.Builder builder = getBuilder(alhajaDTO);
        return create(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Alhaja create(Alhaja.Builder builder) {
        validarBuilder(builder);
        return getInstancia(constructor, builder, tablasDeReferenciaAlhajas);
    }

    /**
     * Crea un objeto constructor a partir del valor de los argumentos.
     *
     * @param alhajaDTO DTO con la información de la pieza de tipo alhaja.
     * @return El objeto constructor creado.
     */
    private static Alhaja.Builder getBuilder(final AlhajaDTO alhajaDTO) {
        return new Alhaja.Builder() {

            @Override
            public String getMetal() {
                return alhajaDTO.getMetal();
            }

            @Override
            public String getColor() {
                return alhajaDTO.getColor();
            }

            @Override
            public String getCalidad() {
                return alhajaDTO.getCalidad();
            }

            @Override
            public String getRango() {
                return alhajaDTO.getRango();
            }

            @Override
            public BigDecimal getPeso() {
                return alhajaDTO.getPeso();
            }

            @Override
            public BigDecimal getIncremento() {
                return alhajaDTO.getIncremento();
            }

            @Override
            public BigDecimal getDesplazamiento() {
                return alhajaDTO.getDesplazamiento();
            }

            @Override
            public ValorExperto getValorExperto() {
                return alhajaDTO.getValorExperto();
            }

            @Override
            public BigDecimal getAvaluoComplementario() {
                return alhajaDTO.getAvaluoComplementario();
            }

            @Override
            public String getSubramo() {
                return alhajaDTO.getSubramo();
            }
        };
    }

    /**
     * Utilizado para validar los valores con los que se quiere crear la entidad.
     *
     * @param builder Objeto constructor de la entidad.
     */
    private static void validarBuilder(final Alhaja.Builder builder) {
        Assert.notNull(builder,
            DomainExceptionCodes.BUILDER_NULO.getMessageException());

        Assert.notNull(builder.getMetal(),
            DomainExceptionCodes.ALHAJA_METAL_NULO.getMessageException());
        Assert.hasText(builder.getMetal(),
            DomainExceptionCodes.ALHAJA_METAL_VACIO.getMessageException());

        Assert.notNull(builder.getPeso(),
            DomainExceptionCodes.ALHAJA_PESO_NULO.getMessageException());
        ValidadorNumero.validarPositivo(builder.getPeso(),
            DomainExceptionCodes.ALHAJA_PESO_MENOR_IGUAL_CERO.getMessageException());

        if (builder.getMetal().equals(TipoMetalEnum.ORO.getTipo())) {
            Assert.notNull(builder.getColor(),
                DomainExceptionCodes.ALHAJA_COLOR_NULO.getMessageException());
            Assert.hasText(builder.getColor(),
                DomainExceptionCodes.ALHAJA_COLOR_VACIO.getMessageException());
        }

        if (builder.getMetal().equals(TipoMetalEnum.ORO.getTipo())
                || builder.getMetal().equals(TipoMetalEnum.PLATA.getTipo())) {
            Assert.notNull(builder.getCalidad(),
                DomainExceptionCodes.ALHAJA_CALIDAD_NULA.getMessageException());
            Assert.hasText(builder.getCalidad(),
                DomainExceptionCodes.ALHAJA_CALIDAD_VACIA.getMessageException());
        }

        if (!ObjectUtils.isEmpty(builder.getDesplazamiento())) {
            ValidadorNumero.validarPositivo(builder.getDesplazamiento(),
                DomainExceptionCodes.ALHAJA_DESPLAZAMIENTO_MENOR_IGUAL_CERO.getMessageException());
        }

        if (!ObjectUtils.isEmpty(builder.getValorExperto())) {
            ValidadorNumero.validarPositivo(builder.getValorExperto().getValor(),
                DomainExceptionCodes.ALHAJA_VALOR_EXPERTO_MENOR_IGUAL_CERO.getMessageException());
        }
    }

}
