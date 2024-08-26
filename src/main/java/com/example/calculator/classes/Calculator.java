package com.example.calculator.classes;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Calculator {
    private String operation;

    public Calculator() {

    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "Calculator{" +
                "operation='" + operation +
                '}';
    }

    public String calculate() {
        String expression = this.getOperation();
        List<String> tokens = tokenize(expression);
        List<String> postfix = infixToPostFix(tokens);
        String result = evaluatePostFix(postfix);
        this.operation = result;

        return result ;

    }

    private static List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                token.append(c);
            } else if (token.length() > 0) {
                tokens.add(token.toString());
                token.setLength(0);
            }
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                tokens.add(String.valueOf(c));
            }


        }
        if (token.length() > 0) {
            tokens.add(token.toString());
        }


        return tokens;
    }

    private List<String> infixToPostFix(List<String> tokens) {
        Stack<String> operators = new Stack<>();
        List<String> output = new ArrayList<>();
        for (String token : tokens) {
            if (isNumber(token)) {
                output.add(token);
            } else if (isOperator(token)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token)) {
                    output.add(operators.pop());
                }
                operators.push(token);
            } else if (token.equals("(")) {
                operators.push(token);
            } else if (token.equals(")")) {
                while (!operators.peek().equals("(")) {
                    output.add(operators.pop());
                }
                operators.pop();
            }
        }
        while (!operators.isEmpty()) {
            output.add(operators.pop());
        }

        return output;
    }

    private String evaluatePostFix(List<String> postfix) {
        Stack<Double> stack = new Stack<>();
        for (String token : postfix) {
            if (isNumber(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if(stack.size()<2){
                  return "Invalid expression";
                }
                double b = stack.pop();
                double a = stack.pop();
                double result = 0;
                switch (token) {
                    case "+":
                        result = a + b;
                        break;
                    case "-":
                        result = a - b;
                        break;
                    case "*":
                        result = a * b;
                        break;
                    case "/":
                        result = a / b;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown  operator: " + token);
                }
                stack.push(result);
            }
        }
        double res = stack.pop();
        if(res % 1 == 0){
            int result = (int) res;
            return String.valueOf(result);
        }
        else
        {
            return String.valueOf(res);
        }

    }

    private boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int precedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            default:
                return -1;
        }
    }


}
