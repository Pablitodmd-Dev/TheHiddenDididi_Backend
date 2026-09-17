package com.example.webthymeleaf.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.webthymeleaf.entity.Round;
import com.example.webthymeleaf.entity.RoundStatus;
import com.example.webthymeleaf.entity.User;
import com.example.webthymeleaf.repository.RoundRepository;

@Service
public class RoundService {

	private final RoundRepository roundRepository;
	private final UserService userService;
	private final Random random = new Random();

	public RoundService(RoundRepository roundRepository, UserService userService) {
		this.roundRepository = roundRepository;
		this.userService = userService;
	}

	public Round createRound(Long hiderId, String hidingImage) {
		Optional<Round> activeRound = roundRepository.findByStatus(RoundStatus.ACTIVE);
		if (activeRound.isPresent()) {
			throw new IllegalStateException("There is already an active round");
		}

		User hider = userService.getUserById(hiderId)
				.orElseThrow(() -> new IllegalArgumentException("Hider not found"));

		User assignedSeeker = spinWheel(hiderId);

		Round round = new Round();
		round.setHider(hider);
		round.setHidingImage(hidingImage);
		round.setAssignedSeeker(assignedSeeker);
		round.setStatus(RoundStatus.ACTIVE);

		return roundRepository.save(round);
	}

	private User spinWheel(Long hiderId) {
		List<User> candidates = userService.getAllUsers().stream()
				.filter(u -> !u.getId().equals(hiderId))
				.collect(Collectors.toList());

		if (candidates.isEmpty()) {
			throw new IllegalStateException("No candidates available to be the seeker");
		}

		int index = random.nextInt(candidates.size());
		return candidates.get(index);
	}

	public Optional<Round> getActiveRound() {
		return roundRepository.findByStatus(RoundStatus.ACTIVE);
	}

	public void resolveRound(Round round) {
		round.setStatus(RoundStatus.RESOLVED);
		roundRepository.save(round);
	}

	public Optional<Round> getRoundById(Long id) {
		return roundRepository.findById(id);
	}
}
