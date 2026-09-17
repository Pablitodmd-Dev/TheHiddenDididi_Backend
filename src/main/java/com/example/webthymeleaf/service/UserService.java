package com.example.webthymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.webthymeleaf.entity.User;
import com.example.webthymeleaf.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(User user) {
		if (userRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already registered");
		}
		return userRepository.save(user);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}
}
