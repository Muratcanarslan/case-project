package com.ets.caseproject.domain.request;

import com.ets.caseproject.domain.dtos.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest {
    @NotNull
    @Size(min = 5,message = "username must be at least 5 characters")
    private String username;
    @NotNull
    @Size(min = 5,message = "name must be at least 5 characters")
    private String name;
    @NotNull
    @Size(min = 8,message = "password must be at least 8 characters")
    private String password;
    @NotNull(message = "user can't be roleless")
    private List<RoleDto> roles;
}
