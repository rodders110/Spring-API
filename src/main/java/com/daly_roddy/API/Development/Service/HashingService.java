package com.daly_roddy.API.Development.Service;


import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class HashingService {

    @Value("${encryption.salt}")
    private String salt;

    public String encrypt(String fullName, String software){
        String originalString = fullName + software + salt;

        String encryptedString = Hashing.sha256()
                .hashString(originalString, StandardCharsets.UTF_8)
                .toString();

        return encryptedString;
    }

    public boolean verify(String fullName, String software, String key){
        return key.equals(this.encrypt(fullName, software));
    }
}
