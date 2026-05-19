# Digital ID System

A console-based backend system for managing digital identities across a federated ecosystem of organisations.

## GitHub Repository

https://github.com/ChannersSoh/digital-id-system

## How to Run

### Requirements
- Java 17
- Maven

### Clone the repository
```
git clone https://github.com/ChannersSoh/digital-id-system
```

### Running the interface
```
mvn compile exec:java -Dexec.mainClass=digitalid.ConsoleInterface
```

### Running the tests
```
mvn clean test
```

## System Structure

The system is split into two main areas: identity management and identity verification.

### Main Classes

- **DigitalId.java** - Represents an identity with attributes like name, date of birth, address, and status.
- **IdentityStatus.java** - Enum for the status of an ID (ACTIVE, SUSPENDED, REVOKED).
- **DigitalIdService.java** - Handles creating IDs, updating address, and changing status.
- **DigitalIdValidator.java** - Validates the data being passed into DigitalId
- **DigitalIdStorage.java** - Stores all digital IDs in memory using an ArrayList.
- **VerificationService.java** - Handles verification requests from different organisations.
- **OrganisationType.java** - Enum for the different types of organisations that interact with the system.
- **EventLog.java** - Records all actions (creation, updates, status changes, verifications) with timestamps.
- **ConsoleInterface.java** - The console interface with a menu for interacting with the system.

### Design Decisions

- Identity management and verification are kept in separate services to keep concerns separated.
- Each organisation type gets a different verification method that returns only the information relevant to their needs.
- Revoked IDs cannot be updated or have their status changed which is enforced by the service layer.
- All fields except address are immutable once created.
- Events are logged for all key actions, so that they can be referenced easily when necessary.
