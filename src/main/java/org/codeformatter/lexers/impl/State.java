package org.codeformatter.lexers.impl;

import lombok.Value;

@Value(staticConstructor = "of")
public class State {

    public static final String INITIAL = "initial";
    public static final String TERMINATED = "terminated";

    String state;

}
