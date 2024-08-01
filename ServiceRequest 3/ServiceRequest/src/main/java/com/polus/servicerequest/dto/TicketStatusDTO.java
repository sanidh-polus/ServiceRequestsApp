package com.polus.servicerequest.dto;

import java.sql.Timestamp;
import java.util.List;

//import com.polus.servicerequest.entity.Person;

import lombok.Data;

@Data
public class TicketStatusDTO {

	private int ticketId;
	private int personId;
	private int categoryId;
	private String fullName;
	private String ticketDescription;
	private String categoryName;
	private Timestamp ticketCreatedTime;
	private Timestamp ticketUpdatedAt;
	private AdminandUsersDTO assignedTo;
	private List<CommentsHistoryDTO> ticketComments;

	public TicketStatusDTO() {
		super();
	}

	public TicketStatusDTO(int ticketId, int personId, int categoryId, String ticketDescription, String categoryName,
			Timestamp ticketCreatedTime, Timestamp ticketUpdatedAt, AdminandUsersDTO assignedTo,
			List<CommentsHistoryDTO> ticketComments) {
		super();
		this.ticketId = ticketId;
		this.personId = personId;
		this.categoryId = categoryId;
		this.ticketDescription = ticketDescription;
		this.categoryName = categoryName;
		this.ticketCreatedTime = ticketCreatedTime;
		this.ticketUpdatedAt = ticketUpdatedAt;
		this.assignedTo = assignedTo;
		this.ticketComments = ticketComments;
	}

	
	

}
