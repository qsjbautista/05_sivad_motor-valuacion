package mx.com.nmp.ms.sivad.valuacion.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ehcache.InstrumentedEhcache;
import mx.com.nmp.ms.arquetipo.config.CoreDatabaseConfiguration;
import mx.com.nmp.ms.arquetipo.config.CoreMetricsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.SortedSet;

/**
 * Configuración de caché.
 *
 * @author osanchez
 */
@SuppressWarnings("unused")
@Configuration
@EnableCaching
@AutoConfigureAfter(value = {CoreMetricsConfiguration.class, CoreDatabaseConfiguration.class, DatabaseConfiguration.class})
public class CacheConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfiguration.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private MetricRegistry metricRegistry;

    private net.sf.ehcache.CacheManager cacheManager;

    @PreDestroy
    public void destroy() {
        LOGGER.info("Cerrando Administrador de Cache");
		SortedSet<String> names = metricRegistry.getNames();
        for (String name : names) {
            metricRegistry.remove(name);
        }
        cacheManager.shutdown();
    }

    @Bean
    public CacheManager cacheManager() {
        LOGGER.debug("Iniciando Ehcache");
        cacheManager = net.sf.ehcache.CacheManager.create();
        cacheManager.getConfiguration().setMaxBytesLocalHeap("16M");
        LOGGER.debug("Registando indicadores de Ehcache para Metrics");
        for (String name : cacheManager.getCacheNames()) {
            net.sf.ehcache.Cache cache = cacheManager.getCache(name);
            if (cache != null) {
                cacheManager.replaceCacheWithDecoratedCache(cache, InstrumentedEhcache.instrument(metricRegistry, cache));
            }
        }
        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }
}
