package com.susi.zipcode.taiwan_zipcode.util;

import lombok.Data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
public class AddressFeature {
    Integer lane;
    Integer alley;
    Integer number;
    boolean isOdd;

    private static final long WEIGHT = 1_000L;

    private static final Pattern ADDRESS_PATTERN =
            Pattern.compile("(?<lane>\\d+)巷|(?<alley>\\d+)弄|(?<number>\\d+)號");

    public Long getWeight() {
        long l = (lane == null) ? 0L : lane.longValue();
        long a = (alley == null) ? 0L : alley.longValue();
        long n = (number == null) ? 0L : number.longValue();

        long primary = (l > 0) ? l : n;

        return primary * 1_000_000L + a * 1_000L + (l > 0 ? n : 0L);
    }

    public static AddressFeature parse(String scope) {
        AddressFeature feature = new AddressFeature();
        Matcher matcher = ADDRESS_PATTERN.matcher(scope);

        while (matcher.find()) {
            if (matcher.group("lane") != null) {
                feature.setLane(Integer.parseInt(matcher.group("lane")));
            }

            if (matcher.group("alley") != null) {
                feature.setAlley(Integer.parseInt(matcher.group("alley")));
            }

            if (matcher.group("number") != null) {
                Integer number = Integer.parseInt(matcher.group("number"));
                feature.setNumber(number);
            }

            if (feature.lane != null && feature.lane > 0) {
                feature.isOdd = (feature.lane % 2 != 0);
            } else if (feature.number != null && feature.number > 0) {
                feature.isOdd = (feature.number % 2 != 0);
            } else {
                feature.isOdd = true;
            }
        }

        return feature;
    }

    @Override
    public String toString() {
        return String.format("巷:%d, 弄:%d, 號:%d, 奇數:%b", lane, alley, number, isOdd);
    }
}
