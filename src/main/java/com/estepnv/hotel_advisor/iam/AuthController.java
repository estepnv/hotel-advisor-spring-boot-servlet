package com.estepnv.hotel_advisor.iam;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

class Whoami {
    public UUID id;
    public String email;
    public List<String> authorities;

    public Whoami(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.authorities = user.getAuthorities().stream().map(a -> a.getAuthority()).toList();
    }
}

class TokenModel {
    public String token;
    TokenModel(String token) { this.token = token; }
}

@RestController
public class AuthController {
    @Autowired
    UserDetailsService userDetailsService;

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

    @GetMapping("/api/whoami")
    EntityModel<Whoami> whoami() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userDetailsService.loadUserByUsername(authentication.getName());

        return EntityModel.of(new Whoami((User)user));
    }
}