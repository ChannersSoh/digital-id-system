package digitalid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DigitalIdValidatorTest {

    private final DigitalIdValidator validator = new DigitalIdValidator();

    @Test
    void testValidDataPasses() {
        boolean result = validator.validate("ID-001", "Fredrich", "Bonaparte", "1890-03-09", "123 Olive Road");
        assertTrue(result);
    }

    @Test
    void testNullIdFails() {
        boolean result = validator.validate(null, "Fredrich", "Bonaparte", "1890-03-09", "123 Olive Road");
        assertFalse(result);
    }

    @Test
    void testEmptyFirstNameFails() {
        boolean result = validator.validate("ID-001", "", "Bonaparte", "1890-03-09", "123 Olive Road");
        assertFalse(result);
    }

    @Test
    void testNullLastNameFails() {
        boolean result = validator.validate("ID-001", "John", null, "1990-05-15", "123 Main Street");
        assertFalse(result);
    }

    @Test
    void testEmptyDateOfBirthFails() {
        boolean result = validator.validate("ID-001", "John", "Smith", "", "123 Main Street");
        assertFalse(result);
    }

    @Test
    void testNullAddressFails() {
        boolean result = validator.validate("ID-001", "John", "Smith", "1990-05-15", null);
        assertFalse(result);
    }

    @Test
    void testInvalidDateFormatFails() {
        boolean result = validator.validate("ID-001", "John", "Smith", "7834223792", "123 Main Street");
        assertFalse(result);
    }

    @Test
    void testInvalidMonthFails() {
        boolean result = validator.validate("ID-001", "John", "Smith", "1990-13-15", "123 Main Street");
        assertFalse(result);
    }

    @Test
    void testInvalidDayFails() {
        boolean result = validator.validate("ID-001", "John", "Smith", "1990-05-32", "123 Main Street");
        assertFalse(result);
    }
}
