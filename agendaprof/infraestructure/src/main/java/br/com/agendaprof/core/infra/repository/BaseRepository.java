package br.com.agendaprof.core.infra.repository;

import br.com.agendaprof.core.domain.repository.Pagina;
import br.com.agendaprof.core.infra.utils.JPAUtils;
import br.com.agendaprof.core.infra.utils.Utils;
import br.com.agendaprof.core.mapper.BaseMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseRepository<DOMAIN, DATA> {

    @Autowired
    protected EntityManager entityManager;

    protected abstract BaseMapper<DOMAIN, DATA> getMapper();

    protected abstract CrudRepository<DATA, Long> getRepository();

    protected abstract Specification<DATA> filtros(DOMAIN filtro);

    protected Class<DATA> getEntidadeClass() {
        Type[] types = Utils.getTypes(this);
        if (types.length > 1)
            return (Class<DATA>) Utils.getTypes(this)[1];
        else
            return (Class<DATA>) Utils.getTypes(this)[0];
    }


    public DOMAIN save(DOMAIN input) {
        DATA data = this.getMapper().toData(input);
        data = this.getRepository().save(data);
        return this.getMapper().toDomain(data);
    }

    public void delete(Long id) {
        Optional<DATA> optional = this.getRepository().findById(id);
        if (optional.isPresent()) this.getRepository().delete(optional.get());
    }

    public Optional<DOMAIN> findById(Long id) {
        Optional<DATA> optional = this.getRepository().findById(id);
        if (optional.isPresent()) Optional.of(this.getMapper().toDomain(optional.get()));
        return Optional.empty();
    }

    public Pagina<DOMAIN> findAll(Pagina pageable, DOMAIN filters) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<DATA> root = query.from(getEntidadeClass());

        query.where(this.filtros(filters).toPredicate(root, query, builder));


        TypedQuery<Tuple> typedQuery = entityManager.createQuery(query);

        this.addPagination(pageable, typedQuery);

        Pagina result = new Pagina(transformTupleInList(typedQuery.getResultList()), count(this.filtros(filters)), pageable.getLimit(), pageable.getOffset());

        return result;
    }

    private Long count(Specification<DATA> specification) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<DATA> root = query.from(getEntidadeClass());

        query.select(builder.countDistinct(root));

        query.where(specification.toPredicate(root, query, builder));

        return entityManager.createQuery(query).getSingleResult();
    }

    private List<DOMAIN> transformTupleInList(List<Tuple> result) {
        return result.stream()
                .map(tuple -> {
                    DATA data = AliasToBeanNestedResultTransformer.transformTuple(tuple, getEntidadeClass());
                    return getMapper().toDomain(data);
                })
                .collect(Collectors.toList());
    }

    private void addSort(CriteriaBuilder builder, CriteriaQuery<?> query, Root<?> root, Pageable paginacao) {
        if (paginacao == null) {
            return;
        }
        Sort sort = paginacao.isPaged() ? paginacao.getSort() : Sort.unsorted();
        if (sort.isSorted()) {
            query.orderBy(QueryUtils.toOrders(sort, root, builder));
        }
    }

    private void addPagination(Pagina paginacao, TypedQuery<?> typedQuery) {
        typedQuery.setFirstResult(paginacao.getOffset());
        typedQuery.setMaxResults(paginacao.getLimit());
    }

    protected <DATA> Optional<DATA> validarPropriedadeUnica(Class<DATA> classe, Specification<DATA> specification) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DATA> query = builder.createQuery(classe);
        Root<DATA> root = query.from(classe);


        query.select(root.get("id"));
        query.where(specification.toPredicate(root, query, builder));

        return JPAUtils.getOptionalSingleResult(entityManager.createQuery(query));
    }
}
