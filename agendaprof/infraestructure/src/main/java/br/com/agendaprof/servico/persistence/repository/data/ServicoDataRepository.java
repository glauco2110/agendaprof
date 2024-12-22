package br.com.agendaprof.servico.persistence.repository.data;

import br.com.agendaprof.servico.persistence.entity.ServicoData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServicoDataRepository extends CrudRepository<ServicoData, Long> {
}
