package br.com.agendaprof.core.infra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public abstract class BaseEntityData {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="created_at", nullable = false)
    protected Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    protected Date updatedAt;

    public abstract Long getId();
    public abstract void setId(Long id);
}
