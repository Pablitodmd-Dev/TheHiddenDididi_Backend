package com.example.webthymeleaf.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webthymeleaf.converter.ResolutionConverter;
import com.example.webthymeleaf.model.ResolutionDTO;
import com.example.webthymeleaf.entity.Resolution;
import com.example.webthymeleaf.entity.User;
import com.example.webthymeleaf.service.ResolutionService;
import com.example.webthymeleaf.service.UserService;

@RestController
@RequestMapping("/resolutions")
public class ResolutionController {

	@Autowired
	private ResolutionService resolutionService;

	@Autowired
	private UserService userService;

	@Autowired
	private ResolutionConverter resolutionConverter;

	@PostMapping
	public ResponseEntity<?> resolveRound(@RequestBody ResolveRequest request,
			@AuthenticationPrincipal UserDetails principal) {
		try {
			User user = userService.getUserByUsername(principal.getUsername())
					.orElseThrow(() -> new IllegalStateException("Authenticated user not found"));

			Resolution resolution = resolutionService.resolveRound(
					request.getRoundId(), user.getId(), request.getProofImage(), request.getComment());

			return ResponseEntity.ok(resolutionConverter.entity2dto(resolution));

		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
		}
	}

	@GetMapping("/points/{userId}")
	public ResponseEntity<Integer> getPoints(@PathVariable Long userId) {
		return ResponseEntity.ok(resolutionService.getPointsByUser(userId));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<ResolutionDTO>> getResolutionsByUser(@PathVariable Long userId) {
		List<ResolutionDTO> resolutions = resolutionService.getResolutionsByUser(userId).stream()
				.map(resolutionConverter::entity2dto)
				.collect(Collectors.toList());
		return ResponseEntity.ok(resolutions);
	}

	public static class ResolveRequest {
		private Long roundId;
		private String proofImage;
		private String comment;

		public Long getRoundId() { return roundId; }
		public void setRoundId(Long roundId) { this.roundId = roundId; }

		public String getProofImage() { return proofImage; }
		public void setProofImage(String proofImage) { this.proofImage = proofImage; }

		public String getComment() { return comment; }
		public void setComment(String comment) { this.comment = comment; }
	}
}