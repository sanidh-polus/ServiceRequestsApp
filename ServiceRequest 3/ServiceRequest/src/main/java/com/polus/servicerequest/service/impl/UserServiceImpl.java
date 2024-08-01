package com.polus.servicerequest.service.impl;

import java.sql.Timestamp;
import java.time.Instant;
// import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.polus.servicerequest.constant.SignupAndLoginConstants;
import com.polus.servicerequest.dto.LoginResponseDTO;
import com.polus.servicerequest.dto.PersonDTO;
import com.polus.servicerequest.dto.RolesDTO;
import com.polus.servicerequest.entity.Country;
import com.polus.servicerequest.entity.Person;
import com.polus.servicerequest.entity.PersonRoles;
import com.polus.servicerequest.exception.InvalidCredentialsException;
import com.polus.servicerequest.repo.CountryRepository;
import com.polus.servicerequest.repo.RolesRepository;
import com.polus.servicerequest.repo.UserRepository;
import com.polus.servicerequest.repo.PersonRolesRepository;

@Service

public class UserServiceImpl implements UserService {

	private Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PersonRolesRepository personRolesRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private CountryRepository countryRepository;

	public ResponseEntity<Object> signup(PersonDTO personDTO) {
		try {
			Optional<Country> country = countryRepository.findById(personDTO.getCountryCode());

			Person person = new Person();
			person.setFirstName(personDTO.getFirstName());
			person.setLastName(personDTO.getLastName());
			person.setFullName(personDTO.getFirstName()+" " + personDTO.getLastName());
			person.setDesignation(personDTO.getDesignation());
			person.setEmailAddress(personDTO.getEmailAddress());
			person.setPassword(personDTO.getPassword());
			person.setCreatedAt(Timestamp.from(Instant.now()));
			person.setCountryCode(country.get());
			person.setState(personDTO.getState());
			person.setAddress(personDTO.getAddress());
			person.setPhoneNo(personDTO.getPhoneNo());
			person.setUpdatedTime(Timestamp.from(Instant.now()));
			person = userRepository.save(person);
			PersonRoles personRole = new PersonRoles();
			personRole.setPerson(person);
			personRole.setRole(rolesRepository.findById(SignupAndLoginConstants.INVESTIGATOR).orElseThrow());
			personRolesRepository.save(personRole);

			return ResponseEntity.ok().body(Map.of("message", "signup successful"));
		} catch (Exception e) {
			logger.info(e.getMessage());
			return ResponseEntity.badRequest().body(Map.of("message", "signup failed") + e.getMessage());
		}
	}

	public LoginResponseDTO login(String email, String password) {
		try {
			Person person = userRepository.findByEmailAddressAndPassword(email, password);
			if (person != null) {
				LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
				loginResponseDTO.setPersonid(person.getPersonId());
				loginResponseDTO.setFirstName(person.getFirstName());
				loginResponseDTO.setLastName(person.getLastName());
				loginResponseDTO.setDesignation(person.getDesignation());
				loginResponseDTO.setEmail(person.getEmailAddress());
				loginResponseDTO.setCreatedAt(person.getCreatedAt());
				loginResponseDTO.setCountry(person.getCountryCode());
				loginResponseDTO.setState(person.getState());
				loginResponseDTO.setAddress(person.getAddress());
				loginResponseDTO.setPhone_no(person.getPhoneNo());

				List<RolesDTO> roles = person.getPersonRoles().stream().map(userRole -> {
					RolesDTO roleDTO = new RolesDTO();
					roleDTO.setRoleId(userRole.getRole().getRoleId());
					roleDTO.setRoleName(userRole.getRole().getRoleName());
					roleDTO.setRoleDescription(userRole.getRole().getRoleDescription());
					return roleDTO;
				}).collect(Collectors.toList());
				loginResponseDTO.setRoles(roles);

				return loginResponseDTO;
			} else {
				throw new InvalidCredentialsException("No user found");
			}
		} catch (RuntimeException e) {
			logger.info(e.getMessage());
			throw new RuntimeException("invalid credentials");
		}

	}

	public List<Country> getAllCountries() {
		try {
			List<Country> countrylist = countryRepository.findAll();
			if (countrylist.isEmpty()) {
				throw new Exception("No countries found");
			}
			return countrylist;
		} catch (Exception e) {
			logger.info(e.getMessage());
			return null;
		}
	}

	public ResponseEntity<Object> updatePersonDetails(PersonDTO personDTO) {
		try {
			Person person = userRepository.findById(personDTO.getPersonId())
					.orElseThrow(() -> new RuntimeException("person not found"));
			Person personWithEmailAddress = userRepository.findByEmailAddress(personDTO.getEmailAddress());
			if (!person.getEmailAddress().equals(personDTO.getEmailAddress())) {
				if (personWithEmailAddress != null) {
					throw new RuntimeException("This email id is already taken");
				}
			}
			Optional<Country> country = countryRepository.findById(personDTO.getCountryCode());
			person.setFirstName(personDTO.getFirstName());
			person.setLastName(personDTO.getLastName());
			person.setFullName(personDTO.getFirstName() + personDTO.getLastName());
			person.setEmailAddress(personDTO.getEmailAddress());
			person.setDesignation(personDTO.getDesignation());
			person.setAddress(person.getAddress());
			person.setCountryCode(country.get());
			person.setAddress(personDTO.getAddress());
			person.setPassword(personDTO.getPhoneNo());
			person.setUpdatedTime(Timestamp.from(Instant.now()));
			userRepository.save(person);
			LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
			loginResponseDTO.setPersonid(person.getPersonId());
			loginResponseDTO.setFirstName(person.getFirstName());
			loginResponseDTO.setLastName(person.getLastName());
			loginResponseDTO.setDesignation(person.getDesignation());
			loginResponseDTO.setEmail(person.getEmailAddress());
			loginResponseDTO.setCreatedAt(person.getCreatedAt());
			loginResponseDTO.setCountry(person.getCountryCode());
			loginResponseDTO.setState(person.getState());
			loginResponseDTO.setAddress(person.getAddress());
			loginResponseDTO.setPhone_no(person.getPhoneNo());

			List<RolesDTO> roles = person.getPersonRoles().stream().map(userRole -> {
				RolesDTO roleDTO = new RolesDTO();
				roleDTO.setRoleId(userRole.getRole().getRoleId());
				roleDTO.setRoleName(userRole.getRole().getRoleName());
				roleDTO.setRoleDescription(userRole.getRole().getRoleDescription());
				return roleDTO;
			}).collect(Collectors.toList());
			loginResponseDTO.setRoles(roles);
			return ResponseEntity.ok().body(loginResponseDTO);
		} catch (Exception e) {
			logger.info(e.getMessage());
			return ResponseEntity.badRequest().body(Map.of("message", "person details updation failed") + e.getMessage());
		}
	}

}
