package com.hgy.happybank.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {

    public static String encryptedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}
