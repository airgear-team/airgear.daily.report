package com.airgear.airgeardailyreport.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledService {

    private PdfProcessorService pdfProcessorService;

    @Autowired
    public ScheduledService(PdfProcessorService pdfProcessorService) {
        this.pdfProcessorService = pdfProcessorService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void writeDailyStatisticsPdf() {
        pdfProcessorService.createDailyStatReport(1);
    }
}
