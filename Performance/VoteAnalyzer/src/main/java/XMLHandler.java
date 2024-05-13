import org.xml.sax.helpers.*;
import org.xml.sax.*;

import java.sql.SQLException;

public class XMLHandler extends DefaultHandler {

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parse XML");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("voter")) {
            String name = attributes.getValue("name");
            String birthDate = attributes.getValue("birthDay");
            try {
                DBConnection.countVoter(name, birthDate);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("Stop parse XML");
    }
}
