package com.alexsds.multithreadpolling.service;

import java.io.File;
import java.util.Map;

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

    public File write(Map<String, String> data) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element root = document.createElement("Data");
        document.appendChild(root);

        data.forEach((key, value) -> {
            Element item = document.createElement("item");
            root.appendChild(item);

            Element url = document.createElement("url");
            url.appendChild(document.createTextNode(key));
            item.appendChild(url);

            Element itemData = document.createElement("item_data");
            itemData.appendChild(document.createTextNode(value));

            item.appendChild(itemData);
        });

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        File file = new File(XML_FILE_NAME);
        StreamResult streamResult = new StreamResult(file);

        transformer.transform(domSource, streamResult);

        return file;
    }
}
