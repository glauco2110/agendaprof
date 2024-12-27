package br.com.agendaprof.usuario.usecase;

import br.com.agendaprof.core.domain.usecase.BaseRegras;
import br.com.agendaprof.core.exceptions.RegraNegocioException;
import br.com.agendaprof.usuario.entity.Usuario;
import br.com.agendaprof.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioRegras extends BaseRegras<Usuario> {
    private final static String LOGIN_DUPLICADO = "Já existe um usuario cadastrado com este login";
    private final UsuarioRepository repository;

    @Override
    protected List<RegraNegocioException> validate(Usuario usuario) {
        List<RegraNegocioException> erros = new ArrayList<>();

        usuario.validate();

        Optional<Usuario> existe = repository.existeUsuarioMesmoLogin(usuario);

        if(existe.isPresent()) erros.add(new RegraNegocioException(LOGIN_DUPLICADO));

        return erros;
    }
}
