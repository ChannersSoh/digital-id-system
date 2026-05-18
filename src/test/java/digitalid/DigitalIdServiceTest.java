package digitalid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DigitalIdServiceTest {

    private final DigitalIdService service = new DigitalIdService();

    @Test
    void testCreateValidDigitalId() {
        DigitalId result = service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");

        assertNotNull(result);
        assertEquals("Freddie", result.getFirstName());
        assertEquals("Mercury", result.getLastName());
        assertEquals(IdentityStatus.ACTIVE, result.getStatus());
    }

    @Test
    void testCreateWithInvalidDataReturnsNull() {
        DigitalId result = service.createDigitalId("", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");

        assertNull(result);
    }

    @Test
    void testGetDigitalIdAfterCreating() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");

        DigitalId found = service.getDigitalId("ID-100");
        assertNotNull(found);
        assertEquals("Freddie", found.getFirstName());
        assertEquals("Mercury", found.getLastName());
    }

    @Test
    void testGetDigitalIdThatDoesNotExist() {
        DigitalId found = service.getDigitalId("ID-999");
        assertNull(found);
    }

    @Test
    void testCannotCreateDuplicateId() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");
        DigitalId duplicate = service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");

        assertNull(duplicate);
    }
}
