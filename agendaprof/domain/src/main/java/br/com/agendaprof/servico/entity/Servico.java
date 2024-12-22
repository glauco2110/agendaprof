package br.com.agendaprof.servico.entity;

import br.com.agendaprof.core.domain.entity.BaseEntity;
import br.com.agendaprof.core.exceptions.RegraNegocioException;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Servico extends BaseEntity {

    private String nome;
    private String descricao;
    private Double valor;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Servico servico = (Servico) o;
        return Objects.equals(id, servico.id);
    }

    public void validate(List<RegraNegocioException> erros) {
        if(this.getNome() == null || this.getNome().isEmpty()) {
            erros.add(new RegraNegocioException("Nome é de preenchimento obrigatorio"));
        }

        if(this.getDescricao() == null || this.getDescricao().isEmpty()) {
            erros.add(new RegraNegocioException("Descrição é de preenchimento obrigatorio"));
        }

        if(this.getValor() == null) {
            erros.add(new RegraNegocioException("Valor é de preenchimento obrigatorio"));
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
