package com.polus.servicerequest.dto;

import lombok.Data;

@Data
public class CountDTO {

	private int inProgressCount;
	private int rejectedCount;
	private int approvedCount;
	private int assignedCount;
	private int assignedToMeCount;
	private int approvedByMeCount;
	private int rejectedByMeCount;
	 
	public CountDTO(int inProgressCount, int rejectedCount, int approvedCount, int assignedCount, 
			int assignedToMeCount, int approvedByMeCount, int rejectedByMeCount) {
		super();
		this.inProgressCount = inProgressCount;
		this.rejectedCount = rejectedCount;
		this.approvedCount = approvedCount;
		this.assignedCount = assignedCount;
		this.assignedToMeCount = assignedToMeCount;
		this.approvedByMeCount = approvedByMeCount;
		this.rejectedByMeCount = rejectedByMeCount;
	}
	public CountDTO() {
		super();
	}
	 
}
