package br.com.agendaprof.servico.command;

import br.com.agendaprof.servico.entity.Servico;
import jakarta.annotation.Nonnull;

import java.util.Date;

public record AlterarServicoCommand(@Nonnull Long id, @Nonnull String nome, @Nonnull String descricao, @Nonnull Double valor, @Nonnull Date createdAt, @Nonnull Date updatedAt) {

    public Servico toDomain() {
        Servico servico = new Servico();
        servico.setId(id);
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setValor(valor);
        servico.setCreatedAt(createdAt);
        servico.setUpdatedAt(updatedAt);
        return servico;
    }
}
