package digitalid;

public class VerificationService {

    private final DigitalIdStorage storage;
    private final EventLog eventLog;

    public VerificationService(DigitalIdStorage storage, EventLog eventLog) {
        this.storage = storage;
        this.eventLog = eventLog;
    }

    public boolean isActive(String id) {
        DigitalId digitalId = storage.findById(id);
        if (digitalId == null) {
            throw new IllegalArgumentException("ID not found");
        }
        eventLog.log("VERIFICATION_CHECK", id);
        return digitalId.getStatus() == IdentityStatus.ACTIVE;
    }

    public String verifyForTax(String id, OrganisationType orgType) {
        if (orgType != OrganisationType.TAX) {
            throw new IllegalArgumentException("Only tax organisations can use this verification");
        }

        DigitalId digitalId = storage.findById(id);
        if (digitalId == null) {
            throw new IllegalArgumentException("ID not found");
        }

        eventLog.log("TAX_VERIFICATION", id);

        if (digitalId.getStatus() == IdentityStatus.REVOKED) {
            return "ID is revoked";
        }
        if (digitalId.getStatus() == IdentityStatus.SUSPENDED) {
            return "ID is currently suspended";
        }
        return "ID is active";
    }

    public String verifyForEmployer(String id, OrganisationType orgType) {
        if (orgType != OrganisationType.EMPLOYER && orgType != OrganisationType.BANK) {
            throw new IllegalArgumentException("Only employer or bank organisations can use this verification");
        }

        DigitalId digitalId = storage.findById(id);
        if (digitalId == null) {
            throw new IllegalArgumentException("ID not found");
        }

        eventLog.log("EMPLOYER_VERIFICATION", id);

        if (digitalId.getStatus() == IdentityStatus.ACTIVE) {
            return "VALID";
        }
        return "INVALID";
    }

    public String verifyForDrivingLicence(String id, OrganisationType orgType) {
        if (orgType != OrganisationType.DRIVING_LICENCE) {
            throw new IllegalArgumentException("Only driving licence organisations can use this verification");
        }

        DigitalId digitalId = storage.findById(id);
        if (digitalId == null) {
            throw new IllegalArgumentException("ID not found");
        }

        eventLog.log("DRIVING_LICENCE_VERIFICATION", id);

        if (digitalId.getStatus() != IdentityStatus.ACTIVE) {
            return "ID is not active";
        }

        int birthYear = Integer.parseInt(digitalId.getDateOfBirth().substring(0, 4));
        int currentYear = java.time.LocalDate.now().getYear();
        int age = currentYear - birthYear;

        if (age < 17) {
            return "Does not meet minimum age requirement";
        }

        return "Eligible for driving licence";
    }

    public String verifyForLocalAuthority(String id, OrganisationType orgType) {
        if (orgType != OrganisationType.LOCAL_AUTHORITY) {
            throw new IllegalArgumentException("Only local authority organisations can use this verification");
        }

        DigitalId digitalId = storage.findById(id);
        if (digitalId == null) {
            throw new IllegalArgumentException("ID not found");
        }

        eventLog.log("LOCAL_AUTHORITY_VERIFICATION", id);

        if (digitalId.getStatus() == IdentityStatus.REVOKED) {
            return "ID is revoked";
        }
        if (digitalId.getStatus() == IdentityStatus.SUSPENDED) {
            return "ID is currently suspended";
        }
        return "ID is active. Address: " + digitalId.getAddress();
    }
}
