/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.api.ws;

import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaAlhajaServiceEndpoint;
import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaDiamantesServiceEndpoint;
import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.api.ws.exception.WebServiceException;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.ValuadorDiamantesService;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.*;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.xml.ws.Endpoint;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Clase de prueba utilizada para validar el comportamiento de la clase {@link ValuadorDiamantesService}.
 *
 * @author osanchez, ngonzalez
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = MotorValuacionApplication.class, properties = "server.port=10344",
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ValuadorDiamantesEndpointSystemTest {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ValuadorDiamantesEndpointSystemTest.class);

    private static final int NUM_PIEZAS_1 = 1;
    private static final int NUM_PIEZAS_2 = 2;

    private static final String CALIDAD = "14";
    private static final String CERTIFICADO = "ABC";
    private static final String CLARIDAD = "VS1";
    private static final String COLOR_A = "Amarillo";
    private static final String COLOR_D = "D";
    private static final String CORTE = "Oval";
    private static final String METAL = "AU";
    private static final String PIEZA_ALHAJA_ID = "1234567891";
    private static final String PIEZA_COMPLEMENTO_ID = "1234567892";
    private static final String PIEZA_DIAMANTE_ID = "1234567893";
    private static final String PRENDA_ID = "1234567890";
    private static final String PRENDA_RAMO = "Alhajas";
    private static final String PRENDA_SUBRAMO = "Diamantes";
    private static final String PRENDA_TIPO = "A";
    private static final String RANGO = "F1";

    private static final BigDecimal QUILATES =
        new BigDecimal(0.92D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal DESPLAZAMIENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal INCREMENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PESO =
        new BigDecimal(25.00D).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Referencia hacia el endpoint del WS ReferenciaAlhajaService.
     */
    private static Endpoint endpointAlhajas;

    /**
     * Referencia hacia el endpoint del WS ReferenciaDiamanteService.
     */
    private static Endpoint endpointDiamantes;

    /**
     * Referencia hacia el servicio que permite la valuación de prendas.
     */
    @Inject
    private ValuadorDiamantesService valuadorDiamantesService;

    /**
     * Bus para WS ReferenciaAlhajaService.
     */
    @Inject
    private SpringBus busAlhajas;

    /**
     * Bus para WS ReferenciaDiamanteService.
     */
    @Inject
    private SpringBus busDiamantes;



    // METODOS

    /**
     * Constructor.
     */
    public ValuadorDiamantesEndpointSystemTest() {
        super();
    }

    /**
     * Configuración inicial.
     */
    @PostConstruct
    public void setUp() {
        if (ObjectUtils.isEmpty(endpointAlhajas)) {
            endpointAlhajas = new EndpointImpl(busAlhajas, new ReferenciaAlhajaServiceEndpoint());
            ((EndpointImpl) endpointAlhajas).setPublishedEndpointUrl("http://localhost:10344/soap-api/ReferenciaAlhajaService?wsdl");
            endpointAlhajas.publish("/ReferenciaAlhajaService");
        }

        if (ObjectUtils.isEmpty(endpointDiamantes)) {
            endpointDiamantes = new EndpointImpl(busDiamantes, new ReferenciaDiamantesServiceEndpoint());
            ((EndpointImpl) endpointDiamantes).setPublishedEndpointUrl("http://localhost:10344/soap-api/ReferenciaDiamanteService");
            endpointDiamantes.publish("/ReferenciaDiamanteService");
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (correcta)
     *     1 - Pieza con:
     *         1 - Diamante (correcto)
     *     1 - Pieza con:
     *        1 - Complemento (correcto)
     */
    @Test
    public void valuarPrendaBasico01() {
        LOGGER.debug(">> valuarPrendaBasico01");

        Alhaja alhaja = crearAlhaja();
        Diamante diamante = crearDiamante();
        Complemento complemento = crearComplemento();

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);
        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);
        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);
        piezas.add(piezaDiamante);
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        ValuarPrendaBasicoResponse response = valuadorDiamantesService.valuarPrendaBasico(request);
        assertNotNull(response);

        Prenda prendaValuada = response.getPrendaValuada();
        assertNotNull(prendaValuada);
        assertNotNull(prendaValuada.getAvaluo());
        assertNotNull(prendaValuada.getAvaluo().getValorMinimo());
        assertNotNull(prendaValuada.getAvaluo().getValorPromedio());
        assertNotNull(prendaValuada.getAvaluo().getValorMaximo());
        assertNotNull(prendaValuada.getPieza());
        assertEquals(3, prendaValuada.getPieza().size());

        LOGGER.debug("----------------------------------------------------------------------------------");
        LOGGER.debug("Avaluo Total - Valor Minimo: [{}]", prendaValuada.getAvaluo().getValorMinimo());
        LOGGER.debug("Avaluo Total - Valor Promedio: [{}]", prendaValuada.getAvaluo().getValorPromedio());
        LOGGER.debug("Avaluo Total - Valor Maximo: [{}]", prendaValuada.getAvaluo().getValorMaximo());

        for (Pieza pieza : prendaValuada.getPieza()) {
            assertNotNull(pieza.getAvaluo());
            assertNotNull(pieza.getAvaluo().getValorMinimo());
            assertNotNull(pieza.getAvaluo().getValorPromedio());
            assertNotNull(pieza.getAvaluo().getValorMaximo());

            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("Avaluo Pieza - Valor Minimo: [{}]", pieza.getAvaluo().getValorMinimo());
            LOGGER.debug("Avaluo Pieza - Valor Promedio: [{}]", pieza.getAvaluo().getValorPromedio());
            LOGGER.debug("Avaluo Pieza - Valor Maximo: [{}]", pieza.getAvaluo().getValorMaximo());
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (correcta)
     *         1 - Diamante (correcto)
     *         1 - Complemento (correcto)
     */
    @Test(expected = WebServiceException.class)
    public void valuarPrendaBasico02() {
        LOGGER.debug(">> valuarPrendaBasico02");

        Alhaja alhaja = crearAlhaja();
        Diamante diamante = crearDiamante();
        Complemento complemento = crearComplemento();

        Pieza pieza = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, diamante, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(pieza);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);
        valuadorDiamantesService.valuarPrendaBasico(request);
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         0 - Alhaja
     *         0 - Diamante
     *         0 - Complemento
     */
    @Test(expected = WebServiceException.class)
    public void valuarPrendaBasico03() {
        LOGGER.debug(">> valuarPrendaBasico03");

        Pieza pieza = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, null, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(pieza);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);
        valuadorDiamantesService.valuarPrendaBasico(request);
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (con la cantidad de 2)
     */
    @Test(expected = WebServiceException.class)
    public void valuarPrendaBasico04() {
        LOGGER.debug(">> valuarPrendaBasico04");

        Alhaja alhaja = crearAlhaja();

        Pieza pieza = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_2, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(pieza);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);
        valuadorDiamantesService.valuarPrendaBasico(request);
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (vacía)
     */
    @Test(expected = WebServiceException.class)
    public void valuarPrendaBasico05() {
        LOGGER.debug(">> valuarPrendaBasico05");

        Alhaja alhaja = new Alhaja();

        Pieza pieza = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(pieza);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);
        valuadorDiamantesService.valuarPrendaBasico(request);
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (vacío)
     */
    @Test(expected = WebServiceException.class)
    public void valuarPrendaBasico06() {
        LOGGER.debug(">> valuarPrendaBasico06");

        Diamante diamante = new Diamante();

        Pieza pieza = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(pieza);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);
        valuadorDiamantesService.valuarPrendaBasico(request);
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Complemento (vacío)
     */
    @Test(expected = WebServiceException.class)
    public void valuarPrendaBasico07() {
        LOGGER.debug(">> valuarPrendaBasico07");

        Complemento complemento = new Complemento();

        Pieza pieza = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(pieza);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);
        valuadorDiamantesService.valuarPrendaBasico(request);
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Lista de piezas - VACIA
     */
    @Test(expected = WebServiceException.class)
    public void valuarPrendaBasico08() {
        LOGGER.debug(">> valuarPrendaBasico08");

        List<Pieza> piezas = new ArrayList<>();

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);
        valuadorDiamantesService.valuarPrendaBasico(request);
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaNMP" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (correcta)
     *     1 - Pieza con:
     *         1 - Diamante (correcto)
     *     1 - Pieza con:
     *        1 - Complemento (correcto)
     */
    @Test(expected = WebServiceException.class)
    public void valuarPrendaNMP01() {
        LOGGER.debug(">> valuarPrendaNMP01");

        Alhaja alhaja = crearAlhaja();
        Diamante diamante = crearDiamante();
        Complemento complemento = crearComplemento();

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);
        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);
        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);
        piezas.add(piezaDiamante);
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaNMPRequest request = new ValuarPrendaNMPRequest();
        request.setPrenda(prenda);
        valuadorDiamantesService.valuarPrendaNMP(request);
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link Prenda}.
     *
     * @param piezas La lista de piezas que conforman la prenda.
     * @return La entidad {@link Prenda} creada.
     */
    private Prenda crearPrenda(List<Pieza> piezas) {
        Prenda prenda = new Prenda();
        prenda.setId(PRENDA_ID);
        prenda.setRamo(PRENDA_RAMO);
        prenda.setSubramo(PRENDA_SUBRAMO);
        prenda.setTipo(PRENDA_TIPO);
        prenda.getPieza().addAll(piezas);

        return prenda;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link Pieza}.
     *
     * @param id Identificador de la pieza.
     * @param cantidad Número de piezas con características idénticas.
     * @param alhaja La alhaja.
     * @param diamante El diamante.
     * @param complemento El complemento.
     * @return La entidad {@link Pieza} creada.
     */
    private Pieza crearPieza(String id,
                             int cantidad,
                             Alhaja alhaja,
                             Diamante diamante,
                             Complemento complemento) {
        Pieza pieza = new Pieza();
        pieza.setId(id);
        pieza.setCantidad(cantidad);
        pieza.setAlhaja(alhaja);
        pieza.setDiamante(diamante);
        pieza.setComplemento(complemento);

        return pieza;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link Alhaja}.
     *
     * @return La entidad {@link Alhaja} creada.
     */
    private Alhaja crearAlhaja() {
        Alhaja alhaja = new Alhaja();
        alhaja.setCalidad(CALIDAD);
        alhaja.setColor(COLOR_A);
        alhaja.setCondicion("EX");
        alhaja.setDesplazamiento(DESPLAZAMIENTO);
        alhaja.setForma("F-Alhaja");
        alhaja.setIncremento(INCREMENTO);
        alhaja.setPeso(PESO);
        alhaja.setMetal(METAL);
        alhaja.setRango(RANGO);
        alhaja.setTipo("T-Alhaja");
        alhaja.setValorExperto(null);

        return alhaja;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link Diamante}.
     *
     * @return La entidad {@link Diamante} creada.
     */
    private Diamante crearDiamante() {
        Diamante diamante = new Diamante();
        diamante.setCertificado(CERTIFICADO);
        diamante.setClaridad(CLARIDAD);
        diamante.setColor(COLOR_D);
        diamante.setCorte(CORTE);
        diamante.setForma("F-Diamante");
        diamante.setQuilataje(QUILATES);
        diamante.setValorExperto(null);

        return diamante;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link Complemento}.
     *
     * @return La entidad {@link Complemento} creada.
     */
    private Complemento crearComplemento() {
        Complemento complemento = new Complemento();
        complemento.setPeso("P-Complemento");
        complemento.setTipo("T-Complemento");
        complemento.setValorExperto(crearValorExperto(new BigDecimal(10), null));

        return complemento;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link ValorExperto}.
     *
     * @param total El valor del experto por el total de los complementos.
     * @param unitario El valor del experto por pieza del complemento.
     * @return La entidad {@link ValorExperto} creada.
     */
    private ValorExperto crearValorExperto(BigDecimal total, BigDecimal unitario) {
        ValorExperto valorExperto = new ValorExperto();
        valorExperto.setTotal(total);
        valorExperto.setUnitario(unitario);

        return valorExperto;
    }

    /**
     * Metodo utilizado para detener los endpoints.
     */
    @AfterClass
    public static void afterClass() {
        try {
            endpointAlhajas.stop();
            endpointDiamantes.stop();
        } catch (Throwable t) {
            System.out.println("Error thrown: " + t.getMessage());
        }
    }

}
