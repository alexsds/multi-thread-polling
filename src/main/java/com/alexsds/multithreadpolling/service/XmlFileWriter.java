package com.alexsds.multithreadpolling.service;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@Service
public class XmlFileWriter {
    public static final String XML_FILE_NAME = "output.xml";

    private final Document document;

    public XmlFileWriter() throws ParserConfigurationException {
        document = this.createDocument();
    }

    private Document createDocument() throws ParserConfigurationException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();
        Element root = document.createElement("Data");
        document.appendChild(root);

        return document;
    }

    public void addUrlData(String url, String content) {
        synchronized (document) {
            Element itemElement = document.createElement("item");
            document.getDocumentElement().appendChild(itemElement);

            Element urlElement = document.createElement("url");
            urlElement.appendChild(document.createTextNode(url));
            itemElement.appendChild(urlElement);

            Element contentElement = document.createElement("item_data");
            contentElement.appendChild(document.createTextNode(content));
            itemElement.appendChild(contentElement);
        }
    }

    public void saveFile() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        File file = new File(XML_FILE_NAME);
        StreamResult streamResult = new StreamResult(file);
        transformer.transform(domSource, streamResult);
    }
}
