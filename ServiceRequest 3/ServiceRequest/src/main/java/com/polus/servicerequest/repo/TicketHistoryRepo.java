package com.polus.servicerequest.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polus.servicerequest.entity.TicketHistory;

import jakarta.transaction.Transactional;

public interface TicketHistoryRepo extends JpaRepository<TicketHistory,Integer>{

	@Transactional
	void deleteByTicketTicketId(int ticketId);

}
