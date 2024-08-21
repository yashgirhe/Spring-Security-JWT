package com.example.security.jwt.utils;

import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class BlackListToken {

    private final HashSet<String> tokens = new HashSet<>();

    public void blacklist(String token){
        tokens.add(token);
    }
    public boolean isBlacklisted(String token){
        return tokens.contains(token);
    }
}
