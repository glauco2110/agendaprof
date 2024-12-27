package br.com.agendaprof.usuario.entity;

import br.com.agendaprof.core.exceptions.RegraNegocioException;
import lombok.Getter;

@Getter
public class Senha {
    private static final String MSG_SENHA_INVALIDA = "A senha deve conter pelo menos uma letra ou caractere especial";
    private static final String MSG_SENHA_VAZIA = "Senha é de preenchimento obrigatorio";
    private static final String MSG_SENHA_MENOR_QUE_SEIS_CARACTERES = "A senha deve ter pelo menos 6 caracteres";
    private String valor;
    private Status status;

    private Senha() {
    }

    public Senha(String valor) {
        this.valor = valor;
        this.status = Status.RAW;
        validate();
    }

    public Senha(String valor, Status status) {
        this.valor = valor;
        this.status = status;
    }

    private void validate() {
        if(this.status.equals(Status.RAW)) {
            if (valor == null || valor.trim().isEmpty()) {
                throw new RegraNegocioException(MSG_SENHA_VAZIA);
            }

            if (valor.length() < 6) {
                throw new RegraNegocioException(MSG_SENHA_MENOR_QUE_SEIS_CARACTERES);
            }

            String specialCharacters = "!@#$%^&*()_+-=[]{}|;:,.<>?";
            boolean hasLetterOrSpecial = false;

            for (char c : valor.toCharArray()) {
                if (Character.isLetter(c) || specialCharacters.contains(String.valueOf(c))) {
                    hasLetterOrSpecial = true;
                    break;
                }
            }

            if (!hasLetterOrSpecial) {
                throw new RegraNegocioException(MSG_SENHA_INVALIDA);
            }
        }
    }

    public enum Status {
        RAW, HASHED
    }

}
