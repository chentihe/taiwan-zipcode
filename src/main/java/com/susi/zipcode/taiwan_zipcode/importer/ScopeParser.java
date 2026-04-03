package com.susi.zipcode.taiwan_zipcode.importer;

import com.susi.zipcode.taiwan_zipcode.entity.ZipcodeReference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScopeParser {
    private static final Pattern SCOPE_PATTERN =
            Pattern.compile("(單|雙|連)\\\\s*(\\\\d+)?\\\\s*(以上|以下|至)?\\\\s*(\\\\d+)?");

    public static void parseAndSet(String scope, ZipcodeReference entity) {
        if (scope == null || scope.isEmpty() || scope.contains("全")) {
            entity.setScope("ALL");
            entity.setMinNum(0);
            entity.setMaxNum(99999);
        }

        Matcher matcher = SCOPE_PATTERN.matcher(scope);
        if (matcher.find()) {
            // 1. identify SINGLE / DOUBLE
            String type = matcher.group(1);
            entity.setScopeType(type.equals("單") ? "SINGLE" : type.equals("雙") ? "DOUBLE" : "ALL");

            // 2. identify interval
            int num1 = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 0;
            String relation = matcher.group(3);
            int num2 = matcher.group(4) != null ? Integer.parseInt(matcher.group(4)) : 0;

            if ("以上".equals(relation)) {
                entity.setMinNum(num1);
                entity.setMaxNum(99999);
            } else if ("以下".equals(relation)) {
                entity.setMinNum(0);
                entity.setMaxNum(num1);
            } else if ("至".equals(relation)) {
                entity.setMinNum(num1);
                entity.setMaxNum(num2);
            } else {
                entity.setMinNum(num1);
                entity.setMaxNum(num1);
            }
        }
    }
}
