package com.polus.servicerequest.dto;

//import java.sql.Timestamp;

import lombok.Data;

@Data
public class NewServiceCategoryDTO {

	public String categoryName;
	public String categoryDescription;
	public Integer categoryCreatedBy;

	public NewServiceCategoryDTO() {
		super();
	}
	public NewServiceCategoryDTO(String categoryName, String categoryDescription, Integer categoryCreatedBy) {
		super();
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.categoryCreatedBy = categoryCreatedBy;
	}
	
}
