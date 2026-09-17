package com.example.webthymeleaf.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webthymeleaf.entity.Resolution;
import com.example.webthymeleaf.entity.Round;
import com.example.webthymeleaf.entity.RoundStatus;
import com.example.webthymeleaf.repository.ResolutionRepository;

@Service
public class ResolutionService {

	private final ResolutionRepository resolutionRepository;
	private final RoundService roundService;

	public ResolutionService(ResolutionRepository resolutionRepository, RoundService roundService) {
		this.resolutionRepository = resolutionRepository;
		this.roundService = roundService;
	}

	public Resolution resolveRound(Long roundId, Long userId, String proofImage, String comment) {
		Round round = roundService.getRoundById(roundId)
				.orElseThrow(() -> new IllegalArgumentException("Round not found"));

		if (round.getStatus() != RoundStatus.ACTIVE) {
			throw new IllegalStateException("Round is not active");
		}

		if (!round.getAssignedSeeker().getId().equals(userId)) {
			throw new IllegalStateException("Only the assigned seeker can resolve this round");
		}

		Resolution resolution = new Resolution();
		resolution.setRound(round);
		resolution.setUser(round.getAssignedSeeker());
		resolution.setProofImage(proofImage);
		resolution.setComment(comment);

		Resolution saved = resolutionRepository.save(resolution);
		roundService.resolveRound(round);

		return saved;
	}

	public int getPointsByUser(Long userId) {
		return resolutionRepository.findByUserId(userId).size();
	}

	public List<Resolution> getResolutionsByUser(Long userId) {
		return resolutionRepository.findByUserId(userId);
	}
}