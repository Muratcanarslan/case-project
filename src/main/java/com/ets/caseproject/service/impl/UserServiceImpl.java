package com.ets.caseproject.service.impl;

import com.ets.caseproject.core.mapping.ModelMapperService;
import com.ets.caseproject.domain.Role;
import com.ets.caseproject.domain.User;
import com.ets.caseproject.domain.dtos.AuthDto;
import com.ets.caseproject.domain.dtos.UserDto;
import com.ets.caseproject.domain.dtos.UserRoleDto;
import com.ets.caseproject.domain.request.LoginRequest;
import com.ets.caseproject.domain.request.UserSaveRequest;
import com.ets.caseproject.repository.UserRepository;
import com.ets.caseproject.service.RoleService;
import com.ets.caseproject.service.UserService;
import com.ets.caseproject.util.JwtUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ModelMapperService modelMapperService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ModelMapperService modelMapperService, RoleService roleService,@Lazy AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.modelMapperService = modelMapperService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDto saveUser(UserSaveRequest user) {
        log.info("saving user to database username: {}",user.getUsername());
        this.checkUserNameIsExists(user.getUsername());
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
        return this.modelMapperService.forDto().map(this.userRepository.save(this.modelMapperService.forRequest().map(user,User.class)),UserDto.class);
    }

    private void checkUserNameIsExists(String username) {
        if(this.userRepository.existsByUsername(username)){
            throw new RuntimeException("user name already exists! username : " + username);
        }
    }

    @Override
    @Transactional
    public void addRoleToUser(UserRoleDto userRoleDto) {
        log.info("adding role to user role name : {} username : {} ",userRoleDto.getRoleName(),userRoleDto.getUsername());
        Role role = this.roleService.getRoleByName(userRoleDto.getRoleName());
        User user = this.getUser(userRoleDto.getUsername());
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("getting user username: {}",username);
        return this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user can not be found, given user name: "+username));
    }


    @Override
    public UserDto findUserById(Long id) {
        return this.modelMapperService.forDto().map(this.userRepository.findById(id).orElseThrow(() -> new RuntimeException("user not found by this id : "+id)),UserDto.class);
    }

    @Override
    public List<UserDto> getUsers() {
        log.info("getting all users");
        return this.userRepository.findAll().stream().map(user -> this.modelMapperService.forDto().map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public AuthDto login(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword());
            Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String token = JwtUtil.generateToken(user);
            return new AuthDto(token);
        }catch (Exception e){
            throw new RuntimeException("Check your credentials! "+ e.getMessage());
        }

    }
}
