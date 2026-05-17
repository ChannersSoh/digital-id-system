package digitalid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DigitalIdTest {

    @Test
    void testCreateDigitalId() {
        DigitalId id = new DigitalId("ID-001", "John", "Smith", "1990-05-15", "123 Main Street");

        assertEquals("ID-001", id.getId());
        assertEquals("John", id.getFirstName());
        assertEquals("Smith", id.getLastName());
        assertEquals("1990-05-15", id.getDateOfBirth());
        assertEquals("123 Main Street", id.getAddress());
    }

    @Test
    void testNewDigitalIdIsActive() {
        DigitalId id = new DigitalId("ID-001", "John", "Smith", "1990-05-15", "123 Main Street");

        assertEquals(IdentityStatus.ACTIVE, id.getStatus());
    }

    @Test
    void testChangeStatus() {
        DigitalId id = new DigitalId("ID-001", "John", "Smith", "1990-05-15", "123 Main Street");

        id.setStatus(IdentityStatus.SUSPENDED);
        assertEquals(IdentityStatus.SUSPENDED, id.getStatus());
    }

}
