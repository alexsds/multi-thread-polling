package com.alexsds.multithreadpolling.service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.logging.Logger;

public class UrlReader implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(UrlReader.class.getName());

    private final String name;
    private final UrlsStorage urlsStorage;
    private final XmlFileWriter xmlFileWriter;
    private final Thread thread;

    public UrlReader(String name, UrlsStorage urlsStorage, XmlFileWriter xmlFileWriter) {
        this.name = name;
        this.urlsStorage = urlsStorage;
        this.xmlFileWriter = xmlFileWriter;
        thread = new Thread(this);
    }

    public void start() {
        thread.start();
    }

    public void join() throws InterruptedException {
        thread.join();
    }

    @Override
    public void run() {
        LOGGER.info("UrlReader " + name + " started");
        while (urlsStorage.hasNext()) {
            try {
                String url = urlsStorage.next();
                LOGGER.info("UrlReader " + name + " getContent for " + url);
                String content = getContent(url);
                xmlFileWriter.addUrlData(url, content);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("UrlReader " + name + " finished");
    }

    private String getContent(String url) throws IOException {
        try (Scanner scanner = new Scanner(new URL(url).openStream(), StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
