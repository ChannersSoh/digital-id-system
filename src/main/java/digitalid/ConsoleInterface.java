package digitalid;

import java.util.Scanner;

public class ConsoleInterface {

    private static final DigitalIdStorage storage = new DigitalIdStorage();
    private static final EventLog eventLog = new EventLog();
    private static final DigitalIdService service = new DigitalIdService(storage, eventLog);
    private static final VerificationService verificationService = new VerificationService(storage, eventLog);
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Digital ID System");
        System.out.println();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    createDigitalId();
                    break;
                case "2":
                    updateAddress();
                    break;
                case "3":
                    changeStatus();
                    break;
                case "4":
                    verifyId();
                    break;
                case "5":
                    viewLogs();
                    break;
                case "6":
                    lookupId();
                    break;
                case "7":
                    running = false;
                    System.out.println("Exiting system.");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
            System.out.println();
        }
    }

    private static void printMenu() {
        System.out.println("Menu");
        System.out.println("1. Create Digital ID");
        System.out.println("2. Update Address");
        System.out.println("3. Change Status");
        System.out.println("4. Verify ID");
        System.out.println("5. View Event Log");
        System.out.println("6. Lookup ID");
        System.out.println("7. Exit");
        System.out.print("Choose an option: ");
    }

    private static void createDigitalId() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter date of birth (YYYY-MM-DD): ");
        String dob = scanner.nextLine().trim();
        System.out.print("Enter address: ");
        String address = scanner.nextLine().trim();

        try {
            DigitalId digitalId = service.createDigitalId(id, firstName, lastName, dob, address);
            System.out.println("Digital ID created successfully for " + digitalId.getFirstName() + " " + digitalId.getLastName());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void updateAddress() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter new address: ");
        String newAddress = scanner.nextLine().trim();

        try {
            service.updateAddress(id, newAddress);
            System.out.println("Address updated successfully.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void changeStatus() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();

        DigitalId digitalId = service.getDigitalId(id);
        if (digitalId == null) {
            System.out.println("ID not found");
            return;
        }

        System.out.println("You are currently " + digitalId.getStatus() + ". Select new status:");
        System.out.println("1. ACTIVE");
        System.out.println("2. SUSPENDED");
        System.out.println("3. REVOKED");
        System.out.print("Select an option: ");
        String statusChoice = scanner.nextLine().trim();

        IdentityStatus newStatus;
        switch (statusChoice) {
            case "1":
                newStatus = IdentityStatus.ACTIVE;
                break;
            case "2":
                newStatus = IdentityStatus.SUSPENDED;
                break;
            case "3":
                newStatus = IdentityStatus.REVOKED;
                break;
            default:
                System.out.println("Invalid status choice.");
                return;
        }

        try {
            service.changeStatus(id, newStatus);
            System.out.println("Status changed to " + newStatus);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verifyId() {
        System.out.print("Enter ID to verify: ");
        String id = scanner.nextLine().trim();
        System.out.println("Select organisation type:");
        System.out.println("1. Tax");
        System.out.println("2. Employer");
        System.out.println("3. Bank");
        System.out.println("4. Driving Licence");
        System.out.println("5. Local Authority");
        System.out.print("Choice: ");
        String orgChoice = scanner.nextLine().trim();

        try {
            String result;
            switch (orgChoice) {
                case "1":
                    result = verificationService.verifyForTax(id, OrganisationType.TAX);
                    break;
                case "2":
                    result = verificationService.verifyForEmployer(id, OrganisationType.EMPLOYER);
                    break;
                case "3":
                    result = verificationService.verifyForEmployer(id, OrganisationType.BANK);
                    break;
                case "4":
                    result = verificationService.verifyForDrivingLicence(id, OrganisationType.DRIVING_LICENCE);
                    break;
                case "5":
                    result = verificationService.verifyForLocalAuthority(id, OrganisationType.LOCAL_AUTHORITY);
                    break;
                default:
                    System.out.println("Invalid organisation choice.");
                    return;
            }
            System.out.println("Verification result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void viewLogs() {
        if (eventLog.getLogs().isEmpty()) {
            System.out.println("No events logged yet.");
            return;
        }
        System.out.println("Event Log");
        for (String entry : eventLog.getLogs()) {
            System.out.println(entry);
        }
    }

    private static void lookupId() {
        System.out.print("Enter ID: ");
        String id = scanner.nextLine().trim();

        DigitalId digitalId = service.getDigitalId(id);
        if (digitalId == null) {
            System.out.println("ID not found.");
            return;
        }

        System.out.println("-Digital ID Details");
        System.out.println("ID: " + digitalId.getId());
        System.out.println("Name: " + digitalId.getFirstName() + " " + digitalId.getLastName());
        System.out.println("Date of Birth: " + digitalId.getDateOfBirth());
        System.out.println("Address: " + digitalId.getAddress());
        System.out.println("Status: " + digitalId.getStatus());
    }
}
