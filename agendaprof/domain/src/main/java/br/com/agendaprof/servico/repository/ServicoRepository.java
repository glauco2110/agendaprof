package br.com.agendaprof.servico.repository;

import br.com.agendaprof.core.domain.repository.BaseCrudRepository;
import br.com.agendaprof.servico.entity.Servico;

import java.util.Optional;

public interface ServicoRepository extends BaseCrudRepository<Servico, Servico> {
    Optional<Servico> existeServicoMesmoNome(Servico servico);
}
