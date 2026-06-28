# Bank Console Application

A Java Console-Based Banking Application built using Core Java principles. The project demonstrates Object-Oriented Programming (OOP), Collections, Exception Handling, Java Streams, and layered architecture.

## Features

- Create Customer
- Open Savings/Current Account
- List All Accounts
- Deposit Money
- Withdraw Money
- Transfer Money Between Accounts
- View Account Statement
- Search Accounts by Customer Name
- Transaction History
- Custom Exception Handling
- Input Validation

## Technologies Used

- Java 17+
- Object-Oriented Programming (OOP)
- Collections Framework
- Java Streams
- Exception Handling
- UUID
- LocalDateTime

## Project Structure

```
src
├── app
│   └── Main.java
├── domain
│   ├── Account.java
│   ├── Customer.java
│   ├── Transaction.java
│   └── Type.java
├── exception
│   ├── AccountNotFoundException.java
│   ├── InsufficientFundsException.java
│   └── InvalidAmountException.java
├── repository
│   ├── AccountRepository.java
│   ├── CustomerRepository.java
│   └── TransactionRepository.java
└── service
    ├── BankService.java
    └── impl
        └── BankServiceImpl.java
```

## Functionalities

### Open Account

- Create a new customer
- Generate a unique Customer ID
- Generate a unique Account Number
- Select Account Type
  - Savings
  - Current

### Deposit

- Deposit money into an account
- Records every deposit as a transaction
- Prevents invalid deposit amounts

### Withdraw

- Withdraw money
- Checks sufficient balance
- Records withdrawal transaction

### Transfer

- Transfer money between accounts
- Prevents transfer to the same account
- Validates balance
- Creates both Transfer Out and Transfer In transactions

### Statement

Displays complete transaction history including:

- Deposit
- Withdraw
- Transfer In
- Transfer Out

### Search

Search customer accounts by customer name.

## Custom Exceptions

- AccountNotFoundException
- InsufficientFundsException
- InvalidAmountException

## Sample Output

```
========== BANK MENU ==========
1. Open Account
2. List Accounts
3. Deposit
4. Withdraw
5. Transfer
6. Statement
7. Search Customer
0. Exit
===============================

Choose: 1

Name: Afzal
Email: afzal@example.com
Account Type: Savings

Account Created !
Account Number: Ac000001
```

## Learning Outcomes

This project demonstrates:

- Object-Oriented Programming
- Layered Architecture
- Java Streams
- Collections
- Exception Handling
- Repository Pattern
- Business Logic Separation
- Transaction Management
- Clean Code Practices

## Future Improvements

- File-Based Storage
- JDBC Database Integration
- Spring Boot REST API
- Authentication
- Interest Calculation
- Account Closure
- Unit Testing using JUnit
- Logging
- Docker Support

## Author

**Mohammed Afzal**

GitHub: https://github.com/Mohammed-Afzal21

---

⭐ If you found this project useful, consider giving it a star.
