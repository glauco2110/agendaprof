package br.com.agendaprof.usuario.entity;

import br.com.agendaprof.core.exceptions.RegraNegocioException;
import lombok.Getter;

@Getter
public class Email {
    private final String MSG_EMAIL_INVALIDO = "Email Invalido";
    private String valor;
    private Email(){}
    public Email(String valor) {
        this.valor = valor;
        validate();
    }

    private void validate(){
        if (valor == null || valor.trim().isEmpty()) {
           throw new RegraNegocioException(MSG_EMAIL_INVALIDO);
        }

        // Expressão regular para validação de email
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        if(!valor.matches(regex)){
            throw new RegraNegocioException(MSG_EMAIL_INVALIDO);
        }
    }
}
