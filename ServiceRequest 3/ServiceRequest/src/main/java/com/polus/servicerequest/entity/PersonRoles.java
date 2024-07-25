package com.polus.servicerequest.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PERSON_ROLES")
public class PersonRoles implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PERSON_ROLES_ID")
	private int id;

	@ManyToOne
	@JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", referencedColumnName = "ROLE_ID")
	private Roles role;
	
	@LastModifiedDate
	@Column(name="UPDATED_TIME")
	private Timestamp updatedTime;
	
	
	@Column(name = "UPDATED_BY")
	private Integer updatedBy;
	
	

	public PersonRoles() {
		super();
	}


	public int getId() {
		return id;
	}
}
