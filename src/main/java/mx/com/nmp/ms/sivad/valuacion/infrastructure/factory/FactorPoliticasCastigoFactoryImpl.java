/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.FactorPoliticasCastigoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.validador.ValidadorNumero;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Fábrica para crear Value Object tipo FactorPoliticasCastigo
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component
public class FactorPoliticasCastigoFactoryImpl implements FactorPoliticasCastigoFactory {

    /**
     * Constructor.
     */
    public FactorPoliticasCastigoFactoryImpl() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Class<? extends Pieza>, BigDecimal> crearCon(@NotNull BigDecimal factorDiamante,
            @NotNull BigDecimal factorAlhaja, @NotNull BigDecimal factorComplemento) {
        ValidadorNumero.validarPositivo(factorDiamante);
        ValidadorNumero.validarPositivo(factorAlhaja);
        ValidadorNumero.validarPositivo(factorComplemento);

        Map<Class<? extends Pieza>, BigDecimal> factores = new HashMap<>();

        factores.put(Diamante.class, factorDiamante);
        factores.put(Alhaja.class, factorAlhaja);
        factores.put(Complementario.class, factorComplemento);

        return factores;
    }
}
