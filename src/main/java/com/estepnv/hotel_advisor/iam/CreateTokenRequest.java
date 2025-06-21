package com.estepnv.hotel_advisor.iam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateTokenRequest {
    @NotBlank
    @NotNull
    private String email;

    @NotNull
    @NotBlank
    private String password;

    public CreateTokenRequest(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
