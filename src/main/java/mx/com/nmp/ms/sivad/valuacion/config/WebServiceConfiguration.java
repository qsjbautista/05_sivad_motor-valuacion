package mx.com.nmp.ms.sivad.valuacion.config;

import mx.com.nmp.ms.arquetipo.profile.NmpProfile;
import mx.com.nmp.ms.sivad.valuacion.api.ws.ValuadorDiamantesEndpoint;
import mx.com.nmp.ms.sivad.valuacion.ws.diamantes.ValuadorDiamantesService;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.xml.ws.Endpoint;
import java.util.Arrays;
import java.util.Collection;

/**
 * Configuración de web services.
 * La configuración actual hace que estén accesibles en http://servidor:puerto/soap-api/*
 */
@Configuration
public class WebServiceConfiguration {

    @Inject
    private Environment env;

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
        final SpringBus springBus = new SpringBus();

        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(NmpProfile.DEV)) {
            LoggingFeature loggingFeature = new LoggingFeature();
            loggingFeature.setPrettyLogging(true);
            loggingFeature.initialize(springBus);
            springBus.getFeatures().add(loggingFeature);
        }

        return springBus;
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
