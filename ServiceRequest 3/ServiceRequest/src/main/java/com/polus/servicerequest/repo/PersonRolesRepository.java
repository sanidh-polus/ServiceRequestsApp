package com.polus.servicerequest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.polus.servicerequest.entity.PersonRoles;

import jakarta.transaction.Transactional;

public interface PersonRolesRepository extends JpaRepository<PersonRoles,Integer>{

	@Modifying
	@Transactional
	@Query("DELETE FROM PersonRoles P WHERE P.person.personId=:personId and P.role.roleId=1")
	void deleteByPersonIdAndRoleId(@Param("personId") Integer personId);
}
