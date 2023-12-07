package com.mgustran.jpp;

import lombok.ToString;

import java.util.List;


public interface InputHelper {

    String ENTER = "\n";

    default void doJobBefore(List<String> outputLines) {}
    default void doJobAfter(List<String> outputLines) {}
    default String getMatcherLineContains() {
        return null;
    }
    default String getMatcherLineStartWith() {
        return null;
    }
    default String getMatcherLineEndWith() {
        return null;
    }
    default String getMatcherLineRegex() {
        return null;
    }
    default String getInputValue() {
        return "";
    }

    default String toStr() {
        return InputHelper.class.getSimpleName() + "( " +
                "getMatcherLineContains='" + this.getMatcherLineContains() + "', " +
                "getMatcherLineStartWith='" + this.getMatcherLineStartWith() + "', " +
                "getMatcherLineEndWith='" + this.getMatcherLineEndWith() + "', " +
                "getMatcherLineRegex='" + this.getMatcherLineRegex() + "', " +
                "getInputValue='" + this.getInputValue() + "', " +
                " )";
    }
}
