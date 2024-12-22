package br.com.agendaprof.servico.mapper;

import br.com.agendaprof.core.mapper.BaseMapper;
import br.com.agendaprof.servico.entity.Servico;
import br.com.agendaprof.servico.persistence.entity.ServicoData;
import org.springframework.stereotype.Component;

@Component
public class ServicoMapper extends BaseMapper<Servico, ServicoData> {

    @Override
    public Servico toDomain(ServicoData data) {
        if(data == null) return null;
        Servico domain = new Servico();
        domain.setId(data.getId());
        domain.setNome(data.getNome());
        domain.setDescricao(data.getDescricao());
        domain.setValor(data.getValor());
        domain.setCreatedAt(data.getCreatedAt());
        domain.setUpdatedAt(data.getUpdatedAt());
        return domain;
    }

    @Override
    public ServicoData toData(Servico domain) {
        if(domain == null) return null;
        ServicoData data = new ServicoData();
        data.setId(domain.getId());
        data.setNome(domain.getNome());
        data.setDescricao(domain.getDescricao());
        data.setValor(domain.getValor());
        data.setCreatedAt(domain.getCreatedAt());
        data.setUpdatedAt(domain.getUpdatedAt());
        return data;
    }
}
