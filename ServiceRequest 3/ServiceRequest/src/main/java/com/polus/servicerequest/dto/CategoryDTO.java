package com.polus.servicerequest.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CategoryDTO {
	
	private int categoryId;

	
	private String categoryName;

	
	private String categoryDescription;


	private int categoryCreatedBy;

	private Timestamp categoryCreatedAt;

	public CategoryDTO(int categoryId, String categoryName, String categoryDescription, int categoryCreatedBy,
			Timestamp categoryCreatedAt) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.categoryCreatedBy = categoryCreatedBy;
		this.categoryCreatedAt = categoryCreatedAt;
	}

	public CategoryDTO() {
		super();
	}
	

}
