package br.com.agendaprof.usuario.persistence.entity;

import br.com.agendaprof.core.infra.entity.BaseEntityData;
import br.com.agendaprof.usuario.entity.EnumRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "TB_USUARIO")
public class UsuarioData extends BaseEntityData {

    @Id
    @Column(name = "id_usuario", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "senha", nullable = false, length = 500)
    private String senha;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultimo_login")
    private Date ultimoLogin;

    @ElementCollection
    @CollectionTable(
            name = "TB_PERMISSOES_USUARIO",
            joinColumns = @JoinColumn(name = "id_usuario")
    )
    @Column(name = "permissao")
    @Enumerated(EnumType.STRING)
    private Set<EnumRoles> permissoes;
}
