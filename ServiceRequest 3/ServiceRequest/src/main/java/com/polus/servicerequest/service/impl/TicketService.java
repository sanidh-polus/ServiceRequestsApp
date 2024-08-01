package com.polus.servicerequest.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.polus.servicerequest.dto.AddOrRemoveAdminDTO;
import com.polus.servicerequest.dto.AdminAssignDTO;
import com.polus.servicerequest.dto.AdminandUsersDTO;
import com.polus.servicerequest.dto.AssignedToAdminTicketsDTO;
//import com.polus.servicerequest.dto.CategoryDTO;
import com.polus.servicerequest.dto.ChangeStatusDTO;
//import com.polus.servicerequest.dto.CountDTO;
import com.polus.servicerequest.dto.NewServiceCategoryDTO;
import com.polus.servicerequest.dto.TicketStatusDTO;
import com.polus.servicerequest.dto.TicketsDTO;
import com.polus.servicerequest.dto.SrTicketDTO;
//import com.polus.servicerequest.entity.SrTicketCategory;

public interface TicketService {

	ResponseEntity<Object> getAllCategories();

	ResponseEntity<Object> DeleteInprogressTickets(int ticketId);

	ResponseEntity<Object> createOrUpdateTicket(SrTicketDTO srTicketDTO);

	List<AdminandUsersDTO> getAllPersonByRole(Integer roleId);

	List<TicketStatusDTO> findTicketsForPersonWithStatus(TicketsDTO ticketsDTO);
	
	ResponseEntity<Object> assignAdminToTicket(AdminAssignDTO adminAssignDTO);

	ResponseEntity<Object> approveOrRejectTicket(ChangeStatusDTO changeStatusDTO);

	ResponseEntity<Object> makeAnUserAdmin(AddOrRemoveAdminDTO addOrRemoveAdminDTO);
	
	ResponseEntity<Object> getCountOfTicketStatus(int personId);

	ResponseEntity<Object> addNewServiceCategory(NewServiceCategoryDTO newServiceCategoryDTO);

	List<TicketStatusDTO> ticketsHandledByAnAdmin(AssignedToAdminTicketsDTO assignedToAdminTicketsDTO);

	ResponseEntity<Object> removeAnAdmin(Integer adminId, Integer personId);

	

}
