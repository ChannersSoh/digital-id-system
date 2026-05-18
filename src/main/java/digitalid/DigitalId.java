package digitalid;

public class DigitalId {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private String address;
    private IdentityStatus status;

    public DigitalId(String id, String firstName, String lastName, String dateOfBirth, String address) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.status = IdentityStatus.ACTIVE;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public IdentityStatus getStatus() {
        return status;
    }

    public void setStatus(IdentityStatus status) {
        this.status = status;
    }
}
