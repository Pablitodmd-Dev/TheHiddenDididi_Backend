package com.example.webthymeleaf.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "resolutions")
public class Resolution {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "round_id", nullable = false, unique = true)
	private Round round;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "proof_image", nullable = false)
	private String proofImage;

	@Column(nullable = false)
	private String comment;

	@Column(name = "resolution_date", nullable = false, updatable = false)
	private LocalDateTime resolutionDate = LocalDateTime.now();

	public Resolution() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Round getRound() {
		return round;
	}

	public void setRound(Round round) {
		this.round = round;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProofImage() {
		return proofImage;
	}

	public void setProofImage(String proofImage) {
		this.proofImage = proofImage;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public LocalDateTime getResolutionDate() {
		return resolutionDate;
	}

	public void setResolutionDate(LocalDateTime resolutionDate) {
		this.resolutionDate = resolutionDate;
	}
}
