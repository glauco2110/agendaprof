package br.com.agendaprof.usuario.rest;

import br.com.agendaprof.usuario.command.InserirUsuarioCommand;
import br.com.agendaprof.usuario.command.InserirUsuarioOutput;
import br.com.agendaprof.usuario.usecase.InserirUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController("/usuario")
@RequiredArgsConstructor
public class UsuarioController {
    private final InserirUsuario inserirUsuario;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public InserirUsuarioOutput incluir(InserirUsuarioCommand command){
        return inserirUsuario.execute(command);
    }
}
