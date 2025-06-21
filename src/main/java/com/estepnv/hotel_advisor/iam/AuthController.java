package com.estepnv.hotel_advisor.iam;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


class TokenModel {
    public String token;
    TokenModel(String token) { this.token = token; }
}

@RestController
public class AuthController {
    private JwtService service;
    AuthController(JwtService service) {
        this.service = service;
    }

    @PermitAll
    @PostMapping("/api/token")
    ResponseEntity<?> createToken(@Valid @RequestBody CreateTokenRequest request) {
        var model = new TokenModel(service.createToken(request));
        return ResponseEntity.ok(model);
    }
}