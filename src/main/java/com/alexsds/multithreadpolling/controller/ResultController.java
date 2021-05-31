package com.alexsds.multithreadpolling.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexsds.multithreadpolling.service.UrlsFileReader;

@RestController
public class ResultController {

    private final UrlsFileReader urlsFileReader;

    @Autowired
    public ResultController(UrlsFileReader urlsFileReader) {
        this.urlsFileReader = urlsFileReader;
    }

    @GetMapping("/urls")
    List<String> getUrls() {
        return urlsFileReader.read();
    }
}
