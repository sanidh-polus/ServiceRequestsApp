package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class SrTicketDTO {

	private Integer ticketId;
	private int personId;
    private int categoryId;
    private String ticketDescription;
	public SrTicketDTO(Integer ticketId, int personId, int categoryId, String ticketDescription) {
		super();
		this.ticketId = ticketId;
		this.personId = personId;
		this.categoryId = categoryId;
		this.ticketDescription = ticketDescription;
	}
	
    
    
}
