package br.com.agendaprof.auth.service;

import br.com.agendaprof.auth.entity.UsuarioLogado;
import br.com.agendaprof.auth.entity.UsuarioLogadoAuth;
import br.com.agendaprof.auth.mapper.UsuarioLogadoMapper;
import br.com.agendaprof.auth.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthRepository repository;
    private final UsuarioLogadoMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UsuarioLogado> optional = repository.signin(username);
        if(optional.isPresent()) {
            UsuarioLogadoAuth auth = mapper.toAuth(optional.get());
            repository.atualizaUltimoLogin(username, new Date());
            return auth;
        }
        throw new UsernameNotFoundException(username);
    }
}
