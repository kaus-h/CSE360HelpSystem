# CSE360 Help System

A JavaFX coursework project that prototypes a role-aware student help system with authentication, invitation-based onboarding, user administration, and multi-role access.

The project was built for ASU CSE 360 to practice software-engineering fundamentals through a desktop application with separated UI, authentication, user-service, and persistence responsibilities.

## Implemented features

- JavaFX desktop interface for account setup and login
- First-user administrator bootstrap flow
- Password hashing before authentication
- Invitation-code generation and acceptance
- One-time password and expiration-state support
- Multiple user roles, including administrator, student, and instructor
- Add and remove role operations for existing users
- User listing and deletion workflows
- Service classes that separate authentication and user operations from the UI

## Architecture

```text
HelpSystemUI.java
    |
    +-- AuthenticationService.java
    |       |
    |       +-- Database.java
    |
    +-- UserService.java
            |
            +-- User.java
            +-- Role.java
```

### Main components

- `HelpSystemUI.java`: JavaFX application shell and user interaction flow
- `AuthenticationService.java`: login, invitation, password-reset, and role-management operations
- `UserService.java`: user collection and account operations
- `Database.java`: persistence abstraction used by the authentication layer
- `User.java`: user model and account state
- `Role.java`: application role definitions

## Engineering focus

This repository demonstrates early software-engineering work around:

- role-based access modeling
- authentication flow design
- separation of concerns
- object-oriented Java
- desktop UI development with JavaFX
- account lifecycle and administrative workflows

## Coursework context

The broader CSE 360 assignment described a multi-phase help platform for students and instructional staff. This repository contains the Phase 1 style identity and role-management prototype rather than the full multi-phase product described in the original course specification.

That distinction is intentional here so the README reflects what the code actually implements.

## Running the project

The source uses the package `com.usermanagement` and requires a Java environment with JavaFX available. Configure the files in a Java project with JavaFX on the module or class path, then run `HelpSystemUI` as the application entry point.