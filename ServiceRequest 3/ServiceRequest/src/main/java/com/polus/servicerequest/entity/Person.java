package com.polus.servicerequest.entity;

import java.util.Date;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "PERSON")
public class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PERSON_ID")
	private int personId;

	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;

	@Column(name = "FULL_NAME")
	private String fullName;
	
	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "EMAIL", unique = true, nullable = false)
	private String emailAddress;

	@Column(name = "PASSWORD")
	private String password;

	@LastModifiedDate
	@Column(name = "CREATED_AT", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;

	@LastModifiedDate
	@Column(name="UPDATED_TIME")
	private Timestamp updatedTime;
	
	@ManyToOne
	@JoinColumn(name = "COUNTRY_CODE", referencedColumnName = "COUNTRY_CODE")
	private Country countryCode;

	@Column(name = "STATE")
	private String state;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE_NO")
	private String phoneNo;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PersonRoles> personRoles = new ArrayList<>();

	

	public Person() {
		super();
	}



	public Person(int personId, String firstName, String lastName, String fullName, String designation,
			String emailAddress, String password, Timestamp createdAt, Timestamp updatedTime, Country countryCode, String state,
			String address, String phoneNo, List<PersonRoles> personRoles) {
		super();
		this.personId = personId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.designation = designation;
		this.emailAddress = emailAddress;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedTime = updatedTime;
		this.countryCode = countryCode;
		this.state = state;
		this.address = address;
		this.phoneNo = phoneNo;
		this.personRoles = personRoles;
	}

	
}
