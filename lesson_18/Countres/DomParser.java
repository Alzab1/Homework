package dom;

import model.Country;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class DomParser {
    public List<Country> parse(Document document) {
        NodeList nodeList = document.getElementsByTagName("Country");
        List<Country> countries = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            countries.add(getCountry(nodeList.item(i)));
        }
        return countries;
    }

    private static Country getCountry(Node node) {
        Country country = new Country();
        Element element = (Element) node;
        country.setId(Integer.parseInt(element.getAttribute("id")));
        country.setName(getTagValue("Name", element));
        country.setCode(getTagValue("Code", element));
        country.setDescription(getTagValue("Description", element));
        return country;
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
}