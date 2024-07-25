package com.polus.servicerequest.dto;


import java.util.Date;
import java.util.List;

import com.polus.servicerequest.entity.Country;

import lombok.Data;

@Data
public class LoginResponseDTO {
	private int personid;
	private String firstName;
	private String lastName;
	private String designation;
	private String email;
	private Date createdAt;
	private Country country;
	private String state;
	private String address;
	private String phone_no;
	private List<RolesDTO> roles;
	
}
