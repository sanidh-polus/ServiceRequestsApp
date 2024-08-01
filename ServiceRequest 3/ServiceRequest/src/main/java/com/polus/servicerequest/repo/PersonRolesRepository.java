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
	
//	@Query(value="SELECT * from person_roles where person_id=:personId and role_id=1 ORDER BY updated_time DESC LIMIT 1", nativeQuery = true)
//	PersonRoles findAdminByPersonId(@Param("personId") Integer personId);
//	
//	@Query(value="SELECT * from person_roles where person_id=:personId and role_id=2 ORDER BY updated_time DESC LIMIT 1", nativeQuery = true)
//	PersonRoles findPersonByPersonId(@Param("personId") Integer personId);
	

	@Query(value = "SELECT * FROM person_roles WHERE person_id = ?1 AND role_id = 1 LIMIT 1", nativeQuery = true)
	PersonRoles findAdminByPersonId(Integer personId);
	 
	@Query(value="SELECT * from person_roles" + " where person_id= ?1 and role_id= ?2 LIMIT 1", nativeQuery = true)
	PersonRoles findPersonByPersonId(Integer personId, Integer roleId);
	 
	@Query("SELECT CASE WHEN (COUNT(p) > 0) THEN TRUE ELSE FALSE END FROM PersonRoles p  WHERE p.person.personId=:personId AND p.role.roleId = :roleId ")
	boolean existsByPersonIdAndRoleId(Integer personId, Integer roleId);
	 
}
