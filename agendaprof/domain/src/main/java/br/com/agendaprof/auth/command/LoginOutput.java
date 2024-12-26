package br.com.agendaprof.auth.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginOutput {
    private String username;
    private String token;
}
