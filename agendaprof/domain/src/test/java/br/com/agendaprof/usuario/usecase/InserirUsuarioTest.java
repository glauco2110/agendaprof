package br.com.agendaprof.usuario.usecase;

import br.com.agendaprof.core.exceptions.RegraNegocioException;
import br.com.agendaprof.usuario.command.InserirUsuarioCommand;
import br.com.agendaprof.usuario.entity.Senha;
import br.com.agendaprof.usuario.entity.Usuario;
import br.com.agendaprof.usuario.repository.UsuarioRepository;
import br.com.agendaprof.usuario.security.PasswordEncryptionService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = {InserirUsuario.class, UsuarioRegras.class, UsuarioRepository.class, PasswordEncryptionService.class})
public class InserirUsuarioTest {

    private static final String MSG_SENHA_INVALIDA = "A senha deve conter pelo menos uma letra ou caractere especial";
    private static final String MSG_SENHA_VAZIA = "Senha é de preenchimento obrigatorio";
    private static final String MSG_SENHA_MENOR_QUE_SEIS_CARACTERES = "A senha deve ter pelo menos 6 caracteres";
    private static final String LOGIN_DUPLICADO = "Já existe um usuario cadastrado com este login";
    private static final String NOME_OBRIGATORIO = "Nome é de preenchimento obrigatorio";
    private static final String LOGIN_OBRIGATORIO = "Login é de preenchimento obrigatorio";
    private static final String SENHA_OBRIGATORIO = "Senha é de preenchimento obrigatorio";

    @MockBean
    private UsuarioRepository repository;

    @Autowired
    private InserirUsuario useCase;

    @MockBean
    private PasswordEncryptionService passwordEncryptionService;

    @Test
    void dadoQueTenhoUsuarioValido_QuandoTentarInserir_DeveRetornarUsuarioPersistido() {

        final var expectedNome = UUID.randomUUID().toString();
        final var expectedLogin = UUID.randomUUID().toString();
        final var expectedSenha = "123@456";
        final var senha = new Senha("6AutLCJNOpFCutVn2VP9nA==:$argon2id$v=19$m=65536,t=3,p=1$mPMJS/g2Y6Jg8PWA5JTtdA$K90jsTpi8N6gWK4rqBzyBAhDT1gvLKzmOXhb4gFQnc4", Senha.Status.HASHED);

        final var command = new InserirUsuarioCommand(expectedNome, expectedLogin, expectedSenha);

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(command.login());
        usuario.setNome(command.nome());
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        Mockito.when(passwordEncryptionService.encrypt(any())).thenReturn(senha);
        Mockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        final var output = useCase.execute(command);

        verify(repository, times(1)).save(any());
        verify(passwordEncryptionService, times(1)).encrypt(any());

        assertEquals(expectedLogin, output.login());
        assertEquals(expectedNome, output.nome());
        assertNotNull(output.id());
        assertNotNull(output.createdAt());
        assertNotNull(output.updatedAt());
    }

    @Test
    void dadoQueTenhoUsuarioComSenhaVazia_QuandoTentarInserir_DeveRetornarErroDeSenhaInvalida() {
        final var expectedNome = UUID.randomUUID().toString();
        final var expectedLogin = UUID.randomUUID().toString();
        final var expectedSenha = "";
        final var expectedMessage = MSG_SENHA_VAZIA;

        final var command = new InserirUsuarioCommand(expectedNome, expectedLogin, expectedSenha);

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void dadoQueTenhoUsuarioComSenhaSemCaracteresEspeciais_QuandoTentarInserir_DeveRetornarErroDeSenhaInvalida() {
        final var expectedNome = UUID.randomUUID().toString();
        final var expectedLogin = UUID.randomUUID().toString();
        final var expectedSenha = "123456";
        final var expectedMessage = MSG_SENHA_INVALIDA;

        final var command = new InserirUsuarioCommand(expectedNome, expectedLogin, expectedSenha);

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void dadoQueTenhoUsuarioComSenhaMenorQueSeisCaracteres_QuandoTentarInserir_DeveRetornarErroDeSenhaInvalida() {
        final var expectedNome = UUID.randomUUID().toString();
        final var expectedLogin = UUID.randomUUID().toString();
        final var expectedSenha = "12345";
        final var expectedMessage = MSG_SENHA_MENOR_QUE_SEIS_CARACTERES;

        final var command = new InserirUsuarioCommand(expectedNome, expectedLogin, expectedSenha);

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());
    }

    @Test
    void dadoQueTenhoUsuarioValidoComLoginDuplicado_QuandoTentarInserir_DeveRetornarErroDeLoginExistente() {
        final var expectedNome = UUID.randomUUID().toString();
        final var expectedLogin = UUID.randomUUID().toString();
        final var expectedSenha = "123@456";
        final var expectedMessage = LOGIN_DUPLICADO;
        final var senha = new Senha("6AutLCJNOpFCutVn2VP9nA==:$argon2id$v=19$m=65536,t=3,p=1$mPMJS/g2Y6Jg8PWA5JTtdA$K90jsTpi8N6gWK4rqBzyBAhDT1gvLKzmOXhb4gFQnc4", Senha.Status.HASHED);

        final var command = new InserirUsuarioCommand(expectedNome, expectedLogin, expectedSenha);

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(command.login());
        usuario.setNome(command.nome());
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        Mockito.when(passwordEncryptionService.encrypt(any())).thenReturn(senha);
        Mockito.when(repository.existeUsuarioMesmoLogin(any(Usuario.class))).thenReturn(Optional.of(usuario));

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());

    }

    @Test
    void dadoQueTenhoUsuarioSemNome_QuandoTentarInserir_DeveRetornarErroDeNomeObrigatorio() {
        final var expectedNome = "";
        final var expectedLogin = UUID.randomUUID().toString();
        final var expectedSenha = "123@456";
        final var expectedMessage = NOME_OBRIGATORIO;
        final var senha = new Senha("6AutLCJNOpFCutVn2VP9nA==:$argon2id$v=19$m=65536,t=3,p=1$mPMJS/g2Y6Jg8PWA5JTtdA$K90jsTpi8N6gWK4rqBzyBAhDT1gvLKzmOXhb4gFQnc4", Senha.Status.HASHED);

        final var command = new InserirUsuarioCommand(expectedNome, expectedLogin, expectedSenha);

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(command.login());
        usuario.setNome(command.nome());
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        Mockito.when(passwordEncryptionService.encrypt(any())).thenReturn(senha);
        Mockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());

    }

    @Test
    void dadoQueTenhoUsuarioCoNomeNull_QuandoTentarInserir_DeveRetornarErroDeNomeObrigatorio() {
        final var expectedLogin = UUID.randomUUID().toString();
        final var expectedSenha = "123@456";
        final var expectedMessage = NOME_OBRIGATORIO;
        final var senha = new Senha("6AutLCJNOpFCutVn2VP9nA==:$argon2id$v=19$m=65536,t=3,p=1$mPMJS/g2Y6Jg8PWA5JTtdA$K90jsTpi8N6gWK4rqBzyBAhDT1gvLKzmOXhb4gFQnc4", Senha.Status.HASHED);

        final var command = new InserirUsuarioCommand(null, expectedLogin, expectedSenha);

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(command.login());
        usuario.setNome(command.nome());
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        Mockito.when(passwordEncryptionService.encrypt(any())).thenReturn(senha);
        Mockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());

    }

    @Test
    void dadoQueTenhoUsuarioSemLogin_QuandoTentarInserir_DeveRetornarErroDeLoginObrigatorio() {
        final var expectedNome = UUID.randomUUID().toString();
        final var expectedLogin = "";
        final var expectedSenha = "123@456";
        final var expectedMessage = LOGIN_OBRIGATORIO;
        final var senha = new Senha("6AutLCJNOpFCutVn2VP9nA==:$argon2id$v=19$m=65536,t=3,p=1$mPMJS/g2Y6Jg8PWA5JTtdA$K90jsTpi8N6gWK4rqBzyBAhDT1gvLKzmOXhb4gFQnc4", Senha.Status.HASHED);

        final var command = new InserirUsuarioCommand(expectedNome, expectedLogin, expectedSenha);

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(command.login());
        usuario.setNome(command.nome());
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        Mockito.when(passwordEncryptionService.encrypt(any())).thenReturn(senha);
        Mockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());

    }

    @Test
    void dadoQueTenhoUsuarioComLoginNull_QuandoTentarInserir_DeveRetornarErroDeLoginObrigatorio() {
        final var expectedNome = UUID.randomUUID().toString();
        final var expectedSenha = "123@456";
        final var expectedMessage = LOGIN_OBRIGATORIO;
        final var senha = new Senha("6AutLCJNOpFCutVn2VP9nA==:$argon2id$v=19$m=65536,t=3,p=1$mPMJS/g2Y6Jg8PWA5JTtdA$K90jsTpi8N6gWK4rqBzyBAhDT1gvLKzmOXhb4gFQnc4", Senha.Status.HASHED);

        final var command = new InserirUsuarioCommand(expectedNome, null, expectedSenha);

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(command.login());
        usuario.setNome(command.nome());
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        Mockito.when(passwordEncryptionService.encrypt(any())).thenReturn(senha);
        Mockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());

    }

    @Test
    void dadoQueTenhoUsuarioSemSenha_QuandoTentarInserir_DeveRetornarErroDeSenhaObrigatorio() {
        final var expectedNome = UUID.randomUUID().toString();
        final var expectedLogin = "";
        final var expectedMessage = SENHA_OBRIGATORIO;
        final var senha = new Senha("6AutLCJNOpFCutVn2VP9nA==:$argon2id$v=19$m=65536,t=3,p=1$mPMJS/g2Y6Jg8PWA5JTtdA$K90jsTpi8N6gWK4rqBzyBAhDT1gvLKzmOXhb4gFQnc4", Senha.Status.HASHED);

        final var command = new InserirUsuarioCommand(expectedNome, expectedLogin, null);

        Usuario usuario = new Usuario();
        usuario.setSenha(senha);
        usuario.setLogin(command.login());
        usuario.setNome(command.nome());
        usuario.setId(new Random().nextLong());
        usuario.setCreatedAt(new Date());
        usuario.setUpdatedAt(new Date());

        Mockito.when(passwordEncryptionService.encrypt(any())).thenReturn(senha);
        Mockito.when(repository.save(any(Usuario.class))).thenReturn(usuario);

        final var result = assertThrows(RegraNegocioException.class, () -> useCase.execute(command));

        assertEquals(expectedMessage, result.getMessage());

    }
}
