package com.example.testapp.service.security;

import com.example.testapp.model.User;
import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
    User getCurrentUser();
}
