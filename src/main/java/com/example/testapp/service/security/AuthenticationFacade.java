package com.example.testapp.service.security;

import com.example.testapp.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override // получение личности того кто сейчас выполняет запрос
    public User getCurrentUser() {
        return ((User)getAuthentication().getPrincipal());
    }
}
