package com.airgear.airgeardailyreport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AirGearDailyReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirGearDailyReportApplication.class, args);
    }

}
