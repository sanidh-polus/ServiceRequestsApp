package com.polus.servicerequest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "SR_TICKET")
public class SrTicket implements Serializable{
    
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TICKET_ID")
    private int ticketId;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    private SrTicketCategory category;

    @Column(name = "TICKET_DESCRIPTION")
    private String ticketDescription;

    @ManyToOne
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
    private SrTicketStatus status;

    @ManyToOne
    @JoinColumn(name = "TICKET_ASSIGNED_TO", referencedColumnName = "PERSON_ID")
    private Person assignedTo;

    @Column(name = "TICKET_CREATED_TIME")
    private Timestamp ticketCreatedTime;
    
    @Column(name="TICKET_UPDATED_AT")
    private Timestamp ticketUpdatedAt;

    @OneToMany(mappedBy = "ticket",cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<TicketHistory> ticketHistories;

    @OneToMany(mappedBy = "ticket")
    private Set<CommentsHistory> commentsHistories;

	public SrTicket() {
		super();
	}

	public SrTicket(int ticketId, Person person, SrTicketCategory category, String ticketDescription,
			SrTicketStatus status, Person assignedTo, Timestamp ticketCreatedTime, Timestamp ticketUpdatedAt,
			Set<TicketHistory> ticketHistories, Set<CommentsHistory> commentsHistories) {
		super();
		this.ticketId = ticketId;
		this.person = person;
		this.category = category;
		this.ticketDescription = ticketDescription;
		this.status = status;
		this.assignedTo = assignedTo;
		this.ticketCreatedTime = ticketCreatedTime;
		this.ticketUpdatedAt = ticketUpdatedAt;
		this.ticketHistories = ticketHistories;
		this.commentsHistories = commentsHistories;
	}

	public SrTicket(int ticketId) {
		super();
		this.ticketId = ticketId;
	}

	

}

