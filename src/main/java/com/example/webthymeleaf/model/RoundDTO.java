package com.example.webthymeleaf.model;

import java.time.LocalDateTime;

import com.example.webthymeleaf.entity.RoundStatus;

public class RoundDTO {

	private Long id;
	private UserDTO hider;
	private String hidingImage;
	private RoundStatus status;
	private LocalDateTime startDate;
	private UserDTO assignedSeeker;

	public RoundDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getHider() {
		return hider;
	}

	public void setHider(UserDTO hider) {
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

	public UserDTO getAssignedSeeker() {
		return assignedSeeker;
	}

	public void setAssignedSeeker(UserDTO assignedSeeker) {
		this.assignedSeeker = assignedSeeker;
	}
}