/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.arquetipo.journal.util.ApplicationContextProvider;
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

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getConstructor;
import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getInstancia;

/**
 * Fábrica para crear entidades tipo {@link Diamante}.
 *
 * @author ngonzalez
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
        final Diamante.Builder builder =
            getBuilder(diamanteDTO);
        return create(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Diamante create(Diamante.Builder builder) {
        validarBuilder(builder);
        return getInstancia(constructor, builder, getTablasDeReferenciaDiamantes());
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
        };
    }

    /**
     * Utilizado para validar los valores con los que se quiere crear la entidad.
     *
     * @param builder Objeto constructor de la entidad.
     */
    private static void validarBuilder(final Diamante.Builder builder) {
        Assert.notNull(builder, DomainExceptionCodes.BUILDER_NULO.getMessageException());
        Assert.notNull(builder.getCorte(), DomainExceptionCodes.DIAMANTE_CORTE_NULO.getMessageException());
        Assert.notNull(builder.getColor(), DomainExceptionCodes.DIAMANTE_COLOR_NULO.getMessageException());
        Assert.notNull(builder.getClaridad(), DomainExceptionCodes.DIAMANTE_CLARIDAD_NULA.getMessageException());
        Assert.notNull(builder.getQuilates(), DomainExceptionCodes.DIAMANTE_QUILATES_NULO.getMessageException());

        ValidadorNumero.validarPositivo(builder.getNumeroDePiezas());
        ValidadorNumero.validarPositivo(builder.getQuilates());

        if (!ObjectUtils.isEmpty(builder.getValorExperto()) &&
            !ObjectUtils.isEmpty(builder.getValorExperto().getValor())) {
            ValidadorNumero.validarPositivo(builder.getValorExperto().getValor());
        }
    }

    /**
     * Permite obtener la referencia hacia el conector con el sistema de "tablas de referencia".
     *
     * @return Referencia hacia el conector con el sistema de "tablas de referencia".
     */
    public TablasDeReferenciaDiamantes getTablasDeReferenciaDiamantes() {
        if (ObjectUtils.isEmpty(tablasDeReferenciaDiamantes)) {
            tablasDeReferenciaDiamantes =
                ApplicationContextProvider.get().getBean(TablasDeReferenciaDiamantes.class);
        }

        return tablasDeReferenciaDiamantes;
    }

}
