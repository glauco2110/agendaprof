package br.com.agendaprof.core.domain.repository;


import java.util.Optional;

public interface BaseCrudRepository<INPUT, OUTPUT> {
    OUTPUT save(INPUT input);
    void delete(Long id);
    Optional<OUTPUT> findById(Long id);
    Pagina<OUTPUT> findAll(Pagina pageable, INPUT filters);
}
