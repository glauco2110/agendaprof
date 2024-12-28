package br.com.agendaprof.auth.repository;

import br.com.agendaprof.auth.entity.UsuarioLogado;

import java.util.Date;
import java.util.Optional;

public interface AuthRepository {
    Optional<UsuarioLogado> signin(String login);
    void atualizaUltimoLogin(String login, Date date);
}
