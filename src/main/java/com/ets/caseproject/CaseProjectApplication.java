package com.ets.caseproject;

import com.ets.caseproject.domain.Role;
import com.ets.caseproject.domain.User;
import com.ets.caseproject.domain.dtos.UserRoleDto;
import com.ets.caseproject.service.RoleService;
import com.ets.caseproject.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class CaseProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CaseProjectApplication.class, args);
	}

}
