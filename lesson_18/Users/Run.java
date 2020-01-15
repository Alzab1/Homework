package runner;

import dom.DomParser;
import model.User;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import sax.UserHandler;
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

    private static final String USERS_XML = "users.xml";

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException,
            XMLStreamException {
        System.out.println(" ========================= SAX parser ==============================");
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        UserHandler userHandler = new UserHandler();
        saxParser.parse(new File(USERS_XML), userHandler);
        List<User> users = userHandler.getUsers();
        users.forEach(System.out::println);

        System.out.println(" ============================== STAX parser =========================");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new FileInputStream(USERS_XML));
        users = new StaxParser().parse(xmlEventReader);
        users.forEach(System.out::println);

        System.out.println(" ============================== DOM parser =========================");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(USERS_XML);
        users = new DomParser().parse(document);
        users.forEach(System.out::println);
    }

}
