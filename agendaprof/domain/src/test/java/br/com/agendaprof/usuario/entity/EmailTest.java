package br.com.agendaprof.usuario.entity;

import br.com.agendaprof.core.exceptions.RegraNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {

    @Test
    void dadoQueTenhoEmailValido_quandoCriarInstancia_deveRetornarSemErros(){
        final var expectedValor = "email@agendaprof.com.br";
        Email email = new Email(expectedValor);
        assertEquals(expectedValor, email.getValor());
    }

    @Test
    void dadoQueTenhoEmailInvalido_quandoCriarInstancia_deveRetornarErro(){
        final var expectedMessage = "Email Invalido";

        String[] emailsParaTestar = {
                "email@dominio",
                "@dominio.com",
                "usuario@.com",
                "usuario@dominio.",
                "usuario com espacos@dominio.com",
                ""
        };

        for (String email : emailsParaTestar) {
            final var expectedException =  assertThrows(RegraNegocioException.class, () -> new Email(email));
            assertEquals(expectedMessage, expectedException.getMessage());
        }
    }
}
