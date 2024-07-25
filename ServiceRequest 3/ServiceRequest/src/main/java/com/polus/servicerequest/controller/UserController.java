package com.polus.servicerequest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.polus.servicerequest.dto.LoginDTO;
import com.polus.servicerequest.dto.LoginResponseDTO;
import com.polus.servicerequest.dto.PersonDTO;
import com.polus.servicerequest.entity.Country;
import com.polus.servicerequest.service.impl.UserService;

@RestController
@RequestMapping("/authenticate")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@RequestBody PersonDTO personDTO) {
		try {
			return userService.signup(personDTO);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO) {
		try {
			LoginResponseDTO response = userService.login(loginDTO.getEmailAddress(), loginDTO.getPassword());
			return ResponseEntity.ok(response);
		} catch (RuntimeException e) {
			return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during sign-in.");
		}

	}

	@GetMapping("/country")
	public ResponseEntity<Object> getAllCountries() {
		try {
			List<Country> countries = userService.getAllCountries();
			if (countries != null) {
				return new ResponseEntity<>(countries, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("county not found", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/updateuserdetails")
	public ResponseEntity<Object> upadtePersonDetails(@RequestBody PersonDTO personDTO){
		return userService.updatePersonDetails(personDTO);
	}
	
	
	
}
