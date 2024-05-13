import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DataGather {
    @Override
    public String toString() {
        return "DataGather{" +
                "listStations=" + listStations +
                '}';
    }

    Map<String, SingleStation> listStations = new HashMap<>();
    String DATA_FILE = "";

    public Map<String, SingleStation> getListStations() {
        return listStations;
    }

    public Map<String, SingleStation> fileReader(String path) throws ParseException, FileNotFoundException {
        File doc = new File(path);
        if (doc.isFile()) {
            DATA_FILE = doc.getAbsolutePath();
            if (doc.getName().endsWith(".json")) {
                getDatesFromJson(doc);
            }

            if (doc.getName().endsWith(".csv")) {
                getDatesFromCsv(doc);
            }
        } else {
            File[] files = doc.listFiles();
            for (File file : files) {
                fileReader(file.getAbsolutePath());
            }
        }

        return listStations;
    }

    private void getDatesFromJson(File doc) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONArray jsonData = (JSONArray) parser.parse(getJsonFile());
        for (Object it : jsonData) {
            JSONObject stationJsonObject = (JSONObject) it;
            String stationName = (String) stationJsonObject.get("station_name");
            if (!listStations.containsKey(stationName)) {
                listStations.put(stationName, new SingleStation(stationName));
            }
            if (doc.getName().startsWith("dates")) {
                String date = (String) stationJsonObject.get("dates");
                listStations.get(stationName).setDate(date);
            } else if (doc.getName().startsWith("depths")) {
                String depth = String.valueOf(stationJsonObject.get("depth"));
                listStations.get(stationName).setDepth(depth);
            }

        }
    }

    private void getDatesFromCsv(File doc) throws FileNotFoundException {
        String filePath = doc.getAbsolutePath();
        BufferedReader reader = new BufferedReader(new FileReader(filePath)); //filePath
        try {
            String splitBy = ",";
            String line = "";
            while ((line = reader.readLine()) != null) {
                String[] lines = line.split(splitBy, 2);
                for (int i = 0; i < lines.length; i++) {
                    if (i % 2 == 0) {
                        String stationName = lines[i];
                        if (!listStations.containsKey(stationName)) {
                            listStations.put(stationName, new SingleStation(stationName));
                        }
                        if (doc.getName().startsWith("dates")) {
                            listStations.get(stationName).setDate(lines[i + 1]);
                        } else if (doc.getName().startsWith("depth")) {
                            listStations.get(stationName).setDepth(lines[i + 1]);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getJsonFile() {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> lines = Files.readAllLines(Paths.get(DATA_FILE));
            lines.forEach(line -> builder.append(line));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return builder.toString();
    }

}

