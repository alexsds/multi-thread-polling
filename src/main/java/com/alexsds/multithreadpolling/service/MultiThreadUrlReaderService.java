package com.alexsds.multithreadpolling.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MultiThreadUrlReaderService {
    private static final Logger LOGGER = Logger.getLogger(MultiThreadUrlReaderService.class.getName());

    private final List<UrlReader> urlReaders = new ArrayList<>();
    private final UrlsFileReader urlsFileReader;

    @Value("${app.threads.count}")
    private int threadCount;

    public MultiThreadUrlReaderService(UrlsFileReader urlsFileReader) {
        this.urlsFileReader = urlsFileReader;
    }

    public void run() throws ParserConfigurationException, TransformerException, InterruptedException {
        UrlsStorage urlsStorage = new UrlsStorage(urlsFileReader.read());
        XmlFileWriter xmlFileWriter = new XmlFileWriter();

        for (int i = 1; i <= threadCount; i++) {
            LOGGER.info("Create thread " + i);
            UrlReader urlReader = new UrlReader(String.valueOf(i), urlsStorage, xmlFileWriter);
            urlReader.start();
            urlReaders.add(urlReader);
        }

        for (UrlReader urlReader : urlReaders) {
            urlReader.join();
        }

        LOGGER.info("All threads finished");
        xmlFileWriter.saveFile();
    }
}
