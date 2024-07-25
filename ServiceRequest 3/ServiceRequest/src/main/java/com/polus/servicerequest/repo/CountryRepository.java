package com.polus.servicerequest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.polus.servicerequest.entity.Country;

public interface CountryRepository extends JpaRepository<Country, String> {
	
}
