package br.com.agendaprof.auth.repository;

import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AuthDataRepository extends CrudRepository<UsuarioData, Long> {

    @Query("SELECT u.login, u.senha, u.permissoes from UsuarioData u where u.login = :login")
    UsuarioData signin(String login);

    @Modifying
    @Query("UPDATE UsuarioData u set u.ultimoLogin = :date WHERE u.login = :login")
    void atualizaUltimoLogin(String login, Date date);
}
