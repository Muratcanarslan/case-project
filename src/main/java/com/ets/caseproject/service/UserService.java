package com.ets.caseproject.service;

import com.ets.caseproject.domain.Role;
import com.ets.caseproject.domain.User;
import com.ets.caseproject.domain.dtos.UserDto;
import com.ets.caseproject.domain.dtos.UserRoleDto;
import com.ets.caseproject.domain.request.UserSaveRequest;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserSaveRequest user);
    void addRoleToUser(UserRoleDto userRoleDto);
    User getUser(String username);

    UserDto findUserById(Long id);
    List<UserDto> getUsers();
}

