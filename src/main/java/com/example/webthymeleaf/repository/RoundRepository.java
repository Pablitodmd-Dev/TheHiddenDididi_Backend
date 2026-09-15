package com.example.webthymeleaf.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webthymeleaf.entity.Round;
import com.example.webthymeleaf.entity.RoundStatus;

public interface RoundRepository extends JpaRepository<Round, Long> {

	Optional<Round> findByStatus(RoundStatus status);
}
