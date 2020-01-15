package sax;

import model.User;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class UserHandler extends DefaultHandler {
    private List<User> users;
    private User user;
    boolean bLogin = false;
    boolean bDomain = false;
    boolean bPassword = false;

    public List<User> getUsers() {
        return users;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        if (qName.equalsIgnoreCase("User")) {
            String id = attributes.getValue("id");
            user = new User();
            user.setId(Integer.parseInt(id));
            if (users == null) {
                users = new ArrayList<>();
            }
        } else if (qName.equalsIgnoreCase("login")) {
            bLogin = true;
        } else if (qName.equalsIgnoreCase("domain")) {
            bDomain = true;
        } else if (qName.equalsIgnoreCase("password")) {
            bPassword = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (qName.equalsIgnoreCase("user")) {
            users.add(user);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {


        if (bLogin) {
            user.setLogin(new String(ch, start, length));
            bLogin = false;
        } else if (bDomain) {
            user.setDomain(new String(ch, start, length));
            bDomain = false;
        } else if (bPassword) {
            user.setPassword(new String(ch, start, length));
            bPassword = false;
        }
    }
}