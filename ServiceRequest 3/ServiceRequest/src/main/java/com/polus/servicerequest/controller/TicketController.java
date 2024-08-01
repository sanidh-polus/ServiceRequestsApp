package com.polus.servicerequest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polus.servicerequest.dto.AdminandUsersDTO;
import com.polus.servicerequest.dto.AssignedToAdminTicketsDTO;
//import com.polus.servicerequest.dto.CategoryDTO;
import com.polus.servicerequest.dto.ChangeStatusDTO;
//import com.polus.servicerequest.dto.CountDTO;
import com.polus.servicerequest.dto.NewServiceCategoryDTO;
import com.polus.servicerequest.dto.AddOrRemoveAdminDTO;
import com.polus.servicerequest.dto.AdminAssignDTO;
import com.polus.servicerequest.dto.TicketStatusDTO;
import com.polus.servicerequest.dto.TicketsDTO;
import com.polus.servicerequest.dto.SrTicketDTO;
//import com.polus.servicerequest.entity.Person;
//import com.polus.servicerequest.entity.SrTicketCategory;
//import com.polus.servicerequest.repo.UserRepository;
import com.polus.servicerequest.service.impl.TicketService;

@RestController
@RequestMapping("/homepage")

public class TicketController {

	@Autowired
	private TicketService ticketService;

	@GetMapping("/category")
	public ResponseEntity<Object> getAllCategories() {
		try {
			return ticketService.getAllCategories();

		} catch (Exception e) {
			return new ResponseEntity<>("An unexpected error occurred. Please try again later.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/roles/{roleId}")
	public ResponseEntity<Object> getPersonsByRole(@PathVariable Integer roleId) {
		try {
			List<AdminandUsersDTO> admin = ticketService.getAllPersonByRole(roleId);
			return ResponseEntity.ok(admin);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}

	}

	@PostMapping("/create")
	public ResponseEntity<Object> createTicket(@RequestBody SrTicketDTO srTicketDTO) {
		try {
			return ticketService.createOrUpdateTicket(srTicketDTO);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping("/tickets")
	public ResponseEntity<Object> getTicketsByPersonIdAndStatusId(@RequestBody TicketsDTO ticketsDTO) {
		try {
			List<TicketStatusDTO> tickets = ticketService.findTicketsForPersonWithStatus(ticketsDTO);
			return ResponseEntity.ok(tickets);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
		}
 
	}

	@DeleteMapping("/delete/{ticketId}")
	public ResponseEntity<Object> DeletInProgressTickets(@PathVariable int ticketId) {
		try {
			return ticketService.DeleteInprogressTickets(ticketId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to delete ticket. Please try again later.");
		}
	}

	@PutMapping("/assign")
	public ResponseEntity<Object> assignAdminToTicket(@RequestBody AdminAssignDTO adminAssignDTO) {
		try {
			return ticketService.assignAdminToTicket(adminAssignDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign an admin");
		}

	}

	@PostMapping("/updatestatus")
	public ResponseEntity<Object> approveOrRejectTicket(@RequestBody ChangeStatusDTO changeStatusDTO) {
		try {
			return ticketService.approveOrRejectTicket(changeStatusDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Something went wrong. please try again later");
		}
	}

	@PostMapping("/makeadmin")
	public ResponseEntity<Object> makeAnUserAdmin(@RequestBody AddOrRemoveAdminDTO addOrRemoveAdminDTO) {
		try {
			return ticketService.makeAnUserAdmin(addOrRemoveAdminDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("something went wrong. please try again later");
		}
 
	}

	@GetMapping("/count/{personId}")
	public ResponseEntity<Object> getCountOfTicketStatus(@PathVariable Integer personId) {
		try {
			return ticketService.getCountOfTicketStatus(personId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong");
		}
	}

	@PostMapping("/addservice")
	public ResponseEntity<Object> addNewServiceCategory(@RequestBody NewServiceCategoryDTO newServiceCategoryDTO) {
		try {
			return ticketService.addNewServiceCategory(newServiceCategoryDTO);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("something went wrong. please try again later");
		}

	}

	@DeleteMapping("/removeadmin/{adminId}/{personId}")
	public ResponseEntity<Object> removeAnAdmin(@PathVariable Integer adminId, @PathVariable Integer personId) {
		try {
			return ticketService.removeAnAdmin(adminId, personId);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(e.getMessage());
		}
 
	}

	@PostMapping("/assignedtoadmin")
	public ResponseEntity<Object> ticketsHandledByAnAdmin(
			@RequestBody AssignedToAdminTicketsDTO assignedToAdminTicketsDTO) {
		try {
			List<TicketStatusDTO> tickets = ticketService.ticketsHandledByAnAdmin(assignedToAdminTicketsDTO);
			return ResponseEntity.ok(tickets);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
