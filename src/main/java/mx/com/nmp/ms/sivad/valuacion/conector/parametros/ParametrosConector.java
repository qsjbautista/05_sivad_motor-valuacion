

/*
 *
 *
 *
 * <p><a href="https://wiki.quarksoft.net/display/NMPMidasCloud/Home">Sistema de Operación Prendaria</a></p>
 *
 * <p><b><a href="https://quarksoft.net/">Quarksoft S.A.P.I. de C.V. Copyrigth © 2018</a></b></p>
 *
 *
 */


package mx.com.nmp.ms.sivad.valuacion.conector.parametros;


import mx.com.nmp.ms.sivad.valuacion.api.ws.exception.WebServiceExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ValuacionException;


/**
 * Define el contrato a cumplir para la conexión con el Microservicio de parámetros
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface ParametrosConector {

    /**
     * Se encarga establcer comunicación con el Microservicio de parámetros para recuperar el valor de un parámetro
     *
     * @param tipoParametro Indica el tipo de parámetro a consultar
     * @param parametro Abreviatura del parámetro
     * @param filtros Filtros adicionales para identificar al parametro
     *
     * @return Valor del parametro
     *
     * @throws ValuacionException Si no esta activo el Microservicio o ocurre algún error al recuperar el valor
     * @see WebServiceExceptionCodes#NMPMV011
     * @see WebServiceExceptionCodes#NMPMV012
     */
    Float recuperarValorParametro(TipoParametro tipoParametro, String parametro, FiltroParametro... filtros);
}
