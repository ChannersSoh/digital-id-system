package digitalid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VerificationServiceTest {

    private final DigitalIdStorage storage = new DigitalIdStorage();
    private final EventLog eventLog = new EventLog();
    private final VerificationService verificationService = new VerificationService(storage, eventLog);

    @Test
    void testIsActiveReturnsTrue() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        storage.save(ozzy);

        assertTrue(verificationService.isActive("ID-200"));
    }

    @Test
    void testIsActiveReturnsFalseWhenSuspended() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        ozzy.setStatus(IdentityStatus.SUSPENDED);
        storage.save(ozzy);

        assertFalse(verificationService.isActive("ID-200"));
    }

    @Test
    void testErrorMessageWhenIdNotFound() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            verificationService.isActive("ID-999");
        });
        assertEquals("ID not found", exception.getMessage());
    }

    @Test
    void testTaxVerificationOnActiveId() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        storage.save(ozzy);

        String result = verificationService.verifyForTax("ID-200", OrganisationType.TAX);
        assertEquals("ID is active", result);
    }

    @Test
    void testTaxVerificationOnSuspendedId() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        ozzy.setStatus(IdentityStatus.SUSPENDED);
        storage.save(ozzy);

        String result = verificationService.verifyForTax("ID-200", OrganisationType.TAX);
        assertEquals("ID is currently suspended", result);
    }

    @Test
    void testTaxVerificationRejectsWrongOrgType() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        storage.save(ozzy);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            verificationService.verifyForTax("ID-200", OrganisationType.EMPLOYER);
        });
        assertEquals("Only tax organisations can use this verification", exception.getMessage());
    }

    @Test
    void testEmployerVerificationOnActiveId() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        storage.save(ozzy);

        String result = verificationService.verifyForEmployer("ID-200", OrganisationType.EMPLOYER);
        assertEquals("VALID", result);
    }

    @Test
    void testBankVerificationOnRevokedId() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        ozzy.setStatus(IdentityStatus.REVOKED);
        storage.save(ozzy);

        String result = verificationService.verifyForEmployer("ID-200", OrganisationType.BANK);
        assertEquals("INVALID", result);
    }

    @Test
    void testEmployerVerificationRejectsWrongOrgType() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        storage.save(ozzy);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            verificationService.verifyForEmployer("ID-200", OrganisationType.TAX);
        });
        assertEquals("Only employer or bank organisations can use this verification", exception.getMessage());
    }

    @Test
    void testDrivingLicenceVerificationOnEligiblePerson() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        storage.save(ozzy);

        String result = verificationService.verifyForDrivingLicence("ID-200", OrganisationType.DRIVING_LICENCE);
        assertEquals("Eligible for driving licence", result);
    }

    @Test
    void testDrivingLicenceVerificationOnUnderagePerson() {
        DigitalId young = new DigitalId("ID-201", "Ozzy", "Osbourne", "2015-06-01", "15 Beechwood Road");
        storage.save(young);

        String result = verificationService.verifyForDrivingLicence("ID-201", OrganisationType.DRIVING_LICENCE);
        assertEquals("Does not meet minimum age requirement", result);
    }

    @Test
    void testDrivingLicenceVerificationOnInactiveId() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        ozzy.setStatus(IdentityStatus.SUSPENDED);
        storage.save(ozzy);

        String result = verificationService.verifyForDrivingLicence("ID-200", OrganisationType.DRIVING_LICENCE);
        assertEquals("ID is not active", result);
    }

    @Test
    void testDrivingLicenceVerificationRejectsWrongOrgType() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        storage.save(ozzy);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            verificationService.verifyForDrivingLicence("ID-200", OrganisationType.TAX);
        });
        assertEquals("Only driving licence organisations can use this verification", exception.getMessage());
    }

    @Test
    void testEventLogRecordsVerification() {
        DigitalId ozzy = new DigitalId("ID-200", "Ozzy", "Osbourne", "1948-12-03", "15 Beechwood Road");
        storage.save(ozzy);

        verificationService.isActive("ID-200");
        assertEquals(1, eventLog.getLogs().size());
        assertTrue(eventLog.getLogs().get(0).contains("VERIFICATION_CHECK"));
    }
}
