package com.mgustran.jpp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class ProcessPlusBase {

    private static final Logger log = Logger.getLogger(ProcessPlusBase.class.getCanonicalName());

    public List<String> executeCommandGetLines(final ProcessBuilder builder) {
        try {
            final Process process = builder.start();
            return new BufferedReader(new InputStreamReader(process.getInputStream()))
                    .lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeCommandVoid(final ProcessBuilder builder) {
        try {
            final Process process = builder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeCommandNoBlock(final ProcessBuilder builder) {
        try {
            builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeCommandWithInput(final ProcessBuilder builder, final List<InputHelper> helpers) throws IOException {
        Process p = builder.start();
        OutputStream os = p.getOutputStream();
        Writer writer = new PrintWriter(os);
        InputStream in = p.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String finalLine = line;
            lines.add(line);
//            log.info(line);
            List<InputHelper> helperList = helpers.stream()
                    .filter(inputHelper -> {
                        Boolean valid = null;
                        if (inputHelper.getMatcherLineRegex() != null) {
                            Matcher matcher = Pattern.compile(inputHelper.getMatcherLineRegex()).matcher(finalLine);
                            return matcher.matches();
                        }
                        if (inputHelper.getMatcherLineStartWith() != null) {
                            valid = finalLine.startsWith(inputHelper.getMatcherLineStartWith());
                        }
                        if (inputHelper.getMatcherLineEndWith() != null) {
                            valid =  ! Boolean.FALSE.equals(valid) && finalLine.endsWith(inputHelper.getMatcherLineEndWith());
                        }
                        if (inputHelper.getMatcherLineContains() != null) {
                            valid = ! Boolean.FALSE.equals(valid) && finalLine.contains(inputHelper.getMatcherLineContains());
                        }
                        return valid;

                    })
                    .collect(Collectors.toList());
            helperList.forEach(inputHelper -> {
                inputHelper.doJobBefore(lines);
                try {
                    writer.write(inputHelper.getInputValue() == null ? "" : inputHelper.getInputValue());
                    writer.flush();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                inputHelper.doJobAfter(lines);
            });
        }
        writer.close();
    }
}

