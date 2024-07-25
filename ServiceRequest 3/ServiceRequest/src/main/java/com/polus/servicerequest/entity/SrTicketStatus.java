package com.polus.servicerequest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "SR_TICKET_STATUS")
public class SrTicketStatus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STATUS_ID")
	private int statusId;

	@Column(name = "STATUS_TYPE")
	private String statusType;

	@Column(name = "STATUS_DESCRIPTION")
	private String statusDescription;

	@OneToMany(mappedBy = "status")
	private Set<SrTicket> tickets;

	@OneToMany(mappedBy = "status")
	private Set<TicketHistory> ticketHistories;

	@OneToMany(mappedBy = "status")
	private Set<CommentsHistory> commentsHistories;

	public SrTicketStatus(int statusId, String statusType, String statusDescription, Set<SrTicket> tickets,
			Set<TicketHistory> ticketHistories, Set<CommentsHistory> commentsHistories) {
		super();
		this.statusId = statusId;
		this.statusType = statusType;
		this.statusDescription = statusDescription;
		this.tickets = tickets;
		this.ticketHistories = ticketHistories;
		this.commentsHistories = commentsHistories;
	}

	public SrTicketStatus() {
		super();
	}

}
