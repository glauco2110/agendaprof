package br.com.agendaprof.servico.usecase;

import br.com.agendaprof.core.domain.usecase.UseCase;
import br.com.agendaprof.servico.entity.Servico;
import br.com.agendaprof.servico.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SalvarServico implements UseCase<Servico, Servico> {

    private final ServicoRepository repository;
    private final ServicoRegras regras;

    @Override
    public Servico execute(Servico input) {
        this.regras.execute(input);
        return this.repository.save(input);
    }
}
