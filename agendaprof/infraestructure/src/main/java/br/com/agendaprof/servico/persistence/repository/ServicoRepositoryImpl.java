package br.com.agendaprof.servico.persistence.repository;

import br.com.agendaprof.core.infra.repository.BaseRepository;
import br.com.agendaprof.servico.entity.Servico;
import br.com.agendaprof.servico.mapper.ServicoMapper;
import br.com.agendaprof.servico.persistence.entity.ServicoData;
import br.com.agendaprof.servico.persistence.repository.data.ServicoDataRepository;
import br.com.agendaprof.servico.repository.ServicoRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Repository
@RequiredArgsConstructor
public class ServicoRepositoryImpl extends BaseRepository<Servico, ServicoData> implements ServicoRepository {

    private final ServicoDataRepository repository;
    private final ServicoMapper mapper;

    @Override
    public Optional<Servico> existeServicoMesmoNome(Servico filtro) {
        Optional<ServicoData> optional = this.validarPropriedadeUnica(ServicoData.class,  (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicates.add(builder.equal(builder.lower(root.get("nome")), filtro.getNome().toLowerCase()));
            }

            if(filtro.getId() != null) {
                predicates.add(builder.equal(root.get("id"), filtro.getId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        });

        if (optional.isPresent()) return Optional.of(this.mapper.toDomain(optional.get()));
        return Optional.empty();
    }

    @Override
    protected Specification<ServicoData> filtros(Servico filtro) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                predicates.add(builder.like(builder.lower(root.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
            }

            if(filtro.getId() != null) {
                predicates.add(builder.equal(root.get("id"), filtro.getId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
