package runner;

import dom.DomParser;
import model.Country;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import sax.CountryHandler;
import stax.StaxParser;

import javax.xml.parsers.*;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;


public class Run {

    private static final String COUNTRIES_XML = "Countries.xml";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,
            XMLStreamException {
        System.out.println(" ========================= SAX parser ==============================");
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        CountryHandler countryHandler = new CountryHandler();
        saxParser.parse(new File(COUNTRIES_XML), countryHandler);
        List<Country> countries = countryHandler.getCountries();
        countries.forEach(System.out::println);

        System.out.println(" ============================== STAX parser =========================");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(COUNTRIES_XML));
        countries = new StaxParser().parse(xmlEventReader);
        countries.forEach(System.out::println);

        System.out.println(" ============================== DOM parser =========================");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(COUNTRIES_XML);
        countries = new DomParser().parse(document);
        countries.forEach(System.out::println);
    }

}
