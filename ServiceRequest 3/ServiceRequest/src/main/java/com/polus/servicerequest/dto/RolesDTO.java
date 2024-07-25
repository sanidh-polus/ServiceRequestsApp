package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class RolesDTO {
	private int roleId;
	private String roleName;
	private String roleDescription;

	public RolesDTO() {
		super();
	}

	public RolesDTO(int roleid, String rolename, String roledescription) {
		super();
		this.roleId = roleid;
		this.roleName = rolename;
		this.roleDescription = roledescription;
	}
}
