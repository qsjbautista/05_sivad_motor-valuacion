/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.api.ws;

import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaAlhajaServiceEndpoint;
import mx.com.nmp.ms.sivad.referencia.api.ws.ReferenciaDiamantesServiceEndpoint;
import mx.com.nmp.ms.sivad.valuacion.MotorValuacionApplication;
import mx.com.nmp.ms.sivad.valuacion.api.ws.exception.WebServiceException;
import mx.com.nmp.ms.sivad.valuacion.api.ws.exception.WebServiceExceptionCodes;
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

import static org.junit.Assert.*;

/**
 * Clase de prueba utilizada para validar el comportamiento del motor de valuación.
 *
 * @author ngonzalez
 */
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest(classes = MotorValuacionApplication.class, properties = "server.port=10344",
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ValuadorDiamantesITest {

    /**
     * Utilizada para manipular los mensajes informativos y de error.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ValuadorDiamantesITest.class);

    private static final int NUM_PIEZAS_1 = 1;
    private static final int NUM_PIEZAS_2 = 2;
    private static final int NUM_PIEZAS_CERO = 0;
    private static final int NUM_PIEZAS_MENOR_CERO = -1;

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
    private static final String PIEZA_ID = "1234567894";
    private static final String PRENDA_ID = "1234567890";
    private static final String PRENDA_RAMO = "Alhajas";
    private static final String PRENDA_SUBRAMO = "Diamantes";
    private static final String PRENDA_TIPO = "A";
    private static final String RANGO = "F1";

    private static final BigDecimal CERO =
        new BigDecimal(0.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal MENOR_CERO =
        new BigDecimal(-1.00D).setScale(2, BigDecimal.ROUND_HALF_UP);

    private static final BigDecimal DESPLAZAMIENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal INCREMENTO =
        new BigDecimal(1.10D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal PESO =
        new BigDecimal(25.00D).setScale(2, BigDecimal.ROUND_HALF_UP);
    private static final BigDecimal QUILATES =
        new BigDecimal(0.92D).setScale(2, BigDecimal.ROUND_HALF_UP);

    /**
     * Referencia hacia el endpoint del WS ReferenciaAlhajaService.
     */
    private static Endpoint endpointAlhajas;

    /**
     * Referencia hacia el endpoint del WS ReferenciaDiamanteService.
     */
    private static Endpoint endpointDiamantes;

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

    /**
     * Referencia hacia el servicio que permite la valuación de prendas.
     */
    @Inject
    private ValuadorDiamantesService valuadorDiamantesService;



    // METODOS

    /**
     * Constructor.
     */
    public ValuadorDiamantesITest() {
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
    @Test
    public void valuarPrendaNMP01() {
        LOGGER.debug(">> valuarPrendaNMP01");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);
        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, QUILATES, null);
        Complemento complemento =
            crearComplemento(crearValorExperto(null, new BigDecimal(10)));

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

        try {
            valuadorDiamantesService.valuarPrendaNMP(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV001.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaNMP01. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (correcta con 2 piezas)
     */
    @Test
    public void valuarPrendaBasico01() {
        LOGGER.debug(">> valuarPrendaBasico01");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_2, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV002.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico01. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Calidad - Nula)
     */
    @Test
    public void valuarPrendaBasico02() {
        LOGGER.debug(">> valuarPrendaBasico02");

        Alhaja alhaja =
            crearAlhaja(null, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico02. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Calidad - Vacía)
     */
    @Test
    public void valuarPrendaBasico03() {
        LOGGER.debug(">> valuarPrendaBasico03");

        Alhaja alhaja =
            crearAlhaja("", COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico03. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Color - Nulo)
     */
    @Test
    public void valuarPrendaBasico04() {
        LOGGER.debug(">> valuarPrendaBasico04");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, null, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico04. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Color - Vacío)
     */
    @Test
    public void valuarPrendaBasico05() {
        LOGGER.debug(">> valuarPrendaBasico05");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, "", DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico05. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Metal - Nulo)
     */
    @Test
    public void valuarPrendaBasico06() {
        LOGGER.debug(">> valuarPrendaBasico06");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, null, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico06. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Metal - Vacío)
     */
    @Test
    public void valuarPrendaBasico07() {
        LOGGER.debug(">> valuarPrendaBasico07");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, "", RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico07. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Peso - Nulo)
     */
    @Test
    public void valuarPrendaBasico08() {
        LOGGER.debug(">> valuarPrendaBasico08");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, null, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico08. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Peso - Cero)
     */
    @Test
    public void valuarPrendaBasico09() {
        LOGGER.debug(">> valuarPrendaBasico09");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, CERO, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico09. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja (Peso - Menor a Cero)
     */
    @Test
    public void valuarPrendaBasico10() {
        LOGGER.debug(">> valuarPrendaBasico10");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, MENOR_CERO, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV004.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico10. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Corte - Nulo)
     */
    @Test
    public void valuarPrendaBasico11() {
        LOGGER.debug(">> valuarPrendaBasico11");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, null, QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico11. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Corte - Vacío)
     */
    @Test
    public void valuarPrendaBasico12() {
        LOGGER.debug(">> valuarPrendaBasico12");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, "", QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico12. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Color - Nulo)
     */
    @Test
    public void valuarPrendaBasico13() {
        LOGGER.debug(">> valuarPrendaBasico13");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, null, CORTE, QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico13. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Color - Vacío)
     */
    @Test
    public void valuarPrendaBasico14() {
        LOGGER.debug(">> valuarPrendaBasico14");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, "", CORTE, QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico14. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Claridad - Nula)
     */
    @Test
    public void valuarPrendaBasico15() {
        LOGGER.debug(">> valuarPrendaBasico15");

        Diamante diamante =
            crearDiamante(CERTIFICADO, null, COLOR_D, CORTE, QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico15. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Claridad - Vacía)
     */
    @Test
    public void valuarPrendaBasico16() {
        LOGGER.debug(">> valuarPrendaBasico16");

        Diamante diamante =
            crearDiamante(CERTIFICADO, "", COLOR_D, CORTE, QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico16. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Quilates - Nulo)
     */
    @Test
    public void valuarPrendaBasico17() {
        LOGGER.debug(">> valuarPrendaBasico17");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, null, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico17. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Quilates - Cero)
     */
    @Test
    public void valuarPrendaBasico18() {
        LOGGER.debug(">> valuarPrendaBasico18");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, CERO, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico18. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Quilates - Menor a Cero)
     */
    @Test
    public void valuarPrendaBasico19() {
        LOGGER.debug(">> valuarPrendaBasico19");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, MENOR_CERO, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico19. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Número de Piezas - Cero)
     */
    @Test
    public void valuarPrendaBasico20() {
        LOGGER.debug(">> valuarPrendaBasico20");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_CERO, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico20. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante (Número de Piezas - Menor a Cero)
     */
    @Test
    public void valuarPrendaBasico21() {
        LOGGER.debug(">> valuarPrendaBasico21");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_MENOR_CERO, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV005.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico21. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Complemento (Número de Piezas - Cero)
     */
    @Test
    public void valuarPrendaBasico22() {
        LOGGER.debug(">> valuarPrendaBasico22");

        Complemento complemento =
            crearComplemento(null);

        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_CERO, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV006.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico22. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Complemento (Número de Piezas - Cero)
     */
    @Test
    public void valuarPrendaBasico23() {
        LOGGER.debug(">> valuarPrendaBasico23");

        Complemento complemento =
            crearComplemento(null);

        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_MENOR_CERO, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV006.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico23. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Complemento (Valor Experto - Nulo)
     */
    @Test
    public void valuarPrendaBasico24() {
        LOGGER.debug(">> valuarPrendaBasico24");

        Complemento complemento =
            crearComplemento(null);

        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV006.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico24. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Complemento (Valor Experto con Valor - Nulo)
     */
    @Test
    public void valuarPrendaBasico25() {
        LOGGER.debug(">> valuarPrendaBasico25");

        Complemento complemento =
            crearComplemento(crearValorExperto(null, null));

        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV006.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico25. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Complemento (Valor Experto con Valor - Cero)
     */
    @Test
    public void valuarPrendaBasico26() {
        LOGGER.debug(">> valuarPrendaBasico26");

        Complemento complemento =
            crearComplemento(crearValorExperto(null, CERO));

        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV006.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico26. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Complemento (Valor Experto con Valor - Menor a Cero)
     */
    @Test
    public void valuarPrendaBasico27() {
        LOGGER.debug(">> valuarPrendaBasico27");

        Complemento complemento =
            crearComplemento(crearValorExperto(null, MENOR_CERO));

        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV006.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico27. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza sin Alhaja, sin Diamante y sin Complemento
     */
    @Test
    public void valuarPrendaBasico28() {
        LOGGER.debug(">> valuarPrendaBasico28");

        Pieza pieza = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, null, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(pieza);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV007.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico28. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja
     *         1 - Diamante
     *         0 - Complemento
     */
    @Test
    public void valuarPrendaBasico29() {
        LOGGER.debug(">> valuarPrendaBasico29");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);
        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, QUILATES, null);

        Pieza piezaMixta = crearPieza(PIEZA_ID, NUM_PIEZAS_1, alhaja, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaMixta);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV008.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico29. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja
     *         0 - Diamante
     *         1 - Complemento
     */
    @Test
    public void valuarPrendaBasico30() {
        LOGGER.debug(">> valuarPrendaBasico30");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);
        Complemento complemento =
            crearComplemento(crearValorExperto(null, new BigDecimal(10)));

        Pieza piezaMixta = crearPieza(PIEZA_ID, NUM_PIEZAS_1, alhaja, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaMixta);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV008.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico30. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja
     *         0 - Diamante
     *         1 - Complemento
     */
    @Test
    public void valuarPrendaBasico31() {
        LOGGER.debug(">> valuarPrendaBasico31");

        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, QUILATES, null);
        Complemento complemento =
            crearComplemento(crearValorExperto(null, new BigDecimal(10)));

        Pieza piezaMixta = crearPieza(PIEZA_ID, NUM_PIEZAS_1, null, diamante, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaMixta);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV008.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico31. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja
     *         1 - Diamante
     *         1 - Complemento
     */
    @Test
    public void valuarPrendaBasico32() {
        LOGGER.debug(">> valuarPrendaBasico32");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);
        Diamante diamante =
            crearDiamante(CERTIFICADO, CLARIDAD, COLOR_D, CORTE, QUILATES, null);
        Complemento complemento =
            crearComplemento(crearValorExperto(null, new BigDecimal(10)));

        Pieza piezaMixta = crearPieza(PIEZA_ID, NUM_PIEZAS_1, alhaja, diamante, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaMixta);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV008.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico32. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Lista de Piezas - Vacía
     */
    @Test
    public void valuarPrendaBasico33() {
        LOGGER.debug(">> valuarPrendaBasico33");

        List<Pieza> piezas = new ArrayList<>();

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            valuadorDiamantesService.valuarPrendaBasico(request);
            fail();
        } catch (WebServiceException e) {
            assertNotNull(e);
            assertEquals(WebServiceExceptionCodes.NMPMV003.getMessageException(), e.getMessage());
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico33. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante
     */
    @Test
    public void valuarPrendaBasico34() {
        LOGGER.debug(">> valuarPrendaBasico34");

        Diamante diamante =
            crearDiamante(null, CLARIDAD, COLOR_D, CORTE, QUILATES, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            ValuarPrendaBasicoResponse response = valuadorDiamantesService.valuarPrendaBasico(request);
            assertNotNull(response);
            assertNotNull(response.getPrendaValuada());

            Prenda prendaValuada = response.getPrendaValuada();

            assertNotNull(prendaValuada.getAvaluo());
            assertNotNull(prendaValuada.getAvaluo().getValorMinimo());
            assertNotNull(prendaValuada.getAvaluo().getValorPromedio());
            assertNotNull(prendaValuada.getAvaluo().getValorMaximo());
            assertNotNull(prendaValuada.getPieza());
            assertEquals(1, prendaValuada.getPieza().size());

            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("Avaluo Total - Valor Minimo: [{}]", prendaValuada.getAvaluo().getValorMinimo());
            LOGGER.debug("Avaluo Total - Valor Promedio: [{}]", prendaValuada.getAvaluo().getValorPromedio());
            LOGGER.debug("Avaluo Total - Valor Maximo: [{}]", prendaValuada.getAvaluo().getValorMaximo());

            for (Pieza p : prendaValuada.getPieza()) {
                assertNotNull(p.getAvaluo());
                assertNotNull(p.getAvaluo().getValorMinimo());
                assertNotNull(p.getAvaluo().getValorPromedio());
                assertNotNull(p.getAvaluo().getValorMaximo());

                LOGGER.debug("----------------------------------------------------------------------------------");
                LOGGER.debug("Avaluo Pieza - Valor Minimo: [{}]", p.getAvaluo().getValorMinimo());
                LOGGER.debug("Avaluo Pieza - Valor Promedio: [{}]", p.getAvaluo().getValorPromedio());
                LOGGER.debug("Avaluo Pieza - Valor Maximo: [{}]", p.getAvaluo().getValorMaximo());
            }
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico34. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja
     */
    @Test
    public void valuarPrendaBasico35() {
        LOGGER.debug(">> valuarPrendaBasico35");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            ValuarPrendaBasicoResponse response = valuadorDiamantesService.valuarPrendaBasico(request);
            assertNotNull(response);
            assertNotNull(response.getPrendaValuada());

            Prenda prendaValuada = response.getPrendaValuada();

            assertNotNull(prendaValuada.getAvaluo());
            assertNotNull(prendaValuada.getAvaluo().getValorMinimo());
            assertNotNull(prendaValuada.getAvaluo().getValorPromedio());
            assertNotNull(prendaValuada.getAvaluo().getValorMaximo());
            assertNotNull(prendaValuada.getPieza());
            assertEquals(1, prendaValuada.getPieza().size());

            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("Avaluo Total - Valor Minimo: [{}]", prendaValuada.getAvaluo().getValorMinimo());
            LOGGER.debug("Avaluo Total - Valor Promedio: [{}]", prendaValuada.getAvaluo().getValorPromedio());
            LOGGER.debug("Avaluo Total - Valor Maximo: [{}]", prendaValuada.getAvaluo().getValorMaximo());

            for (Pieza p : prendaValuada.getPieza()) {
                assertNotNull(p.getAvaluo());
                assertNotNull(p.getAvaluo().getValorMinimo());
                assertNotNull(p.getAvaluo().getValorPromedio());
                assertNotNull(p.getAvaluo().getValorMaximo());

                LOGGER.debug("----------------------------------------------------------------------------------");
                LOGGER.debug("Avaluo Pieza - Valor Minimo: [{}]", p.getAvaluo().getValorMinimo());
                LOGGER.debug("Avaluo Pieza - Valor Promedio: [{}]", p.getAvaluo().getValorPromedio());
                LOGGER.debug("Avaluo Pieza - Valor Maximo: [{}]", p.getAvaluo().getValorMaximo());
            }
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico35. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Complemento
     */
    @Test
    public void valuarPrendaBasico36() {
        LOGGER.debug(">> valuarPrendaBasico36");

        Complemento complemento =
            crearComplemento(crearValorExperto(null, new BigDecimal(10)));

        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            ValuarPrendaBasicoResponse response = valuadorDiamantesService.valuarPrendaBasico(request);
            assertNotNull(response);
            assertNotNull(response.getPrendaValuada());

            Prenda prendaValuada = response.getPrendaValuada();

            assertNotNull(prendaValuada.getAvaluo());
            assertNotNull(prendaValuada.getAvaluo().getValorMinimo());
            assertNotNull(prendaValuada.getAvaluo().getValorPromedio());
            assertNotNull(prendaValuada.getAvaluo().getValorMaximo());
            assertNotNull(prendaValuada.getPieza());
            assertEquals(1, prendaValuada.getPieza().size());

            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("Avaluo Total - Valor Minimo: [{}]", prendaValuada.getAvaluo().getValorMinimo());
            LOGGER.debug("Avaluo Total - Valor Promedio: [{}]", prendaValuada.getAvaluo().getValorPromedio());
            LOGGER.debug("Avaluo Total - Valor Maximo: [{}]", prendaValuada.getAvaluo().getValorMaximo());

            for (Pieza p : prendaValuada.getPieza()) {
                assertNotNull(p.getAvaluo());
                assertNotNull(p.getAvaluo().getValorMinimo());
                assertNotNull(p.getAvaluo().getValorPromedio());
                assertNotNull(p.getAvaluo().getValorMaximo());

                LOGGER.debug("----------------------------------------------------------------------------------");
                LOGGER.debug("Avaluo Pieza - Valor Minimo: [{}]", p.getAvaluo().getValorMinimo());
                LOGGER.debug("Avaluo Pieza - Valor Promedio: [{}]", p.getAvaluo().getValorPromedio());
                LOGGER.debug("Avaluo Pieza - Valor Maximo: [{}]", p.getAvaluo().getValorMaximo());
            }
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico36. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante
     *     1 - Pieza con:
     *         1 - Alhaja
     */
    @Test
    public void valuarPrendaBasico37() {
        LOGGER.debug(">> valuarPrendaBasico37");

        Diamante diamante =
            crearDiamante(null, CLARIDAD, COLOR_D, CORTE, QUILATES, null);

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);
        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);
        piezas.add(piezaAlhaja);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            ValuarPrendaBasicoResponse response = valuadorDiamantesService.valuarPrendaBasico(request);
            assertNotNull(response);
            assertNotNull(response.getPrendaValuada());

            Prenda prendaValuada = response.getPrendaValuada();

            assertNotNull(prendaValuada.getAvaluo());
            assertNotNull(prendaValuada.getAvaluo().getValorMinimo());
            assertNotNull(prendaValuada.getAvaluo().getValorPromedio());
            assertNotNull(prendaValuada.getAvaluo().getValorMaximo());
            assertNotNull(prendaValuada.getPieza());
            assertEquals(2, prendaValuada.getPieza().size());

            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("Avaluo Total - Valor Minimo: [{}]", prendaValuada.getAvaluo().getValorMinimo());
            LOGGER.debug("Avaluo Total - Valor Promedio: [{}]", prendaValuada.getAvaluo().getValorPromedio());
            LOGGER.debug("Avaluo Total - Valor Maximo: [{}]", prendaValuada.getAvaluo().getValorMaximo());

            for (Pieza p : prendaValuada.getPieza()) {
                assertNotNull(p.getAvaluo());
                assertNotNull(p.getAvaluo().getValorMinimo());
                assertNotNull(p.getAvaluo().getValorPromedio());
                assertNotNull(p.getAvaluo().getValorMaximo());

                LOGGER.debug("----------------------------------------------------------------------------------");
                LOGGER.debug("Avaluo Pieza - Valor Minimo: [{}]", p.getAvaluo().getValorMinimo());
                LOGGER.debug("Avaluo Pieza - Valor Promedio: [{}]", p.getAvaluo().getValorPromedio());
                LOGGER.debug("Avaluo Pieza - Valor Maximo: [{}]", p.getAvaluo().getValorMaximo());
            }
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico37. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante
     *     1 - Pieza con:
     *         1 - Complemento
     */
    @Test
    public void valuarPrendaBasico38() {
        LOGGER.debug(">> valuarPrendaBasico38");

        Diamante diamante =
            crearDiamante(null, CLARIDAD, COLOR_D, CORTE, QUILATES, null);

        Complemento complemento =
            crearComplemento(crearValorExperto(null, new BigDecimal(10)));

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);
        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            ValuarPrendaBasicoResponse response = valuadorDiamantesService.valuarPrendaBasico(request);
            assertNotNull(response);
            assertNotNull(response.getPrendaValuada());

            Prenda prendaValuada = response.getPrendaValuada();

            assertNotNull(prendaValuada.getAvaluo());
            assertNotNull(prendaValuada.getAvaluo().getValorMinimo());
            assertNotNull(prendaValuada.getAvaluo().getValorPromedio());
            assertNotNull(prendaValuada.getAvaluo().getValorMaximo());
            assertNotNull(prendaValuada.getPieza());
            assertEquals(2, prendaValuada.getPieza().size());

            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("Avaluo Total - Valor Minimo: [{}]", prendaValuada.getAvaluo().getValorMinimo());
            LOGGER.debug("Avaluo Total - Valor Promedio: [{}]", prendaValuada.getAvaluo().getValorPromedio());
            LOGGER.debug("Avaluo Total - Valor Maximo: [{}]", prendaValuada.getAvaluo().getValorMaximo());

            for (Pieza p : prendaValuada.getPieza()) {
                assertNotNull(p.getAvaluo());
                assertNotNull(p.getAvaluo().getValorMinimo());
                assertNotNull(p.getAvaluo().getValorPromedio());
                assertNotNull(p.getAvaluo().getValorMaximo());

                LOGGER.debug("----------------------------------------------------------------------------------");
                LOGGER.debug("Avaluo Pieza - Valor Minimo: [{}]", p.getAvaluo().getValorMinimo());
                LOGGER.debug("Avaluo Pieza - Valor Promedio: [{}]", p.getAvaluo().getValorPromedio());
                LOGGER.debug("Avaluo Pieza - Valor Maximo: [{}]", p.getAvaluo().getValorMaximo());
            }
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico38. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Alhaja
     *     1 - Pieza con:
     *         1 - Complemento
     */
    @Test
    public void valuarPrendaBasico39() {
        LOGGER.debug(">> valuarPrendaBasico39");

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Complemento complemento =
            crearComplemento(crearValorExperto(null, new BigDecimal(10)));

        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);
        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaAlhaja);
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            ValuarPrendaBasicoResponse response = valuadorDiamantesService.valuarPrendaBasico(request);
            assertNotNull(response);
            assertNotNull(response.getPrendaValuada());

            Prenda prendaValuada = response.getPrendaValuada();

            assertNotNull(prendaValuada.getAvaluo());
            assertNotNull(prendaValuada.getAvaluo().getValorMinimo());
            assertNotNull(prendaValuada.getAvaluo().getValorPromedio());
            assertNotNull(prendaValuada.getAvaluo().getValorMaximo());
            assertNotNull(prendaValuada.getPieza());
            assertEquals(2, prendaValuada.getPieza().size());

            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("Avaluo Total - Valor Minimo: [{}]", prendaValuada.getAvaluo().getValorMinimo());
            LOGGER.debug("Avaluo Total - Valor Promedio: [{}]", prendaValuada.getAvaluo().getValorPromedio());
            LOGGER.debug("Avaluo Total - Valor Maximo: [{}]", prendaValuada.getAvaluo().getValorMaximo());

            for (Pieza p : prendaValuada.getPieza()) {
                assertNotNull(p.getAvaluo());
                assertNotNull(p.getAvaluo().getValorMinimo());
                assertNotNull(p.getAvaluo().getValorPromedio());
                assertNotNull(p.getAvaluo().getValorMaximo());

                LOGGER.debug("----------------------------------------------------------------------------------");
                LOGGER.debug("Avaluo Pieza - Valor Minimo: [{}]", p.getAvaluo().getValorMinimo());
                LOGGER.debug("Avaluo Pieza - Valor Promedio: [{}]", p.getAvaluo().getValorPromedio());
                LOGGER.debug("Avaluo Pieza - Valor Maximo: [{}]", p.getAvaluo().getValorMaximo());
            }
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico39. {}", e);
            fail();
        }
    }

    /**
     * Utilizado para solicitar la valuación de una prenda por medio de "valuarPrendaBasico" con las
     * siguientes características:
     *
     * 1 - Prenda con:
     *     1 - Pieza con:
     *         1 - Diamante
     *     1 - Pieza con:
     *         1 - Alhaja
     *     1 - Pieza con:
     *         1 - Complemento
     */
    @Test
    public void valuarPrendaBasico40() {
        LOGGER.debug(">> valuarPrendaBasico40");

        Diamante diamante =
            crearDiamante(null, CLARIDAD, COLOR_D, CORTE, QUILATES, null);

        Alhaja alhaja =
            crearAlhaja(CALIDAD, COLOR_A, DESPLAZAMIENTO, INCREMENTO, PESO, METAL, RANGO, null);

        Complemento complemento =
            crearComplemento(crearValorExperto(null, new BigDecimal(10)));

        Pieza piezaDiamante = crearPieza(PIEZA_DIAMANTE_ID, NUM_PIEZAS_1, null, diamante, null);
        Pieza piezaAlhaja = crearPieza(PIEZA_ALHAJA_ID, NUM_PIEZAS_1, alhaja, null, null);
        Pieza piezaComplemento = crearPieza(PIEZA_COMPLEMENTO_ID, NUM_PIEZAS_1, null, null, complemento);

        List<Pieza> piezas = new ArrayList<>();
        piezas.add(piezaDiamante);
        piezas.add(piezaAlhaja);
        piezas.add(piezaComplemento);

        Prenda prenda = crearPrenda(piezas);

        ValuarPrendaBasicoRequest request = new ValuarPrendaBasicoRequest();
        request.setPrenda(prenda);

        try {
            ValuarPrendaBasicoResponse response = valuadorDiamantesService.valuarPrendaBasico(request);
            assertNotNull(response);
            assertNotNull(response.getPrendaValuada());

            Prenda prendaValuada = response.getPrendaValuada();

            assertNotNull(prendaValuada.getAvaluo());
            assertNotNull(prendaValuada.getAvaluo().getValorMinimo());
            assertNotNull(prendaValuada.getAvaluo().getValorPromedio());
            assertNotNull(prendaValuada.getAvaluo().getValorMaximo());
            assertNotNull(prendaValuada.getPieza());
            assertEquals(3, prendaValuada.getPieza().size());

            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("----------------------------------------------------------------------------------");
            LOGGER.debug("Avaluo Total - Valor Minimo: [{}]", prendaValuada.getAvaluo().getValorMinimo());
            LOGGER.debug("Avaluo Total - Valor Promedio: [{}]", prendaValuada.getAvaluo().getValorPromedio());
            LOGGER.debug("Avaluo Total - Valor Maximo: [{}]", prendaValuada.getAvaluo().getValorMaximo());

            for (Pieza p : prendaValuada.getPieza()) {
                assertNotNull(p.getAvaluo());
                assertNotNull(p.getAvaluo().getValorMinimo());
                assertNotNull(p.getAvaluo().getValorPromedio());
                assertNotNull(p.getAvaluo().getValorMaximo());

                LOGGER.debug("----------------------------------------------------------------------------------");
                LOGGER.debug("Avaluo Pieza - Valor Minimo: [{}]", p.getAvaluo().getValorMinimo());
                LOGGER.debug("Avaluo Pieza - Valor Promedio: [{}]", p.getAvaluo().getValorPromedio());
                LOGGER.debug("Avaluo Pieza - Valor Maximo: [{}]", p.getAvaluo().getValorMaximo());
            }
        } catch (Exception e) {
            LOGGER.error("<< Error inesperado al ejecutar valuarPrendaBasico40. {}", e);
            fail();
        }
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.Prenda}.
     *
     * @param piezas La lista de piezas que conforman la prenda.
     * @return La entidad {@link mx.com.nmp.ms.sivad.valuacion.ws.diamantes.datatypes.Prenda} creada.
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
     * @param calidad La calidad del metal de la alhaja.
     * @param color El color del metal de la alhaja.
     * @param desplazamiento El factor de desplazamiento.
     * @param incremento El factor de incremento.
     * @param peso El peso de la alhaja.
     * @param metal El tipo de metal de la alhaja.
     * @param rango El rango.
     * @param valorExperto El valor experto.
     * @return La entidad {@link Alhaja} creada.
     */
    private Alhaja crearAlhaja(String calidad,
                               String color,
                               BigDecimal desplazamiento,
                               BigDecimal incremento,
                               BigDecimal peso,
                               String metal,
                               String rango,
                               ValorExperto valorExperto) {
        Alhaja alhaja = new Alhaja();
        alhaja.setCalidad(calidad);
        alhaja.setColor(color);
        alhaja.setCondicion("EX");
        alhaja.setDesplazamiento(desplazamiento);
        alhaja.setForma("F-Alhaja");
        alhaja.setIncremento(incremento);
        alhaja.setPeso(peso);
        alhaja.setMetal(metal);
        alhaja.setRango(rango);
        alhaja.setTipo("T-Alhaja");
        alhaja.setValorExperto(valorExperto);

        return alhaja;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link Diamante}.
     *
     * @param certificado El certificado del diamante.
     * @param claridad La claridad del diamante.
     * @param color El color del diamante.
     * @param corte El tipo de corte del diamante.
     * @param quilates La calidad en quilates del diamante.
     * @param valorExperto El valor experto.
     * @return La entidad {@link Diamante} creada.
     */
    private Diamante crearDiamante(String certificado,
                                   String claridad,
                                   String color,
                                   String corte,
                                   BigDecimal quilates,
                                   ValorExperto valorExperto) {
        Diamante diamante = new Diamante();
        diamante.setCertificado(certificado);
        diamante.setClaridad(claridad);
        diamante.setColor(color);
        diamante.setCorte(corte);
        diamante.setForma("F-Diamante");
        diamante.setQuilataje(quilates);
        diamante.setValorExperto(valorExperto);

        return diamante;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link Complemento}.
     *
     * @param valorExperto
     * @return La entidad {@link Complemento} creada.
     */
    private Complemento crearComplemento(ValorExperto valorExperto) {
        Complemento complemento = new Complemento();
        complemento.setPeso("P-Complemento");
        complemento.setTipo("T-Complemento");
        complemento.setValorExperto(valorExperto);

        return complemento;
    }

    /**
     * Metodo auxiliar utilizado para crear una entidad de tipo {@link ValorExperto}.
     *
     * @param total El valor del experto por el total de los complementos.
     * @param unitario El valor del experto por pieza del complemento.
     * @return La entidad {@link ValorExperto} creada.
     */
    private ValorExperto crearValorExperto(BigDecimal total,
                                           BigDecimal unitario) {
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
