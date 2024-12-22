package br.com.agendaprof.core.infra.utils;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class JPAUtils {

    public static  <E> Optional<E> getOptionalSingleResult(TypedQuery<E> typedQuery) {
        return Optional.ofNullable(getSingleResult(typedQuery));
    }

    public static  <E> E getSingleResult(TypedQuery<E> typedQuery) {
        E t;

        try {
            typedQuery.setMaxResults(1);
            t = typedQuery.getSingleResult();
        } catch (NoResultException e) {
            t = null;
        }

        return t;
    }
}
