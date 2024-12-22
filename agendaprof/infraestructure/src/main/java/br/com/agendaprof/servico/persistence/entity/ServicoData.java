package br.com.agendaprof.servico.persistence.entity;

import br.com.agendaprof.core.infra.entity.BaseEntityData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_SERVICO")
public class ServicoData extends BaseEntityData {

    @Id
    @Column(name = "id_servico", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "valor", nullable = false, precision = 2)
    private Double valor;
}
