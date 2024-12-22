package br.com.agendaprof.usuario.command;

import java.util.Date;

public record InserirUsuarioOutput(Long id, String nome, String login, Date createdAt, Date updatedAt) {
}
