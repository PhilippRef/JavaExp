import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Finder {
    private Document document;
    private List<SingleLine> lines;
    private BoxStations containerStations;
    private DataGather collector;

    public Finder(String url) throws Exception {
        document = Jsoup.connect(url).maxBodySize(0).get();
        lines = new ArrayList<>();
        containerStations = new BoxStations();
    }

    public List<SingleLine> getLines() {
        return lines;
    }

    public BoxStations getContainerStations() {
        return containerStations;
    }

    public DataGather getDataCollector() {
        return collector;
    }

    public JSONArray parseLine() {
        Elements linesList = document.getElementsByAttributeValueStarting("class", "js-metro-line t-metrostation-list-header t-icon-metroln ln");
        JSONArray linesObjectArray = new JSONArray();
        for (Element element : linesList) {
            SingleLine line = new SingleLine(element.attr("data-line"), element.ownText());
            linesObjectArray.add(line.getJsonLine());
            lines.add(line);
        }
        return linesObjectArray;
    }

    public JSONObject parseStation() {
        Elements dataList = document.getElementsByClass("js-metro-stations t-metrostation-list-table");
        JSONObject stationsObject = new JSONObject();
        for (Element element : dataList) {
            JSONArray stationsArray = new JSONArray();
            Elements stationsList = element.getElementsByClass("name");
            for (Element stationElement : stationsList) {
                stationsArray.add(stationElement.text());
                stationsObject.put(element.attr("data-line"), stationsArray);
                containerStations.addStation(new SingleStation(stationElement.text(), element.attr("data-line")));
            }
        }
        return stationsObject;
    }

    public void parseConnection() {
        Elements dataList = document.getElementsByClass("js-metro-stations t-metrostation-list-table");
        for (Element element : dataList) {
            Elements connectionsList = element.select("p:has(span[title])");
            for (Element connectionElement : connectionsList) {
                String station = connectionElement.text();
                int indexSpace = station.lastIndexOf(";");
                String stationName = station.substring(indexSpace + 1).trim();

                ParseConnections stationsConnection = new ParseConnections();
                stationsConnection.addStation(new SingleStation(stationName, element.attr("data-line")));

                Elements connectionsSpanList = connectionElement.select("span[title]");
                for (Element conSpanElement : connectionsSpanList) {
                    String line = conSpanElement.attr("class");
                    int indexDash = line.lastIndexOf("-");
                    String numberLine = line.substring(indexDash + 1);

                    String text = conSpanElement.attr("title");
                    int indexQuote = text.indexOf("«");
                    int lastIndexQuote = text.lastIndexOf("»");
                    String station1 = text.substring(indexQuote + 1, lastIndexQuote);

                    stationsConnection.addStation(new SingleStation(station1, numberLine));
                }
                containerStations.addConnection(stationsConnection);
            }
        }
    }

    public JSONArray writeConnectionsInJSON(Set<ParseConnections> connections) {
        JSONArray connectionsArray = new JSONArray();
        for (ParseConnections stationsCon : connections) {
            JSONArray connectionObjectArray = new JSONArray();
            for (SingleStation station : stationsCon.getConnectionStations()) {
                JSONObject connectionObject = new JSONObject();
                connectionObject.put("line", station.getNumberLine());
                connectionObject.put("station", station.getName());
                connectionObjectArray.add(connectionObject);
            }
            connectionsArray.add(connectionObjectArray);
        }
        return connectionsArray;
    }
}
