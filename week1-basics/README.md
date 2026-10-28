# Week 1: Introduction to Java Basics

**Next Gen Software Hub Pvt. Ltd. — Internship Program**

---

## 📌 Syllabus Overview
1. **Installation & Setup**: JDK configuration (OpenJDK 21), IDE setup (IntelliJ IDEA, Eclipse, VS Code).
2. **Variables, Data Types, and Operators**: Primitive types, type conversion (widening vs narrowing), arithmetic, logical, relational, and bitwise operators.
3. **Control Statements**: `if-else`, modern `switch` expressions, `for`, `while`, `do-while`, jump statements (`break`, `continue`).
4. **Simple Console Projects**:
   - `GradeCalculator`: Computes grades, percentage, GPA (10-point scale), and honors.
   - `NumberGuessingGame`: Configurable difficulty, hot/cold hints, score calculation, replay mode.

---

## 📂 Source Files
- `src/com/nextgen/week1/HelloJava.java`
- `src/com/nextgen/week1/VariablesAndDataTypes.java`
- `src/com/nextgen/week1/ControlStatements.java`
- `src/com/nextgen/week1/projects/GradeCalculator.java`
- `src/com/nextgen/week1/projects/NumberGuessingGame.java`

---

## 🚀 How to Compile & Run (CLI)

```powershell
# From the repository root (c:\D Drive\f):

# Compile all Week 1 files
javac -d week1-basics/bin $(Get-ChildItem -Path week1-basics/src -Recurse -Filter *.java | ForEach-Object { $_.FullName })

# Run HelloJava
java -cp week1-basics/bin com.nextgen.week1.HelloJava

# Run Variables and Operators demo
java -cp week1-basics/bin com.nextgen.week1.VariablesAndDataTypes

# Run Control Statements demo
java -cp week1-basics/bin com.nextgen.week1.ControlStatements

# Run Mini-Project 1: Grade Calculator
java -cp week1-basics/bin com.nextgen.week1.projects.GradeCalculator

# Run Mini-Project 2: Number Guessing Game
java -cp week1-basics/bin com.nextgen.week1.projects.NumberGuessingGame
```
