package org.codeformatter.utils;

import java.util.function.Predicate;

public class PredicateUtil {

    public static <T> Predicate<T> any() {
        return x -> true;
    }

}
