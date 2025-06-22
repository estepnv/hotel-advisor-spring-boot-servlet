package com.estepnv.hotel_advisor.iam;

import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

class Whoami {
    public String id;

    Whoami(){
        this.id = "nobody";
    }
}

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

    @GetMapping("/api/whoami")
    EntityModel<Whoami> whoami() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var details = (WebAuthenticationDetails)authentication.getDetails();

        var model = new Whoami();
        model.id = details.toString();

        return EntityModel.of(model);
    }
}