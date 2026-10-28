package com.nextgen.week2.calculator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * OOP Extensible Calculator engine using Strategy Pattern.
 */
public class Calculator {
    private final Map<String, Operation> operations = new HashMap<>();

    public Calculator() {
        registerDefaultOperations();
    }

    private void registerDefaultOperations() {
        registerOperation("+", (a, b) -> a + b);
        registerOperation("-", (a, b) -> a - b);
        registerOperation("*", (a, b) -> a * b);
        registerOperation("/", (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Division by zero is not permitted!");
            }
            return a / b;
        });
        registerOperation("%", (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("Modulo by zero is not permitted!");
            }
            return a % b;
        });
        registerOperation("^", (a, b) -> Math.pow(a, b));
    }

    public void registerOperation(String symbol, Operation op) {
        operations.put(symbol, op);
    }

    public boolean hasOperation(String symbol) {
        return operations.containsKey(symbol);
    }

    public Set<String> getSupportedOperations() {
        return operations.keySet();
    }

    public double calculate(String operator, double a, double b) {
        Operation op = operations.get(operator);
        if (op == null) {
            throw new UnsupportedOperationException("Unknown operator: '" + operator + "'");
        }
        return op.execute(a, b);
    }
}
