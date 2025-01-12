package br.com.agendaprof.auth.rest;

import br.com.agendaprof.E2ETest;
import br.com.agendaprof.auth.command.LoginCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
@Testcontainers
public class LoginControllerTest {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("agendaprof")
            .withUsername("postgres")
            .withPassword("postgres");


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.start();
        System.setProperty("spring.datasource.url", postgreSQLContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgreSQLContainer.getUsername());
        System.setProperty("spring.datasource.password", postgreSQLContainer.getPassword());
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void dadoQueTenhoUsuarioESenhaValidos_QuandoTentarExecutarLogin_DeveSerRealizadoComSucesso() throws Exception {
        assertTrue(postgreSQLContainer.isRunning());
        LoginCommand command = new LoginCommand();
        command.setUsername("admin");
        command.setPassword("123@456");

        final var request = post("/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(command));

        final var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

    }

}
