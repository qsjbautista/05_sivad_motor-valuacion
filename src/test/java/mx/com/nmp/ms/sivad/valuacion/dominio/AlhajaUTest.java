package mx.com.nmp.ms.sivad.valuacion.dominio;

import mx.com.nmp.ms.sivad.valuacion.conector.TablasDeReferenciaAlhajas;
import mx.com.nmp.ms.sivad.valuacion.conector.consumidor.BigDecimalConsumidor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasGramoOroProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.MetalCalidadRangoProveedor;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto;
import mx.com.nmp.ms.sivad.valuacion.infrastructure.factory.ConstructorUtil;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public class AlhajaUTest {

    private Constructor<Alhaja> constructor;

    @Mock
    private TablasDeReferenciaAlhajas conector;

    public AlhajaUTest() {
        super();

        MockitoAnnotations.initMocks(this);

        constructor = ConstructorUtil
            .getConstructor(Alhaja.class, Alhaja.Builder.class, TablasDeReferenciaAlhajas.class);
    }

    @Test
    public void valuarMetalOroPesoTest() {
        addComportamientoGramoOro(BigDecimal.valueOf(104.12));

        Alhaja test = ConstructorUtil.getInstancia(constructor, getBuilder("AU", null, null, null, null), conector);
        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
    }

    @Test
    public void valuarMetalOroPesoFactorTest() {
        addComportamientoGramoOro(BigDecimal.valueOf(104.12));
        addComportamientoFactor(BigDecimal.valueOf(1.09));

        Alhaja test = ConstructorUtil.getInstancia(constructor, getBuilder("AU", "BU", null, null, null), conector);
        assertNotNull(test);

        Avaluo avaluo = test.valuar();

        assertNotNull(avaluo);
    }

    private void addComportamientoGramoOro(BigDecimal retorno) {
        when(conector.obtenerValorGramoOro(any(CaracteristicasGramoOroProveedor.class)))
            .thenReturn(getBigDecimalConsumidor(retorno));
    }

    private void addComportamientoFactor(BigDecimal retorno) {
        when(conector.obtenerFactor(any(MetalCalidadRangoProveedor.class)))
            .thenReturn(getBigDecimalConsumidor(retorno));
    }

    private static Alhaja.Builder getBuilder(
            final String met, final String cal, final ValorExperto ve, final BigDecimal inc, final BigDecimal des) {
        return new Alhaja.Builder() {
            @Override
            public String getMetal() {
                return met;
            }

            @Override
            public String getColor() {
                return null;
            }

            @Override
            public String getCalidad() {
                return cal;
            }

            @Override
            public String getRango() {
                return "R";
            }

            @Override
            public BigDecimal getPeso() {
                return BigDecimal.valueOf(2.13);
            }

            @Override
            public BigDecimal getIncremento() {
                return inc;
            }

            @Override
            public BigDecimal getDesplazamiento() {
                return des;
            }

            @Override
            public ValorExperto getValorExperto() {
                return ve;
            }
        };
    }

    private BigDecimalConsumidor getBigDecimalConsumidor(final BigDecimal valor) {
        return new BigDecimalConsumidor() {
            @Override
            public BigDecimal getValor() {
                return valor;
            }
        };
    }
}
