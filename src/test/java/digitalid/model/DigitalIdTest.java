package digitalid.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DigitalIdTest {

    @Test
    void testCreateDigitalId() {
        DigitalId id = new DigitalId("ID-001", "Angus", "Young", "1955-03-31", "10 Guitar Lane");

        assertEquals("ID-001", id.getId());
        assertEquals("Angus", id.getFirstName());
        assertEquals("Young", id.getLastName());
        assertEquals("1955-03-31", id.getDateOfBirth());
        assertEquals("10 Guitar Lane", id.getAddress());
    }

    @Test
    void testNewDigitalIdIsActive() {
        DigitalId id = new DigitalId("ID-001", "Angus", "Young", "1955-03-31", "10 Guitar Lane");

        assertEquals(IdentityStatus.ACTIVE, id.getStatus());
    }

    @Test
    void testChangeStatus() {
        DigitalId id = new DigitalId("ID-001", "Angus", "Young", "1955-03-31", "10 Guitar Lane");

        id.setStatus(IdentityStatus.SUSPENDED);
        assertEquals(IdentityStatus.SUSPENDED, id.getStatus());
    }
}
