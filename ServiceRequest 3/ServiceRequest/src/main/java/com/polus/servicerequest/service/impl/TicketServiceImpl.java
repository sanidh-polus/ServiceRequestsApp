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

	public List<TicketStatusDTO> findTicketsForPersonWithStatus(Integer personId, Integer statusId, Integer page,
			Integer size) {
		try {
			List<TicketStatusDTO> response = new ArrayList<>();
			Person adminList = new Person();
			Integer offset = page * size;
			if (statusId != null) {
				List<SrTicket> ticket = srTicketRepository.findTicketsForPersonWithStatus(personId, statusId, size,
						offset);
				
				for (SrTicket tickets : ticket) {
					TicketStatusDTO dto = new TicketStatusDTO();
					dto.setTicketId(tickets.getTicketId());
					dto.setPersonId(tickets.getPerson().getPersonId());
					dto.setCategoryId(tickets.getCategory().getCategoryId());
					dto.setCategoryName(tickets.getCategory().getCategoryName());
					dto.setTicketDescription(tickets.getTicketDescription());
					dto.setTicketCreatedTime(tickets.getTicketCreatedTime());
					dto.setTicketUpdatedAt(tickets.getTicketUpdatedAt());
					if (ServiceTicketConstants.INPROGRESS != (statusId)) {
						adminList = userRepository.findById(tickets.getAssignedTo().getPersonId())
								.orElseThrow(() -> new RuntimeException("Not found"));
						dto.setAssignedTo(getAdminDetails(adminList));
					}
					if (statusId == ServiceTicketConstants.APPROVED || statusId == ServiceTicketConstants.REJECTED) {
						List<CommentsHistory> comments = commentsHistoryRepo.findAllByTicketId(tickets.getTicketId());
						dto.setTicketComments(getAllComments(comments, adminList));
					}
					response.add(dto);
				}
			}
			return response;
		} catch (Exception e) {
			logger.error("Error finding tickets for personId: {} and statusId: {}", personId, statusId, e);
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
			if (status.getStatusId() == ServiceTicketConstants.ASSIGNED) {
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

	public ResponseEntity<Object> makeAnUserAdmin(Integer adminId, Integer personId) {
		try {
		PersonRoles admin = personRolesRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("person not found"));
		PersonRoles person = personRolesRepository.findById(personId)
				.orElseThrow(() -> new RuntimeException("person not found"));
		if (admin.getRole().getRoleId() == SignupAndLoginConstants.ADMINISTRATOR) {
			if (person.getRole().getRoleId() != SignupAndLoginConstants.ADMINISTRATOR) {
				PersonRoles personrole = new PersonRoles();
				personrole.setPerson(person.getPerson());
				personrole.setRole(rolesRepository.findById(SignupAndLoginConstants.ADMINISTRATOR).orElseThrow());
				personrole.setUpdatedTime(Timestamp.from(Instant.now()));
				personrole.setUpdatedBy(adminId);
				personRolesRepository.save(personrole);
			}
			return ResponseEntity.ok().body(Map.of("message", "status updated"));
		} else {
			return ResponseEntity.ok().body(Map.of("message", "The person is not an admin"));
		}
		}catch(Exception e) {
			logger.error("something went wrong{}",e.getMessage());
			throw e;
		}

	}

	public ResponseEntity<Object> getCountOfTicketStatus(int personId, int statusId) {
		try {
			int count = srTicketRepository.GetCountOfTickets(statusId, personId);
			CountDTO counts = new CountDTO(count);
			return ResponseEntity.ok(counts);
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
		PersonRoles admin = personRolesRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("person  not found"));
		personRolesRepository.findById(personId).orElseThrow(() -> new RuntimeException("person not found"));
		if (admin.getRole().getRoleId() == SignupAndLoginConstants.ADMINISTRATOR) {
			personRolesRepository.deleteByPersonIdAndRoleId(personId);
			return ResponseEntity.ok().body(Map.of("message", "status updated"));
		} else {
			return ResponseEntity.ok().body(Map.of("message", "The person is not an admin"));
		}
		}catch(Exception e) {
			throw e;
		}
	}

	public List<TicketStatusDTO> assignedToAnAdminTickets(AssignedToAdminTicketsDTO assignedToAdminTicketsDTO,
			Integer page, Integer size) {
		try {
			List<TicketStatusDTO> response = new ArrayList<>();
			Person adminList = new Person();
			Integer offset = page * size;

			if (assignedToAdminTicketsDTO.statusId != null) {
				List<SrTicket> ticket = srTicketRepository.findTicketsAssignedToAnAdmin(
						assignedToAdminTicketsDTO.assignedTo, assignedToAdminTicketsDTO.statusId, size, offset);
				for (SrTicket tickets : ticket) {
					TicketStatusDTO dto = new TicketStatusDTO();
					dto.setTicketId(tickets.getTicketId());
					dto.setPersonId(tickets.getPerson().getPersonId());
					dto.setCategoryId(tickets.getCategory().getCategoryId());
					dto.setCategoryName(tickets.getCategory().getCategoryName());
					dto.setTicketDescription(tickets.getTicketDescription());
					dto.setTicketCreatedTime(tickets.getTicketCreatedTime());
					dto.setTicketUpdatedAt(tickets.getTicketUpdatedAt());
					if (ServiceTicketConstants.INPROGRESS != (assignedToAdminTicketsDTO.statusId)) {
						adminList = userRepository.findById(tickets.getAssignedTo().getPersonId())
								.orElseThrow(() -> new RuntimeException("Not found"));
						dto.setAssignedTo(getAdminDetails(adminList));
					}
					if (assignedToAdminTicketsDTO.statusId == ServiceTicketConstants.APPROVED
							|| assignedToAdminTicketsDTO.statusId == ServiceTicketConstants.REJECTED) {
						List<CommentsHistory> comments = commentsHistoryRepo.findAllByTicketId(tickets.getTicketId());
						dto.setTicketComments(getAllComments( comments, adminList));
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

	private AdminandUsersDTO getAdminDetails(Person adminlist) {
		AdminandUsersDTO list = new AdminandUsersDTO();
		list.setId(adminlist.getPersonId());
		list.setFirstName(adminlist.getFirstName());
		list.setLastName(adminlist.getLastName());
		list.setDesignation(adminlist.getDesignation());
		list.setEmail(adminlist.getEmailAddress());
		return (list);
	}

	private List<CommentsHistoryDTO> getAllComments(List<CommentsHistory> comments, Person adminList) {
		List<CommentsHistoryDTO> commentsDtoList = new ArrayList<>();
		for (CommentsHistory comment : comments) { 
			CommentsHistoryDTO commentDto = new CommentsHistoryDTO();
			commentDto.setCommentId(comment.getCommentId());
			commentDto.setComments(comment.getComments());
			commentDto.setCommentedBy(adminList.getFirstName() + adminList.getLastName());
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
