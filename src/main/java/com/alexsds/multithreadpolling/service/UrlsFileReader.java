package com.alexsds.multithreadpolling.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class UrlsFileReader {
    private static final String URLS_FILE_NAME = "urls.csv";

    public List<String> read() {
        List<String> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(new ClassPathResource(URLS_FILE_NAME).getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                records.add(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }
}
