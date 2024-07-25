package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class AssignedToAdminTicketsDTO {

	public Integer assignedTo;
	public Integer statusId;
	public AssignedToAdminTicketsDTO() {
		super();
	}
	public AssignedToAdminTicketsDTO(Integer assignedTo, Integer statusId) {
		super();
		this.assignedTo = assignedTo;
		this.statusId = statusId;
	}
	
	
}
