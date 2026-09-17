package com.example.webthymeleaf.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.webthymeleaf.entity.User;
import com.example.webthymeleaf.repository.UserRepository;
import com.example.webthymeleaf.security.JwtTokenProvider;

@Service
public class AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtTokenProvider tokenProvider;
	@Autowired
	private EmailService emailService;

	public String login(String username, String password) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!user.isEmailVerified()) {
			throw new RuntimeException("You must verify your email before logging in");
		}

		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return tokenProvider.generateToken(authentication);
	}

	public User register(User user) {
		if (userRepository.findByUsername(user.getUsername()).isPresent()) {
			throw new RuntimeException("Username is already taken!");
		}

		String token = UUID.randomUUID().toString();

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_USER");
		user.setDeleted(false);
		user.setEmailVerified(false);
		user.setVerificationToken(token);

		User savedUser = userRepository.save(user);

		emailService.sendVerificationEmail(user.getEmail(), token);

		return savedUser;
	}
}
