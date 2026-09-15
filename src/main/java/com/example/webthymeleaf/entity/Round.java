package com.example.webthymeleaf.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "rounds")
public class Round {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hider_id", nullable = false)
	private User hider;

	@Column(name = "hiding_image")
	private String hidingImage;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private RoundStatus status = RoundStatus.ACTIVE;

	@Column(name = "start_date", nullable = false, updatable = false)
	private LocalDateTime startDate = LocalDateTime.now();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigned_seeker_id", nullable = false)
	private User assignedSeeker;

	public Round() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getHider() {
		return hider;
	}

	public void setHider(User hider) {
		this.hider = hider;
	}

	public String getHidingImage() {
		return hidingImage;
	}

	public void setHidingImage(String hidingImage) {
		this.hidingImage = hidingImage;
	}

	public RoundStatus getStatus() {
		return status;
	}

	public void setStatus(RoundStatus status) {
		this.status = status;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public User getAssignedSeeker() {
		return assignedSeeker;
	}

	public void setAssignedSeeker(User assignedSeeker) {
		this.assignedSeeker = assignedSeeker;
	}
}
