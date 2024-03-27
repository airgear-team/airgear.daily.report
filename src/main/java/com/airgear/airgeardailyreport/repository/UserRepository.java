package com.airgear.airgeardailyreport.repository;


import com.airgear.airgeardailyreport.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Long countByCreatedAtBetween(OffsetDateTime start, OffsetDateTime end);

    Long countByDeleteAtBetween(OffsetDateTime start, OffsetDateTime end);
  
}