package br.com.agendaprof.usuario.persistence.repository.data;

import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDataRepository extends CrudRepository<UsuarioData, Long> {
}
