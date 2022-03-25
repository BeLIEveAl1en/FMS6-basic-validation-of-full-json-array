package com.artem.validator;

public class ValidationResult {
    private final boolean isValid;
    private static String comment;

    private ValidationResult(boolean isValid, String comment) {
        this.isValid = isValid;
        ValidationResult.comment = comment;
    }

    public static ValidationResult valid(){
        return new ValidationResult(true, "");
    }

    private static String fillComment(char symbol, int pos){
        return String.format("Unexpected symbol " + symbol + " at " + pos, symbol, pos);
    }

    public static ValidationResult unexpectedSymbol(char symbol, int pos){
        return new ValidationResult(false, fillComment(symbol, pos));
    }

    public static ValidationResult unexpectedEOF(){
        return new ValidationResult(false, "Unexpected EOF");
    }

    public boolean isValid() {
        return isValid;
    }

    public String getComment(){
        return comment;
    }
}
