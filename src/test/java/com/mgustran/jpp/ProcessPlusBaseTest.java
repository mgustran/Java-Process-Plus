package com.mgustran.jpp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class ProcessPlusBaseTest {

    private static final Logger log = Logger.getLogger(ProcessPlusBaseTest.class.getCanonicalName());

    private final ProcessPlusBase processPlus = new ProcessPlusBase(
            ProcessPlusConfig.builder()
                    .debugInfo(true)
                    .debugOutput(false)
                    .build());

    @Test
    @DisplayName("Command with string return")
    void commandGetString() {
        ProcessBuilder builder = new ProcessBuilder().command("sh", "-c", "bash " + this.getScriptPath("script.sh"));
        String output = this.processPlus.executeCommandGetString(builder);
        assert output.startsWith("Saving debug log to /var/log/letsencrypt/letsencrypt.log");
    }

    @Test
    @DisplayName("Command with lines (List<String>) return")
    void commandGetLines() {
        ProcessBuilder builder = new ProcessBuilder().command("sh", "-c", "bash " + this.getScriptPath("script.sh"));
        List<String> lines = this.processPlus.executeCommandGetLines(builder);
        assert !lines.isEmpty() && lines.get(0).startsWith("Saving debug log to /var/log/letsencrypt/letsencrypt.log");
    }

    @Test
    @DisplayName("Command with void return")
    void commandVoid() {
        ProcessBuilder builder = new ProcessBuilder().command("sh", "-c", "bash " + this.getScriptPath("script.sh"));
        this.processPlus.executeCommandVoid(builder);
    }

    @Test
    @DisplayName("Command with no-blocking void return")
    void commandNoBlock() {
        ProcessBuilder builder = new ProcessBuilder().command("sh", "-c", "bash " + this.getScriptPath("script_with_sleeps.sh"));
        this.processPlus.executeCommandNoBlock(builder);
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

        ProcessBuilder builder = new ProcessBuilder().command("sh", "-c", "bash " + this.getScriptPath("script_with_input.sh"));
        this.processPlus.executeCommandWithInput(builder, Arrays.asList(inputHelper1, inputHelper2));
    }

    @Test
    @DisplayName("Command with custom InputHelper")
    void commandWithCustomInput() {
        ProcessBuilder builder = new ProcessBuilder().command("sh", "-c", "bash " + this.getScriptPath("script_with_input.sh"));
        this.processPlus.executeCommandWithInput(builder, Arrays.asList(new HelloTextInputHelper(), new Certbot1InputHelper()));
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
        ProcessBuilder builder = new ProcessBuilder().command("sh", "-c", "bash " + this.getScriptPath("script_with_input.sh"));
        this.processPlus.executeCommandWithInput(builder, Arrays.asList(certbot, helloText));
    }

    private String getScriptPath(final String filename) {
        URL scriptPath = ProcessPlusBaseTest.class.getClassLoader().getResource(filename);
        assert scriptPath != null;
        return scriptPath.getFile();
    }
}
