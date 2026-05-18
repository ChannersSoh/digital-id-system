package digitalid;

public class DigitalIdService {

    private final DigitalIdValidator validator = new DigitalIdValidator();
    private final DigitalIdStorage storage = new DigitalIdStorage();

    public DigitalId createDigitalId(String id, String firstName, String lastName, String dateOfBirth, String address) {
        if (!validator.validate(id, firstName, lastName, dateOfBirth, address)) {
            throw new IllegalArgumentException("All fields must be provided");
        }

        if (storage.findById(id) != null) {
            throw new IllegalArgumentException("ID already exists");
        }

        DigitalId digitalId = new DigitalId(id, firstName, lastName, dateOfBirth, address);
        storage.save(digitalId);
        return digitalId;
    }

    public DigitalId getDigitalId(String id) {
        return storage.findById(id);
    }

    public void updateAddress(String id, String newAddress) {
        DigitalId digitalId = storage.findById(id);
        if (digitalId == null) {
            throw new IllegalArgumentException("ID not found");
        }

        if (digitalId.getStatus() == IdentityStatus.REVOKED) {
            throw new IllegalStateException("Cannot update a revoked ID");
        }

        if (newAddress == null || newAddress.isEmpty()) {
            throw new IllegalArgumentException("New address must not be empty");
        }

        digitalId.setAddress(newAddress);
    }

    public void changeStatus(String id, IdentityStatus newStatus) {
        DigitalId digitalId = storage.findById(id);
        if (digitalId == null) {
            throw new IllegalArgumentException("ID not found");
        }

        IdentityStatus currentStatus = digitalId.getStatus();

        if (currentStatus == IdentityStatus.REVOKED) {
            throw new IllegalStateException("Cannot change status of a revoked ID");
        }

        if (currentStatus == newStatus) {
            throw new IllegalStateException("ID already has that status");
        }

        digitalId.setStatus(newStatus);
    }
}
