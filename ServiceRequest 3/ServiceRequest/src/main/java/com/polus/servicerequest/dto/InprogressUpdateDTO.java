package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class InprogressUpdateDTO {

	private int ticketId;
	private int categoryId;
	private String ticketDescription;

	public InprogressUpdateDTO(int ticketId, int categoryId, String ticketDescription) {
		super();
		this.ticketId = ticketId;
		this.categoryId = categoryId;
		this.ticketDescription = ticketDescription;
	}

	

}
