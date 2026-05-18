package digitalid;

public class DigitalIdService {

    private final DigitalIdValidator validator = new DigitalIdValidator();
    private final DigitalIdStorage storage = new DigitalIdStorage();

    public DigitalId createDigitalId(String id, String firstName, String lastName, String dateOfBirth, String address) {
        if (!validator.validate(id, firstName, lastName, dateOfBirth, address)) {
            return null;
        }

        if (storage.findById(id) != null) {
            return null;
        }

        DigitalId digitalId = new DigitalId(id, firstName, lastName, dateOfBirth, address);
        storage.save(digitalId);
        return digitalId;
    }

    public DigitalId getDigitalId(String id) {
        return storage.findById(id);
    }
}
