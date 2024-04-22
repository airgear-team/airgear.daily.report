package com.airgear.airgeardailyreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EntityScan(basePackages = {"com.airgear.model"})
@EnableJpaRepositories(basePackages = "com.airgear.airgeardailyreport.repository")
public class AirGearDailyReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirGearDailyReportApplication.class, args);
    }

}
