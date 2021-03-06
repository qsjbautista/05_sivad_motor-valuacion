/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaDiamantes;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.DomainExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.DiamanteFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.DiamanteDTO;
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
 * Fábrica para crear entidades tipo {@link Diamante}.
 *
 * @author ngonzalez, ecancino
 */
@Component
public class DiamanteFactoryImpl implements DiamanteFactory {

    /**
     * Referencia al constructor de la entidad.
     */
    private final Constructor<Diamante> constructor;

    /**
     * Referencia hacia el conector con el sistema de "tablas de referencia".
     */
    @Inject
    private TablasDeReferenciaDiamantes tablasDeReferenciaDiamantes;



    // METODOS

    /**
     * Constructor.
     */
    public DiamanteFactoryImpl() {
        super();

        constructor = getConstructor(Diamante.class, Diamante.Builder.class,
            TablasDeReferenciaDiamantes.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Diamante create(DiamanteDTO diamanteDTO) {
        final Diamante.Builder builder = getBuilder(diamanteDTO);
        return create(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Diamante create(Diamante.Builder builder) {
        validarBuilder(builder);
        return getInstancia(constructor, builder, tablasDeReferenciaDiamantes);
    }

    /**
     * Crea un objeto constructor a partir del valor de los argumentos.
     *
     * @param diamanteDTO DTO con la información de la pieza de tipo diamante.
     * @return El objeto constructor creado.
     */
    private static Diamante.Builder getBuilder(final DiamanteDTO diamanteDTO) {
        return new Diamante.Builder() {

            @Override
            public int getNumeroDePiezas() {
                return diamanteDTO.getNumeroDePiezas();
            }

            @Override
            public String getCorte() {
                return diamanteDTO.getCorte();
            }

            @Override
            public String getSubcorte() {
                return diamanteDTO.getSubcorte();
            }

            @Override
            public String getColor() {
                return diamanteDTO.getColor();
            }

            @Override
            public String getClaridad() {
                return diamanteDTO.getClaridad();
            }

            @Override
            public BigDecimal getQuilates() {
                return diamanteDTO.getQuilates();
            }

            @Override
            public String getCertificadoDiamante() {
                return diamanteDTO.getCertificadoDiamante();
            }

            @Override
            public ValorExperto getValorExperto() {
                return diamanteDTO.getValorExperto();
            }

            @Override
            public BigDecimal getQuilatesDesde() {
                return diamanteDTO.getQuilatesDesde();
            }

            @Override
            public BigDecimal getQuilatesHasta() {
                return diamanteDTO.getQuilatesHasta();
            }

        };
    }

    /**
     * Utilizado para validar los valores con los que se quiere crear la entidad.
     *
     * @param builder Objeto constructor de la entidad.
     */
    private static void validarBuilder(final Diamante.Builder builder) {
        Assert.notNull(builder,
            DomainExceptionCodes.BUILDER_NULO.getMessageException());

        ValidadorNumero.validarPositivo(builder.getNumeroDePiezas(),
            DomainExceptionCodes.DIAMANTE_NUM_PIEZAS_MENOR_IGUAL_CERO.getMessageException());

        Assert.notNull(builder.getCorte(),
            DomainExceptionCodes.DIAMANTE_CORTE_NULO.getMessageException());
        Assert.hasText(builder.getCorte(),
            DomainExceptionCodes.DIAMANTE_CORTE_VACIO.getMessageException());

        Assert.notNull(builder.getSubcorte(),
            DomainExceptionCodes.DIAMANTE_SUBCORTE_NULO.getMessageException());
        Assert.hasText(builder.getSubcorte(),
            DomainExceptionCodes.DIAMANTE_SUBCORTE_VACIO.getMessageException());

        Assert.notNull(builder.getColor(),
            DomainExceptionCodes.DIAMANTE_COLOR_NULO.getMessageException());
        Assert.hasText(builder.getColor(),
            DomainExceptionCodes.DIAMANTE_COLOR_VACIO.getMessageException());

        Assert.notNull(builder.getClaridad(),
            DomainExceptionCodes.DIAMANTE_CLARIDAD_NULA.getMessageException());
        Assert.hasText(builder.getClaridad(),
            DomainExceptionCodes.DIAMANTE_CLARIDAD_VACIA.getMessageException());

        Assert.notNull(builder.getQuilates(),
            DomainExceptionCodes.DIAMANTE_QUILATES_NULO.getMessageException());
        ValidadorNumero.validarPositivo(builder.getQuilates(),
            DomainExceptionCodes.DIAMANTE_QUILATES_MENOR_IGUAL_CERO.getMessageException());

        if (!ObjectUtils.isEmpty(builder.getValorExperto()) &&
            !ObjectUtils.isEmpty(builder.getValorExperto().getValor())) {
            ValidadorNumero.validarPositivo(builder.getValorExperto().getValor(),
                DomainExceptionCodes.DIAMANTE_VALOR_EXPERTO_MENOR_IGUAL_CERO.getMessageException());
        }

        Assert.notNull(builder.getQuilatesDesde(),
            DomainExceptionCodes.DIAMANTE_QUILATES_DESDE_NULO.getMessageException());
        ValidadorNumero.validarPositivo(builder.getQuilatesDesde(),
            DomainExceptionCodes.DIAMANTE_QUILATES_DESDE_MENOR_IGUAL_CERO.getMessageException());

        Assert.notNull(builder.getQuilatesHasta(),
            DomainExceptionCodes.DIAMANTE_QUILATES_HASTA_NULO.getMessageException());
        ValidadorNumero.validarPositivo(builder.getQuilatesHasta(),
            DomainExceptionCodes.DIAMANTE_QUILATES_HASTA_MENOR_IGUAL_CERO.getMessageException());
    }

}
