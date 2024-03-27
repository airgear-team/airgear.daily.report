package com.airgear.airgeardailyreport.repository;

import com.airgear.airgeardailyreport.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.UUID;


public interface MessageRepository extends JpaRepository<Message, UUID> {

    Long countBySendAtBetween(OffsetDateTime start, OffsetDateTime end);

}
