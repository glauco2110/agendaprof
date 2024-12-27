package br.com.agendaprof.servico.domain.usecase;

import br.com.agendaprof.core.exceptions.RegraNegocioException;
import br.com.agendaprof.servico.entity.Servico;
import br.com.agendaprof.servico.repository.ServicoRepository;
import br.com.agendaprof.servico.usecase.SalvarServico;
import br.com.agendaprof.servico.usecase.ServicoRegras;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {SalvarServico.class, ServicoRegras.class, ServicoRepository.class})
class InserirServicoTest {

    @MockBean
    private ServicoRepository repository;

    @Autowired
    private SalvarServico useCase;

    @Test
    void dadoQueTenhoUmServicoValido_QuandoTentarInserir_DeveRetornarServicoPersistido() {
        final var expectedNome = UUID.randomUUID().toString();
        final var expectedDescricao = UUID.randomUUID().toString();
        final var expectedValor = 22.0;

        Servico servico = new Servico();
        servico.setNome(expectedNome);
        servico.setDescricao(expectedDescricao);
        servico.setValor(expectedValor);
        Mockito.when(repository.save(servico)).thenReturn(servico);

        assertNull(servico.getId());

        final var result = useCase.execute(servico);


        verify(repository, times(1)).save(servico);

        assertEquals(expectedNome, result.getNome());
        assertEquals(expectedDescricao, result.getDescricao());
        assertEquals(expectedValor, result.getValor());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    void dadoQueTenhoUmServicoSemNome_QuandoTentarInserir_DeveRetornarRegraNegocioExecptionNomeObrigatorio() {
        final var expectedMessage = "Nome é de preenchimento obrigatorio";
        final var expectedDescricao = UUID.randomUUID().toString();
        final var expectedValor = 22.0;

        Servico servico = new Servico();
        servico.setNome(null);
        servico.setDescricao(expectedDescricao);
        servico.setValor(expectedValor);

        assertNull(servico.getId());

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(servico));

        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void dadoQueTenhoUmServicoSemDescricao_QuandoTentarInserir_DeveRetornarRegraNegocioExecptionDescricaoObrigatorio() {
        final var expectedMessage = "Descrição é de preenchimento obrigatorio";
        final var nome = UUID.randomUUID().toString();
        final var expectedValor = 22.0;

        Servico servico = new Servico();
        servico.setNome(nome);
        servico.setDescricao(null);
        servico.setValor(expectedValor);

        assertNull(servico.getId());

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(servico));

        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void dadoQueTenhoUmServicoSemValor_QuandoTentarInserir_DeveRetornarRegraNegocioExecptionValorObrigatorio() {
        final var expectedMessage = "Valor é de preenchimento obrigatorio";
        final var nome = UUID.randomUUID().toString();
        final var descricao = UUID.randomUUID().toString();

        Servico servico = new Servico();
        servico.setNome(nome);
        servico.setDescricao(descricao);
        servico.setValor(null);

        assertNull(servico.getId());

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(servico));

        assertEquals(expectedMessage, result.getMessage());
    }
}
