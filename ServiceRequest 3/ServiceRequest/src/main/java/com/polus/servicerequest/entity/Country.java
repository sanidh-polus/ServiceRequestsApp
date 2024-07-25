package com.polus.servicerequest.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "COUNTRY")
public class Country implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COUNTRY_CODE")
	private String countryCode;

	@Column(name = "COUNTRY_NAME")
	private String countryName;

	@Column(name = "CURRENCY_CODE")
	private String currencyCode;

	@LastModifiedDate
	@Column(name = "UPDATE_TIMESTAMP")
	private Date updatedAt;

	@Column(name = "UPDATE_USER")
	private String updatedBy;
	
	@Column(name="COUNTRY_CODE_ISO2")
	private String countryCodeIso;

	public Country() {
		super();
	}

	public Country(String countryCode, String countryName, String currencyCode, Date updatedAt, String updatedBy) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.currencyCode = currencyCode;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
	}
}
