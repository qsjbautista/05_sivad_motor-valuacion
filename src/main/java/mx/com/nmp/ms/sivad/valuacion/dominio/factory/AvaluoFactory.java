/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.validador.ValidadorNumero;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

/**
 * Fabrica que encapsula la lógica para crear Value Object tipo {@link Avaluo}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class AvaluoFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(AvaluoFactory.class);

    private static final Constructor<Avaluo> constructor;

    static {
        constructor = ConstructorUtil
            .getConstructor(Avaluo.class, BigDecimal.class, BigDecimal.class, BigDecimal.class);
    }

    /**
     * Constructor. Privado ya que no debe haber instancias.
     */
    private AvaluoFactory() {
        super();
    }

    /**
     * Crea un Value Object a partir del valor de los argumentos.
     *
     * @param valorMinimo Valor mínimo del avaluó.
     * @param valorPromedio Valor promedio del avaluó.
     * @param valorMaximo Valor máximo del avaluó.
     *
     * @return Value Object creado.
     *
     * @throws IllegalArgumentException Si el valor de algún argumento es menor o igual que {@code 0}
     */
    public static Avaluo crearCon(BigDecimal valorMinimo, BigDecimal valorPromedio, BigDecimal valorMaximo) {
        ValidadorNumero.validarPositivo(valorMinimo);
        ValidadorNumero.validarPositivo(valorPromedio);
        ValidadorNumero.validarPositivo(valorMaximo);

        LOGGER.info("Creado avaluo con Avaluo({}, {}, {})", valorMinimo, valorPromedio, valorMaximo);

        return ConstructorUtil.getInstancia(constructor, valorMinimo, valorPromedio, valorMaximo);
    }
}
