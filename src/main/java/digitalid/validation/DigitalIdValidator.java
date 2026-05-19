package digitalid.validation;

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
        if (!isValidDate(dateOfBirth)) {
            return false;
        }
        if (address == null || address.isEmpty()) {
            return false;
        }
        return true;
    }

    public boolean isValidDate(String date) {
        if (date.length() != 10) {
            return false;
        }
        if (date.charAt(4) != '-' || date.charAt(7) != '-') {
            return false;
        }

        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            int day = Integer.parseInt(date.substring(8, 10));

            if (year < 1800 || year > java.time.LocalDate.now().getYear()) {
                return false;
            }
            if (month < 1 || month > 12) {
                return false;
            }
            if (day < 1 || day > 31) {
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
