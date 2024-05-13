package org.example;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser extends RecursiveTask<Set<String>> {

    private final String url;
    private final String startUrl;
    private static final CopyOnWriteArraySet<String> allLinks = new CopyOnWriteArraySet<>();

    public Parser(String url, String startUrl) {
        this.url = url;
        this.startUrl = startUrl;
    }

    @Override
    protected Set<String> compute() {
        Set<String> siteMap = new HashSet<>();

        String correctedUrl = correctFormatLink(url);
        siteMap.add(correctedUrl);

        Set<Parser> subTaskSet = getLinks();

        for (Parser task : subTaskSet) {
            siteMap.addAll(task.join());
        }
        return siteMap;
    }

    private CopyOnWriteArraySet<Parser> getLinks() {
        CopyOnWriteArraySet<Parser> subTask = new CopyOnWriteArraySet<>();

        try {
            Thread.sleep(150);

            Document doc = Jsoup.connect(url)
                    .ignoreHttpErrors(true)
                    .ignoreContentType(true)
                    .userAgent("Chrome/112.0.0.0 Safari/537.36")
                    .referrer("http://google.com")
                    .followRedirects(false)
                    .get();

            Elements elements = doc.select("a");
            for (Element nextLink : elements) {
                String absLink = nextLink.attr("abs:href");
                String correctedLink = correctFormatLink(absLink);

                if (isLink(correctedLink)) {
                    Parser parser = new Parser(correctedLink, startUrl);
                    parser.fork();
                    subTask.add(parser);
                    allLinks.add(correctedLink);

                    System.out.println("New link: " + correctedLink);
                }
            }

        } catch (InterruptedException | IOException ex) {
            ex.printStackTrace();
        }
        return subTask;
    }

    private boolean isLink(String link) {
        return !link.isEmpty() &&
                !allLinks.contains(link) &&
                !link.contains("#") &&
                !link.contains("?") &&
                link.startsWith(startUrl);
    }

    private String correctFormatLink(String link) {
        if (link.lastIndexOf("/") != (link.length() - 1)) {
            link += "/";
        }
        return link;
    }
}
