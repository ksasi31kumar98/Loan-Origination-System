
# Loan Origination System (Backend)

A scalable and thread-safe **Loan Origination System** built using **Spring Boot** and **MySQL**, designed to handle high volumes of loan applications with automated processing, agent assignment, and manual decision workflows.

---

## Features

- Automated loan processing using background workers
- Thread-safe agent assignment with database locking
- Manual loan approval/rejection by agents
- Decoupled notification system (mocked)
- Clean layered architecture
- Unit-tested core business logic

---

## Architecture Overview

- **Controller Layer** – Exposes REST APIs
- **Service Layer** – Business logic and workflows
- **Repository Layer** – Database access using Spring Data JPA
- **Entity Layer** – Domain models and mappings
- **Notification Layer** – Decoupled notification interface

---

## Evaluation Criteria Coverage

### Scalability
- Loans are processed asynchronously using scheduled background workers
- Supports concurrent loan processing without conflicts

### Thread-Safety
- Uses **PESSIMISTIC_WRITE locks** during:
    - Loan processing
    - Agent assignment
- Prevents race conditions in multi-threaded execution

### Clean Architecture
- Clear separation of concerns
- Interfaces used for services and notifications
- Modular and maintainable codebase

### Mock Integration
- `NotificationService` interface used
- `MockNotificationService` logs notifications instead of sending real SMS/email
- Easily replaceable with real notification providers

### Database Design
- Normalized schema
- Separate tables for:
    - Users
    - Loans
    - Agent status
    - Agent hierarchy
- Foreign keys and indexes used where applicable

### Testing
- Unit tests written for core services
- Mockito used for mocking repositories and notifications
- Focus on business logic validation

### Code Quality
- Readable and consistent code
- SOLID principles followed
- No tight coupling between components

---

## Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL
- JUnit 5
- Mockito

---

## Running the Application Locally

### Prerequisites
- Java 17 or higher
- Maven
- MySQL 8+

---

### Database Setup

Create database:

```sql
CREATE DATABASE loan_origination;

```
Update application.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/loan_origination
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

```
### Build and Run

```bash

mvn clean install
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.
---
## API Endpoints
- `POST http://localhost:8080/api/v1/loans` – Submit a new loan application
Request Body:
```json
{
  "customerName": "sasi",
  "customerPhone": "98895498510",
  "loanAmount": 700000,
  "loanType": "PERSONAL"
}
``` 
Response:
```json
{
  "createdAt": "2025-12-26T10:16:22.1301893",
  "customerName": "sasi",
  "customerPhone": "98895498510",
  "loanAmount": 700000.0,
  "loanId": 1,
  "loanType": "PERSONAL",
  "status": "APPLIED"
}
```
if the loan amount is greater than 500000 and lesser than 1000000, it will be assigned to a available agent and you will get a notification
in the logs like below
``` 
2025-12-26T10:16:30.056+05:30  INFO 38564 --- [loan-origination-system] [  Loan-worker-1] c.t.l.n.MockNotificationService          : Notification sent to agent Agent One for loan 1
  
```
- `POST http://localhost:8080/api/v1/loans` – Submit a new loan application
Request Body:
```json
{
  "customerName": "Arun",
  "customerPhone": "9876500000",
  "loanAmount": 700000,
  "loanType": "HOME"
}
```
Response:
```json
{
  "createdAt": "2025-12-26T10:22:48.4766016",
  "customerName": "Arun",
  "customerPhone": "9876500000",
  "loanAmount": 700000.0,
  "loanId": 2,
  "loanType": "HOME",
  "status": "APPLIED"
}
```
if there is manager to agent assignment, you will get a notification in the logs like below
```
2025-12-26T10:23:00.220+05:30  INFO 38564 --- [loan-origination-system] [  Loan-worker-2] c.t.l.n.MockNotificationService          : Notification sent to agent Agent Two for loan 2
2025-12-26T10:23:00.224+05:30  INFO 38564 --- [loan-origination-system] [  Loan-worker-2] c.t.l.n.MockNotificationService          : Notification sent to manager Agent One for loan 2
```
- `POST http://localhost:8080/api/v1/loans` – Submit a new loan application
- Request Body:
```json
{
  "customerName": "Kumar",
  "customerPhone": "9999999999",
  "loanAmount": 1500000,
  "loanType": "HOME"
}
```
Response:
```json
{
  "createdAt": "2025-12-26T10:26:56.3807489",
  "customerName": "Kumar",
  "customerPhone": "9999999999",
  "loanAmount": 1500000.0,
  "loanId": 3,
  "loanType": "HOME",
  "status": "APPLIED"
}
```
If the loan amount is greater than 1000000, it will be Rejected By system and you can see the staus change in the database table loan.```



```
|  3 | 2025-12-26 10:26:56.380749 |     1500000 | HOME      | REJECTED_BY_SYSTEM |       1 |              NULL |          13 |
```
- `POST http://localhost:8080/api/v1/loans` – Submit a new loan application

Request Body:
```json
{
  "customerName": "Amit",
  "customerPhone": "9999999979",
  "loanAmount": 400000,
  "loanType": "HOME"
}
```
Response:
```json
{
  "createdAt": "2025-12-26T10:35:17.8238992",
  "customerName": "Amit",
  "customerPhone": "9999999979",
  "loanAmount": 400000.0,
  "loanId": 4,
  "loanType": "HOME",
  "status": "APPLIED"
}
```
If the loan amount is lesser than 600000, it will be Approved By system and you can see the staus change in the database table loan.
```
|  4 | 2025-12-26 10:35:17.823899 |      400000 | HOME      | APPROVED_BY_SYSTEM |       1 |              NULL |          14 |
```
-PUT `http://localhost:8080/api/v1/loans/{loanId}/decision` – Make manual decision on a loan
        http://localhost:8080/api/v1/agents/1/loans/1/decision
Request Body:
```json
{
  "decision":"APPROVE"
}
```
Response:
```json
{
  "message": "Loan decision processed successfully"
}
```
If the agent approves the loan, you can see the status change in the database table loan.
```
|  1 | 2025-12-26 10:16:22.130189 |      700000 | PERSONAL  | APPROVED_BY_AGENT  |       3 |                 1 |          11 |
```
-PUT `http://localhost:8080/api/v1/loans/{loanId}/decision` – Make manual decision on a loan
        http://localhost:8080/api/v1/agents/2/loans/2/decision
Request Body:
```json
{
  "decision":"REJECT"
}
```
Response:
```json
{
  "message": "Loan decision processed successfully"
}
```
If the agent rejects the loan, you can see the status change in the database table loan.
```
|  2 | 2025-12-26 10:22:48.476602 |      700000 | HOME      | REJECTED_BY_AGENT  |       2 |                 1 |          12 |
```
-PUT `http://localhost:8080/api/v1/loans/{loanId}/decision` – Make manual decision on a loan
        http://localhost:8080/api/v1/agents/2/loans/1/decision
Request Body:
```json
{
  "decision":"REJECT"
}
```
Response:
```json

Agent 2 is not assigned to loan 3

```
if an agent who is not assigned to the loan is trying to make a decision, you will get the above custom exception response.

```
```
```
```
-PUT `http://localhost:8080/api/v1/loans/{loanId}/decision` – Make manual decision on a loan
http://localhost:8080/api/v1/agents/5/loans/7/decision
Request Body:
```json
{
  "decision":"APPROVE"
}
```
Response:
```json

  Loan with id 7 not found

```
if an agent is trying to make a decision on a loan which is not present, you will get the above custom exception response.

```
```
```
```
-GET http://localhost:8080/api/v1/loans/status-count
Response:
```json
{
  "APPROVED_BY_SYSTEM": 1,
  "REJECTED_BY_SYSTEM": 1,
  "APPROVED_BY_AGENT": 1,
  "REJECTED_BY_AGENT": 1
}
```
- Get count of loans by status
```
```
```
```
-GET http://localhost:8080/api/v1/customers/top
Response:
```json
[
  {
    "approvedLoanCount": 1,
    "customerName": "sasi",
    "customerPhone": "98895498510"
  },
  {
    "approvedLoanCount": 1,
    "customerName": "Amit",
    "customerPhone": "9999999979"
  }
]
```
- Get top 3 customers with approved loans
```
```
```
```
-GET http://localhost:8080/api/v1/loans?status=APPROVED_BY_SYSTEM&page=0&size=0
Response:
```json
{
  "content": [
    {
      "createdAt": "2025-12-26T10:35:17.823899",
      "customerName": "Amit",
      "customerPhone": "9999999979",
      "loanAmount": 400000.0,
      "loanId": 4,
      "loanType": "HOME",
      "status": "APPROVED_BY_SYSTEM"
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 1,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "unpaged": false
  },
  "size": 20,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "totalElements": 1,
  "totalPages": 1
}
```
- Get loans by status with pagination
```
```
```
```
---
## GitHub Repository
link: [https://github.com/ksasi31kumar98/Loan-Origination-System]

















