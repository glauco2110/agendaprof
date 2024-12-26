package br.com.agendaprof.auth.persistence.repository.data;

import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface AuthDataRepository extends CrudRepository<UsuarioData, Long> {

    @Query("SELECT u.id, u.login, u.nome from UsuarioData u where u.login = :login and u.senha = :senha")
    UsuarioData signin(String login, String senha);

    @Modifying
    @Query("UPDATE UsuarioData u set u.ultimoLogin = :date WHERE u.id = :id")
    void atualizaUltimoLogin(Long id, Date date);
}
