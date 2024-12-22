package br.com.agendaprof.usuario.command;

import jakarta.annotation.Nonnull;

public record InserirUsuarioCommand(@Nonnull String nome, @Nonnull String login, @Nonnull String senha) {
}
