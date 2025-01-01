package br.com.agendaprof.auth.provider;

public interface ValidateTokenProvider {
    boolean validate(String token);
}
