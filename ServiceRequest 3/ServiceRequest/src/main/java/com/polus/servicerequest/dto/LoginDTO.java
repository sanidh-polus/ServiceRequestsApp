package com.polus.servicerequest.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginDTO {

	private String emailAddress;
	private String password;

	public LoginDTO() {
		super();
	}

	public LoginDTO(String emailAddress, String password) {
		super();
		this.emailAddress = emailAddress;
		this.password = password;
	}

}
