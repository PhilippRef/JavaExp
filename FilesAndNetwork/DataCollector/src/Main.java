import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) throws Exception {
        Finder finder = new Finder("ссылка на карту метро");

        JSONArray linesArray = finder.parseLine();

        JSONObject stationsObject = finder.parseStation();

        finder.parseConnection();
        List<SingleStation> stations = finder.getContainerStations().getStations();
        Set<ParseConnections> connections = finder.getContainerStations().getConnections();
        JSONArray connectionsArray = finder.writeConnectionsInJSON(connections);
        MetroCreate metro = new MetroCreate(stationsObject, linesArray, connectionsArray);

        ObjectMapper mapper2 = new ObjectMapper();
        File output2 = new File("result/metroMSK.json");
        ObjectWriter writer2 = mapper2.writer(new DefaultPrettyPrinter());
        writer2.writeValue(output2, metro.getMetroObject());

        DataGather collector = new DataGather();
        collector.fileReader("stationsData/data/");

        Map<String, SingleStation> listStations = collector.getListStations();

        setParameterHasConnection(connections, listStations);
        setParameterLineName(stations, linesArray, listStations);

        JSONObject stationObject = new JSONObject();
        JSONArray stationsArray = new JSONArray();

        for (Map.Entry<String, SingleStation> entry : listStations.entrySet()) {
            JSONObject stationObj = new JSONObject();
            if (entry.getValue().getName() != null) {
                stationObj.put("name", entry.getValue().getName());
            }
            if (entry.getValue().getLineName() != null) {
                stationObj.put("line", entry.getValue().getLineName());
            }
            if (entry.getValue().getDate() != null) {
                stationObj.put("date", entry.getValue().getDate());
            }
            if (entry.getValue().getDepth() != null) {
                stationObj.put("depth", entry.getValue().getDepth());
            }
            stationObj.put("hasConnection", entry.getValue().isHasConnection());

            stationsArray.add(stationObj);
        }
        stationObject.put("stations", stationsArray);

        ObjectMapper mapper = new ObjectMapper();
        File output = new File("result/stations.json");
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(output, stationObject);
    }

    private static void setParameterHasConnection(Set<ParseConnections> connections, Map<String, SingleStation> listStations) {
        listStations.keySet().forEach(k -> {
            for (ParseConnections connection : connections) {
                for (SingleStation station : connection.getConnectionStations()) {
                    if (station.getName().equals(k)) {
                        listStations.get(k).setHasConnection(true);
                    }
                }
            }
        });
    }

    private static void setParameterLineName(List<SingleStation> stations, JSONArray linesArray, Map<String, SingleStation> listStations) {
        listStations.keySet().forEach(k -> {
            for (SingleStation station : stations) {
                if (station.getName().equals(k)) {
                    linesArray.forEach(lineObject -> {
                        JSONObject lineJsonObject = (JSONObject) lineObject;
                        if (lineJsonObject.get("number").equals(station.getNumberLine())) {
                            listStations.get(k).setLineName((String) lineJsonObject.get("name"));
                        }
                    });
                }
            }
        });
    }
}

