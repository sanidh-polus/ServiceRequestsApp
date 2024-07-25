package com.polus.servicerequest.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import com.polus.servicerequest.dto.AdminandUsersDTO;
import com.polus.servicerequest.entity.Person;

public interface UserRepository extends JpaRepository<Person,Integer>{

	Person findByEmailAddressAndPassword(String email, String password);

	@Query("SELECT p FROM Person p JOIN p.personRoles pr WHERE pr.role.roleId =1")
	List<Person> findAllByRoleId();
	
	@Query("SELECT p FROM Person p " +
		       "JOIN p.personRoles pr1 " +
		       "WHERE pr1.role.roleId = 2 AND p.personId NOT IN (" +
		       "SELECT pr2.person.personId FROM PersonRoles pr2 WHERE pr2.role.roleId = 1)")
	List<Person> GetAllInvestigators();

	Person findByEmailAddress(String emailAddress);
}
