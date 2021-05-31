package com.alexsds.multithreadpolling.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alexsds.multithreadpolling.service.UrlReader;
import com.alexsds.multithreadpolling.service.UrlsFileReader;
import com.alexsds.multithreadpolling.service.XmlFileWriter;

@RestController
public class ResultController {

    private final UrlsFileReader urlsFileReader;
    private final UrlReader urlReader;
    private final XmlFileWriter xmlFileWriter;

    @Autowired
    public ResultController(UrlsFileReader urlsFileReader, UrlReader urlReader, XmlFileWriter xmlFileWriter) {
        this.urlsFileReader = urlsFileReader;
        this.urlReader = urlReader;
        this.xmlFileWriter = xmlFileWriter;
    }

    @GetMapping("/urls")
    List<String> getUrls() {
        return urlsFileReader.read();
    }

    @GetMapping("/result")
    ResponseEntity<Resource> getResult() throws FileNotFoundException, ParserConfigurationException, TransformerException {
        Map<String, String> result = new HashMap<>();
        urlsFileReader.read().forEach(url -> {
            String content = null;
            try {
                content = urlReader.read(url);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            result.put(url, content);
        });

        File file = xmlFileWriter.write(result);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok().contentLength(file.length()).contentType(MediaType.APPLICATION_XML).body(resource);
    }
}
