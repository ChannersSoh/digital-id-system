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
    void testCreateWithInvalidDataThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createDigitalId("", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");
        });
        assertEquals("All fields must be provided", exception.getMessage());
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

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");
        });
        assertEquals("ID already exists", exception.getMessage());
    }

    @Test
    void testRevokeActiveId() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");

        service.changeStatus("ID-100", IdentityStatus.REVOKED);
        assertEquals(IdentityStatus.REVOKED, service.getDigitalId("ID-100").getStatus());
    }

    @Test
    void testReactivateSuspendedId() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");
        service.changeStatus("ID-100", IdentityStatus.SUSPENDED);

        service.changeStatus("ID-100", IdentityStatus.ACTIVE);
        assertEquals(IdentityStatus.ACTIVE, service.getDigitalId("ID-100").getStatus());
    }

    @Test
    void testCannotChangeRevokedId() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");
        service.changeStatus("ID-100", IdentityStatus.REVOKED);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.changeStatus("ID-100", IdentityStatus.ACTIVE);
        });
        assertEquals("Cannot change status of a revoked ID", exception.getMessage());
    }

    @Test
    void testCannotChangeStatusOfNonExistentId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.changeStatus("ID-999", IdentityStatus.SUSPENDED);
        });
        assertEquals("ID not found", exception.getMessage());
    }

    @Test
    void testUpdateAddress() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");

        service.updateAddress("ID-100", "25 Garden Lodge");
        assertEquals("25 Garden Lodge", service.getDigitalId("ID-100").getAddress());
    }

    @Test
    void testCannotUpdateAddressOfRevokedId() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");
        service.changeStatus("ID-100", IdentityStatus.REVOKED);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            service.updateAddress("ID-100", "25 Garden Lodge");
        });
        assertEquals("Cannot update a revoked ID", exception.getMessage());
    }

    @Test
    void testCannotUpdateAddressWithEmptyValue() {
        service.createDigitalId("ID-100", "Freddie", "Mercury", "1946-09-05", "1 Logan Place");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.updateAddress("ID-100", "");
        });
        assertEquals("New address must not be empty", exception.getMessage());
    }
}
