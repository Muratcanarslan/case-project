package com.ets.caseproject.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
