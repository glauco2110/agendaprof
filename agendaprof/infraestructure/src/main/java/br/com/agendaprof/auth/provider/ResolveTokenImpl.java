package br.com.agendaprof.auth.provider;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class ResolveTokenImpl implements ResolveTokenProvider{
    @Override
    public String resolve(HttpServletRequest req) {
        return "";
    }
}
