package com.alexsds.multithreadpolling.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexsds.multithreadpolling.service.UrlReader;
import com.alexsds.multithreadpolling.service.UrlsFileReader;

@RestController
public class ResultController {

    private final UrlsFileReader urlsFileReader;
    private final UrlReader urlReader;

    @Autowired
    public ResultController(UrlsFileReader urlsFileReader, UrlReader urlReader) {
        this.urlsFileReader = urlsFileReader;
        this.urlReader = urlReader;
    }

    @GetMapping("/urls")
    List<String> getUrls() {
        return urlsFileReader.read();
    }

    @GetMapping("/result")
    List<String> getResult() {
        List<String> result = new ArrayList<>();
        urlsFileReader.read().forEach(url -> {
            String content = null;
            try {
                content = this.urlReader.read(url);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            result.add(content);
        });
        return result;
    }
}
