package com.mgustran.jpp;

import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.logging.Logger;

@Value
@Builder(toBuilder = true)
public class BasicInputHelper implements InputHelper {

    private static final Logger log = Logger.getLogger(BasicInputHelper.class.getCanonicalName());

    String matcherLineStartWith;
    String matcherLineEndWith;
    String matcherLineContains;
    String matcherLineRegex;
    String inputValue;
}
