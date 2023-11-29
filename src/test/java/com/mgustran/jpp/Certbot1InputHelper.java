package com.mgustran.jpp;


import java.util.List;
import java.util.logging.Logger;
import java.util.stream.IntStream;


public class Certbot1InputHelper implements InputHelper {

    private static final Logger log = Logger.getLogger(HelloTextInputHelper.class.getCanonicalName());


    @Override
    public String getMatcherLineStartWith() {
        return "Press Enter to Continue";
    }

    @Override
    public void doJobBefore(List<String> outputLines) {

        final String challengeName = outputLines.stream().filter(s -> s.startsWith("_acme-challenge")).findFirst().orElse(null);
        final String challengeTkn = IntStream.range(0, outputLines.size())
                .filter(i -> i > 2 && outputLines.get(i - 2).startsWith("with the following value:"))
                .mapToObj(outputLines::get)
                .findFirst().orElse(null);
        log.info(String.format("name / token : %s / %s", challengeName, challengeTkn));
    }
}
