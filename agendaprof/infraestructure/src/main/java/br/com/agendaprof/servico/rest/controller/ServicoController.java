package br.com.agendaprof.servico.rest.controller;

import br.com.agendaprof.servico.usecase.SalvarServico;
import br.com.agendaprof.servico.command.AlterarServicoCommand;
import br.com.agendaprof.servico.command.IncluirServicoCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ServicoController {
    private final SalvarServico salvarServico;

    @PostMapping("/servico")
    @ResponseStatus(HttpStatus.CREATED)
    public void incluir(IncluirServicoCommand dto) {
        salvarServico.execute(dto.toDomain());
    }

    @PutMapping("/servico")
    @ResponseStatus(HttpStatus.OK)
    public void alterar(AlterarServicoCommand dto) {
        salvarServico.execute(dto.toDomain());
    }

    @DeleteMapping("/servico/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void excluir(@PathVariable Long id) {

    }
}
