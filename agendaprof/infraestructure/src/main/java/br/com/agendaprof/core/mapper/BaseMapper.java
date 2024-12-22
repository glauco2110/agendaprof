package br.com.agendaprof.core.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseMapper<DOMAIN, DATA> {

    public abstract DOMAIN toDomain(DATA data);
    public abstract DATA toData(DOMAIN domain);

    public List<DATA> toDatas(Collection<DOMAIN> domains) {
        if(domains == null || domains.isEmpty()) return new ArrayList<DATA>();
        return domains.stream().map(this::toData).collect(Collectors.toList());
    }

    public List<DOMAIN> toDomains(Collection<DATA> datas) {
        if(datas == null || datas.isEmpty()) return new ArrayList<>();
        return datas.stream().map(this::toDomain).collect(Collectors.toList());
    }
}
