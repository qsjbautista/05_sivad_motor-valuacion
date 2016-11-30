package mx.com.nmp.ms.sivad.valuacion;

import mx.com.nmp.ms.arquetipo.logger.util.EnvironmentLogUtil;
import mx.com.nmp.ms.arquetipo.profile.DefaultProfileUtil;
import mx.com.nmp.ms.arquetipo.profile.NmpProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;

@SpringBootApplication
public class MotorValuacionApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(MotorValuacionApplication.class);

    @Inject
    private Environment env;


    /**
     * Despues de inicializacion.
     * <p>
     * Se pueden especificar perfiles como argumentos de línea de comandos: --spring.profiles.active=el-perfil-activo
     */
    @PostConstruct
    public void initApplication() {
        LOGGER.info("Ejecutando con perfil(es) de Spring : {}", Arrays.toString(env.getActiveProfiles()));
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());
        if (activeProfiles.contains(NmpProfile.DEV) && activeProfiles.contains(NmpProfile.PROD)) {
            LOGGER.error("Hay un problema de configuración. No deberían estar presentes los perfiles Dev y Prod a la vez.");
        }
    }

    /**
     * Metodo principal, utilizado para ejecutar la aplicación
     *
     * @param args argumentos de línea de comandos
     * @throws UnknownHostException si no se puede convertir el nombre de localhost a una dirección
     */
    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(MotorValuacionApplication.class);
        DefaultProfileUtil.addDefaultProfile(app);

        Environment env = app.run(args).getEnvironment();
        LOGGER.info(EnvironmentLogUtil.envHeader(env));
        LOGGER.info(EnvironmentLogUtil.configServerHeader(env));
    }
}
