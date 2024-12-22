package br.com.agendaprof.core.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseEntity {
    protected Long id;
    protected Date createdAt;
    protected Date updatedAt;
}
