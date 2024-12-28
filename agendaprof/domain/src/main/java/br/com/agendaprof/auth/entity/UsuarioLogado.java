package br.com.agendaprof.auth.entity;

import br.com.agendaprof.usuario.entity.EnumRoles;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UsuarioLogado {
    private String login;
    private String senha;
    private Set<EnumRoles> permissoes;
}
