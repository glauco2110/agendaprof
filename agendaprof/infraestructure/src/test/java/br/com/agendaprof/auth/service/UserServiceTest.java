package br.com.agendaprof.auth.service;

import br.com.agendaprof.IntegrationTest;
import br.com.agendaprof.auth.mapper.UsuarioLogadoMapper;
import br.com.agendaprof.auth.repository.AuthDataRepository;
import br.com.agendaprof.auth.repository.AuthRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@IntegrationTest
@SpringBootTest(classes = {UserService.class, AuthRepositoryImpl.class, UsuarioLogadoMapper.class, AuthDataRepository.class})
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Test
    void dadoQueTenhoUmUserName_QuandoTentarBuscarUsuario_DeveRetornarUserDetails() {

        final var username = "admin";
        final var userDetails = service.loadUserByUsername(username);
        assertEquals(username, userDetails.getUsername());

    }
}
