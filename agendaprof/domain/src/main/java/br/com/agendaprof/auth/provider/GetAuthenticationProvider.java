package br.com.agendaprof.auth.provider;

public interface GetAuthenticationProvider {
    Object getAuthentication(String token);
}
