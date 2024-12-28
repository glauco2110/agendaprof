package br.com.agendaprof.auth.mapper;

import br.com.agendaprof.auth.entity.UsuarioLogado;
import br.com.agendaprof.auth.entity.Permissoes;
import br.com.agendaprof.auth.entity.UsuarioLogadoAuth;
import br.com.agendaprof.core.mapper.BaseMapper;
import br.com.agendaprof.usuario.entity.EnumRoles;
import br.com.agendaprof.usuario.persistence.entity.UsuarioData;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class UsuarioLogadoMapper extends BaseMapper<UsuarioLogado, UsuarioData> {

    @Override
    public UsuarioLogado toDomain(UsuarioData data) {
        if(data == null) return null;

        UsuarioLogado usuarioLogado = new UsuarioLogado();
        usuarioLogado.setLogin(data.getLogin());
        usuarioLogado.setSenha(data.getSenha());
        usuarioLogado.setPermissoes(data.getPermissoes());

        return usuarioLogado;
    }

    @Override
    public UsuarioData toData(UsuarioLogado domain) {
        if(domain == null) return null;

        UsuarioData usuarioData = new UsuarioData();
        usuarioData.setLogin(domain.getLogin());
        usuarioData.setSenha(domain.getSenha());

        return usuarioData;
    }

    public UsuarioLogadoAuth toAuth(UsuarioLogado domain) {
        if(domain == null) return null;
        UsuarioLogadoAuth usuarioLogadoAuth = new UsuarioLogadoAuth();
        usuarioLogadoAuth.setLogin(domain.getLogin());
        usuarioLogadoAuth.setSenha(domain.getSenha());
        usuarioLogadoAuth.setPermissoes(toPermissoes(domain.getPermissoes()));
        return usuarioLogadoAuth;
    }

    private List<Permissoes> toPermissoes(Set<EnumRoles> roles) {
        if(roles == null) return new ArrayList<>();
        List<Permissoes> permissoes = new ArrayList<>();
        roles.forEach(role -> permissoes.add(new Permissoes(role)));
        return permissoes;
    }



}
