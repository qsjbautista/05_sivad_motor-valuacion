package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.repository;

import mx.com.nmp.ms.arquetipo.annotation.validation.NotNull;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo;
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Quarksoft on 29/12/2016.
 */
@Component
public class PoliticasCastigoRepositoryImpl implements PoliticasCastigoRepository {

    @Override
    public PoliticasCastigo consultar() {
        return null;
    }

    @Override
    public void actualizar(@NotNull PoliticasCastigo politicasCastigo) {

    }

    @Override
    public List<PoliticasCastigo> consultar(@NotNull DateTime fecha) {
        return null;
    }

}
