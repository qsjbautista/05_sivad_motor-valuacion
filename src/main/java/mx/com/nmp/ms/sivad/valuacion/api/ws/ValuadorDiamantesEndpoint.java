/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.api.ws;

import com.codahale.metrics.annotation.Timed;
import mx.com.nmp.ms.sivad.valuacion.api.ws.exception.WebServiceExceptionCodes;
import mx.com.nmp.ms.sivad.valuacion.api.ws.exception.WebServiceExceptionFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ValuacionException;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.AlhajaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.ComplementarioFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.DiamanteFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.PrendaFactory;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.AlhajaDTO;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.ComplementarioDTO;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.DiamanteDTO;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.ValuadorDiamantesService;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

/**
 * Implementación de ValuadorDiamantesService, la cual expone los servicios que permitirán realizar
 * la valuación de las prendas.
 *
 * @author osanchez, ngonzalez, ecancino
 */
@SuppressWarnings("SpringAutowiredFieldsWarningInspection")
public class ValuadorDiamantesEndpoint implements ValuadorDiamantesService {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ValuadorDiamantesEndpoint.class);

    /**
     * Referencia
     */
    @Inject
    private PrendaFactory prendaFactory;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja}.
     */
    @Inject
    private AlhajaFactory alhajaFactory;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante}.
     */
    @Inject
    private DiamanteFactory diamanteFactory;

    /**
     * Referencia hacia la fábrica de entidades tipo {@link Complementario}.
     */
    @Inject
    private ComplementarioFactory complementarioFactory;



    // METODOS

    /**
     * Servicio que permite valuar una pieza compuesta de alhajas, diamantes y/o complemento con base en
     * las tablas de referencia: industriales y comerciales.
     *
     * @param parameters La pieza compuesta que se desea valuar.
     * @return Se devuelve el mensaje de entrada enriquecido con los valores (mínimo, promedio y máximo)
     * correspondientes a cada elemento valuado de la pieza (Alhaja / Diamante / Complemento); así como
     * los valores (mínimo, promedio y máximo) correspondientes al valor total de la Prenda.
     */
    @Override
    @Timed
    public ValuarPrendaBasicoResponse valuarPrendaBasico(ValuarPrendaBasicoRequest parameters) {
        LOGGER.info(">> valuarPrendaBasico({}).", parameters);

        // MAPA UTILIZADO PARA MANTENER LA RELACIÓN DE LA PIEZA ORIGINAL CON LA PIEZA VALUADA.
        Map<Pieza, mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza> relacionPiezas = new HashMap<>();

        // SE CONSTRUYE LA PRENDA QUE SE VA A VALUAR.
        Prenda prenda = parameters.getPrenda();
        String condionPrenda = recuperarCondicionFisica(prenda);

        List<mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza> piezas = crearListaPiezas(prenda, relacionPiezas);

        mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Prenda prendaValuable;
        try {
            prendaValuable = prendaFactory.create(piezas, condionPrenda);
        } catch (IllegalArgumentException e) {
            LOGGER.error("<< valuarPrendaBasico. {}",
                WebServiceExceptionCodes.NMPMV003.getMessageException());

            throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                WebServiceExceptionCodes.NMPMV003.getCodeException(),
                WebServiceExceptionCodes.NMPMV003.getMessageException(), e);
        }

        mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.Avaluo avaluo;
        try {
            avaluo = prendaValuable.valuar();
        } catch (ValuacionException e) {
            LOGGER.error("<< valuarPrendaBasico. {}",
                WebServiceExceptionCodes.NMPMV009.getMessageException());

            throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                WebServiceExceptionCodes.NMPMV009.getCodeException(),
                WebServiceExceptionCodes.NMPMV009.getMessageException(), e);
        } catch (Exception e) {
            LOGGER.error("<< valuarPrendaBasico. {}",
                WebServiceExceptionCodes.NMPMV010.getMessageException());

            throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                WebServiceExceptionCodes.NMPMV010.getCodeException(),
                WebServiceExceptionCodes.NMPMV010.getMessageException(), e);
        }

        // SE REALIZA LA ASIGNACIÓN DE LOS AVALÚOS POR PIEZA Y DEL AVALÚO TOTAL.
        prenda = asignarAvaluosPiezas(prenda, relacionPiezas);

        Avaluo avaluoPrenda = new Avaluo();
        avaluoPrenda.setValorMinimo(avaluo.valorMinimo());
        avaluoPrenda.setValorPromedio(avaluo.valorPromedio());
        avaluoPrenda.setValorMaximo(avaluo.valorMaximo());
        prenda.setAvaluo(avaluoPrenda);

        // SE CONSTRUYE EL RESPONSE CON LA RESPUESTA DEL SERVICIO.
        ValuarPrendaBasicoResponse response = new ValuarPrendaBasicoResponse();
        response.setPrendaValuada(prenda);

        return response;
    }

    /**
     * Servicio que permite valuar una pieza compuesta de alhajas, diamantes y/o complemento con base en
     * una metodología de cálculo utilizada por NMP.
     *
     * @param parameters La pieza compuesta que se desea valuar.
     * @return Se devuelve el mensaje de entrada enriquecido con los valores (mínimo, promedio y máximo)
     * correspondientes a cada elemento valuado de la pieza (Alhaja / Diamante / Complemento); así como
     * los valores (mínimo, promedio y máximo) correspondientes al valor total de la Prenda.
     */
    @Override
    @Timed
    public ValuarPrendaNMPResponse valuarPrendaNMP(ValuarPrendaNMPRequest parameters) {
        LOGGER.info(">> valuarPrendaNMP({}).", parameters);

        LOGGER.error("<< valuarPrendaNMP. {}",
            WebServiceExceptionCodes.NMPMV001.getMessageException());

        throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
            WebServiceExceptionCodes.NMPMV001.getCodeException(),
            WebServiceExceptionCodes.NMPMV001.getMessageException());
    }

    /**
     * Metodo auxiliar utilizado para crear la lista de piezas que serán valuadas con base en la información del
     * argumento recibido.
     *
     * @param prenda La prenda con la información que será utilizada para realizar la valuación.
     * @param relacionPiezas El mapa que mantiene la relación de la pieza original con la pieza valuada.
     * @return La lista de piezas que conforman la prenda.
     */
    private List<mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza> crearListaPiezas(Prenda prenda,
        Map<Pieza, mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza> relacionPiezas) {

        LOGGER.debug(">> crearListaPiezas({}).", prenda);

        List<mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza> piezas = new ArrayList<>();
        for (Pieza pieza : prenda.getPieza()) {

            // SE VALIDA QUE SOLAMENTE HAYA UNA PIEZA VALUABLE (ALHAJA, DIAMANTE O COMPLEMENTO) DENTRO DE LA PIEZA.
            int numPiezasValuables = 0;

            if (!ObjectUtils.isEmpty(pieza.getAlhaja())) {
                numPiezasValuables++;
            }

            if (!ObjectUtils.isEmpty(pieza.getDiamante())) {
                numPiezasValuables++;
            }

            if (!ObjectUtils.isEmpty(pieza.getComplemento())) {
                numPiezasValuables++;
            }

            if (numPiezasValuables == 0) {
                LOGGER.error("<< crearListaPiezas. {}",
                    WebServiceExceptionCodes.NMPMV007.getMessageException());

                throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                    WebServiceExceptionCodes.NMPMV007.getCodeException(),
                    WebServiceExceptionCodes.NMPMV007.getMessageException());
            }

            if (numPiezasValuables > 1) {
                LOGGER.error("<< crearListaPiezas. {}",
                    WebServiceExceptionCodes.NMPMV008.getMessageException());

                throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                    WebServiceExceptionCodes.NMPMV008.getCodeException(),
                    WebServiceExceptionCodes.NMPMV008.getMessageException());
            }

            // SE CREA LA PIEZA VALUABLE.
            if (!ObjectUtils.isEmpty(pieza.getAlhaja())) {
                if (pieza.getCantidad() > 1) {
                    LOGGER.error("<< crearListaPiezas. {}",
                        WebServiceExceptionCodes.NMPMV002.getMessageException());

                    throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                        WebServiceExceptionCodes.NMPMV002.getCodeException(),
                        WebServiceExceptionCodes.NMPMV002.getMessageException());
                }

                mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja alhaja =
                    crearAlhaja(pieza.getAlhaja());
                relacionPiezas.put(pieza, alhaja);
                piezas.add(alhaja);
            } else if (!ObjectUtils.isEmpty(pieza.getDiamante())) {
                mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante diamante =
                    crearDiamante(pieza.getCantidad(), pieza.getDiamante());
                relacionPiezas.put(pieza, diamante);
                piezas.add(diamante);
            } else if (!ObjectUtils.isEmpty(pieza.getComplemento())) {
                Complementario complementario =
                    crearComplementario(pieza.getCantidad(), pieza.getComplemento());
                relacionPiezas.put(pieza, complementario);
                piezas.add(complementario);
            }
        }

        return piezas;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo
     * {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja}
     * con base en la información del argumento recibido.
     *
     * @param alhaja La información de la pieza de tipo alhaja.
     * @return El objeto {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja} creado.
     */
    private mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja crearAlhaja(Alhaja alhaja) {
        LOGGER.debug(">> crearAlhaja({}).", alhaja);

        AlhajaDTO alhajaDTO = new AlhajaDTO(
            alhaja.getMetal(),
            alhaja.getColor(),
            alhaja.getCalidad(),
            alhaja.getRango(),
            alhaja.getPeso(),
            alhaja.getIncremento(),
            ((!ObjectUtils.isEmpty(alhaja.getDesplazamiento()) &&
                (alhaja.getDesplazamiento().compareTo(BigDecimal.ZERO) > 0))
                ? alhaja.getDesplazamiento() : null),
            crearValorExperto(alhaja.getValorExperto()));

        mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Alhaja alhajaValuable;

        try {
            alhajaValuable = alhajaFactory.create(alhajaDTO);
        } catch (IllegalArgumentException e) {
            LOGGER.error("<< crearAlhaja. {}",
                WebServiceExceptionCodes.NMPMV004.getMessageException());

            throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                WebServiceExceptionCodes.NMPMV004.getCodeException(),
                WebServiceExceptionCodes.NMPMV004.getMessageException(), e);
        }

        return alhajaValuable;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo
     * {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante}
     * con base en la información de los argumentos recibidos.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param diamante La información de la pieza de tipo diamante.
     * @return El objeto {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante} creado.
     */
    private mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante crearDiamante(int numeroDePiezas,
                                                                                Diamante diamante) {
        LOGGER.debug(">> crearDiamante({}, {}).", numeroDePiezas, diamante);

        DiamanteDTO diamanteDTO = new DiamanteDTO(
            numeroDePiezas,
            diamante.getCorte(),
            diamante.getColor(),
            diamante.getClaridad(),
            diamante.getQuilataje(),
            diamante.getCertificado(),
            crearValorExperto(diamante.getValorExperto()),
            diamante.getQuilatesDesde(),
            diamante.getQuilatesHasta());

        mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Diamante diamanteValuable;

        try {
            diamanteValuable = diamanteFactory.create(diamanteDTO);
        } catch (IllegalArgumentException e) {
            LOGGER.error("<< crearDiamante. {}",
                WebServiceExceptionCodes.NMPMV005.getMessageException());

            throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                WebServiceExceptionCodes.NMPMV005.getCodeException(),
                WebServiceExceptionCodes.NMPMV005.getMessageException(), e);
        }

        return diamanteValuable;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo
     * {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario}
     * con base en la información de los argumentos recibidos.
     *
     * @param numeroDePiezas El número de piezas con características idénticas.
     * @param complemento La información de la pieza complementaria.
     * @return El objeto {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Complementario} creado.
     */
    private Complementario crearComplementario(int numeroDePiezas,
                                               Complemento complemento) {
        LOGGER.debug(">> crearComplementario({}, {}).", numeroDePiezas, complemento);

        ComplementarioDTO complementarioDTO = new ComplementarioDTO(
            numeroDePiezas,
            crearValorExperto(complemento.getValorExperto()));

        Complementario complementoValuable;

        try {
            complementoValuable = complementarioFactory.create(complementarioDTO);
        } catch (IllegalArgumentException e) {
            LOGGER.error("<< crearComplementario. {}",
                WebServiceExceptionCodes.NMPMV006.getMessageException());

            throw WebServiceExceptionFactory.crearWebServiceExceptionCon(
                WebServiceExceptionCodes.NMPMV006.getCodeException(),
                WebServiceExceptionCodes.NMPMV006.getMessageException(), e);
        }

        return complementoValuable;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo
     * {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto}
     * con base en la información del argumento recibido.
     *
     * @param valorExperto El valor estimado por un experto.
     * @return El objeto {@link mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto} creado.
     */
    private mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto crearValorExperto(ValorExperto valorExperto) {
        LOGGER.debug(">> crearValorExperto({}).", valorExperto);

        mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto valExperto = null;

        if (!ObjectUtils.isEmpty(valorExperto) && !ObjectUtils.isEmpty(valorExperto.getTotal())) {
            valExperto =
                new mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto(valorExperto.getTotal(),
                    mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto.TipoEnum.TOTAL);
        } else if (!ObjectUtils.isEmpty(valorExperto) && !ObjectUtils.isEmpty(valorExperto.getUnitario())) {
            valExperto =
                new mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto(valorExperto.getUnitario(),
                    mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.ValorExperto.TipoEnum.UNITARIO);
        }

        return valExperto;
    }

    /**
     * Metodo auxiliar utilizado para asignar los avalúos que resultaron para cada pieza de la prenda.
     *
     * @param prenda La prenda original a la cual se le asignarán los avalúos de sus piezas.
     * @param relacionPiezas El mapa que mantiene la relación de la pieza original con la pieza valuada.
     * @return La prenda original con los valores de los avalúos de cada una de sus piezas.
     */
    private Prenda asignarAvaluosPiezas(Prenda prenda,
        Map<Pieza, mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza> relacionPiezas) {
        LOGGER.debug(">> asignarAvaluos({}, {}).", prenda);

        for (Pieza pieza : prenda.getPieza()) {
            mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza piezaValuada = relacionPiezas.get(pieza);

            Avaluo avaluo = new Avaluo();
            avaluo.setValorMinimo(piezaValuada.getAvaluo().valorMinimo());
            avaluo.setValorPromedio(piezaValuada.getAvaluo().valorPromedio());
            avaluo.setValorMaximo(piezaValuada.getAvaluo().valorMaximo());
            pieza.setAvaluo(avaluo);
        }

        return prenda;
    }

    /**
     * Recupera la condición fisica de la prenda, por el momento se recupera de la {@link Alhaja}
     * mas adelante deberia obtenerce directamente de la {@link Prenda}
     *
     * ToDo Cuando se valuen prendas sin alhajas cambiar {@link Alhaja#condicion} a la {@link Prenda}.
     */
    private static String recuperarCondicionFisica(Prenda prenda) {
        LOGGER.debug(">> recuperarCondicionFisica. {}", prenda);
        Iterator<Pieza> iterator = prenda.getPieza().iterator();

        while (iterator.hasNext()) {
            Pieza p = iterator.next();

            if (!ObjectUtils.isEmpty(p.getAlhaja())) {
                String condicion = p.getAlhaja().getCondicion();

                // Si la alhaja solo se envio con el fin de recuperar la propiedad condicion fisica de la prenda
                // se elimina de la lista de piezas a valuar
                if (p.getCantidad() == 0 && isAlhajaParaCondicionPrenda(p.getAlhaja())) {
                    iterator.remove();
                    LOGGER.info("Se elimina la alhaja ya que no se debe valuar, " +
                        "solo se envio para recuperar condicion fisica de la prenda");
                }

                LOGGER.debug(">> recuperarCondicionFisica. Result [{}]", condicion);

                return condicion;
            }
        }

        LOGGER.error("No se encontro una alhaja en la lista de piezas," +
            "si se realizaran valuaciones sin alhaja, cambiar la propiedad Alhaja.condicion a la Prenda");
        LOGGER.debug(">> recuperarCondicionFisica. Result [{}]", "XX");

        // Este flujo no debera existir cuando se realice el cambio de Alhaja.condicion a la Prenda
        // Se regresa XX para evitar la validacion de nulo o cadena vacia.
        return "XX";
    }

    /**
     * Verifica si la alhaja solo se envio para recuperar el atributo condicion fisica de la prenda.
     * Para considerar si la alhaja se envia solo para recuperar el mencionado atributo debe cumplir con:
     * La alhaja debe contener todos los atributos vacios excepto el atributo condicion fisica de la prenda.
     *
     * @param alhaja La alhaja a validar.
     *
     * @return Verdadero si la alhaja esta vacia, falso si no.
     */
    private static boolean isAlhajaParaCondicionPrenda(Alhaja alhaja) {
        return isNullStringPropertiesAlhaja(alhaja) &&
            isNullBigdecimalPropertiesAlhaja(alhaja) && isEmpty(alhaja.getValorExperto());
    }

    /**
     * Verifica si los atributos tipo {@link String} de la alhaja cumplen con la condicion:
     * La alhaja debe contener todos los atributos vacios excepto el atributo condicion fisica de la prenda.
     *
     * @param alhaja La alhaja a validar.
     *
     * @return Verdadero si cumple la condicion, falso si no.
     */
    private static boolean isNullStringPropertiesAlhaja(Alhaja alhaja) {
        return isEmpty(alhaja.getTipo()) && isEmpty(alhaja.getForma()) && isEmpty(alhaja.getMetal()) &&
            isEmpty(alhaja.getColor()) && isEmpty(alhaja.getRango()) && isEmpty(alhaja.getCalidad()) &&
            StringUtils.hasText(alhaja.getCondicion());
    }

    /**
     * Verifica si los atributos tipo {@link BigDecimal} de la alhaja cumplen con la condicion:
     * La alhaja debe contener todos los atributos vacios excepto el atributo condicion fisica de la prenda.
     *
     * @param alhaja La alhaja a validar.
     *
     * @return Verdadero si cumple la condicion, falso si no.
     */
    private static boolean isNullBigdecimalPropertiesAlhaja(Alhaja alhaja) {
        return isEmpty(alhaja.getPeso()) && isEmpty(alhaja.getIncremento()) && isEmpty(alhaja.getDesplazamiento());
    }

    /**
     * Verifica si un {@link BigDecimal} esta vacio.
     *
     * @param numero Objeto a validar.
     *
     * @return Verdadero si esta vacio, falso si no.
     */
    private static boolean isEmpty(BigDecimal numero) {
        return numero == null || numero.doubleValue() == 0;
    }

    /**
     * Verifica si un {@link String} esta vacio.
     *
     * @param cadena Objeto a validar.
     *
     * @return Verdadero si esta vacio, falso si no.
     */
    private static boolean isEmpty(String cadena) {
        return !StringUtils.hasText(cadena);
    }

    private static boolean isEmpty(ValorExperto valorExperto) {
        return valorExperto == null || (isEmpty(valorExperto.getTotal()) && isEmpty(valorExperto.getUnitario()));
    }

}
