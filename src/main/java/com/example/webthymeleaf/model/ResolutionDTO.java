package com.example.webthymeleaf.model;

import java.time.LocalDateTime;

public class ResolutionDTO {

	private Long id;
	private Long roundId;
	private UserDTO user;
	private String proofImage;
	private String comment;
	private LocalDateTime resolutionDate;

	public ResolutionDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoundId() {
		return roundId;
	}

	public void setRoundId(Long roundId) {
		this.roundId = roundId;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
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