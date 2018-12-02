package com.daly_roddy.API.Development.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Value("${authentication.password}")
    private String password;

    public boolean authenticate(String password){
        return password.equals(this.password);
    }
}
