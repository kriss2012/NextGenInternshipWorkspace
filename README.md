# Next Gen Software Hub Pvt. Ltd. — Java Programming Internship

![Java](https://img.shields.io/badge/Java-21%20LTS-orange.svg)
![Status](https://img.shields.io/badge/Status-Completed-success.svg)
![Architecture](https://img.shields.io/badge/OOP-Modular%20DAO%20%26%20REST-blue.svg)

A repository covering the 4-week **Java Programming Internship Task** curriculum assigned by **Next Gen Software Hub Pvt. Ltd.**

---

## 🏢 Company & Internship Profile
- **Organization**: Next Gen Software Hub Pvt. Ltd.
- **Website**: [www.nextgensoftwarehub.in](https://www.nextgensoftwarehub.in)
- **Email**: admin@nextgensoftwarehub.in
- **Address**: Musslhapur Patna, 800006
- **Phone**: +91 9508220237

---

## 📅 4-Week Curriculum & Projects Index

| Week | Focus Module | Concept Files | Completed Projects |
|---|---|---|---|
| **Week 1** | **Introduction to Java Basics** | `HelloJava.java`, `VariablesAndDataTypes.java`, `ControlStatements.java` | **1. Student Grade Calculator**<br>**2. Number Guessing Game** |
| **Week 2** | **Object-Oriented Programming (OOP)** | `EncapsulationDemo.java`, `InheritancePolymorphismDemo.java`, `AbstractionInterfaceDemo.java` | **1. Extensible OOP Calculator**<br>**2. Digital Banking System** (Savings, Current, Overdraft, Ledger) |
| **Week 3** | **Advanced Java Concepts** | `ExceptionHandlingDemo.java`, `FileHandlingDemo.java`, `MultithreadingDemo.java` | **Employee Management System** (File I/O, CSV Persistence, Background AutoSave Daemon) |
| **Week 4** | **Database & REST API Integration** | JDBC Connectivity, Parameterized Queries, SQL Schemas | **Library Management System** (Full CRUD, JDBC DAO, Standalone REST API Server on port 8080, Postman Collection) |

---

## 📁 Repository Directory Structure

```text
c:\D Drive\f\
│
├── run.ps1                                    # Unified interactive CLI launcher for all modules
├── README.md                                  # Repository documentation & syllabus mapping
│
├── week1-basics/                              # Week 1: Java Basics
│   ├── README.md
│   └── src/com/nextgen/week1/
│       ├── HelloJava.java                     # JVM environment & main entry
│       ├── VariablesAndDataTypes.java         # Primitives, widening, narrowing, operators
│       ├── ControlStatements.java             # if-else, switch expressions, loops
│       └── projects/
│           ├── GradeCalculator.java           # GPA (10-point), letter grades, honours
│           └── NumberGuessingGame.java        # Hot/cold hints, difficulties, score
│
├── week2-oop/                                 # Week 2: Object-Oriented Programming
│   ├── README.md
│   └── src/com/nextgen/week2/
│       ├── concepts/
│       │   ├── EncapsulationDemo.java         # Data hiding & validation
│       │   ├── InheritancePolymorphismDemo.java # Base/derived, dynamic method dispatch
│       │   └── AbstractionInterfaceDemo.java  # Abstract classes & multiple interfaces
│       ├── calculator/
│       │   ├── Operation.java                 # Functional strategy interface
│       │   ├── Calculator.java                # Extensible operation registry
│       │   └── CalculatorApp.java             # Interactive CLI calculator
│       └── banking/
│           ├── model/
│           │   ├── Account.java               # Abstract base account
│           │   ├── SavingsAccount.java        # Min balance & interest calculations
│           │   ├── CurrentAccount.java        # Overdraft limit facility
│           │   └── Transaction.java           # Immutable transaction ledger
│           ├── service/
│           │   ├── BankService.java           # Service interface
│           │   └── BankServiceImpl.java       # Atomic thread-safe operations
│           └── BankingApp.java                # Full interactive ATM/Bank CLI
│
├── week3-advanced/                            # Week 3: Advanced Java Concepts
│   ├── README.md
│   ├── data/
│   │   └── employees.csv                      # Persistent storage database
│   └── src/com/nextgen/week3/
│       ├── concepts/
│       │   ├── ExceptionHandlingDemo.java     # Checked vs unchecked, try-with-resources
│       │   ├── FileHandlingDemo.java          # BufferedReader/Writer & Java NIO
│       │   └── MultithreadingDemo.java        # Concurrency, synchronization, atomic types
│       ├── exceptions/
│       │   ├── EmployeeNotFoundException.java # Custom exception
│       │   └── InvalidInputException.java     # Custom validation exception
│       └── employee/
│           ├── model/Employee.java            # Employee entity with CSV parsing
│           ├── storage/EmployeeStorage.java   # File I/O persistence engine
│           ├── service/EmployeeService.java   # Business logic & validations
│           ├── service/AutoSaveDaemon.java    # Background multithreaded auto-flusher
│           └── EmployeeApp.java               # Interactive Employee Management CLI
│
└── week4-database-api/                        # Week 4: Database & REST API Integration
    ├── README.md
    ├── LibraryManagementAPI.postman_collection.json # Direct Postman import collection
    ├── sql/
    │   ├── mysql_schema.sql                   # MySQL production schema with seed data
    │   └── sqlite_schema.sql                  # SQLite fallback schema
    └── src/com/nextgen/week4/
        ├── config/DatabaseConfig.java          # JDBC Connection provider & diagnostics
        ├── model/
        │   ├── Book.java                      # Book entity model
        │   ├── Member.java                    # Member entity model
        │   └── BorrowRecord.java              # Lending transaction model
        ├── dao/
        │   ├── BookDao.java & BookDaoImpl.java       # JDBC CRUD & PreparedStatements
        │   ├── MemberDao.java & MemberDaoImpl.java   # Member DAO
        │   └── BorrowDao.java & BorrowDaoImpl.java   # Borrow transaction manager
        ├── api/
        │   ├── SimpleJson.java                # Lightweight JSON parser/serializer
        │   └── LibraryRestApiServer.java      # Built-in HTTP REST API Server
        └── LibraryApp.java                    # Main dual CLI & live REST API launcher
```

---

## ⚡ Quick Start: Interactive Runner

The easiest way to explore, compile, and run all tasks is using the unified PowerShell runner:

```powershell
# In PowerShell inside this directory:
powershell -ExecutionPolicy Bypass -File .\run.ps1
```

This presents an interactive menu to launch any concept demonstration, mini-project, or server with a single keystroke.

---

## 🛠️ Manual Compilation and Execution

If you prefer compiling individual weeks using standard Java CLI tools:

### 1. Compile All Weeks
```powershell
javac -d week1-basics/bin $(Get-ChildItem -Path week1-basics/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
javac -d week2-oop/bin $(Get-ChildItem -Path week2-oop/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
javac -d week3-advanced/bin $(Get-ChildItem -Path week3-advanced/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
javac -d week4-database-api/bin $(Get-ChildItem -Path week4-database-api/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })
```

### 2. Run Week 1: Basics & Projects
```powershell
# Hello Java & JVM info
java -cp week1-basics/bin com.nextgen.week1.HelloJava

# Grade Calculator Project
java -cp week1-basics/bin com.nextgen.week1.projects.GradeCalculator

# Number Guessing Game Project
java -cp week1-basics/bin com.nextgen.week1.projects.NumberGuessingGame
```

### 3. Run Week 2: OOP & Projects
```powershell
# Extensible OOP Calculator
java -cp week2-oop/bin com.nextgen.week2.calculator.CalculatorApp

# Digital Banking System
java -cp week2-oop/bin com.nextgen.week2.banking.BankingApp
```

### 4. Run Week 3: Advanced Java & Employee Management System
```powershell
# Employee Management System with File Persistence & AutoSave Daemon
java -cp week3-advanced/bin com.nextgen.week3.employee.EmployeeApp
```

### 5. Run Week 4: Library Management System & REST API
```powershell
# Launches Interactive CLI + Starts Background REST API Server at http://localhost:8080/api/
java -cp week4-database-api/bin com.nextgen.week4.LibraryApp
```

---

## 🧪 Testing the Week 4 REST API
Once `LibraryApp` is running, the server is live on `http://localhost:8080/api/`. You can test it via browser, Postman, or PowerShell / cURL:

- **Health Check**:
  ```powershell
  curl http://localhost:8080/api/health
  ```
- **List All Books**:
  ```powershell
  curl http://localhost:8080/api/books
  ```
- **Search Books**:
  ```powershell
  curl "http://localhost:8080/api/books?search=Clean"
  ```
- **Add New Book**:
  ```powershell
  Invoke-RestMethod -Uri "http://localhost:8080/api/books" -Method Post -Body '{"title":"Designing Data-Intensive Applications","author":"Martin Kleppmann","isbn":"978-1449373320","genre":"Architecture","copies":3}' -ContentType "application/json"
  ```
- **Postman**: Import `week4-database-api/LibraryManagementAPI.postman_collection.json` into Postman to run pre-configured tests.

---

## 📜 References & Resources
- **W3Schools Java**: [www.w3schools.com/java/](https://www.w3schools.com/java/)
- **GeeksforGeeks Java**: [www.geeksforgeeks.org/java/](https://www.geeksforgeeks.org/java/)
- **Oracle Java Documentation**: [docs.oracle.com](https://docs.oracle.com)
- **Tools**: IntelliJ IDEA / Eclipse IDE, MySQL Database, Postman for API Testing.
