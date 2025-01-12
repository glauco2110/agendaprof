package br.com.agendaprof.auth.service;

import br.com.agendaprof.IntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    @Transactional
    void dadoQueTenhoUmUserName_QuandoTentarBuscarUsuario_DeveRetornarUserDetails() {

        final var username = "admin";
        final var userDetails = service.loadUserByUsername(username);
        assertEquals(username, userDetails.getUsername());

    }
}
