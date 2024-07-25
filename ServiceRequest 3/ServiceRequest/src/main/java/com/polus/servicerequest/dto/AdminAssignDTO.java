package com.polus.servicerequest.dto;

import com.polus.servicerequest.entity.Person;

import lombok.Data;

@Data
public class AdminAssignDTO {

	
	private Integer ticketId;
	private Integer assignedTo;
	public AdminAssignDTO(Integer ticketId, Integer assignedTo) {
		super();
		this.ticketId = ticketId;
		this.assignedTo = assignedTo;
	}
	public AdminAssignDTO() {
		super();
	}
	
	
}
