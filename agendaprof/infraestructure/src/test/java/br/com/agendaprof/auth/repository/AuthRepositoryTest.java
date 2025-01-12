package br.com.agendaprof.auth.repository;

import br.com.agendaprof.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@IntegrationTest
@SpringBootTest
public class AuthRepositoryTest {

    @Autowired
    private AuthRepository repository;

    @Test
    void dadoQueTenhoUmUserName_QuandoTentarBuscarUsuario_DeveRetornarUserDetails() {

        final var username = "admin";
        final var optional = repository.signin(username);
        assertTrue(optional.isPresent());
        assertEquals(username, optional.get().getLogin());

    }
}
