package com.omnitrak.users.domain.ports.out.auth;

import java.util.Optional;

public interface PasswordResetTokenPort {

    // con esto guardo el token generado
    void saveResetToken(String email, String token, long ttlInMinutes);

    // aqui obtengo el email dueño del token
    Optional<String> getEmailByResetToken(String token);

    //elimino el token una vez usado
    void deleteResetToken(String token);
}
