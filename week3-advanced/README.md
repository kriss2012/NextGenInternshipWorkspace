# Week 3: Advanced Java Concepts

**Next Gen Software Hub Pvt. Ltd. — Internship Program**

---

## 📌 Syllabus Overview
1. **Exception Handling**:
   - `try-catch-finally` constructs.
   - Checked vs Unchecked exceptions.
   - `try-with-resources` (`AutoCloseable`).
   - Custom business exceptions (`EmployeeNotFoundException`, `InvalidInputException`).
2. **File Handling**:
   - Traditional Streams (`BufferedReader`, `BufferedWriter`, `FileReader`, `FileWriter`).
   - Modern Java NIO (`java.nio.file.Files`, `Path`, `Paths`).
   - Parsing, reading, and persisting structured CSV files.
3. **Multithreading Basics**:
   - `Thread`, `Runnable`, synchronization (`synchronized`), and thread safety.
   - Background Daemon threads (`setDaemon(true)`).
4. **Project: Employee Management System**:
   - Persistent CSV storage under `week3-advanced/data/employees.csv`.
   - Comprehensive CRUD operations, department filters, and validation.
   - Background `AutoSaveDaemon` periodically persisting modifications automatically.

---

## 📂 Source Structure
- `src/com/nextgen/week3/concepts/`
  - `ExceptionHandlingDemo.java`
  - `FileHandlingDemo.java`
  - `MultithreadingDemo.java`
- `src/com/nextgen/week3/exceptions/`
  - `EmployeeNotFoundException.java`
  - `InvalidInputException.java`
- `src/com/nextgen/week3/employee/`
  - `model/Employee.java`
  - `storage/EmployeeStorage.java`
  - `service/EmployeeService.java`
  - `service/AutoSaveDaemon.java`
  - `EmployeeApp.java`

---

## 🚀 How to Compile & Run (CLI)

```powershell
# From the repository root (c:\D Drive\f):

# Compile all Week 3 files
javac -d week3-advanced/bin $(Get-ChildItem -Path week3-advanced/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })

# Run Exception Handling Demo
java -cp week3-advanced/bin com.nextgen.week3.concepts.ExceptionHandlingDemo

# Run File Handling Demo
java -cp week3-advanced/bin com.nextgen.week3.concepts.FileHandlingDemo

# Run Multithreading Demo
java -cp week3-advanced/bin com.nextgen.week3.concepts.MultithreadingDemo

# Run Main Project: Employee Management System
java -cp week3-advanced/bin com.nextgen.week3.employee.EmployeeApp
```
