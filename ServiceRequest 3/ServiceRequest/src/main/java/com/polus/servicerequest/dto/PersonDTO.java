package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class PersonDTO {

	private Integer personId;
	private String firstName;
	private String lastName;
	private String designation;
	private String emailAddress;
	private String password;
	private String countryCode;
	private String state;
	private String address;
	private String phoneNo;
	
	public PersonDTO(Integer personId,String firstName, String lastName, String designation, String email, String password,
			String countryCode, String state, String address, String phoneNo) {
		super();
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.designation = designation;
		this.emailAddress = email;
		this.password = password;
		this.countryCode = countryCode;
		this.state = state;
		this.address = address;
		this.phoneNo = phoneNo;
	}

	public PersonDTO() {
		super();
	}
	
	

}
