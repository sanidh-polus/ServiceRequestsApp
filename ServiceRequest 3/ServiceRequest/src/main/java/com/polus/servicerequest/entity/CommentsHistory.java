package com.polus.servicerequest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "COMMENTS_HISTORY")
public class CommentsHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMENT_ID")
	private int commentId;

	@ManyToOne
	@JoinColumn(name = "TICKET_ID", referencedColumnName = "TICKET_ID")
	private SrTicket ticket;

	@Column(name = "COMMENTS")
	private String comments;

	@Column(name = "COMMENTED_BY")
	private Integer commentedBy;

	@Column(name = "COMMENTED_AT")
	private Timestamp commentedAt;

	@ManyToOne
	@JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID")
	private SrTicketStatus status;

	public CommentsHistory(int commentId, SrTicket ticket, String comments, Integer commentedBy, Timestamp commentedAt,
			SrTicketStatus status) {
		super();
		this.commentId = commentId;
		this.ticket = ticket;
		this.comments = comments;
		this.commentedBy = commentedBy;
		this.commentedAt = commentedAt;
		this.status = status;
	}

	public CommentsHistory() {
		
	}

}
