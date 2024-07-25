package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class CountDTO {

	private int count;

	public CountDTO(int count) {
		super();
		this.count = count;
	}
	
}
