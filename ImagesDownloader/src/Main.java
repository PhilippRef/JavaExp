import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception {
        String url = "";
        Document doc = Jsoup.connect(url).get();

        Elements images = doc.select("img");
        Set<String> links = new HashSet<>();
        for (Element image : images) {
            links.add(image.attr("abs:src"));
        }
        int number = 1;
        for (String link : links) {
            String extension = link.replaceAll("^.+\\.", "")
                    .replace("?.+$", "");
            String filePath = "data/" + number++ + "." + extension;
            try {
                download(link, filePath);
            } catch (Exception ex) {
                System.out.println("Error download" + link);
            }
        }
    }
    public static void download(String link, String filePath) throws Exception {
        URLConnection connection = new URL(link).openConnection();
        InputStream inStream = connection.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(filePath);
        int b;
        while ((b = inStream.read()) != -1) {
            outputStream.write(b);
        }
        outputStream.flush();
        outputStream.close();
        inStream.close();
    }
}

