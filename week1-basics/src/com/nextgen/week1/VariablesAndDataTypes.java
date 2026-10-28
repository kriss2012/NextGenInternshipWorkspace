package com.nextgen.week1;

/**
 * Week 1: Variables, Data Types, and Operators
 * Next Gen Software Hub Pvt. Ltd. - Internship Task
 */
public class VariablesAndDataTypes {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" 1. JAVA PRIMITIVE DATA TYPES");
        System.out.println("==================================================");

        byte byteVal = 127;                      // 8-bit integer
        short shortVal = 32000;                  // 16-bit integer
        int intVal = 2147483647;                 // 32-bit integer
        long longVal = 9223372036854775807L;     // 64-bit integer
        float floatVal = 3.14159f;               // 32-bit single precision
        double doubleVal = 2.718281828459045;    // 64-bit double precision
        char charVal = 'A';                      // 16-bit Unicode character
        boolean boolVal = true;                  // true / false

        System.out.printf("byte   : %d%n", byteVal);
        System.out.printf("short  : %d%n", shortVal);
        System.out.printf("int    : %d%n", intVal);
        System.out.printf("long   : %d%n", longVal);
        System.out.printf("float  : %.5f%n", floatVal);
        System.out.printf("double : %.15f%n", doubleVal);
        System.out.printf("char   : %c (ASCII: %d)%n", charVal, (int) charVal);
        System.out.printf("boolean: %b%n", boolVal);

        System.out.println("\n==================================================");
        System.out.println(" 2. TYPE CASTING & CONVERSIONS");
        System.out.println("==================================================");

        // Implicit (Widening) Casting: int -> double
        int smallNumber = 42;
        double widened = smallNumber;
        System.out.printf("Widening (int %d -> double): %.2f%n", smallNumber, widened);

        // Explicit (Narrowing) Casting: double -> int
        double originalDouble = 99.99;
        int narrowed = (int) originalDouble;
        System.out.printf("Narrowing (double %.2f -> int): %d (fraction truncated)%n", originalDouble, narrowed);

        System.out.println("\n==================================================");
        System.out.println(" 3. OPERATORS IN ACTION");
        System.out.println("==================================================");

        int a = 20, b = 6;
        System.out.printf("a = %d, b = %d%n", a, b);
        System.out.printf("Addition (a + b)        : %d%n", (a + b));
        System.out.printf("Subtraction (a - b)     : %d%n", (a - b));
        System.out.printf("Multiplication (a * b)  : %d%n", (a * b));
        System.out.printf("Division (a / b)        : %d%n", (a / b));
        System.out.printf("Modulus / Remainder (a %% b): %d%n", (a % b));

        // Relational & Logical
        System.out.printf("Is a > b?               : %b%n", (a > b));
        System.out.printf("Is a == b?              : %b%n", (a == b));
        System.out.printf("(a > 10 && b < 10)      : %b%n", (a > 10 && b < 10));
        System.out.printf("(a < 10 || b < 10)      : %b%n", (a < 10 || b < 10));

        // Bitwise Operators
        System.out.printf("Bitwise AND (a & b)     : %d%n", (a & b));
        System.out.printf("Bitwise OR  (a | b)     : %d%n", (a | b));
        System.out.printf("Bitwise XOR (a ^ b)     : %d%n", (a ^ b));
        System.out.printf("Left Shift  (a << 1)    : %d%n", (a << 1));
        System.out.printf("Right Shift (a >> 1)    : %d%n", (a >> 1));

        // Ternary Operator
        String parity = (a % 2 == 0) ? "Even" : "Odd";
        System.out.printf("Ternary test: 'a' is %s%n", parity);
    }
}
