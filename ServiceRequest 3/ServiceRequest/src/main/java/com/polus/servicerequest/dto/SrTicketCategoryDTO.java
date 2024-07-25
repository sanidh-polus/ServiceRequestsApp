package com.polus.servicerequest.dto;

import java.util.Date;

import lombok.Data;

@Data
public class SrTicketCategoryDTO {

	private int categoryId;
	private String categoryName;
	private String categoryDescription;
	private String createdBy;
	private Date createdTime;
	
	public SrTicketCategoryDTO(int categoryId, String categoryName, String categoryDescription, String createdBy,
			Date createdTime) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
	}
	
	
}
