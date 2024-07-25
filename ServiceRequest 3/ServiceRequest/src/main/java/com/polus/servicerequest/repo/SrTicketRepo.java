package com.polus.servicerequest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.polus.servicerequest.entity.SrTicket;

public interface SrTicketRepo extends JpaRepository<SrTicket, Integer> {

	@Query(value = "SELECT * FROM SR_TICKET t " + "WHERE t.PERSON_ID = :personId AND t.STATUS_ID = :statusId "
			+ "LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<SrTicket> findTicketsForPersonWithStatus(int personId, int statusId, Integer limit, Integer offset);

	@Query(value="SELECT COUNT(*) FROM SR_TICKET t" + " WHERE t.STATUS_ID=:statusId AND t.PERSON_Id=:personId",nativeQuery=true)
	int GetCountOfTickets(int statusId, int personId);

	@Query(value = "SELECT * FROM SR_TICKET t " + "WHERE t.TICKET_ASSIGNED_TO = :assignedTo AND t.STATUS_ID = :statusId "
			+ "LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<SrTicket> findTicketsAssignedToAnAdmin(Integer assignedTo, Integer statusId, Integer limit, Integer offset);

}
