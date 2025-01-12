package br.com.agendaprof.auth.rest;

import br.com.agendaprof.auth.command.LoginCommand;
import br.com.agendaprof.auth.command.LoginOutput;
import br.com.agendaprof.auth.provider.CreateTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final CreateTokenProvider provider;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity signin(@RequestBody final LoginCommand dto) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        LoginOutput token = provider.create(dto.getUsername(), authorities);
        return ResponseEntity.ok(token);
    }


}
