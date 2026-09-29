package com.omnitrak.users.domain.ports.out.auth;

import java.util.Optional;

public interface ActivationTokenPort {

    void saveActivationToken(String username, String token, long ttlInMinutes);
    Optional<String> getUsernameByActivationToken(String token);
    void deleteActivationToken(String token);

}
