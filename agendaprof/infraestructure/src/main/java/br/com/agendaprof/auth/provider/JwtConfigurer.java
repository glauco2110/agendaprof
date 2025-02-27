package br.com.agendaprof.auth.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtTokenFilter customFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
