# Wallet Project

This project is a **Kotlin-based microservice** for wallet management, 
including features like creating wallets, processing transactions (credit, withdrawal, and transfers),
checking balances, and generating statements. 
It uses **Spring Boot** as the primary framework and adheres to domain-driven design principles.

## Frameworks and Dependencies

- **Kotlin**: Main programming language.
- **Spring Boot**: Framework for application configuration and management.
- **Spring Data JPA**: For database interactions using repositories.
- **SLF4J**: For logging throughout the application.
- **Docker**: For containerizing the application.

---

## How to Compile and Run the Application

To run the Wallet Service, follow these steps:

### **1. Requirements**
Make sure the following software is installed:
- **JDK 21**
- **Docker**: For creating and running the Docker container.

### **2. Compile and Package**
Run the following commands in the root directory of the project:

```bash
# Clean and install the project
./gradlew clean build
```

### **3. Run Locally**
Once compilation is done, you can run the application locally:

```bash
# Run the application using Maven
./gradlew bootRun
```

By default, the application will start on port `8080`. You can configure this in the `application.properties` file.

---

## Build and Deploy the Docker Image

The project includes a `Dockerfile` to build a Docker image of the application.

### **1. Create Docker Image**
Run the following command to build the Docker image:

**IMPORTANT**: docker build command will only work after gradle build


```bash
docker build -t wallet-service:latest .
```

### **2. Run the Docker Container**
Once the image is created, you can run a container using:

```bash
docker run -p 8080:8080 wallet-service:latest
```

### **3. Access the Application**
After starting the Docker container, the application will be accessible at `http://localhost:8080`.

To access application documentation, please visit: `http://localhost:8080/docs`

To access application metrics, please visit: `http://localhost:8080/actuator/prometheus`

---

With the above instructions, you should be able to compile, run, and containerize the wallet service. 

---

## Decisions

I implemented a system based on **Hexagonal Architecture**, separating it into three layers:

1. **Application Layer**: Handles entry points (e.g., REST controllers) and configuration.
2. **Domain Layer**: Contains the core business logic, independent of frameworks or infrastructure.
3. **Resource Layer**: Manages infrastructure components like databases, and external systems.

To ensure traceability, I created an **Audit Log Interface** that exposes wallet-related operations for logging, 

Distributed lock was not implemented for this project due to time constraints, but REDIS is a very good choice to this 
kind of implementation, I focused on system design and to implement all proposed features.
