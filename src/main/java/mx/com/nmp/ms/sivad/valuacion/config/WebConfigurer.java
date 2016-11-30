package mx.com.nmp.ms.sivad.valuacion.config;

import mx.com.nmp.ms.arquetipo.profile.NmpProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Configuración de la aplicación web con APIs Servlet 3.0.
 *
 * @author osanchez
 */
@Configuration
public class WebConfigurer implements ServletContextInitializer, EmbeddedServletContainerCustomizer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfigurer.class);

    @Inject
    private Environment env;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        if (env.acceptsProfiles(NmpProfile.DEV)) {
            initH2Console(servletContext);
        }
        LOGGER.info("Se configuro la aplicacion Web");
    }


    /**
     * Configurar motor de Servlet: tipos Mime, documento raiz, caché, etc.
     */
    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
        // resolver ssue IE
        mappings.add("html", "text/html;charset=utf-8");
        // resolver issue en CloudFoundry
        mappings.add("json", "text/html;charset=utf-8");
        container.setMimeMappings(mappings);
    }

    /**
     * Inicializa consola H2.
     */
    private void initH2Console(ServletContext servletContext) {
        LOGGER.debug("Inicializando consola H2");
        ServletRegistration.Dynamic h2ConsoleServlet = servletContext.addServlet("H2Console", new org.h2.server.web.WebServlet());
        h2ConsoleServlet.addMapping("/h2-console/*");
        h2ConsoleServlet.setInitParameter("-properties", "src/main/resources/");
        h2ConsoleServlet.setLoadOnStartup(1);
    }
}
