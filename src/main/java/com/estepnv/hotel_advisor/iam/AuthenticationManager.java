package com.estepnv.hotel_advisor.iam;

import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

public class AuthenticationManager extends ProviderManager
        implements org.springframework.security.authentication.AuthenticationManager
         {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
