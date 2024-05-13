import javax.xml.parsers.*;

public class Loader {

    public static void main(String[] args) throws Exception {
        String fileName = "res/data-1572M.xml";
        long start = System.currentTimeMillis();

        parseFile(fileName);

        System.out.println("Parsing duration: " + (System.currentTimeMillis() - start) + " ms");
    }

    private static void parseFile(String fileName) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        XMLHandler xmlParser = new XMLHandler();
        parser.parse(fileName, xmlParser);
    }
}