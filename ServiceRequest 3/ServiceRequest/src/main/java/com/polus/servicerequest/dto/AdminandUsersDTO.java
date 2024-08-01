package com.polus.servicerequest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class AdminandUsersDTO {
	private int id;
	private String firstName;
	private String lastName;
	private String Designation;
	private String email;
	
	public AdminandUsersDTO(int id, String firstName, String lastName, String designation, String email) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		Designation = designation;
		this.email = email;
	}

	public AdminandUsersDTO() {
		super();
	}

	

}
