package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class AssignedToAdminTicketsDTO {
 
	public Integer assignedTo;
	public Integer statusId;
	public Integer page;
	public Integer size;
	public AssignedToAdminTicketsDTO() {
		super();
	}
	public AssignedToAdminTicketsDTO(Integer assignedTo, Integer statusId, Integer page, Integer size) {
		super();
		this.assignedTo = assignedTo;
		this.statusId = statusId;
		this.page = page;
		this.size = size;
	}
	
}