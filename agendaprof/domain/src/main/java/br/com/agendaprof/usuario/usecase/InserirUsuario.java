package br.com.agendaprof.usuario.usecase;

import br.com.agendaprof.core.domain.usecase.UseCase;
import br.com.agendaprof.usuario.command.InserirUsuarioCommand;
import br.com.agendaprof.usuario.command.InserirUsuarioOutput;
import br.com.agendaprof.usuario.entity.Senha;
import br.com.agendaprof.usuario.entity.Usuario;
import br.com.agendaprof.usuario.repository.UsuarioRepository;
import br.com.agendaprof.usuario.security.PasswordEncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InserirUsuario implements UseCase<InserirUsuarioCommand, InserirUsuarioOutput> {

    private final UsuarioRepository repository;
    private final PasswordEncryptionService passwordEncryptionService;
    private final UsuarioRegras regras;

    @Override
    public InserirUsuarioOutput execute(InserirUsuarioCommand command) {

        final var rawSenha = new Senha(command.senha());
        Senha senha = passwordEncryptionService.encrypt(rawSenha);

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(command.login());
        usuario.setNome(command.nome());

        regras.execute(usuario);

        usuario = repository.save(usuario);

        return new InserirUsuarioOutput(usuario.getId(), usuario.getNome(), usuario.getLogin(), usuario.getCreatedAt(), usuario.getUpdatedAt());
    }
}
