

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


package mx.com.nmp.ms.sivad.valuacion.conector;


import mx.com.nmp.ms.sivad.valuacion.api.ws.exception.WebServiceExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.conector.parametros.ValorParametroResponse;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ValuacionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static mx.com.nmp.ms.sivad.valuacion.conector.parametros.TipoParametro.SUCURSAL_SUBRAMO;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;


/**
 * Servicio REST Mock para pruebas de IT del motor de valuación
 *
 * @author ecancino
 */
@RestController
@ResponseStatus(OK)
@RequestMapping(value = "/parametro", produces = APPLICATION_JSON_UTF8_VALUE)
public class ParametrosAPIMock {

    // Pemite escribir en la bitacora del sistema
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametrosAPIMock.class);

    /**
     * Constructor de la clase
     */
    public ParametrosAPIMock() {
        super();
    }

    /**
     * {@link RequestMethod#GET} /parametro/valor/filtros/{tipo}/{abreviatura} : Punto final que
     * expone la función de recuperar el valor de un parámetro del tipo, abreviatura y filtros indicados.
     *
     * @param tipo Indica el tipo de parámetro para hacer el filtro.
     * @param abreviatura Indica la abreviatura del parámetro para hacer el filtro.
     * @param filtros Valores adicionales para realizar el filtro
     *
     * @return {@link HttpStatus#OK} Se recuperan los parámetros de manera correcta.
     *         {@link HttpStatus#BAD_REQUEST} Si el valor recibido como filtro no es valido.
     *         {@link HttpStatus#NOT_FOUND} Si el valor de parámetro no existe.
     *         {@link HttpStatus#INTERNAL_SERVER_ERROR} Error no esperado.
     */
    @GetMapping("/valor/filtros/{tipo}/{abreviatura}")
    public ValorParametroResponse recuperarValor(
        @PathVariable("tipo")
            String tipo,
        @PathVariable("abreviatura")
            String abreviatura,
        @MatrixVariable(required = false)
            Map<String, String> filtros) {
        LOGGER.trace(">> recuperar() > parámetros {}, {}, {}", tipo, abreviatura, filtros);

        ValorParametroResponse resultado = new ValorParametroResponse();

        if (SUCURSAL_SUBRAMO.name().equals(tipo)) {
            if ("AP".equals(abreviatura)) {

                String subramo = null;
                for (Map.Entry entryCatalogo : filtros.entrySet()) {
                    if (entryCatalogo.getKey().equals("subramo")) {
                        subramo = entryCatalogo.getValue().toString();
                    }

                    if (subramo.equals("Diamantes")) {
                        resultado.setValor(1F);
                    } else {
                        resultado.setValor(0F);
                    }
                }
            }
        } else {
            throw new ValuacionException(WebServiceExceptionCodes.NMPMV010.getMessageException(), "", "");
        }

        LOGGER.trace(">> recuperar() < retorno {}", resultado);

        return resultado;
    }
}


