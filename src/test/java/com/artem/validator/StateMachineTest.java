package com.artem.validator;

import org.junit.Assert;
import org.junit.Test;

public class StateMachineTest {

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

    @Test
    public void shouldPassWithAllElements(){
        shouldPass("[[],{},\"abc\",44.7]");
    }

    @Test
    public void shouldPassWithJSONObj(){
        shouldPass("[{fewfhwofeh}]");
    }

    @Test
    public void shouldPassWithJSONArray(){
        shouldPass("[[sefpsef]]");
    }

    @Test
    public void shouldFailWithExtraDot(){
        shouldFail("[4..96]");
    }

    @Test
    public void shouldFailWithExtraQuote(){
        shouldFail("[\"tot\"\"]");
    }

    @Test
    public void shouldFailWithEMissingBracket(){
        shouldFail("[{}[]");
    }

}


