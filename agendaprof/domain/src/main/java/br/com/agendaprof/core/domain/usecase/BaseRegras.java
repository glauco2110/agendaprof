package br.com.agendaprof.core.domain.usecase;

import br.com.agendaprof.core.domain.entity.BaseEntity;
import br.com.agendaprof.core.exceptions.RegraNegocioException;

import java.util.Date;
import java.util.List;

public abstract class BaseRegras<INPUT extends BaseEntity> {

    public void execute(INPUT input){
        final var date = new Date();

        if(input.getCreatedAt() == null)  input.setCreatedAt(date);

        input.setUpdatedAt(date);

        List<RegraNegocioException> erros = validate(input);
        if(!erros.isEmpty()){
            throw erros.get(0);
        }
    }

    protected abstract List<RegraNegocioException> validate(INPUT input);
}
