package digitalid.storage;

import digitalid.model.DigitalId;
import java.util.ArrayList;
import java.util.List;

public class DigitalIdStorage {

    private final List<DigitalId> digitalIds = new ArrayList<>();

    public void save(DigitalId digitalId) {
        digitalIds.add(digitalId);
    }

    public DigitalId findById(String id) {
        for (DigitalId digitalId : digitalIds) {
            if (digitalId.getId().equals(id)) {
                return digitalId;
            }
        }
        return null;
    }

    public List<DigitalId> findAll() {
        return digitalIds;
    }
}
