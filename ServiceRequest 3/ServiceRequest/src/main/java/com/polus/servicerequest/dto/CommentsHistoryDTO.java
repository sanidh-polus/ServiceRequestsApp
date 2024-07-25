package com.polus.servicerequest.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentsHistoryDTO {
	private Integer commentId; 
	private Integer ticketId;
	private String comments;
	private String commentedBy;
	private Timestamp commentedAt;
	public CommentsHistoryDTO(Integer commentId, Integer ticketId, String comments, String commentedBy,
			Timestamp commentedAt) {
		super();
		this.commentId = commentId;
		this.ticketId = ticketId;
		this.comments = comments;
		this.commentedBy = commentedBy;
		this.commentedAt = commentedAt;
	}
	public CommentsHistoryDTO() {
		super();
	}
	
	
}
