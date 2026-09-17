package com.example.webthymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webthymeleaf.entity.Round;
import com.example.webthymeleaf.entity.User;
import com.example.webthymeleaf.service.RoundService;
import com.example.webthymeleaf.service.UserService;

@RestController
@RequestMapping("/rounds")
public class RoundController {

	@Autowired
	private RoundService roundService;

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<?> createRound(@RequestBody CreateRoundRequest request,
			@AuthenticationPrincipal UserDetails principal) {
		try {
			User hider = userService.getUserByUsername(principal.getUsername())
					.orElseThrow(() -> new IllegalStateException("Authenticated user not found"));

			Round round = roundService.createRound(hider.getId(), request.getHidingImage());
			return ResponseEntity.ok(round);

		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/active")
	public ResponseEntity<?> getActiveRound() {
		return roundService.getActiveRound().<ResponseEntity<?>>map(ResponseEntity::ok)
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("No active round right now"));
	}

	public static class CreateRoundRequest {
		private String hidingImage;

		public String getHidingImage() {
			return hidingImage;
		}

		public void setHidingImage(String hidingImage) {
			this.hidingImage = hidingImage;
		}
	}
}