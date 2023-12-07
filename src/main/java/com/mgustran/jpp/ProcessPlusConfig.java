package com.mgustran.jpp;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ProcessPlusConfig {
    boolean debugInfo;
    boolean debugOutput;
}
