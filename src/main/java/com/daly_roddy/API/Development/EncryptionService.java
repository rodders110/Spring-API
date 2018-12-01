package com.daly_roddy.API.Development;


import com.google.common.hash.Hashing;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;


@Service
public class EncryptionService {

    private final String salt = "sodiumchloride";

    public String encrypt(String firstName, String lastName, String software){
        String originalString = firstName + lastName + software + salt;

        String encryptedString = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();

        return encryptedString;
    }

    public boolean verify(String firstName, String lastName, String software, String key){
        return key.equals(this.encrypt(firstName, lastName, software));
    }
}
