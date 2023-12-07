package com.mgustran.jpp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class ProcessPlusTest {

    private static final Logger log = Logger.getLogger(ProcessPlusTest.class.getCanonicalName());

    private final ProcessPlus processPlus = new ProcessPlus(
            ProcessPlusConfig.builder()
                    .debugInfo(true)
                    .debugOutput(false)
                    .build());

    @Test
    @DisplayName("Command with string return")
    void commandGetString() {
        String output = this.processPlus.executeCommandGetString("bash " + this.getScriptPath("script.sh"));
        assert output.startsWith("Saving debug log to /var/log/letsencrypt/letsencrypt.log");
    }

    @Test
    @DisplayName("Command with lines (List<String>) return")
    void commandGetLines() {
        List<String> lines = this.processPlus.executeCommandGetLines("bash " + this.getScriptPath("script.sh"));
        assert !lines.isEmpty() && lines.get(0).startsWith("Saving debug log to /var/log/letsencrypt/letsencrypt.log");
    }

    @Test
    @DisplayName("Command with void return")
    void commandVoid() {
        this.processPlus.executeCommandVoid("bash " + this.getScriptPath("script.sh"));
    }

    @Test
    @DisplayName("Command with no-blocking void return")
    void commandNoBlock() {
        this.processPlus.executeCommandNoBlock("bash " + this.getScriptPath("script_with_sleeps.sh"));
    }

    @Test
    @DisplayName("Command with BasicInputHelper")
    void commandWithBasicInput() {

        BasicInputHelper inputHelper1 = BasicInputHelper.builder()
                .matcherLineStartWith("Hello, who am I talking to?")
                .inputValue("andrei kasparov\n")
                .build();

        BasicInputHelper inputHelper2 = BasicInputHelper.builder()
                .matcherLineStartWith("Press Enter to Continue")
                .inputValue(InputHelper.ENTER)
                .build();

        this.processPlus.executeCommandWithInput("bash " + this.getScriptPath("script_with_input.sh"),
                Arrays.asList(inputHelper1, inputHelper2));
    }

    @Test
    @DisplayName("Command with custom InputHelper")
    void commandWithCustomInput() {
        this.processPlus.executeCommandWithInput("bash " + this.getScriptPath("script_with_input.sh"),
                Arrays.asList(new HelloTextInputHelper(), new Certbot1InputHelper()));
    }

    @Test
    @DisplayName("Command with declared InputHelper")
    void commandWithCustomInput2() {
        InputHelper certbot = new InputHelper() {
            @Override
            public String getMatcherLineStartWith() {
                return "Press Enter to Continue";
            }
            @Override
            public String getInputValue() {
                return ENTER;
            }
            @Override
            public void doJobAfter(List<String> outputLines) {
                log.info("hey there from certbot custom input helper (doJobAfter)");
                log.info("hey there");
            }
        };
        InputHelper helloText = new InputHelper() {
            @Override
            public String getMatcherLineStartWith() {
                return "Hello, who am I talking to?";
            }
            @Override
            public String getInputValue() {
                return "Antonio Banderotti" + ENTER;
            }
            @Override
            public void doJobBefore(List<String> outputLines) {
                log.info("hey there from helloText custom input helper");
            }
        };
        this.processPlus.executeCommandWithInput("bash " + this.getScriptPath("script_with_input.sh"),
                Arrays.asList(certbot, helloText));
    }

    private String getScriptPath(final String filename) {
        URL scriptPath = ProcessPlusTest.class.getClassLoader().getResource(filename);
        assert scriptPath != null;
        return scriptPath.getFile();
    }
}
