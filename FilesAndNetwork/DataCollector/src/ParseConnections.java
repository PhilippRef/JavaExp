import java.util.HashSet;
import java.util.Set;

public class ParseConnections implements Comparable<ParseConnections> {
    private final Set<SingleStation> connectionStations;

    public ParseConnections() {
        connectionStations = new HashSet<>();
    }

    public void addStation(SingleStation station) {
        connectionStations.add(station);
    }

    public Set<SingleStation> getConnectionStations() {
        return connectionStations;
    }

    @Override
    public int compareTo(ParseConnections stationsConnection) {
        if(connectionStations.size() == stationsConnection.getConnectionStations().size()) {
            if(connectionStations.containsAll(stationsConnection.getConnectionStations())) {
                return 0;
            } else {
                return -1;
            }
        }
        if(connectionStations.size() < stationsConnection.getConnectionStations().size()) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((ParseConnections) obj) == 0;
    }
}

