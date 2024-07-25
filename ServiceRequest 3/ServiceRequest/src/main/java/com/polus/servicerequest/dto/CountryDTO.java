package com.polus.servicerequest.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CountryDTO {

	private String countryCode;
	private String countryName;
	private String currencyCode;
	private Date updatedAt;
	private String updatedBy;
	private String countryCodeIso;
	
	public CountryDTO(String countryCode, String countryName, String currencyCode, Date updatedAt, String updatedBy,
			String countryCodeIso) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.currencyCode = currencyCode;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.countryCodeIso = countryCodeIso;
	}
	

}
