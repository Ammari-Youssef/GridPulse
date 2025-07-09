package com.youssef.GridPulse.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterInput {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String confirmPassword;

}
