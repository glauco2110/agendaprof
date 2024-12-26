package br.com.agendaprof.auth.entity;

import br.com.agendaprof.core.domain.entity.BaseEntity;
import br.com.agendaprof.usuario.entity.EnumRoles;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UsuarioLogado extends BaseEntity {
    private String nome;
    private String login;
    private Date ultimoLogin;
    private List<EnumRoles> permissoes;
}
