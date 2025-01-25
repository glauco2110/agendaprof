package br.com.agendaprof.auth;

import br.com.agendaprof.usuario.entity.Senha;
import br.com.agendaprof.usuario.security.PasswordEncryptionService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgendaProfPasswordEncoder implements PasswordEncoder {

    private final PasswordEncryptionService service;

    @Override
    public String encode(CharSequence rawPassword) {
        Senha senha = new Senha(String.valueOf(rawPassword));
        return service.encrypt(senha).getValor();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String storedHash) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        // Divide o hash armazenado para obter o salt e o hash real
        String[] parts = storedHash.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Formato de hash armazenado inválido.");
        }

        String salt = parts[0];
        String hash = parts[1];

        String saltedPassword = salt + this.service.getSaltLength() + this.service.getPepper(); // Concatenando salt, senha e pepper

        try {
            // Verifica a senha comparando o hash calculado com o armazenado
            return argon2.verify(hash, saltedPassword.toCharArray());
        } finally {
            argon2.wipeArray(saltedPassword.toCharArray());
        }
    }
}
