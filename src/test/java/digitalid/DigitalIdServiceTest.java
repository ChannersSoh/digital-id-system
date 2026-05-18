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

    @Test
    void testRevokeActiveId() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");

        boolean result = service.changeStatus("ID-100", IdentityStatus.REVOKED);
        assertTrue(result);
        assertEquals(IdentityStatus.REVOKED, service.getDigitalId("ID-100").getStatus());
    }

    @Test
    void testReactivateSuspendedId() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");
        service.changeStatus("ID-100", IdentityStatus.SUSPENDED);

        boolean result = service.changeStatus("ID-100", IdentityStatus.ACTIVE);
        assertTrue(result);
        assertEquals(IdentityStatus.ACTIVE, service.getDigitalId("ID-100").getStatus());
    }

    @Test
    void testCannotChangeRevokedId() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");
        service.changeStatus("ID-100", IdentityStatus.REVOKED);

        boolean result = service.changeStatus("ID-100", IdentityStatus.ACTIVE);
        assertFalse(result);
        assertEquals(IdentityStatus.REVOKED, service.getDigitalId("ID-100").getStatus());
    }

    @Test
    void testCannotChangeStatusOfNonExistentId() {
        boolean result = service.changeStatus("ID-999", IdentityStatus.SUSPENDED);
        assertFalse(result);
    }
}
