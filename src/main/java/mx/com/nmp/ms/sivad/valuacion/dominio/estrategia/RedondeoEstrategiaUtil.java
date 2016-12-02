/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.estrategia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * Utilería para redondear según la estrategia seleccionada, se diseña como un singleton para que pueda
 * ser utilizada por componentes no manejados por Spring.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RedondeoEstrategiaUtil {
    private static RedondeoEstrategiaUtil INSTANCIA;

    /**
     * Contienen el nombre de la estrategia de redondeo a usar.
     */
    @Value("${valuacion.avaluo.redondeo}")
    private String estrategiaRedondeo;

    private RedondeoEstrategia redondeoEstrategia;

    /**
     * Constructor. Privado ya que es un singleton y no se debería crearse más de una instancia.
     */
    public RedondeoEstrategiaUtil() {
        super();

        RedondeoEstrategiaUtil.set(this);
    }

    /**
     * Redondea un valor tipo {@link BigDecimal} según la estrategia seleccionada.
     *
     * @param valor Valor a redondear.
     *
     * @return Valor redondeado.
     */
    public BigDecimal redondear(final BigDecimal valor) {
        return getRedondeoEstrategia().redondear(valor);
    }

    /**
     * Establece la estrategia seleccionada. Por default es {@link RedondeoEstrategias#DOS_DECIMALES_ROUND_HALF_UP}
     * @see RedondeoEstrategias
     *
     * @return Estrategia seleccionada.
     */
    private RedondeoEstrategia getRedondeoEstrategia() {
        if (ObjectUtils.isEmpty(redondeoEstrategia)) {
            if (ObjectUtils.isEmpty(estrategiaRedondeo)) {
                redondeoEstrategia = RedondeoEstrategias.DOS_DECIMALES_ROUND_HALF_UP;
            } else {
                redondeoEstrategia = RedondeoEstrategias.valueOf(estrategiaRedondeo);
            }
        }

        return redondeoEstrategia;
    }

    /**
     * Recupera la instancia única.
     *
     * @return Instancia única.
     */
    public static RedondeoEstrategiaUtil get() {
        if (ObjectUtils.isEmpty(INSTANCIA)) {
            //Si la instancia única aún no se creada, se crea una con la estrategia default.
            new RedondeoEstrategiaUtil();
        }

        return INSTANCIA;
    }

    /**
     * Establece la instancia única.
     *
     * @param instancia Instancia única.
     */
    private static void set(final RedondeoEstrategiaUtil instancia) {
        RedondeoEstrategiaUtil.INSTANCIA = instancia;
    }
}
