package br.com.agendaprof.auth.rest;

import br.com.agendaprof.auth.command.LoginCommand;
import br.com.agendaprof.auth.jwt.JwtTokenProvider;
import br.com.agendaprof.auth.usecase.AuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager manager;
    private final JwtTokenProvider provider;
    private final AuthUseCase useCase;


    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity signin(LoginCommand dto) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        var user = useCase.execute(dto);

        var token = provider.createToken(dto.getUsername(), new ArrayList<>());
        user.setToken(token);

        return ResponseEntity.ok(user);
    }


}
