package digitalid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DigitalIdStorageTest {

    private final DigitalIdStorage storage = new DigitalIdStorage();

    @Test
    void testSaveAndFindById() {
        DigitalId angus = new DigitalId("ID-010", "Angus", "Young", "1955-03-31", "10 Guitar Lane");
        storage.save(angus);

        DigitalId found = storage.findById("ID-010");
        assertEquals("Angus", found.getFirstName());
        assertEquals("Young", found.getLastName());
    }

    @Test
    void testFindByIdReturnsNullWhenNotFound() {
        DigitalId result = storage.findById("ID-999");
        assertNull(result);
    }

    @Test
    void testFindAllReturnsAllSavedIds() {
        DigitalId angus = new DigitalId("ID-010", "Angus", "Young", "1955-03-31", "10 Guitar Lane");
        DigitalId bon = new DigitalId("ID-011", "Bon", "Scott", "1946-07-09", "5 Highway Road");

        storage.save(angus);
        storage.save(bon);

        assertEquals(2, storage.findAll().size());
    }
}
