import java.util.*;

public class BoxStations {
    private final List<SingleStation> stations;
    private final Set<ParseConnections> connections;

    public BoxStations() {
        stations = new ArrayList<>();
        connections = new HashSet<>();
    }

    public List<SingleStation> getStations() {
        return stations;
    }

    public Set<ParseConnections> getConnections() {
        return connections;
    }

    public void addStation(SingleStation station) {
        stations.add(station);
    }

    public void addConnection(ParseConnections stationsCon) {
        if(!containsStation(stationsCon)) {
            connections.add(stationsCon);
        }
    }

    private boolean containsStation(ParseConnections stationsCon) {
        for(ParseConnections connection : connections) {
            for(SingleStation station : connection.getConnectionStations()) {
                for(SingleStation stationInner : stationsCon.getConnectionStations()) {
                    if(station.getName().equals(stationInner.getName()) && station.getNumberLine().equals(stationInner.getNumberLine())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

