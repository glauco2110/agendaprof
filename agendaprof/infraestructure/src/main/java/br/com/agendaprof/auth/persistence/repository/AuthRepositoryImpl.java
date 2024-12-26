package br.com.agendaprof.auth.persistence.repository;

import br.com.agendaprof.auth.entity.UsuarioLogado;
import br.com.agendaprof.auth.mapper.UsuarioLogadoMapper;
import br.com.agendaprof.auth.persistence.repository.data.AuthDataRepository;
import br.com.agendaprof.auth.repository.AuthRepository;
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
    public Optional<UsuarioLogado> signin(String login, String senha) {
        UsuarioData data = repository.signin(login, senha);
        if(data != null) {
            return Optional.of(mapper.toDomain(data));
        }
        return Optional.empty();
    }

    @Override
    public void atualizaUltimoLogin(Long id, Date date) {
        repository.atualizaUltimoLogin(id, date);
    }

}
