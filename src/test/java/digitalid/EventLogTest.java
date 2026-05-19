package digitalid;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {

    private final EventLog eventLog = new EventLog();

    @Test
    void testLogEntry() {
        eventLog.log("CREATED", "ID-300");

        assertEquals(1, eventLog.getLogs().size());
        assertTrue(eventLog.getLogs().get(0).contains("CREATED"));
        assertTrue(eventLog.getLogs().get(0).contains("ID: ID-300"));
    }

    @Test
    void testMultipleLogEntries() {
        eventLog.log("Created", "ID-300");
        eventLog.log("STATUS_CHANGE", "ID-300");
        eventLog.log("CREATED", "ID-301");

        assertEquals(3, eventLog.getLogs().size());
    }

    @Test
    void testGetLogsForSpecificId() {
        eventLog.log("CREATED", "ID-300");
        eventLog.log("STATUS_CHANGE", "ID-300");
        eventLog.log("CREATED", "ID-301");

        List<String> logsForId = eventLog.getLogsForId("ID-300");
        assertEquals(2, logsForId.size());
    }

    @Test
    void testGetLogsForIdWithNoEntries() {
        eventLog.log("CREATED", "ID-300");

        List<String> logsForId = eventLog.getLogsForId("ID-999");
        assertEquals(0, logsForId.size());
    }
}
