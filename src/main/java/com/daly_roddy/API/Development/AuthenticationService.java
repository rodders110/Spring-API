package com.daly_roddy.API.Development;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final String password = "password1";

    public boolean authenticate(String password){
        return password.equals(this.password);
    }
}
