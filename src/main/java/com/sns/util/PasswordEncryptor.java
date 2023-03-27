package com.sns.util;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PasswordEncryptor {
    private final Argon2 argon2;

    public PasswordEncryptor() {
        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    }

    public String hashPassword(String password) {
        return argon2.hash(2, 65536, 1, password.toCharArray());
    }

    public boolean verifyPassword(String password, String hashedPassword) {
        return argon2.verify(hashedPassword, password.toCharArray());
    }
}
