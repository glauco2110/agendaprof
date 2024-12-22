package br.com.agendaprof.usuario.mapper;

import br.com.agendaprof.core.mapper.BaseMapper;
import br.com.agendaprof.usuario.entity.Usuario;
import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper extends BaseMapper<Usuario, UsuarioData> {

    @Override
    public Usuario toDomain(UsuarioData data) {
        if(data == null) return null;
        Usuario usuario = new Usuario();
        usuario.setId(data.getId());
        usuario.setNome(data.getNome());
        usuario.setLogin(data.getLogin());
        usuario.setUltimoLogin(data.getUltimoLogin());
        usuario.setCreatedAt(data.getCreatedAt());
        usuario.setUpdatedAt(data.getUpdatedAt());
        return usuario;
    }

    @Override
    public UsuarioData toData(Usuario domain) {
        if(domain == null) return null;
        UsuarioData data = new UsuarioData();
        data.setId(domain.getId());
        data.setNome(domain.getNome());
        data.setLogin(domain.getLogin());
        data.setUltimoLogin(domain.getUltimoLogin());
        data.setCreatedAt(domain.getCreatedAt());
        data.setUpdatedAt(domain.getUpdatedAt());
        data.setSenha(domain.getSenha().getValor());
        return data;
    }
}
