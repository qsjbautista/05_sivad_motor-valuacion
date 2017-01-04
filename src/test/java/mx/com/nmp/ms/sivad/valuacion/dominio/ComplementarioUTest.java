/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio;

import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AvaluoFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.ComplementarioFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Clase de pruebas para {@link Complementario}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MotorValuacionApplication.class)
public class ComplementarioUTest {

    @Inject
    private ComplementarioFactory fabrica;

    /**
     * Constructor.
     */
    public ComplementarioUTest() {
        super();
    }

    /**
     * (non-Javadoc)
     * @see Complementario
     */
    @Test
    public void crearComplementarioTest() {
        Complementario test = fabrica.create(getBuilder(3, BigDecimal.valueOf(238.57), ValorExperto.TipoEnum.UNITARIO));
        ValorExperto valorExperto = new ValorExperto(BigDecimal.valueOf(238.57), ValorExperto.TipoEnum.UNITARIO);
        BigDecimal valor = BigDecimal.valueOf(715.71);
        Avaluo resultado = AvaluoFactory.crearCon(valor, valor, valor);

        Avaluo avaluo = test.valuar();

        assertNotNull(test);
        assertEquals(valorExperto, test.getValorExperto());
        assertEquals(resultado, avaluo);
        assertEquals(test.avaluo(), avaluo);
    }

    /**
     * (non-Javadoc)
     * @see Complementario#valuar()
     */
    @Test
    public void valuarComplementarioTest() {
        Complementario test = fabrica.create(getBuilder(1, BigDecimal.valueOf(238.57), null));

        Avaluo avaluo = test.valuar();

        assertNotNull(test);
        assertNotNull(avaluo);
        assertNotNull(test.avaluo());
    }

    /**
     * (non-Javadoc)
     * @see Complementario#toString()
     */
    @Test
    public void toStringComplementarioTest() {
        Complementario test = fabrica.create(getBuilder(1, BigDecimal.valueOf(238.57), null));

        assertNotNull(test.toString());
    }

    /**
     * (non-Javadoc)
     * @see Complementario#hashCode()
     */
    @Test
    public void hashCodeComplementarioTest() {
        Complementario test = fabrica.create(getBuilder(1, BigDecimal.valueOf(238.57), null));

        assertNotNull(test.hashCode());
    }

    /**
     * (non-Javadoc)
     * @see Complementario#equals(Object)
     */
    @Test
    public void equalsComplementarioTest() {
        Complementario test = fabrica.create(getBuilder(1, BigDecimal.valueOf(238.57), null));
        Complementario test2 = fabrica.create(getBuilder(1, BigDecimal.valueOf(238.57), null));
        Complementario test3 = fabrica.create(getBuilder(1, BigDecimal.valueOf(238.51), null));

        assertEquals(test, test);
        assertFalse(test.equals(null));
        assertFalse(test.equals(new Object()));
        assertEquals(test, test2);
        assertFalse(test.equals(test3));
    }

    private Complementario.Builder getBuilder(
            final int numeroDePiezas, final BigDecimal valorExperto, final ValorExperto.TipoEnum tipoEnum) {
        return new Complementario.Builder() {

            @Override
            public int getNumeroDePiezas() {
                return numeroDePiezas;
            }

            @Override
            public ValorExperto getValorExperto() {
                return new ValorExperto(valorExperto, tipoEnum == null ? ValorExperto.TipoEnum.TOTAL : tipoEnum);
            }
        };
    }
}
