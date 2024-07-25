package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class ChangeStatusDTO {
	private Integer ticketId;
	private Integer assignedTo;
	private String comments;
	private Integer statusId;

	public ChangeStatusDTO() {
		super();
	}

	public ChangeStatusDTO(Integer ticketId, Integer assignedTo, String comments, Integer statusId) {
		super();
		this.ticketId = ticketId;
		this.assignedTo = assignedTo;
		this.comments = comments;
		this.statusId = statusId;
	}

}
