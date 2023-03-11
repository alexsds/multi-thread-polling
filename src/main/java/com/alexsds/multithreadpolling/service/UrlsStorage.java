package com.alexsds.multithreadpolling.service;

import java.util.List;
import java.util.ListIterator;

import org.springframework.stereotype.Component;

@Component
public class UrlsStorage {
    private final ListIterator<String> iterator;

    public UrlsStorage(List<String> urls) {
        iterator = urls.listIterator();
    }

    public synchronized boolean hasNext() {
        return iterator.hasNext();
    }

    public synchronized String next() {
        return iterator.next();
    }
}
