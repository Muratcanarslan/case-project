package com.ets.caseproject.controller;

import com.ets.caseproject.domain.Role;
import com.ets.caseproject.domain.dtos.RoleDto;
import com.ets.caseproject.domain.dtos.UserRoleDto;
import com.ets.caseproject.domain.request.RoleSaveRequest;
import com.ets.caseproject.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles/")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("{id}")
    public ResponseEntity<RoleDto> getById(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.roleService.getById(id));
    }

    @PostMapping
    public ResponseEntity<RoleDto> saveRole(@RequestBody RoleSaveRequest role){
        return ResponseEntity.ok().body(this.roleService.saveRole(role));
    }
}
