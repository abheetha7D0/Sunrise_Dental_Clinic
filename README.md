# Sunrise Dental Clinic Management System

A Java-based desktop application designed to manage clinic operations, patient records, dentist schedules, appointment bookings, and automated billing with email notifications.

---

## Key Features

* **User Authentication & Session Management:** Security token registration, role-based access control, and user session handling.
* **Patient Management:** Create, update, search, and delete patient profile records.
* **Dentist Catalog:** Manage dentist details, specialization tracking, and availability statuses.
* **Appointment Scheduling:** Book, update, or cancel patient appointments without schedule collisions.
* **Treatment Catalog:** Track available dental procedures, descriptions, and pricing.
* **Billing & Invoicing:** Calculate invoices dynamically with custom discounts and email bills directly to patients.

---

## Software Architecture & Design Patterns

The project follows clean object-oriented architecture and industry-standard software design patterns:

* **MVC & Data Transfer Object (DTO) Pattern:** Decouples the UI views from backend controllers and database models by encapsulating user input data into lightweight DTO objects for clean data transfer across application layers.
* **Factory Design Pattern:** Dynamically instantiates required service and object instances based on user permissions, application roles, or operational contexts.
* **Singleton Design Pattern:** Guarantees single global instances for centralized state management, such as `UserSession` and database connection handling across screens.
* **Data Access Object (DAO) Pattern:** Isolates low-level database operations from core business logic.

---

## Tech Stack

* **Language:** Java 100%
* **GUI Framework:** Java Swing / AWT
* **IDE / Build Tool:** NetBeans (`nbproject`, `build.xml`, Apache Ant)
* **Database:** MySQL

---

## Repository Structure

```text
Sunrise_Dental_Clinic/
├── lib/           # Dependencies & JAR libraries
├── nbproject/     # NetBeans project configuration
├── src/           # Java source code (MVC, DTOs, Factories, DAOs, Utilities)
├── .gitignore     # Git ignore configuration
├── build.xml      # Ant build configuration script
└── manifest.mf    # Application manifest metadata
