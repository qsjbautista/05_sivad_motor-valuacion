/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.arquetipo.journal.util.ApplicationContextProvider;
import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaDiamantes;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.DiamanteFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
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

    private static final String BUILDER_NULO = "El objeto constructor no debe ser nulo.";
    private static final String CLARIDAD_NULA = "La claridad no debe ser nula.";
    private static final String COLOR_NULO = "El corte no debe ser nulo.";
    private static final String CORTE_NULO = "El corte no debe ser nulo.";
    private static final String QUILATES_NULO = "El valor en quilates no debe ser nulo.";

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
    public Diamante create(int numeroDePiezas,
                           String corte,
                           String color,
                           String claridad,
                           BigDecimal quilates,
                           String certificadoDiamante,
                           ValorExperto valorExperto) {
        final Diamante.Builder builder =
            getBuilder(numeroDePiezas, corte, color, claridad, quilates, certificadoDiamante, valorExperto);
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
     * @param numeroDePiezas El número de piezas de tipo {@link Diamante} con características idénticas.
     * @param corte El tipo de corte del diamante.
     * @param color El tipo de color del diamante.
     * @param claridad El tipo de claridad del diamante.
     * @param quilates El valor en quilates del diamante.
     * @param certificadoDiamante El valor del certificado del diamante.
     * @param valorExperto El valor experto para la pieza en particular.
     * @return El objeto constructor creado.
     */
    private static Diamante.Builder getBuilder(final int numeroDePiezas,
                                               final String corte,
                                               final String color,
                                               final String claridad,
                                               final BigDecimal quilates,
                                               final String certificadoDiamante,
                                               final ValorExperto valorExperto) {
        return new Diamante.Builder() {

            @Override
            public int getNumeroDePiezas() {
                return numeroDePiezas;
            }

            @Override
            public String getCorte() {
                return corte;
            }

            @Override
            public String getColor() {
                return color;
            }

            @Override
            public String getClaridad() {
                return claridad;
            }

            @Override
            public BigDecimal getQuilates() {
                return quilates;
            }

            @Override
            public String getCertificadoDiamante() {
                return certificadoDiamante;
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
    private static void validarBuilder(final Diamante.Builder builder) {
        Assert.notNull(builder, BUILDER_NULO);
        Assert.notNull(builder.getCorte(), CORTE_NULO);
        Assert.notNull(builder.getColor(), COLOR_NULO);
        Assert.notNull(builder.getClaridad(), CLARIDAD_NULA);
        Assert.notNull(builder.getQuilates(), QUILATES_NULO);

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
