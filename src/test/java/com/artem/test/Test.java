package com.artem.test;

import com.artem.validator.StateMachine;
import com.artem.validator.ValidationResult;
import org.junit.Assert;

public class Test {

    public void shouldPass(String str){
        StateMachine stateMachine = new StateMachine();

        ValidationResult result = stateMachine.validate(str);

        Assert.assertTrue(result.getComment(), result.isValid());
    }

    public void shouldFail(String str){
        StateMachine stateMachine = new StateMachine();

        ValidationResult result = stateMachine.validate(str);

        Assert.assertFalse(result.getComment(), result.isValid());
    }

    @org.junit.Test
    public void shouldPassWithWhitespace(){
        shouldPass("[{\" name \" : \" Вася \"}]");
    }

    @org.junit.Test
    public void shouldPassWithInteger(){
        shouldPass("[{\" name \" : 55}]");
    }

    @org.junit.Test
    public void shouldPassWithStringTrue(){
        shouldPass("[{\"name\":true}]");
    }

    @org.junit.Test
    public void shouldPassWithStringFalse(){
        shouldPass("[{\"name\":null}]");
    }

    @org.junit.Test
    public void shouldPassWithNull(){
        shouldPass("[{\"name\":false}]");
    }

    @org.junit.Test
    public void shouldPassWithTwoObj(){
        shouldPass("[{" +
                            "\"name\":true," +
                            "\"lastName\":\"fff\"" +
                       "}]");
    }

    @org.junit.Test
    public void shouldFailWithExtraLetter(){
        shouldFail("[{\"name\"f:\"Вася\"}]");
    }

    @org.junit.Test
    public void shouldFailWithTwoColon(){
        shouldFail("[{\"name\"::\"Вася\"}]");
    }

    @org.junit.Test
    public void shouldFailWithExtraQuotes(){
        shouldFail("[{\"\"name\"\":\"Вася\"}]");
    }

    @org.junit.Test
    public void shouldFailWithExtraBracket(){
        shouldFail("[{\"\"name\"\":\"Вася\"}]");
    }
}

