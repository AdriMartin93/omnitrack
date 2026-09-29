package com.omnitrak.users.domain.ports.in.account;

import java.util.UUID;

public interface DeleteAccountUseCase {
    void deleteAccount(UUID userId, String password);
}
