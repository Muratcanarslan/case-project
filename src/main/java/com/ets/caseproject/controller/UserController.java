package com.ets.caseproject.controller;

import com.ets.caseproject.domain.dtos.AuthDto;
import com.ets.caseproject.domain.dtos.UserDto;
import com.ets.caseproject.domain.dtos.UserRoleDto;
import com.ets.caseproject.domain.request.LoginRequest;
import com.ets.caseproject.domain.request.UserSaveRequest;
import com.ets.caseproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUsers(){
        return ResponseEntity.ok().body(this.userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<UserDto> saveUser(@RequestBody UserSaveRequest user){
        return ResponseEntity.ok().body(this.userService.saveUser(user));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getByUserId(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(this.userService.findUserById(id));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDto> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok().body(this.userService.login(loginRequest));
    }

    @PostMapping("/add-role-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody UserRoleDto userRoleDto){
        this.userService.addRoleToUser(userRoleDto);
        return ResponseEntity.ok().body("successfuly added");
    }

}
