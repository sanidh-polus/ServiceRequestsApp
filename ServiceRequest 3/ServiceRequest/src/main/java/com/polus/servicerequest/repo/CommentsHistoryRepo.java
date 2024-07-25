package com.polus.servicerequest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.polus.servicerequest.entity.CommentsHistory;

public interface CommentsHistoryRepo extends JpaRepository<CommentsHistory,Integer>{
	@Query("SELECT c FROM CommentsHistory c WHERE c.ticket.ticketId=:ticketId")
	List<CommentsHistory> findAllByTicketId(long ticketId);
}
