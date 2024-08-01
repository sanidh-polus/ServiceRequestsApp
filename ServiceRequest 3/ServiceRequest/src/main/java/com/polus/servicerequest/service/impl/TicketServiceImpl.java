package com.polus.servicerequest.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.polus.servicerequest.constant.ServiceTicketConstants;
import com.polus.servicerequest.constant.SignupAndLoginConstants;
import com.polus.servicerequest.dto.AddOrRemoveAdminDTO;
import com.polus.servicerequest.dto.AdminAssignDTO;
import com.polus.servicerequest.dto.AdminandUsersDTO;
import com.polus.servicerequest.dto.AssignedToAdminTicketsDTO;
import com.polus.servicerequest.dto.CategoryDTO;
import com.polus.servicerequest.dto.ChangeStatusDTO;
import com.polus.servicerequest.dto.CommentsHistoryDTO;
import com.polus.servicerequest.dto.CountDTO;
import com.polus.servicerequest.dto.NewServiceCategoryDTO;
import com.polus.servicerequest.dto.SrTicketDTO;
import com.polus.servicerequest.dto.TicketStatusDTO;
import com.polus.servicerequest.dto.TicketsDTO;
import com.polus.servicerequest.entity.CommentsHistory;
import com.polus.servicerequest.entity.Person;
import com.polus.servicerequest.entity.PersonRoles;
import com.polus.servicerequest.entity.SrTicket;
import com.polus.servicerequest.entity.SrTicketCategory;
import com.polus.servicerequest.entity.SrTicketStatus;
import com.polus.servicerequest.entity.TicketHistory;
import com.polus.servicerequest.repo.CommentsHistoryRepo;
import com.polus.servicerequest.repo.PersonRolesRepository;
import com.polus.servicerequest.repo.RolesRepository;
import com.polus.servicerequest.repo.SrTicketCategoryRepo;
import com.polus.servicerequest.repo.SrTicketRepo;
import com.polus.servicerequest.repo.SrTicketStatusRepo;
import com.polus.servicerequest.repo.TicketHistoryRepo;
import com.polus.servicerequest.repo.UserRepository;

@Service
public class TicketServiceImpl implements TicketService {

	private Logger logger = LogManager.getLogger(TicketServiceImpl.class);

	@Autowired
	private SrTicketRepo srTicketRepo;

	@Autowired
	private TicketHistoryRepo ticketHistoryRepo;

	@Autowired
	private SrTicketCategoryRepo categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SrTicketStatusRepo srTicketStatusRepository;

	@Autowired
	private SrTicketRepo srTicketRepository;

	@Autowired
	private TicketHistoryRepo ticketHistoryRepository;

	@Autowired
	CommentsHistoryRepo commentsHistoryRepository;

	@Autowired
	PersonRolesRepository personRolesRepository;

	@Autowired
	RolesRepository rolesRepository;

	@Autowired
	SrTicketCategoryRepo srTicketCategoryRepo;

	@Autowired
	CommentsHistoryRepo commentsHistoryRepo;

	public ResponseEntity<Object> getAllCategories() {
		try {
			List<SrTicketCategory> categories = categoryRepository.findAll();
			List<CategoryDTO> categorylist = categories.stream()
					.map(category -> new CategoryDTO(category.getCategoryId(), category.getCategoryName(),
							category.getCategoryDescription(), category.getCategoryCreatedBy().getPersonId(),
							category.getCategoryCreatedAt()))
					.collect(Collectors.toList());
			if (categorylist.isEmpty()) {
				return ResponseEntity.badRequest().body(Map.of("message", "no categories available"));
			}

			return ResponseEntity.ok(categorylist);
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw e;
		}
	}

	public List<AdminandUsersDTO> getAllPersonByRole(Integer roleId) {
		try {
			List<Person> persons = null;
			if (roleId == SignupAndLoginConstants.ADMINISTRATOR) {
				persons = userRepository.findAllByRoleId();
			}
			if (roleId == SignupAndLoginConstants.INVESTIGATOR) {
				persons = userRepository.GetAllInvestigators();
			}
			return persons.stream()
					.map(person -> new AdminandUsersDTO(person.getPersonId(), person.getFirstName(),
							person.getLastName(), person.getDesignation(), person.getEmailAddress()))
					.collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Something went wrong {}", e.getMessage());
			throw e;
		}
	}

	public List<TicketStatusDTO> findTicketsForPersonWithStatus(TicketsDTO ticketsDTO) {
		try {
			List<TicketStatusDTO> response = new ArrayList<>();
			Person adminList = new Person();
			Integer offset = ticketsDTO.getPage() * ticketsDTO.getSize();
			if (ticketsDTO.getStatusId() != null) {
				List<SrTicket> ticket = srTicketRepository.findTicketsForPersonWithStatus(ticketsDTO.getPersonId(),
						ticketsDTO.getStatusId(), ticketsDTO.getSize(), offset);
 
				for (SrTicket tickets : ticket) {
					TicketStatusDTO dto = new TicketStatusDTO();
					dto.setTicketId(tickets.getTicketId());
					dto.setPersonId(tickets.getPerson().getPersonId());
					dto.setCategoryId(tickets.getCategory().getCategoryId());
					dto.setCategoryName(tickets.getCategory().getCategoryName());
					dto.setTicketDescription(tickets.getTicketDescription());
					dto.setTicketCreatedTime(tickets.getTicketCreatedTime());
					dto.setTicketUpdatedAt(tickets.getTicketUpdatedAt());
					if (ServiceTicketConstants.INPROGRESS != (ticketsDTO.getStatusId())) {
						adminList = userRepository.findById(tickets.getAssignedTo().getPersonId())
								.orElseThrow(() -> new RuntimeException("Not found"));
						dto.setAssignedTo(getAdminDetails(tickets));
					}
					if (ticketsDTO.getStatusId() == ServiceTicketConstants.APPROVED
							|| ticketsDTO.getStatusId() == ServiceTicketConstants.REJECTED) {
						List<CommentsHistory> comments = commentsHistoryRepo.findAllByTicketId(tickets.getTicketId());
						dto.setTicketComments(getAllComments(comments, tickets));
					}
					response.add(dto);
				}
			}
			return response;
		} catch (Exception e) {
			logger.error("Error finding tickets for personId: {} and statusId: {}", ticketsDTO.getPersonId(),
					ticketsDTO.getStatusId(), e);
			throw e;
		}
	}

	public ResponseEntity<Object> createOrUpdateTicket(SrTicketDTO srTicketDTO) {
		try {
			Person person = userRepository.findById(srTicketDTO.getPersonId())
					.orElseThrow(() -> new RuntimeException("Person not found"));
			SrTicketCategory category = categoryRepository.findById(srTicketDTO.getCategoryId())
					.orElseThrow(() -> new RuntimeException("Category not found"));
			SrTicketStatus status = srTicketStatusRepository.findById(ServiceTicketConstants.INPROGRESS)
					.orElseThrow(() -> new RuntimeException("Status not found"));

			if (srTicketDTO.getTicketId() == null) {
				SrTicket srTicket = new SrTicket();
				srTicket.setPerson(person);
				srTicket.setCategory(category);
				srTicket.setTicketDescription(srTicketDTO.getTicketDescription());
				srTicket.setStatus(status);
				srTicket.setAssignedTo(null);
				srTicket.setTicketCreatedTime(Timestamp.from(Instant.now()));
				srTicket.setTicketUpdatedAt(Timestamp.from(Instant.now()));
				SrTicket savedTicket = srTicketRepository.save(srTicket);
				ticketHistoryRepository.save(setTicketHistory(savedTicket, person, status));
				return ResponseEntity.ok().body(Map.of("message", "Ticket created"));
			} else {			
				SrTicket ticket = srTicketRepository.findById(srTicketDTO.getTicketId())
						.orElseThrow(() -> new RuntimeException("Invalid ticketid"));
				ticket.setCategory(category);
				ticket.setTicketDescription(srTicketDTO.getTicketDescription());
				ticket.setTicketUpdatedAt(Timestamp.from(Instant.now()));
				srTicketRepository.save(ticket);
				
				ticketHistoryRepository.save(setTicketHistory(ticket,ticket.getPerson(),ticket.getStatus()));
				return ResponseEntity.ok().body(Map.of("message",ticket.getTicketUpdatedAt()));
			}
		} catch (Exception e) {
			logger.error("something went wrong{}", e.getMessage());
			return ResponseEntity.badRequest().body(Map.of("message", "Creation Failed"));
		}
	}

	public ResponseEntity<Object> DeleteInprogressTickets(int ticketId) {
		try {
			ticketHistoryRepo.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket not found"));
			ticketHistoryRepo.deleteByTicketTicketId(ticketId);
			srTicketRepo.deleteById(ticketId);
			return ResponseEntity.ok().body(Map.of("message","Ticket deleted"));
		} catch (Exception e) {
			logger.error("Error deleting ticket with ID: {}", ticketId, e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Failed to delete ticket. Please try again later.");
		}
	}

	public ResponseEntity<Object> assignAdminToTicket(AdminAssignDTO adminAssignDTO) { 
		try {
			SrTicket ticket = srTicketRepo.findById(adminAssignDTO.getTicketId())
					.orElseThrow(() -> new RuntimeException("Ticket not found"));
			
			Person assignedTo = userRepository.findById(adminAssignDTO.getAssignedTo())
					.orElseThrow(() -> new RuntimeException("Assigned person not found"));
			
			SrTicketStatus status = srTicketStatusRepository.findById(ServiceTicketConstants.ASSIGNED)
					.orElseThrow(() -> new RuntimeException("Status not found"));
			
			ticket.setStatus(status);
			ticket.setAssignedTo(assignedTo);
			ticket.setTicketUpdatedAt(Timestamp.from(Instant.now()));
			srTicketRepository.save(ticket);
			ticketHistoryRepository.save(setTicketHistory(ticket,ticket.getPerson(), status));
			
			return ResponseEntity.ok().body(Map.of("admin",assignedTo.getPersonId()));
		} catch (Exception e) {
			logger.info("error in assigning admin{}", e);
			return ResponseEntity.badRequest().body(Map.of("Message", "admin assign failed"));
		}
	}

	public ResponseEntity<Object> approveOrRejectTicket(ChangeStatusDTO changeStatusDTO) {
		try {
			SrTicket ticket = srTicketRepo.findById(changeStatusDTO.getTicketId())
					.orElseThrow(() -> new RuntimeException("ticket not found"));
			Person assignedTo = userRepository.findById(changeStatusDTO.getAssignedTo())
					.orElseThrow(() -> new RuntimeException("Assigned person not found"));
			SrTicketStatus status = srTicketStatusRepository.findById(changeStatusDTO.getStatusId())
					.orElseThrow(() -> new RuntimeException("Status not found"));
			if (ticket.getStatus().getStatusId() == ServiceTicketConstants.ASSIGNED) {
				ticket.setStatus(status);
				ticket.setTicketUpdatedAt(Timestamp.from(Instant.now()));
				srTicketRepository.save(ticket);
				ticketHistoryRepository.save(setTicketHistory(ticket,ticket.getPerson(), status));
				CommentsHistory comment = new CommentsHistory();
				comment.setTicket(ticket);
				comment.setStatus(status);
				comment.setComments(changeStatusDTO.getComments());
				comment.setCommentedBy(assignedTo.getPersonId());
				comment.setCommentedAt(Timestamp.from(Instant.now()));
				commentsHistoryRepository.save(comment);
				return ResponseEntity.ok().body(Map.of("message", "status updated"));
			} else {
				return ResponseEntity.badRequest().body(Map.of("Message", "updation failed"));
			}
		} catch (Exception e) {
			logger.info("error in assigning admin", e.getMessage());
			return ResponseEntity.badRequest().body(Map.of("Message", "updation failed"));
		}

	}

	public ResponseEntity<Object> makeAnUserAdmin(AddOrRemoveAdminDTO addOrRemoveAdminDTO) {
		try {
			PersonRoles admin = personRolesRepository.findAdminByPersonId(addOrRemoveAdminDTO.getAdminId());
			if (admin == null) {
				throw new RuntimeException("Person not found");
			}
			PersonRoles person = personRolesRepository.findPersonByPersonId(addOrRemoveAdminDTO.getPersonId(),SignupAndLoginConstants.INVESTIGATOR);
			if (person == null) {
				throw new RuntimeException("Person not found");
			}
				if (!(personRolesRepository.existsByPersonIdAndRoleId(addOrRemoveAdminDTO.getPersonId(), SignupAndLoginConstants.ADMINISTRATOR))) {
					PersonRoles personrole = new PersonRoles();
					personrole.setPerson(person.getPerson());
					personrole.setRole(rolesRepository.findById(SignupAndLoginConstants.ADMINISTRATOR).orElseThrow());
					personrole.setUpdatedTime(Timestamp.from(Instant.now()));
					personrole.setUpdatedBy(addOrRemoveAdminDTO.getAdminId());
					personRolesRepository.save(personrole);
					return ResponseEntity.ok().body(Map.of("message", "status updated"));
				} else {
					return ResponseEntity.ok().body(Map.of("message", "person is already an admin"));
				}
		} catch (Exception e) {
			logger.error("something went wrong{}", e.getMessage());
			throw e;
		}
 
	}

	public ResponseEntity<Object> getCountOfTicketStatus(int personId) {
		try {
			CountDTO count = new CountDTO();
				count.setInProgressCount(srTicketRepository.GetCountOfTickets(ServiceTicketConstants.INPROGRESS,
						personId));
				count.setAssignedCount(srTicketRepository.GetCountOfTickets(ServiceTicketConstants.ASSIGNED,
						personId));
				count.setApprovedCount(srTicketRepository.GetCountOfTickets(ServiceTicketConstants.APPROVED,
						personId));
				count.setRejectedCount(srTicketRepository.GetCountOfTickets(ServiceTicketConstants.REJECTED,
						personId));
				count.setAssignedToMeCount(srTicketRepository.GetCountOfTicketsHandledByAnAdmin(
						ServiceTicketConstants.ASSIGNED, personId));
				count.setApprovedByMeCount(srTicketRepository.GetCountOfTicketsHandledByAnAdmin(
						ServiceTicketConstants.APPROVED, personId));
				count.setRejectedByMeCount(srTicketRepository.GetCountOfTicketsHandledByAnAdmin(
						ServiceTicketConstants.REJECTED, personId));
			return ResponseEntity.ok(count);
		} catch (Exception e) {
			logger.info("something went wrong:" + e);
			throw e;
		}
	}

	public ResponseEntity<Object> addNewServiceCategory(NewServiceCategoryDTO newServiceCategoryDTO) {
		Person person = userRepository.findById(newServiceCategoryDTO.getCategoryCreatedBy())
				.orElseThrow(() -> new RuntimeException("person not found"));
		try {
			SrTicketCategory service = new SrTicketCategory();
			service.setCategoryName(newServiceCategoryDTO.getCategoryName());
			service.setCategoryDescription(newServiceCategoryDTO.getCategoryDescription());
			service.setCategoryCreatedBy(person);
			service.setCategoryCreatedAt(Timestamp.from(Instant.now()));
			srTicketCategoryRepo.save(service);
			return ResponseEntity.ok().body(Map.of("message", "status updated"));
		} catch (Exception e) { 
			logger.error("something went wrong{}",e.getMessage());
			return ResponseEntity.ok().body(Map.of("message", "error in adding new category"));
		}
	}

	public ResponseEntity<Object> removeAnAdmin(Integer adminId, Integer personId) {
		try {
			PersonRoles admin = personRolesRepository.findAdminByPersonId(adminId);
			if (admin == null) {
				throw new RuntimeException("Person not found");
			}
			if (personRolesRepository.findPersonByPersonId(personId,SignupAndLoginConstants.ADMINISTRATOR) == null) {
				throw new RuntimeException("Person is not an admin");
			}
			personRolesRepository.deleteByPersonIdAndRoleId(personId);
			return ResponseEntity.ok().body(Map.of("message", "admin removed"));		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "error in removing admin"));
		}
	}

	public List<TicketStatusDTO> ticketsHandledByAnAdmin(AssignedToAdminTicketsDTO assignedToAdminTicketsDTO) {
		try {
			List<TicketStatusDTO> response = new ArrayList<>();
			Integer offset = assignedToAdminTicketsDTO.page * assignedToAdminTicketsDTO.size;
 
			if (assignedToAdminTicketsDTO.statusId != null) {
				List<SrTicket> tickets = srTicketRepository.findTicketsAssignedToAnAdmin(
						assignedToAdminTicketsDTO.assignedTo, assignedToAdminTicketsDTO.statusId,
						assignedToAdminTicketsDTO.size, offset);
				for (SrTicket ticket : tickets) {
					TicketStatusDTO dto = new TicketStatusDTO();
					dto.setTicketId(ticket.getTicketId());
					dto.setPersonId(ticket.getPerson().getPersonId());
					dto.setFullName(ticket.getPerson().getFirstName() + ' ' + ticket.getPerson().getLastName());
					dto.setCategoryId(ticket.getCategory().getCategoryId());
					dto.setCategoryName(ticket.getCategory().getCategoryName());
					dto.setTicketDescription(ticket.getTicketDescription());
					dto.setTicketCreatedTime(ticket.getTicketCreatedTime());
					dto.setTicketUpdatedAt(ticket.getTicketUpdatedAt());
					if (ServiceTicketConstants.INPROGRESS != (assignedToAdminTicketsDTO.statusId)) {
						dto.setAssignedTo(getAdminDetails(ticket));
					}
					if (assignedToAdminTicketsDTO.statusId == ServiceTicketConstants.APPROVED
							|| assignedToAdminTicketsDTO.statusId == ServiceTicketConstants.REJECTED) {
						List<CommentsHistory> comments = commentsHistoryRepo.findAllByTicketId(ticket.getTicketId());
						dto.setTicketComments(getAllComments(comments, ticket));
					}
					response.add(dto);
				}
			}
			return response;
 
		} catch (Exception e) {
			logger.error("Error finding tickets for assignedTo: {} and statusId: {}",
					assignedToAdminTicketsDTO.assignedTo, assignedToAdminTicketsDTO.statusId, e);
			throw e;
		}
	}

	private AdminandUsersDTO getAdminDetails(SrTicket ticket) {
		AdminandUsersDTO list = new AdminandUsersDTO();
		list.setId(ticket.getAssignedTo().getPersonId());
		list.setFirstName(ticket.getAssignedTo().getFirstName());
		list.setLastName(ticket.getAssignedTo().getLastName());
		list.setDesignation(ticket.getAssignedTo().getDesignation());
		list.setEmail(ticket.getAssignedTo().getEmailAddress());
		return (list);
	}
 
	private List<CommentsHistoryDTO> getAllComments(List<CommentsHistory> comments, SrTicket tickets) {
		List<CommentsHistoryDTO> commentsDtoList = new ArrayList<>();
		for (CommentsHistory comment : comments) {
			CommentsHistoryDTO commentDto = new CommentsHistoryDTO();
			commentDto.setCommentId(comment.getCommentId());
			commentDto.setComments(comment.getComments());
			commentDto.setCommentedBy(tickets.getAssignedTo().getFullName());
			commentDto.setTicketId(comment.getTicket().getTicketId());
			commentDto.setCommentedAt(comment.getCommentedAt());
			commentsDtoList.add(commentDto);
		}
		return commentsDtoList;
	}
 

	private TicketHistory setTicketHistory(SrTicket savedTicket, Person person, SrTicketStatus status) {
		TicketHistory ticketHistory = new TicketHistory(); 
		ticketHistory.setTicket(savedTicket);
		ticketHistory.setUpdatedBy(person);
		ticketHistory.setUpdatedTime(Timestamp.from(Instant.now()));
		ticketHistory.setStatus(status);
		return ticketHistory;
	}

	
}
