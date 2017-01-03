package mx.com.nmp.ms.sivad.valuacion.config;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import mx.com.nmp.ms.arquetipo.profile.NmpProfile;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.SQLException;

/**
 * Configuración de base de datos
 *
 * @author osanchez
 */
@Configuration
@EnableJpaRepositories("mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository")
public class DatabaseConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    @Profile(NmpProfile.DEV)
    public Server h2TCPServer() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers");
    }

    @Bean
    public Hibernate4Module hibernate4Module() {
        return new Hibernate4Module();
    }

}
