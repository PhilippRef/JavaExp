package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static String url = "https://nopaper.ru";

    public static void main(String[] args) {

        Parser parser = new Parser(url, url);
        ForkJoinPool fjp = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Set<String> unsortedSiteMap = fjp.invoke(parser);

        List<String> sortedSiteMap = new ArrayList<>(unsortedSiteMap);
        Collections.sort(sortedSiteMap);

        System.out.println("Количество ссылок: " + sortedSiteMap.size());

        int offset = url.length();
        for (int i = 1; i < sortedSiteMap.size(); i++) {
            String link = sortedSiteMap.get(i);
            int depth = link.substring(offset).split("/").length;
            for (int j = 0; j < depth; j++) {
                link = "\t" + link;
            }
            sortedSiteMap.remove(i);
            sortedSiteMap.add(i, link);
        }
        writeFiles(sortedSiteMap);
    }

    private static void writeFiles(List<String> map) {
        String filePath = "src/main/resources/siteMap.txt";

        File file = new File(filePath);
        try (PrintWriter writer = new PrintWriter(file)) {
            Iterator<String> iterator = map.iterator();
            while (iterator.hasNext()) {
                writer.write(iterator.next() + "\r\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
