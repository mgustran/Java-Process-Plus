package com.mgustran.jpp;

import lombok.NoArgsConstructor;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public class ProcessPlusBase {

    private final ProcessPlusBaseUtils processUtils;
    private final ProcessPlusConfig config;

    private static final Logger log = Logger.getLogger(ProcessPlusBase.class.getCanonicalName());

    public ProcessPlusBase(final ProcessPlusConfig config) {
        this.config = config;
        this.processUtils = new ProcessPlusBaseUtils(config);
        if (config.isDebugInfo() || config.isDebugOutput()) {
//            System.setProperty("java.util.logging.SimpleFormatter.format",
//                    "%1$tF %1$tT %4$s %2$s %5$s%6$s%n"
////                    "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n"
//            );
        }
    }

    public ProcessPlusBase() {
        this.config = ProcessPlusConfig.builder().build();
        this.processUtils = new ProcessPlusBaseUtils(this.config);
    }

    public List<String> executeCommandGetLines(final ProcessBuilder builder) {
        try {
            final Process process = builder.start();
            return new BufferedReader(new InputStreamReader(process.getInputStream()))
                    .lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String executeCommandGetString(final ProcessBuilder builder) {
        try {
            final Process process = builder.start();
            return new BufferedReader(new InputStreamReader(process.getInputStream()))
                    .lines().collect(Collectors.joining("\n"));
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

    public void executeCommandWithInput(final ProcessBuilder builder, final List<InputHelper> helpers) {
        try {

            final Process p = builder.start();
            final OutputStream os = p.getOutputStream();
            final Writer writer = new PrintWriter(os);
            final InputStream in = p.getInputStream();
            final BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                if (this.config.isDebugOutput()) {
                    log.info(line);
                }
                lines.add(line);
                List<InputHelper> validHelpers = this.processUtils.getValidHelpers(helpers, line);
                validHelpers.forEach(inputHelper -> this.processUtils.executeHelper(inputHelper, lines, writer));
            }
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

