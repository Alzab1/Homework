package stax;

import model.User;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.util.ArrayList;
import java.util.List;

public class StaxParser {
    private static final String LOGIN = "login";
    private static final String DOMAIN = "domain";
    private static final String PASSWORD = "password";
    private static final String ID = "id";
    private static final String USER = "User";

    private User user;
    List<User> users = new ArrayList<>();

    public List<User> parse(XMLEventReader xmlEventReader) throws XMLStreamException {
        while (xmlEventReader.hasNext()) {
            XMLEvent xmlEvent = xmlEventReader.nextEvent();
            proceedStartElement(xmlEvent, xmlEventReader);
            proceedEndElement(xmlEvent);
        }
        return users;
    }

    private void proceedStartElement(XMLEvent xmlEvent, XMLEventReader xmlEventReader) throws XMLStreamException {
        if (xmlEvent.isStartElement()) {
            StartElement startElement = xmlEvent.asStartElement();
            if (isTagNameEqual(startElement, USER)) {
                user = new User();
                Attribute attribute = startElement.getAttributeByName(new QName(ID));
                if (attribute != null) {
                    user.setId(Integer.parseInt(attribute.getValue()));
                }
            } else if (isTagNameEqual(startElement, LOGIN)) {
                user.setLogin(xmlEventReader.nextEvent().asCharacters().getData());
            } else if (isTagNameEqual(startElement, DOMAIN)) {
                user.setDomain(xmlEventReader.nextEvent().asCharacters().getData());
            } else if (isTagNameEqual(startElement, PASSWORD)) {
                user.setPassword(xmlEventReader.nextEvent().asCharacters().getData());
            }
        }
    }

    private void proceedEndElement(XMLEvent xmlEvent) {
        if (xmlEvent.isEndElement()) {
            EndElement endElement = xmlEvent.asEndElement();
            if (endElement.getName().getLocalPart().equals(USER)) {
                users.add(user);
            }
        }
    }

    private boolean isTagNameEqual(StartElement startElement, String tagName) {
        return startElement.getName().getLocalPart().equals(tagName);
    }
}