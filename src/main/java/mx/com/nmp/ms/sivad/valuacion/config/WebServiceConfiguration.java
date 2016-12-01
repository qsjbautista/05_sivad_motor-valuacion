package mx.com.nmp.ms.sivad.valuacion.config;

import mx.com.nmp.ms.sivad.valuacion.api.ws.ValuadorDiamantesEndpoint;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.ValuadorDiamantesService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

/**
 * Configuración de web services.
 * La configuración actual hace que estén accesibles en http://servidor:puerto/soap-api/*
 */
@Configuration
public class WebServiceConfiguration {

    /**
     * Configura la ruta en la que serán expuestos los web services
     *
     * @return servlet de web services
     */
    @Bean
    public ServletRegistrationBean cxfServlet() {
        return new ServletRegistrationBean(new CXFServlet(), "/soap-api/*");
    }

    /**
     * Bus CXF
     *
     * @return
     */
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }

    /**
     * Bean con endpoint de valuación de diamantes
     *
     * @return bean
     */
    @Bean
    public ValuadorDiamantesService valuadorDiamantesService() {
        return new ValuadorDiamantesEndpoint();
    }

    /**
     * Configuración del WS de Alhajas
     *
     * @return endpoint de alhajas
     */
    @Bean
    public Endpoint endpointValuadorDiamantes() {
        EndpointImpl endpoint = new EndpointImpl(springBus(), valuadorDiamantesService());
        endpoint.publish("/ValuadorDiamantes");
        endpoint.setWsdlLocation("ValuadorDiamantes.wsdl");
        return endpoint;
    }
}
