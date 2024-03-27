package com.airgear.airgeardailyreport.util;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Util {

    public File createFolder(String nameFolder) {
        java.io.File folder = new java.io.File(System.getProperty("user.dir"), nameFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }
}
