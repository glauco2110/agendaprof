package br.com.agendaprof.servico.command;

import java.util.Date;

public record ConsultarServicoCommand(Long id, String nome, String descricao, Double valor, Date createdAt, Date updatedAt) {
}
