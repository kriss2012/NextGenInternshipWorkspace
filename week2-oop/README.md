# Week 2: Object-Oriented Programming (OOP)

**Next Gen Software Hub Pvt. Ltd. — Internship Program**

---

## 📌 Syllabus Overview
1. **Classes & Objects**: State, behavior, constructors, memory allocation.
2. **Inheritance & Polymorphism**:
   - `extends` and `super` keywords.
   - Dynamic Method Dispatch (Runtime polymorphism via method overriding).
   - Method Overloading (Compile-time polymorphism).
3. **Encapsulation**: Private fields, accessors/mutators with invariant validation, immutability.
4. **Abstraction & Interfaces**: Abstract classes with shared behavior, multiple interface implementation, default interface methods.
5. **Mini-Projects**:
   - **OOP Extensible Calculator**: Strategy pattern using functional interfaces.
   - **Comprehensive Banking System**: `Account`, `SavingsAccount`, `CurrentAccount` with overdraft protection, `Transaction` audit ledger, and atomic thread-safe transfers.

---

## 📂 Source Structure
- `src/com/nextgen/week2/concepts/`
  - `EncapsulationDemo.java`
  - `InheritancePolymorphismDemo.java`
  - `AbstractionInterfaceDemo.java`
- `src/com/nextgen/week2/calculator/`
  - `Operation.java`
  - `Calculator.java`
  - `CalculatorApp.java`
- `src/com/nextgen/week2/banking/`
  - `model/Transaction.java`
  - `model/Account.java`
  - `model/SavingsAccount.java`
  - `model/CurrentAccount.java`
  - `service/BankService.java`
  - `service/BankServiceImpl.java`
  - `BankingApp.java`

---

## 🚀 How to Compile & Run (CLI)

```powershell
# From the repository root (c:\D Drive\f):

# Compile all Week 2 files
javac -d week2-oop/bin $(Get-ChildItem -Path week2-oop/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })

# Run Encapsulation Concept Demo
java -cp week2-oop/bin com.nextgen.week2.concepts.EncapsulationDemo

# Run Inheritance & Polymorphism Concept Demo
java -cp week2-oop/bin com.nextgen.week2.concepts.InheritancePolymorphismDemo

# Run Abstraction & Interfaces Concept Demo
java -cp week2-oop/bin com.nextgen.week2.concepts.AbstractionInterfaceDemo

# Run Mini-Project 1: OOP Calculator
java -cp week2-oop/bin com.nextgen.week2.calculator.CalculatorApp

# Run Mini-Project 2: Banking System
java -cp week2-oop/bin com.nextgen.week2.banking.BankingApp
```
