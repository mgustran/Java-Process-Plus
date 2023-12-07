package com.mgustran.jpp;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class ProcessPlusBaseUtils {

    private final ProcessPlusConfig config;

    private static final Logger log = Logger.getLogger(ProcessPlusBaseUtils.class.getCanonicalName());

    public ProcessPlusBaseUtils(ProcessPlusConfig config) {
        this.config = config;
    }

    protected List<InputHelper> getValidHelpers(final List<InputHelper> helpers, final String line) {
        return helpers.stream()
                .filter(inputHelper -> {
                    Boolean valid = null;
                    if (inputHelper.getMatcherLineRegex() != null) {
                        Matcher matcher = Pattern.compile(inputHelper.getMatcherLineRegex()).matcher(line);
                        return matcher.matches();
                    }
                    if (inputHelper.getMatcherLineStartWith() != null) {
                        valid = line.startsWith(inputHelper.getMatcherLineStartWith());
                    }
                    if (inputHelper.getMatcherLineEndWith() != null) {
                        valid =  ! Boolean.FALSE.equals(valid) && line.endsWith(inputHelper.getMatcherLineEndWith());
                    }
                    if (inputHelper.getMatcherLineContains() != null) {
                        valid = ! Boolean.FALSE.equals(valid) && line.contains(inputHelper.getMatcherLineContains());
                    }
                    return valid;

                })
                .collect(Collectors.toList());
    }

    protected void executeHelper(final InputHelper inputHelper, final List<String> lines, final Writer writer) {
        if (this.config.isDebugInfo()) {
            log.finest(String.format("executing helper: %s", inputHelper.toStr()));
        }
        inputHelper.doJobBefore(lines);
        try {
            writer.write(inputHelper.getInputValue() == null ? "" : inputHelper.getInputValue());
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        inputHelper.doJobAfter(lines);
    }
}

