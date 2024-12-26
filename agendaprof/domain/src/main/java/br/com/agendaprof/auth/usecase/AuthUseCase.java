package br.com.agendaprof.auth.usecase;

import br.com.agendaprof.auth.command.LoginCommand;
import br.com.agendaprof.auth.command.LoginOutput;
import br.com.agendaprof.auth.entity.UsuarioLogado;
import br.com.agendaprof.auth.repository.AuthRepository;
import br.com.agendaprof.core.domain.usecase.UseCase;
import br.com.agendaprof.core.exceptions.RegraNegocioException;
import br.com.agendaprof.usuario.entity.Senha;
import br.com.agendaprof.usuario.security.PasswordEncryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUseCase implements UseCase<LoginCommand, LoginOutput> {

    private final AuthRepository repository;
    private final PasswordEncryptionService passwordEncryptionService;

    @Override
    public LoginOutput execute(LoginCommand command) {
        var username = command.getUsername();
        var password = passwordEncryptionService.encrypt(new Senha(command.getPassword()));

        Optional<UsuarioLogado> usuario = repository.signin(username, password.getValor());

        if (usuario.isPresent()) {
            UsuarioLogado usuarioLogado = usuario.get();
            repository.atualizaUltimoLogin(usuarioLogado.getId(), new Date());
            LoginOutput output = new LoginOutput();
            output.setUsername(username);
            return output;
        }

        throw new RegraNegocioException("Usuario ou Senha invalido");
    }
}
