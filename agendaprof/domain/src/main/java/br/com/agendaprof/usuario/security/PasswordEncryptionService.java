package br.com.agendaprof.usuario.security;

import br.com.agendaprof.usuario.entity.Senha;

public interface PasswordEncryptionService {
    Senha encrypt(Senha password);
    String getPepper();
    int getSaltLength();
}
