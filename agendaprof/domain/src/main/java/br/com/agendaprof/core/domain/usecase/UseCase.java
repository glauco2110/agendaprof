package br.com.agendaprof.core.domain.usecase;

public interface UseCase <INPUT, OUTPUT>{

    OUTPUT execute(INPUT input);
}
