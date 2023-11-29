package com.mgustran.jpp;


import java.util.List;
import java.util.logging.Logger;


public class HelloTextInputHelper implements InputHelper {

    private static final Logger log = Logger.getLogger(HelloTextInputHelper.class.getCanonicalName());

    @Override
    public String getMatcherLineStartWith() {
        return "Hello, who am I talking to?";
    }

    @Override
    public String getInputValue() {
        return "Andrei Potato\n";
    }

    @Override
    public void doJobBefore(List<String> outputLines) {
        log.info("test before text input into shell process");
    }

    @Override
    public void doJobAfter(List<String> outputLines) {
        log.info("test after text input into shell process");
    }
}
