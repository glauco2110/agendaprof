package br.com.agendaprof.usuario.security.password;

import br.com.agendaprof.usuario.entity.Senha;
import br.com.agendaprof.usuario.security.PasswordEncryptionService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class ArgonPasswordEncryptionService implements PasswordEncryptionService {

    private static final int SALT_LENGTH = 16;
    private static final String PEPPER = "AGENDAPROF@123";

    public static void main(String[] args) {
        PasswordEncryptionService service = new ArgonPasswordEncryptionService();

        Senha senha = service.encrypt(new Senha("123@456"));
        System.out.println(senha.getValor());

    }


    @Override
    public Senha encrypt(Senha senha) {
        String password = senha.getValor();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        String salt = generateSalt();
        String saltedPassword = salt + password + PEPPER; // Concatenando salt, senha e pepper

        try {
            int iterations = 3;      // Número de iterações
            int memory = 65536;      // Memória em KiB (64 MB)
            int parallelism = 1;     // Número de threads

            String hash = argon2.hash(iterations, memory, parallelism, saltedPassword.toCharArray());

            return new Senha(salt + ":" + hash, Senha.Status.HASHED);
        } finally {
            argon2.wipeArray(saltedPassword.toCharArray());
        }
    }

    @Override
    public String getPepper() {
        return ArgonPasswordEncryptionService.PEPPER;
    }

    @Override
    public int getSaltLength() {
        return ArgonPasswordEncryptionService.SALT_LENGTH;
    }

    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
