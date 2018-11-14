package mx.com.nmp.ms.sivad.valuacion.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	private final CorsConfiguration cors = new CorsConfiguration();

	public CorsConfiguration getCors() {
		return cors;
	}

}