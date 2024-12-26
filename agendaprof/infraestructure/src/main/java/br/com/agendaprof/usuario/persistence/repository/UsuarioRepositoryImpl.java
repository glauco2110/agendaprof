package br.com.agendaprof.usuario.persistence.repository;

import br.com.agendaprof.core.infra.repository.BaseRepository;
import br.com.agendaprof.core.mapper.BaseMapper;
import br.com.agendaprof.usuario.entity.Usuario;
import br.com.agendaprof.usuario.mapper.UsuarioMapper;
import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import br.com.agendaprof.usuario.persistence.repository.data.UsuarioDataRepository;
import br.com.agendaprof.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl extends BaseRepository<Usuario, UsuarioData>  implements  UsuarioRepository {

    private final UsuarioDataRepository repository;
    private final UsuarioMapper mapper;

    @Override
    protected UsuarioMapper getMapper() {
        return mapper;
    }

    @Override
    protected CrudRepository<UsuarioData, Long> getRepository() {
        return repository;
    }

    @Override
    protected Specification<UsuarioData> filtros(Usuario filtro) {
        return null;
    }

    @Override
    public Optional<Usuario> existeUsuarioMesmoLogin(Usuario servico) {
        return Optional.empty();
    }

}
