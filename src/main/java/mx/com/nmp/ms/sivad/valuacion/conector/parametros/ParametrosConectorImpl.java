

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

import static org.springframework.http.HttpMethod.GET;

/**
 * Clase que abstrae la lógica para la conexión con el Microservicio de parámetros
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 * @author ecancino
 */
@Component
public class ParametrosConectorImpl implements ParametrosConector {

    // Pemirte escribir en la bitacora del sistema
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametrosConectorImpl.class);

    private static final String SEPARADOR_MATRIX_VARIABLES = ";";
    private static final String SEPARADOR_NOMBRE_VALOR_MATRIX_VARIABLEs = "=";

    /**
     * Url base para el Microservicio de parametros
     */
    @Value("${servicio.parametros.valor.filtros.baseUrl}")
    private String baseURL;

    /**
     * Nombre del encabezado del ApiKey
     */
    @Value("${servicio.parametros.header.api.name}")
    private String apiName;

    /**
     * Valor del encabezado del ApiKey
     */
    @Value("${servicio.parametros.header.api.key}")
    private String apiKey;

    /**
     * Objeto que permite hacer peticiones REST por http
     */
    private RestTemplate restTemplate;

    /**
     * Constructor de la clase
     */
    public ParametrosConectorImpl() {
        super();

        restTemplate = new RestTemplate();
    }

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
     *
     */
    @Cacheable(value = "ParametrosConector.recuperarValorParametro",
        key = "T(java.util.Objects).hash(#tipoParametro, #parametro, #filtros)")
    public Float recuperarValorParametro(final TipoParametro tipoParametro, final String parametro, final FiltroParametro...filtros) {
        String url = construirURL(tipoParametro, parametro, filtros);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(apiName, apiKey);
        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        try{
            ResponseEntity<ValorParametroResponse> response =
                    restTemplate.exchange(url, GET, httpEntity, ValorParametroResponse.class);

            Objects.requireNonNull(response);
            if (response.getBody() == null || response.getStatusCodeValue() != HttpStatus.OK.value()) {
                String mensaje = String
                    .format("Ocurrio un error al consultar el valor parámetro [%s.%s]", tipoParametro, parametro);
                throw new ValuacionException(WebServiceExceptionCodes.NMPMV012.getCodeException(), mensaje, WebServiceExceptionCodes.NMPMV012.getMessageException());
            }

            return response.getBody().getValor();
        } catch (HttpClientErrorException e) {
            LOGGER.warn("Ocurrio un error al consultar el parametro", e);
            String mensaje = String.format("Ocurrio un error al consultar el parámetro [%s], detalle %s",
                    parametro, e.getResponseBodyAsString());
            throw new ValuacionException(WebServiceExceptionCodes.NMPMV012.getCodeException(), mensaje, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error al conectar con MS de parametros", e);
            final String mensaje = "Ocurrió un error de comunicación con el Micro Servicio Parámetros.";
            throw new ValuacionException(WebServiceExceptionCodes.NMPMV011.getCodeException(), mensaje, e.getMessage());
        }
    }

    /**
     * Se encarga de construir la URL de consulta del recurso
     *
     * @param tipoParametro Tipo del parametro a consultar
     * @param parametro Abreviatura del parametro a consultar
     * @param filtros Filtros adicionales para identificar el parametro
     *
     * @return URL de recurso consultado
     */
    private String construirURL(TipoParametro tipoParametro, String parametro, FiltroParametro ...filtros) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(baseURL);
        String urlBuilder = uriBuilder.buildAndExpand(tipoParametro.name(), parametro).toString();

        return urlBuilder + construirMatrixVariable(filtros);
    }

    /**
     * Se encarga de construir la Matrix Variable basado en los filtros adicionales
     *
     * @param filtros Arreglo con los filtros
     *
     * @return Matrix variable en formato para URL
     */
    private String construirMatrixVariable(FiltroParametro ...filtros) {
        StringBuilder matrixVariable = new StringBuilder();

        if (!ObjectUtils.isEmpty(filtros)) {
            for (FiltroParametro fp : filtros) {
                matrixVariable.append(SEPARADOR_MATRIX_VARIABLES);
                matrixVariable.append(fp.getNombre());
                matrixVariable.append(SEPARADOR_NOMBRE_VALOR_MATRIX_VARIABLEs);
                matrixVariable.append(fp.getValor());
            }
        }

        return matrixVariable.toString();
    }
}
