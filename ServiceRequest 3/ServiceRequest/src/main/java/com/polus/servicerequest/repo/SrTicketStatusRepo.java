package com.polus.servicerequest.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.polus.servicerequest.entity.SrTicketStatus;

public interface SrTicketStatusRepo extends JpaRepository<SrTicketStatus,Integer>{


}