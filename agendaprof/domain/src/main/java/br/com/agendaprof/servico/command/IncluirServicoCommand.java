package br.com.agendaprof.servico.command;

import br.com.agendaprof.servico.entity.Servico;
import jakarta.annotation.Nonnull;

public record IncluirServicoCommand(@Nonnull String nome, @Nonnull String descricao, @Nonnull Double valor) {

    public Servico toDomain() {
        Servico servico = new Servico();
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setValor(valor);
        return servico;
    }
}
