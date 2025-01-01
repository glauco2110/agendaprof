package br.com.agendaprof.auth.provider;

import br.com.agendaprof.auth.command.LoginOutput;

import java.util.List;

public interface CreateTokenProvider {

    LoginOutput create(String username, List<String> roles);
}
