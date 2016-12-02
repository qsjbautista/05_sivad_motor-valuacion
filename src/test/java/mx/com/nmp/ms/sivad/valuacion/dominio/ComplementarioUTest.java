/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Pruebas de unidad para la clase {@link Complementario}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class ComplementarioUTest {

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
        Complementario test = new Complementario(getBuilder(BigDecimal.valueOf(238.57)));

        assertNotNull(test);
    }

    /**
     * (non-Javadoc)
     * @see Complementario#valuar()
     */
    @Test
    public void valuarComplementarioTest() {
        Complementario test = new Complementario(getBuilder(BigDecimal.valueOf(238.57)));

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
        Complementario test = new Complementario(getBuilder(BigDecimal.valueOf(238.57)));

        assertNotNull(test.toString());
    }

    /**
     * (non-Javadoc)
     * @see Complementario#hashCode()
     */
    @Test
    public void hashCodeComplementarioTest() {
        Complementario test = new Complementario(getBuilder(BigDecimal.valueOf(238.57)));

        assertNotNull(test.hashCode());
    }

    /**
     * (non-Javadoc)
     * @see Complementario#equals(Object)
     */
    @Test
    public void equalsComplementarioTest() {
        Complementario test = new Complementario(getBuilder(BigDecimal.valueOf(238.57)));
        Complementario test2 = new Complementario(getBuilder(BigDecimal.valueOf(238.57)));
        Complementario test3 = new Complementario(getBuilder(BigDecimal.valueOf(238.51)));

        assertEquals(test, test);
        assertFalse(test.equals(null));
        assertFalse(test.equals(new Object()));
        assertEquals(test, test2);
        assertFalse(test.equals(test3));
    }

    private Complementario.Builder getBuilder(final BigDecimal valorExperto) {
        return new Complementario.Builder() {
            @Override
            public BigDecimal getValorExperto() {
                return valorExperto;
            }
        };
    }
}