package com.mgustran.jpp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mgustran.jpp.InputHelper.ENTER;


public class MainTest {

    private final ProcessPlus shell = new ProcessPlus();

    @Test
    @DisplayName("Test1")
    void test1() throws IOException {

        BasicInputHelper inputHelper1 = BasicInputHelper.builder()
                .matcherLineStartWith("Hello, who am I talking to?")
                .inputValue("andrei kasparov\n")
                .build();

        BasicInputHelper inputHelper2 = BasicInputHelper.builder()
                .matcherLineStartWith("Press Enter to Continue")
                .inputValue(ENTER)
                .build();

        URL scriptPath = MainTest.class.getClassLoader().getResource("testinput.sh");
        assert scriptPath != null;
        this.shell.executeCommandWithInput("bash " + scriptPath.getFile(),
                Arrays.asList(inputHelper1, inputHelper2));

        String line = "Test this is a lien test1.example.com BOOT\tmaster\tcommander\t123789ert23456";
        Matcher matcher = Pattern.compile("^Test.*56$").matcher(line);
        System.out.println(matcher.matches());
//        System.out.println(test);
    }
}
