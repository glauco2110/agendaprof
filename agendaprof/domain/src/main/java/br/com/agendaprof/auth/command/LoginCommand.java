package br.com.agendaprof.auth.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCommand {
    private String username;
    private String password;
}
