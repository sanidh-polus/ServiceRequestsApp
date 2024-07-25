package com.polus.servicerequest.service.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.polus.servicerequest.dto.LoginResponseDTO;
import com.polus.servicerequest.dto.PersonDTO;
import com.polus.servicerequest.entity.Country;

@Service
public interface UserService {

	ResponseEntity<Object> signup(PersonDTO personDTO);

	LoginResponseDTO login(String email, String password);

	List<Country> getAllCountries();

	ResponseEntity<Object> updatePersonDetails(PersonDTO personDTO);
}
