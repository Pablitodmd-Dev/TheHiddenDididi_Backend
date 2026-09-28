package com.example.webthymeleaf.converter;

import org.springframework.stereotype.Component;

import com.example.webthymeleaf.entity.Round;
import com.example.webthymeleaf.model.RoundDTO;

@Component
public class RoundConverter {

	private final UserConverter userConverter;

	public RoundConverter(UserConverter userConverter) {
		this.userConverter = userConverter;
	}

	public RoundDTO entity2dto(Round round) {
		if (round == null) {
			return null;
		}

		RoundDTO dto = new RoundDTO();
		dto.setId(round.getId());
		dto.setHider(userConverter.entity2dto(round.getHider()));
		dto.setHidingImage(round.getHidingImage());
		dto.setStatus(round.getStatus());
		dto.setStartDate(round.getStartDate());
		dto.setAssignedSeeker(userConverter.entity2dto(round.getAssignedSeeker()));

		return dto;
	}
}