package com.example.webthymeleaf.converter;

import org.springframework.stereotype.Component;

import com.example.webthymeleaf.entity.Resolution;
import com.example.webthymeleaf.model.ResolutionDTO;

@Component
public class ResolutionConverter {

	private final UserConverter userConverter;

	public ResolutionConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	public ResolutionDTO entity2dto(Resolution resolution) {
		if (resolution == null) {
			return null;
		}

		ResolutionDTO dto = new ResolutionDTO();
		dto.setId(resolution.getId());
		dto.setRoundId(resolution.getRound().getId());
		dto.setUser(userConverter.entity2dto(resolution.getUser()));
		dto.setProofImage(resolution.getProofImage());
		dto.setComment(resolution.getComment());
		dto.setResolutionDate(resolution.getResolutionDate());

		return dto;
	}
}