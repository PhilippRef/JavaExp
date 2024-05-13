import org.json.simple.JSONObject;

public class SingleLine implements Comparable<SingleLine>{
    private final JSONObject jsonLine;
    private final String number;
    private final String name;

    public SingleLine(String number, String name) {
        jsonLine = new JSONObject();
        jsonLine.put("number", number);
        jsonLine.put("name", name);
        this.number = number;
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public Object getJsonLine() {
        return jsonLine;
    }

    @Override
    public int compareTo(SingleLine line) {
        return number.compareToIgnoreCase(line.getNumber());
    }

    @Override
    public boolean equals(Object obj) {
        return compareTo((SingleLine) obj) == 0;
    }

    @Override
    public String toString() {
        return name;
    }
}

