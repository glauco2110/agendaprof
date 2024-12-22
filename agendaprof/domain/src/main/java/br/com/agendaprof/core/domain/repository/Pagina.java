package br.com.agendaprof.core.domain.repository;

import lombok.Getter;

import java.util.List;

@Getter
public class Pagina<OUTPUT> {
    private List<OUTPUT> items;
    private Long count;
    private Integer limit;
    private Integer offset;

    private Pagina() {

    }

    public Pagina(List<OUTPUT> items, Long count, Integer limit, Integer offset) {
        this.items = items;
        this.count = count;
        this.limit = limit;
        this.offset = offset;
    }
}
