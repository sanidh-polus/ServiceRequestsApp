package com.polus.servicerequest.entity;

import java.io.Serializable;
import java.sql.Timestamp;
// import java.util.Date;
import java.util.Set;

import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "SR_TICKET_CATEGORY")
public class SrTicketCategory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID")
	private int categoryId;

	@Column(name = "CATEGORY_NAME")
	private String categoryName;

	@Column(name = "CATEGORY_DESCRIPTION")
	private String categoryDescription;

	@ManyToOne
	@JoinColumn(name = "CATEGORY_CREATED_BY",referencedColumnName="PERSON_ID")
	private Person categoryCreatedBy;

	@LastModifiedDate
	@Column(name = "CATEGORY_CREATED_AT")
	private Timestamp categoryCreatedAt;

	@OneToMany(mappedBy = "category")
	private Set<SrTicket> tickets;

	public SrTicketCategory(int categoryId, String categoryName, String categoryDescription, Person categoryCreatedBy,
			Timestamp categoryCreatedAt) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.categoryCreatedBy = categoryCreatedBy;
		this.categoryCreatedAt = categoryCreatedAt;
	}

	public SrTicketCategory() {
		super();
	}

}
