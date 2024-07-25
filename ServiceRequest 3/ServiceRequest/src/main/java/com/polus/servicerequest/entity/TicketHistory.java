package com.polus.servicerequest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "TICKET_HISTORY")
public class TicketHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TICKET_HISTORY_ID")
	private int ticketHistoryId;

	@ManyToOne
	@JoinColumn(name = "TICKET_ID", referencedColumnName = "TICKET_ID")
	private SrTicket ticket;

	@ManyToOne
	@JoinColumn(name = "UPDATED_BY",referencedColumnName = "PERSON_ID")
	private Person updatedBy;

	@Column(name = "UPDATED_TIME")
	private Timestamp updatedTime;

	@ManyToOne
	@JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
	private SrTicketStatus status;

	public TicketHistory(int ticketHistoryId, SrTicket ticket, Person updatedBy, Timestamp updatedTime,
			SrTicketStatus status) {
		super();
		this.ticketHistoryId = ticketHistoryId;
		this.ticket = ticket;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
		this.status = status;
	}

	public TicketHistory() {
		super();
	}

}
