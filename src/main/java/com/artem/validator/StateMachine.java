package com.artem.validator;

public class StateMachine {
    private char[] str = new char[0];
    private int counter = 0;
    private int position = 0;
    private int counterOfQuotes = 0;
    private int counterOfSquareBrackets = 0;
    private int counterOfBraces = 0;
    private boolean colon = false;
    private final State state = new State(0);

    public ValidationResult validate(String inputStr){
        str = inputStr.toCharArray();
        ValidationResult result;
        for (position = 0; position < str.length; position++){
            if (!validationSymbol())
                return ValidationResult.unexpectedSymbol(str[position], position);
        }
        if (counterOfBraces == 0 && counterOfSquareBrackets == 0 && counterOfQuotes == 0 && counter == 0){
            result = ValidationResult.valid();
        }
        else {
            result = ValidationResult.unexpectedEOF();
        }
        return result;
    }

    public boolean validationSymbol() {
        char symbol = str[position];
        switch (state.getState()){
            case 0 :
                if (symbol == '['){
                    counterOfSquareBrackets++;
                    state.setState(1);
                }
                else {
                    return false;
                }
                return true;
            case 1 :
                if (symbol == '{' && counterOfBraces == 0){
                    counterOfBraces++;
                }
                else if (symbol == '"'){
                    counterOfQuotes++;
                    state.setState(3);
                }
                else if (!Character.isWhitespace(symbol)){
                    return false;
                }
                return true;

            case 3 :
                if (Character.isDigit(symbol) || Character.isAlphabetic(symbol) || Character.isWhitespace(symbol)){
                    state.setState(3);
                }
                else if (symbol == '"'){
                    counterOfQuotes--;
                    state.setState(4);
                }
                else if(!Character.isWhitespace(symbol)){
                    return false;
                }
                return true;

            case 4 :
                if (symbol == '"' && counterOfQuotes == 0){
                    counterOfQuotes++;
                    state.setState(5);
                }
                else if (symbol == 't'){
                    state.setState(6);
                }
                else if (symbol == 'f'){
                    state.setState(7);
                }
                else if (symbol == 'n'){
                    state.setState(8);
                }
                else if (Character.isDigit(symbol)){
                    state.setState(9);
                }
                else if (symbol == ':' && !colon){
                    colon = true;
                    state.setState(4);
                }
                else if (symbol == '{'){
                    state.setState(11);
                }
                else if (symbol == '['){
                    state.setState(12);
                }
                else if (!Character.isWhitespace(symbol)){
                    return false;
                }
                return true;

            case 5 :
                if (Character.isAlphabetic(symbol) || Character.isWhitespace(symbol) || Character.isDigit(symbol)){
                    state.setState(5);
                }
                else if (symbol == '"' && counterOfQuotes == 1){
                    counterOfQuotes--;
                    state.setState(10);
                }
                else{
                    return false;
                }
                return true;

            case 6 :
                if (symbol == 'r' && counter == 0){
                    counter++;
                }
                else if (symbol == 'u'  && counter == 1){
                    counter++;
                }
                else if (symbol == 'e'  && counter == 2){
                    counter = 0;
                    state.setState(10);
                }
                else {
                    return false;
                }
                return true;

            case 7 :
                if (symbol == 'a'  && counter == 0){
                    counter++;
                }
                else if (symbol == 'l'  && counter == 1){
                    counter++;
                }
                else if (symbol == 's'  && counter == 2){
                    counter++;
                }
                else if (symbol == 'e'  && counter == 3){
                    counter = 0;
                    state.setState(10);
                }
                else {
                    return false;
                }
                return true;

            case 8 :
                if (symbol == 'u'  && counter == 0){
                    counter++;
                }
                else if (symbol == 'l'  && counter == 1){
                    counter++;
                }
                else if (symbol == 'l'  && counter == 2){
                    counter = 0;
                    state.setState(10);
                }
                else {
                    return false;
                }
                return true;

            case 9 :
                if (Character.isDigit(symbol)){
                    state.setState(9);
                }
                else if (symbol == ','){
                    colon = false;
                    state.setState(1);
                }
                else if (symbol == '}'){
                    counterOfBraces--;
                    state.setState(10);
                }
                else {
                    return false;
                }
                return true;

            case 11 :
                if (symbol == '}'){
                    state.setState(10);
                }
                else {
                    return false;
                }
                return true;

            case 12 :
                if (symbol == ']'){
                    state.setState(10);
                }
                else {
                    return false;
                }
                return true;

            case 10 :
                if (symbol == '}' && counterOfBraces == 1){
                    counterOfBraces--;
                    state.setState(10);
                }
                else if (symbol == ','){
                    colon = false;
                    state.setState(1);
                }
                else if (symbol == ']' && counterOfSquareBrackets == 1){
                    counterOfSquareBrackets--;
                }
                else if (!Character.isWhitespace(symbol)){
                    return false;
                }
                return true;
        }
        return false;
    }
}

