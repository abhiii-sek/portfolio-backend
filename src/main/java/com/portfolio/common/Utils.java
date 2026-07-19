package com.portfolio.common;

import java.util.UUID;
import java.util.function.Predicate;

public class Utils {

    public static String generateUniqueId(String prefix, Predicate<String> existsChecker) {
        String id;
        do {
            id = prefix + "_" + UUID.randomUUID()
                            .toString()
                            .replace("-", "")
                            .substring(0, 8)
                            .toUpperCase();
        } while (existsChecker.test(id));
        return id;
    }
}
