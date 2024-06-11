package org.example.demo_review_jwt_security_api.service;

import org.example.demo_review_jwt_security_api.model.dto.request.FormLogin;
import org.example.demo_review_jwt_security_api.model.dto.request.FormRegister;
import org.example.demo_review_jwt_security_api.model.dto.response.JWTResponse;

public interface UserService {
    boolean register(FormRegister formRegister);
    public JWTResponse login(FormLogin formLogin);
}
