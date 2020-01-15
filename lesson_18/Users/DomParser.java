package dom;

import model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DomParser {
    public List<User> parse(Document document) {
        NodeList nodeList = document.getElementsByTagName("User");
        List<User> users = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            users.add(getUser(nodeList.item(i)));
        }
        return users;
    }

    private static User getUser(Node node) {
        User user = new User();
        Element element = (Element) node;
        user.setId(Integer.parseInt(element.getAttribute("id")));
        user.setLogin(getTagValue("login", element));
        user.setDomain(getTagValue("domain", element));
        user.setPassword(getTagValue("password", element));
        return user;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}