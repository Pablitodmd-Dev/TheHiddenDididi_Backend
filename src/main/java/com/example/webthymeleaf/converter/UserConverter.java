package com.example.webthymeleaf.converter;

import org.springframework.stereotype.Component;

import com.example.webthymeleaf.entity.User;
import com.example.webthymeleaf.model.UserDTO;

@Component
public class UserConverter {

	public UserDTO entity2dto(User user) {
		if (user == null) {
			return null;
		}

		UserDTO dto = new UserDTO();
		dto.setId(user.getId());
		dto.setName(user.getName());
		dto.setUsername(user.getUsername());
		dto.setEmail(user.getEmail());
		dto.setRole(user.getRole());
		dto.setEmailVerified(user.isEmailVerified());
		dto.setRegistrationDate(user.getRegistrationDate());

		return dto;
	}
}