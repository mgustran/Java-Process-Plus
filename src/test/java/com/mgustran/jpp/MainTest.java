package com.mgustran.jpp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainTest {

    private final ProcessPlus shell = new ProcessPlus();

    @Test
    @DisplayName("Test1")
    void test1() {

//        URL scriptPath = MainTest.class.getClassLoader().getResource("testinput.sh");
//        assert scriptPath != null;
//        this.shell.executeCommandWithInput("bash " + scriptPath.getFile(),
//                Arrays.asList(new HelloTextInputHelper(), new Certbot1InputHelper()));

        String line = "Test this is a lien test1.example.com BOOT\tmaster\tcommander\t123789ert23456";
        Matcher matcher = Pattern.compile("^Test.*56$").matcher(line);
        System.out.println(matcher.matches());
//        System.out.println(test);
    }
}
