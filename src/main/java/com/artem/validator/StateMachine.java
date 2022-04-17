package com.artem.validator;

public class StateMachine {
    private char[] str = new char[0];
    private int position = 0;
    private int counterOfQuotes = 0;
    private int counterOfSquareBrackets = 0;
    private int counterOfBraces = 0;
    private final State state = new State(0);

    public ValidationResult validate(String inputStr){
        str = inputStr.toCharArray();
        ValidationResult result;
        for (position = 0; position < str.length; position++){
            if (!validationSymbol())
                return ValidationResult.unexpectedSymbol(str[position], position);
        }
        if (state.getState() == 4 && counterOfBraces == 0 && counterOfSquareBrackets == 0 && counterOfQuotes == 0){
            result = ValidationResult.valid();
        }
        else {
            result = ValidationResult.unexpectedEOF();
        }
        return result;
    }

    public boolean validationSymbol() {
        char symbol = str[position];
        switch (state.getState()) {
            case 0:
                if (symbol == '[') {
                    counterOfSquareBrackets++;
                    state.setState(1);
                } else {
                    return false;
                }
                return true;

            case 1:
                if (symbol == '{') {
                    counterOfBraces++;
                    state.setState(3);
                } else if (symbol == '"') {
                    counterOfQuotes++;
                    state.setState(2);
                } else {
                    return false;
                }
                return true;

            case 2:
                if (symbol == '"') {
                    counterOfQuotes--;
                } else if (symbol == ',') {
                    state.setState(1);
                } else if (symbol == ']') {
                    counterOfSquareBrackets--;
                    state.setState(4);
                } else if (!Character.isAlphabetic(symbol)) {
                    return false;
                }
                return true;

            case 3:
                if (symbol == '}') {
                    counterOfBraces--;
                } else if (symbol == ',') {
                    state.setState(1);
                } else if (symbol == ']') {
                    counterOfSquareBrackets--;
                    state.setState(4);
                }
                else {
                    return false;
                }
                return true;

            case 4:

        }
        return false;
    }
}

