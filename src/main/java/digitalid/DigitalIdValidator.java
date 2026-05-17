package digitalid;
public class DigitalIdValidator {

    public boolean validate(String id, String firstName, String lastName, String dateOfBirth, String address) {
        if (id == null || id.isEmpty()) {
            return false;
        }
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        if (lastName == null || lastName.isEmpty()) {
            return false;
        }
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return false;
        }
        // TODO: should the date format be checked for validity?
        if (address == null || address.isEmpty()) {
            return false;
        }
        return true;
    }
}
