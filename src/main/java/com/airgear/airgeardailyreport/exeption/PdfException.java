package com.airgear.airgeardailyreport.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PdfException {

    public static ResponseStatusException createPdfException(String fileName) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File with name '" + fileName + "' cannot be created");
    }

}
