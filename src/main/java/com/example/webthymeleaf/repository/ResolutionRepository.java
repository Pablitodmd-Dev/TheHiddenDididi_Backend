package com.example.webthymeleaf.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webthymeleaf.entity.Resolution;

public interface ResolutionRepository extends JpaRepository<Resolution, Long> {

	Optional<Resolution> findByRoundId(Long roundId);

	List<Resolution> findByUserId(Long userId);
}
