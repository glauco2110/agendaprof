package br.com.agendaprof.core.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RegraNegocioException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -4506442970006191470L;

    private final List<Mensagem> mensagens = new ArrayList<>();

    public RegraNegocioException(String mensagem, Object... args) {
    	super(mensagem);
        this.mensagens.add(new Mensagem(mensagem, args));
    }

    public RegraNegocioException(String mensagem) {
    	super(mensagem);
        this.mensagens.add(new Mensagem(mensagem));
    }

    public RegraNegocioException(String mensagem, Exception ex) {
        super(mensagem, ex);
        this.mensagens.add(new Mensagem(mensagem));
    }

}
