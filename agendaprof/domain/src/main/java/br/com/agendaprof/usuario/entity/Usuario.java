package br.com.agendaprof.usuario.entity;

import br.com.agendaprof.core.domain.entity.BaseEntity;
import br.com.agendaprof.core.exceptions.RegraNegocioException;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Usuario extends BaseEntity {
    private String nome;
    private String login;
    private Senha senha;
    private Date ultimoLogin;
    private List<EnumRoles> permissoes;

    public void validate() {
        if(nome == null || nome.isEmpty()) {
            throw new RegraNegocioException("Nome é de preenchimento obrigatio");
        }

        if(login == null || login.isEmpty()) {
            throw new RegraNegocioException("Login é de preenchimento obrigatio");
        }

        if(senha == null) {
            throw new RegraNegocioException("Senha é de preenchimento obrigatio");
        }
    }
}
