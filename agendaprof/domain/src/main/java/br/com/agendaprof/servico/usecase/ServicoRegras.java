package br.com.agendaprof.servico.usecase;

import br.com.agendaprof.core.domain.usecase.BaseRegras;
import br.com.agendaprof.core.exceptions.RegraNegocioException;
import br.com.agendaprof.servico.entity.Servico;
import br.com.agendaprof.servico.repository.ServicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ServicoRegras extends BaseRegras<Servico> {

    private final ServicoRepository repository;

    @Override
    protected List<RegraNegocioException> validate(Servico servico) {
        List<RegraNegocioException> erros = new ArrayList<>();

        servico.validate(erros);

        Optional<Servico> existe = repository.existeServicoMesmoNome(servico);

        if(existe.isPresent()) erros.add(new RegraNegocioException("Já existe um serviço cadastrado com este nome"));

        return erros;
    }
}
