package com.example.webthymeleaf.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.webthymeleaf.entity.User;
import com.example.webthymeleaf.repository.UserRepository;
import com.example.webthymeleaf.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
		try {
			String token = authService.login(request.get("username"), request.get("password"));
			User user = userRepository.findByUsername(request.get("username"))
					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
			return ResponseEntity.ok(Map.of("token", token, "role", user.getRole()));
		} catch (RuntimeException e) {
			return ResponseEntity.status(401).body(Map.of("message", e.getMessage()));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<User> register(@RequestBody Map<String, Object> request) {
		User user = new User();
		user.setUsername((String) request.get("username"));
		user.setEmail((String) request.get("email"));
		user.setPassword((String) request.get("password"));
		user.setName((String) request.get("name"));

		User savedUser = authService.register(user);
		return ResponseEntity.ok(savedUser);
	}

	@GetMapping("/verify")
	public ResponseEntity<String> verifyEmail(@RequestParam String token) {
		User user = userRepository.findByVerificationToken(token)
				.orElseThrow(() -> new RuntimeException("Invalid or expired token"));

		user.setEmailVerified(true);
		user.setVerificationToken(null);
		userRepository.save(user);

		return ResponseEntity.ok(
			"<html><body style='font-family:sans-serif;text-align:center;padding:40px;background:#0F0E17;color:white;'>"
			+ "<h1 style='color:#C8920A;'>¡Cuenta verificada!</h1>"
			+ "<p>Tu cuenta de El Dididi Oculto ha sido verificada correctamente.</p>"
			+ "<p>Ya puedes iniciar sesión en la aplicación.</p>"
			+ "</body></html>"
		);
	}
}