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
    private static final String NOME_OBRIGATORIO = "Nome é de preenchimento obrigatorio";
    private static final String LOGIN_OBRIGATORIO = "Login é de preenchimento obrigatorio";
    private static final String SENHA_OBRIGATORIO = "Senha é de preenchimento obrigatorio";

    private String nome;
    private String login;
    private Senha senha;
    private Date ultimoLogin;
    private List<EnumRoles> permissoes;

    public void validate() {
        if(nome == null || nome.isEmpty()) {
            throw new RegraNegocioException(NOME_OBRIGATORIO);
        }

        if(login == null || login.isEmpty()) {
            throw new RegraNegocioException(LOGIN_OBRIGATORIO);
        }

        if(senha == null) {
            throw new RegraNegocioException(SENHA_OBRIGATORIO);
        }
    }
}
