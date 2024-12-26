package br.com.agendaprof.auth.mapper;

import br.com.agendaprof.auth.entity.UsuarioLogado;
import br.com.agendaprof.core.mapper.BaseMapper;
import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import org.springframework.stereotype.Component;

@Component
public class UsuarioLogadoMapper extends BaseMapper<UsuarioLogado, UsuarioData> {

    @Override
    public UsuarioLogado toDomain(UsuarioData data) {
        if(data == null) return null;

        UsuarioLogado usuarioLogado = new UsuarioLogado();
        usuarioLogado.setId(data.getId());
        usuarioLogado.setLogin(data.getLogin());
        usuarioLogado.setNome(data.getNome());

        return usuarioLogado;
    }

    @Override
    public UsuarioData toData(UsuarioLogado domain) {
        if(domain == null) return null;

        UsuarioData usuarioData = new UsuarioData();
        usuarioData.setId(domain.getId());
        usuarioData.setLogin(domain.getLogin());
        usuarioData.setNome(domain.getNome());

        return usuarioData;
    }
}
