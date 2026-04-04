package com.susi.zipcode.taiwan_zipcode.importer;

import com.susi.zipcode.taiwan_zipcode.entity.ZipcodeReference;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScopeParser {
    private static final Pattern UNIT_PATTERN =
            Pattern.compile("(\\d+)(巷|弄|號)");

    public static void parseAndSet(String scope, ZipcodeReference entity) {
        if (scope == null || scope.equals("全")) {
            entity.setScopeType("ALL");
            entity.setMaxNum(0L);
            entity.setMaxNum(Long.MAX_VALUE);
        }

        if (scope.contains("單")) {
            entity.setScopeType("SINGLE");
        } else if (scope.contains("雙")) {
            entity.setScopeType("DOUBLE");
        } else {
            entity.setScopeType("ALL");
        }

        if (scope.contains("以下")) {
            entity.setMinNum(0L);
            entity.setMaxNum(parseToWeight(scope));
        } else if (scope.contains("以上")) {
            entity.setMinNum(parseToWeight(scope));
            entity.setMaxNum(Long.MAX_VALUE);
        } else if (scope.contains("至")) {
            String[] parts = scope.split("至");
            entity.setMinNum(parseToWeight(parts[0]));
            entity.setMaxNum(parseToWeight(parts[1]));
        } else {
            long weight = parseToWeight(scope);
            entity.setMinNum(weight);
            entity.setMaxNum(weight);
        }
    }

    public static long parseToWeight(String scope) {
        if (scope == null || scope.isEmpty()) return 0L;

        long primary = 0;
        long secondary = 0;
        long tertiary = 0;

        Matcher matcher = UNIT_PATTERN.matcher(scope);
        while (matcher.find()) {
            int num = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "巷":
                    primary = num;
                    break;
                case "弄":
                    secondary = num;
                    break;
                case "號":
                    if (primary == 0) {
                        primary = num;
                    } else {
                        tertiary = num;
                    }
                    break;
            }
        }

        return (primary * 1_000_000L) + (secondary * 1_000L) + tertiary;
    }
}
