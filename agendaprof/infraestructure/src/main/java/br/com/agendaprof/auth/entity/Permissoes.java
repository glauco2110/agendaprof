package br.com.agendaprof.auth.entity;

import br.com.agendaprof.usuario.entity.EnumRoles;
import org.springframework.security.core.GrantedAuthority;

public class Permissoes implements GrantedAuthority {

    private EnumRoles role;

    public Permissoes(EnumRoles role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }
}
