package com.ets.caseproject.service;


import com.ets.caseproject.domain.Role;
import com.ets.caseproject.domain.dtos.RoleDto;
import com.ets.caseproject.domain.request.RoleSaveRequest;

public interface RoleService {
    RoleDto saveRole(RoleSaveRequest role);

    RoleDto getById(Long id);

    Role getRoleByName(String name);
}
