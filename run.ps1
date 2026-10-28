# ==============================================================================
# Interactive Runner for Next Gen Software Hub - Java Internship Projects
# ==============================================================================

# Locate java and javac
$JavaBin = "C:\Program Files\Microsoft\jdk-21.0.13.21-hotspot\bin"
if (Test-Path "$JavaBin\javac.exe") {
    $JavacCmd = "$JavaBin\javac.exe"
    $JavaCmd  = "$JavaBin\java.exe"
} else {
    $JavacCmd = "javac"
    $JavaCmd  = "java"
}

function Compile-Week([string]$weekDir, [string]$binDir) {
    Write-Host "`n[*] Compiling $weekDir sources..." -ForegroundColor Cyan
    if (!(Test-Path $binDir)) {
        New-Item -ItemType Directory -Path $binDir -Force | Out-Null
    }
    $sources = Get-ChildItem -Path "$weekDir/src" -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    if ($sources.Count -gt 0) {
        & $JavacCmd -d $binDir $sources
        if ($LASTEXITCODE -eq 0) {
            Write-Host "[+] Compilation successful for $weekDir." -ForegroundColor Green
        } else {
            Write-Host "[-] Compilation failed for $weekDir." -ForegroundColor Red
        }
    }
}

function Compile-All() {
    Write-Host "`n=======================================================" -ForegroundColor Yellow
    Write-Host "      Compiling All 4 Weeks of Internship Code         " -ForegroundColor Yellow
    Write-Host "=======================================================" -ForegroundColor Yellow
    Compile-Week "week1-basics" "week1-basics/bin"
    Compile-Week "week2-oop" "week2-oop/bin"
    Compile-Week "week3-advanced" "week3-advanced/bin"
    Compile-Week "week4-database-api" "week4-database-api/bin"
    Write-Host "`nAll projects compiled and ready to execute!`n" -ForegroundColor Green
}

# Auto compile initially if bin folders are missing
if (!(Test-Path "week4-database-api/bin")) {
    Compile-All
}

while ($true) {
    Write-Host "`n=======================================================" -ForegroundColor Cyan
    Write-Host "   NEXT GEN SOFTWARE HUB PVT. LTD. - JAVA INTERNSHIP   " -ForegroundColor Cyan
    Write-Host "=======================================================" -ForegroundColor Cyan
    Write-Host " [0]  Compile All Projects"
    Write-Host "`n --- WEEK 1: JAVA BASICS ---" -ForegroundColor Yellow
    Write-Host " [1]  Week 1: Hello Java (Environment & JVM Demo)"
    Write-Host " [2]  Week 1: Variables, Types & Operators"
    Write-Host " [3]  Week 1: Control Statements (if-else, switch, loops)"
    Write-Host " [4]  Week 1 Project: Student Grade Calculator"
    Write-Host " [5]  Week 1 Project: Number Guessing Game"
    Write-Host "`n --- WEEK 2: OBJECT-ORIENTED PROGRAMMING (OOP) ---" -ForegroundColor Yellow
    Write-Host " [6]  Week 2: Encapsulation Demo"
    Write-Host " [7]  Week 2: Inheritance & Polymorphism Demo"
    Write-Host " [8]  Week 2: Abstraction & Interfaces Demo"
    Write-Host " [9]  Week 2 Project: OOP Calculator"
    Write-Host " [10] Week 2 Project: Digital Banking System"
    Write-Host "`n --- WEEK 3: ADVANCED JAVA CONCEPTS ---" -ForegroundColor Yellow
    Write-Host " [11] Week 3: Exception Handling Demo"
    Write-Host " [12] Week 3: File Handling & NIO Demo"
    Write-Host " [13] Week 3: Multithreading & Synchronization Demo"
    Write-Host " [14] Week 3 Project: Employee Management System (CSV & AutoSave)"
    Write-Host "`n --- WEEK 4: DATABASE & REST API INTEGRATION ---" -ForegroundColor Yellow
    Write-Host " [15] Week 4 Final Project: Library Management System & REST API"
    Write-Host "`n [q]  Quit" -ForegroundColor Magenta
    Write-Host "=======================================================" -ForegroundColor Cyan

    $choice = Read-Host "Enter your selection"

    switch ($choice) {
        "0"  { Compile-All }
        "1"  { Compile-Week "week1-basics" "week1-basics/bin"; & $JavaCmd -cp "week1-basics/bin" com.nextgen.week1.HelloJava }
        "2"  { Compile-Week "week1-basics" "week1-basics/bin"; & $JavaCmd -cp "week1-basics/bin" com.nextgen.week1.VariablesAndDataTypes }
        "3"  { Compile-Week "week1-basics" "week1-basics/bin"; & $JavaCmd -cp "week1-basics/bin" com.nextgen.week1.ControlStatements }
        "4"  { Compile-Week "week1-basics" "week1-basics/bin"; & $JavaCmd -cp "week1-basics/bin" com.nextgen.week1.projects.GradeCalculator }
        "5"  { Compile-Week "week1-basics" "week1-basics/bin"; & $JavaCmd -cp "week1-basics/bin" com.nextgen.week1.projects.NumberGuessingGame }
        "6"  { Compile-Week "week2-oop" "week2-oop/bin"; & $JavaCmd -cp "week2-oop/bin" com.nextgen.week2.concepts.EncapsulationDemo }
        "7"  { Compile-Week "week2-oop" "week2-oop/bin"; & $JavaCmd -cp "week2-oop/bin" com.nextgen.week2.concepts.InheritancePolymorphismDemo }
        "8"  { Compile-Week "week2-oop" "week2-oop/bin"; & $JavaCmd -cp "week2-oop/bin" com.nextgen.week2.concepts.AbstractionInterfaceDemo }
        "9"  { Compile-Week "week2-oop" "week2-oop/bin"; & $JavaCmd -cp "week2-oop/bin" com.nextgen.week2.calculator.CalculatorApp }
        "10" { Compile-Week "week2-oop" "week2-oop/bin"; & $JavaCmd -cp "week2-oop/bin" com.nextgen.week2.banking.BankingApp }
        "11" { Compile-Week "week3-advanced" "week3-advanced/bin"; & $JavaCmd -cp "week3-advanced/bin" com.nextgen.week3.concepts.ExceptionHandlingDemo }
        "12" { Compile-Week "week3-advanced" "week3-advanced/bin"; & $JavaCmd -cp "week3-advanced/bin" com.nextgen.week3.concepts.FileHandlingDemo }
        "13" { Compile-Week "week3-advanced" "week3-advanced/bin"; & $JavaCmd -cp "week3-advanced/bin" com.nextgen.week3.concepts.MultithreadingDemo }
        "14" { Compile-Week "week3-advanced" "week3-advanced/bin"; & $JavaCmd -cp "week3-advanced/bin" com.nextgen.week3.employee.EmployeeApp }
        "15" { Compile-Week "week4-database-api" "week4-database-api/bin"; & $JavaCmd -cp "week4-database-api/bin" com.nextgen.week4.LibraryApp }
        "q"  { Write-Host "Exiting. Thank you!" -ForegroundColor Green; break }
        default { Write-Host "Invalid choice! Please choose an option from the menu." -ForegroundColor Red }
    }
}
