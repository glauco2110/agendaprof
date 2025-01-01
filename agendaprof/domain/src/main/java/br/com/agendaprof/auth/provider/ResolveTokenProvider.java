package br.com.agendaprof.auth.provider;

import jakarta.servlet.http.HttpServletRequest;

public interface ResolveTokenProvider {

    String resolve(HttpServletRequest req);
}
