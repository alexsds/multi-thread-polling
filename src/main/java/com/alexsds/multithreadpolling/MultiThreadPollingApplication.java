package com.alexsds.multithreadpolling;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.alexsds.multithreadpolling.service.MultiThreadUrlReaderService;

@SpringBootApplication
public class MultiThreadPollingApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MultiThreadPollingApplication.class, args);
        try {
            context.getBean(MultiThreadUrlReaderService.class).run();
        }
        catch (ParserConfigurationException | TransformerException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
