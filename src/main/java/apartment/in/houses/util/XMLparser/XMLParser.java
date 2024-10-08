package apartment.in.houses.util.XMLparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XMLParser {
    public static Map<String, String> parse(String xmlFilePath) {
        Map<String, String> config = new HashMap<>();
        try {
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            SAXParser parser = parserFactory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                private String currentElement;
                private final StringBuilder currentValue = new StringBuilder();

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    currentElement = qName;
                    currentValue.setLength(0);

                    if ("mapping".equals(qName)) {
                        String currentMapping = attributes.getValue("class");
                        config.put("mapping_" + currentMapping, currentMapping);
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) {
                    currentValue.append(ch, start, length);
                }

                @Override
                public void endElement(String uri, String localName, String qName) {
                    if (currentElement != null && currentValue.length() > 0 && !"mapping".equals(qName)) {
                        config.put(currentElement, currentValue.toString().trim());
                    }
                }
            };

            parser.parse(new File(xmlFilePath), handler);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
        return config;
    }
}

