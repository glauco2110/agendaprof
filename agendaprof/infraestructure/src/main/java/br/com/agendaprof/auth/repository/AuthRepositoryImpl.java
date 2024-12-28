package br.com.agendaprof.auth.repository;

import br.com.agendaprof.auth.entity.UsuarioLogado;
import br.com.agendaprof.auth.mapper.UsuarioLogadoMapper;
import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthRepositoryImpl implements AuthRepository {

    private final AuthDataRepository repository;
    private final UsuarioLogadoMapper mapper;

    @Override
    public Optional<UsuarioLogado> signin(String login) {
        UsuarioData data = repository.signin(login);
        if(data != null) {
            return Optional.of(mapper.toDomain(data));
        }
        return Optional.empty();
    }

    @Override
    public void atualizaUltimoLogin(String login, Date date) {
        repository.atualizaUltimoLogin(login, date);
    }

}
