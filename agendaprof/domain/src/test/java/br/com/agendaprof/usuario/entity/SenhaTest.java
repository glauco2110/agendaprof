package br.com.agendaprof.usuario.entity;

import br.com.agendaprof.core.exceptions.RegraNegocioException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SenhaTest {

    @Test
    void dadoQueTenhoValorValido_quandoTentarCriarUmaSenha_deveRetornarUmaInstancia(){
        String[] senhasParaTestar = {
                "abc123",
                "12345@",
                "abcdef",
                "123!@#",
        };

        for (String expectedValor : senhasParaTestar) {
            final Senha senha = new Senha(expectedValor);
            assertEquals(expectedValor, senha.getValor());
        }
    }

    @Test
    void dadoQueTenhoValorInvalido_quandoTentarCriarUmaSenha_deveRetornarErro(){
        final var expectedValor = "123456";
        final var expectedMessage = "A senha deve conter pelo menos uma letra ou caractere especial";

        final var expectedException = assertThrows(RegraNegocioException.class, () -> new Senha(expectedValor));

        assertEquals(expectedMessage, expectedException.getMessage());
    }

    @Test
    void dadoQueTenhoTamanhoInvalido_quandoTentarCriarUmaSenha_deveRetornarErro(){
        final var expectedValor = "12345";
        final var expectedMessage = "A senha deve ter pelo menos 6 caracteres";

        final var expectedException = assertThrows(RegraNegocioException.class, () -> new Senha(expectedValor));

        assertEquals(expectedMessage, expectedException.getMessage());
    }

    @Test
    void dadoQueTenhoValorVazio_quandoTentarCriarUmaSenha_deveRetornarErro(){
        final var expectedValor = "";
        final var expectedMessage = "A senha não pode ser vazia";

        final var expectedException = assertThrows(RegraNegocioException.class, () -> new Senha(expectedValor));

        assertEquals(expectedMessage, expectedException.getMessage());
    }

    @Test
    void dadoQueNull_quandoTentarCriarUmaSenha_deveRetornarErro(){
        final String expectedValor = null;
        final var expectedMessage = "A senha não pode ser vazia";

        final var expectedException = assertThrows(RegraNegocioException.class, () -> new Senha(expectedValor));

        assertEquals(expectedMessage, expectedException.getMessage());
    }

    @Test
    void dadoQueTenhoEspacosVazio_quandoTentarCriarUmaSenha_deveRetornarErro(){
        final var expectedValor = "      ";
        final var expectedMessage = "A senha não pode ser vazia";

        final var expectedException = assertThrows(RegraNegocioException.class, () -> new Senha(expectedValor));

        assertEquals(expectedMessage, expectedException.getMessage());
    }
}
