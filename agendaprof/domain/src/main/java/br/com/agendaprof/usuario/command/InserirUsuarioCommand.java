package br.com.agendaprof.usuario.command;

import br.com.agendaprof.usuario.entity.EnumRoles;
import jakarta.annotation.Nonnull;

import java.util.Set;

public record InserirUsuarioCommand(@Nonnull String nome, @Nonnull String login, @Nonnull String senha, Set<EnumRoles> permissoes) {
}
