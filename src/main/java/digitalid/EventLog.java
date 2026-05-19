package digitalid;

import java.util.ArrayList;
import java.util.List;

public class EventLog {

    private final List<String> logs = new ArrayList<>();

    public void log(String action, String id) {
        String entry = java.time.LocalDateTime.now() + " | " + action + " | ID: " + id;
        logs.add(entry);
    }

    public List<String> getLogs() {
        return logs;
    }

    public List<String> getLogsForId(String id) {
        List<String> filtered = new ArrayList<>();
        for (String entry : logs) {
            if (entry.contains("ID: " + id)) {
                filtered.add(entry);
            }
        }
        return filtered;
    }
}
