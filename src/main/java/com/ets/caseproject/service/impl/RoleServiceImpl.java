package com.ets.caseproject.service.impl;

import com.ets.caseproject.core.mapping.ModelMapperService;
import com.ets.caseproject.domain.Role;
import com.ets.caseproject.domain.dtos.RoleDto;
import com.ets.caseproject.domain.request.RoleSaveRequest;
import com.ets.caseproject.repository.RoleRepository;
import com.ets.caseproject.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final ModelMapperService modelMapperService;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapperService modelMapperService) {
        this.roleRepository = roleRepository;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public RoleDto saveRole(RoleSaveRequest role) {
        return this.modelMapperService.forDto().map(this.roleRepository.save(this.modelMapperService.forRequest().map(role,Role.class)),RoleDto.class);
    }
    @Override
    public RoleDto getById(Long id){
        return this.modelMapperService.forDto().map(this.roleRepository.findById(id).orElseThrow(() -> new RuntimeException("role cant be found for this id : "+id)),RoleDto.class);
    }

    @Override
    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name).orElseThrow(() -> new RuntimeException("role not found by this role name: "+name));
    }
}
