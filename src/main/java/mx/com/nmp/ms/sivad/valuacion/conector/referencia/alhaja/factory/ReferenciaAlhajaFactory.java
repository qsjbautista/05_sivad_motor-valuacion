/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.referencia.alhaja.factory;


import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObjectFactory;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerFactorRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoMetalRequest;
import mx.com.nmp.ms.sivad.referencia.ws.alhajas.datatypes.ObtenerValorGramoOroRequest;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.CaracteristicasGramoOroProveedor;
import mx.com.nmp.ms.sivad.valuacion.conector.provedor.MetalCalidadRangoProveedor;
import org.springframework.stereotype.Component;

/**
 * Fabrica que se encarga de crear los Tipos de Datos del Servicio Web Referencia de Alhajas.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@Component
public class ReferenciaAlhajaFactory {
    /**
     * Referencia al creador de objetos.
     * @see ObjectFactory
     */
    private ObjectFactory objectFactory;

    /**
     * Constructor.
     */
    public ReferenciaAlhajaFactory() {
        super();

        objectFactory = new ObjectFactory();
    }

    /**
     * Crea una instancia de {@link ObtenerFactorRequest}
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return La instancia de {@link ObtenerFactorRequest}
     */
    public ObtenerFactorRequest crearObtenerFactorRequest(final MetalCalidadRangoProveedor proveedor) {
        ObtenerFactorRequest factor = objectFactory.createObtenerFactorRequest();

        factor.setMetal(proveedor.getMetal());
        factor.setCalidad(proveedor.getCalidad());
        factor.setRango(proveedor.getRango());

        return factor;
    }

    /**
     * Crea una instancia de {@link ObtenerValorGramoMetalRequest}
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return La instancia de {@link ObtenerValorGramoMetalRequest}
     */
    public ObtenerValorGramoMetalRequest crearObtenerValorGramoMetalRequest(
            final MetalCalidadRangoProveedor proveedor) {
        ObtenerValorGramoMetalRequest gramoMetal = objectFactory.createObtenerValorGramoMetalRequest();

        gramoMetal.setMetal(proveedor.getMetal());
        gramoMetal.setCalidad(proveedor.getCalidad());

        return gramoMetal;
    }

    /**
     * Crea una instancia de {@link ObtenerValorGramoOroRequest}
     *
     * @param proveedor Objeto que contiene los criterios de consulta.
     *
     * @return La instancia de {@link ObtenerValorGramoOroRequest}
     */
    public ObtenerValorGramoOroRequest crearObtenerValorGramoOroRequest(
            final CaracteristicasGramoOroProveedor proveedor) {
        ObtenerValorGramoOroRequest gramoOro = objectFactory.createObtenerValorGramoOroRequest();

        gramoOro.setColor(proveedor.getColor());
        gramoOro.setCalidad(proveedor.getCalidad());

        return gramoOro;
    }
}
