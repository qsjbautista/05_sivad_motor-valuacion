package mx.com.nmp.ms.sivad.valuacion.security;

import mx.com.nmp.ms.arquetipo.security.AbstractRESTAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Authentication entry point for REST services
 * <p>
 * We can tell Spring Security exactly what to do if someone tries to access a protected resource
 * without being authenticated.
 */
@Component
public class RESTAuthenticationEntryPoint extends AbstractRESTAuthenticationEntryPoint {

}
