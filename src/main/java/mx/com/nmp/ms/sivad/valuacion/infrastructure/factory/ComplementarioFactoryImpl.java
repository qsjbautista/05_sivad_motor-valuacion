/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.exception.DomainExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.ComplementarioFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import mx.com.nmp.ms.sivad.valuacion.dominio.validador.ValidadorNumero;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Constructor;

import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getConstructor;
import static mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil.getInstancia;

/**
 * Fábrica para crear entidades tipo {@link Complementario}.
 *
 * @author ngonzalez
 */
@Component
public class ComplementarioFactoryImpl implements ComplementarioFactory {

    /**
     * Referencia al constructor de la entidad.
     */
    private final Constructor<Complementario> constructor;



    // METODOS

    /**
     * Constructor.
     */
    public ComplementarioFactoryImpl() {
        super();

        constructor = getConstructor(Complementario.class, Complementario.Builder.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Complementario create(int numeroDePiezas,
                                 ValorExperto valorExperto) {
        final Complementario.Builder builder =
            getBuilder(numeroDePiezas, valorExperto);
        return create(builder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Complementario create(Complementario.Builder builder) {
        validarBuilder(builder);
        return getInstancia(constructor, builder);
    }

    /**
     * Crea un objeto constructor a partir del valor de los argumentos.
     *
     * @param numeroDePiezas El número de piezas de tipo {@link Complementario} con características idénticas.
     * @param valorExperto El valor estimado por un experto.
     * @return El objeto constructor creado.
     */
    private static Complementario.Builder getBuilder(final int numeroDePiezas,
                                                     final ValorExperto valorExperto) {
        return new Complementario.Builder() {

            @Override
            public int getNumeroDePiezas() {
                return numeroDePiezas;
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
    private static void validarBuilder(final Complementario.Builder builder) {
        Assert.notNull(builder, DomainExceptionCodes.BUILDER_NULO.getMessageException());

        ValidadorNumero.validarPositivo(builder.getNumeroDePiezas());

        if (!ObjectUtils.isEmpty(builder.getValorExperto()) &&
            !ObjectUtils.isEmpty(builder.getValorExperto().getValor())) {
            ValidadorNumero.validarPositivo(builder.getValorExperto().getValor());
        }
    }

}