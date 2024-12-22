package br.com.agendaprof.usuario.repository;

import br.com.agendaprof.core.domain.repository.BaseCrudRepository;
import br.com.agendaprof.usuario.entity.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends BaseCrudRepository<Usuario, Usuario> {
    Optional<Usuario> existeUsuarioMesmoLogin(Usuario servico);
    Optional<Usuario> logar(String login, String senha);
}
