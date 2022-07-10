package numbers;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum Properties {
    BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, EVEN, ODD, JUMPING, SAD, HAPPY;
    public static final Properties[][] MUTUALLY_EXCLUSIVE_PROPERTIES = {
            {ODD, EVEN},
            {DUCK, SPY},
            {SUNNY, SQUARE},
            {SAD, HAPPY}
    };

    public static final Properties[][] MUTUALLY_EXCLUDE_EXCLUSIVE_PROPERTIES = {
            {ODD, EVEN},
            {SAD, HAPPY}
    };

    public static boolean isMutualExclusiveProperty(List<Properties> props) {
        return Arrays.stream(MUTUALLY_EXCLUSIVE_PROPERTIES)
                .anyMatch(properties -> props.contains(properties[0]) && props.contains(properties[1]));
    }

    public static boolean isMutualExcludedExclusiveProperty(List<Properties> props) {
        return Arrays.stream(MUTUALLY_EXCLUDE_EXCLUSIVE_PROPERTIES)
                .anyMatch(properties -> props.contains(properties[0]) && props.contains(properties[1]));
    }

    public static String getMutualExcludedExclusiveProperty(List<Properties> props) {
        return Arrays.stream(MUTUALLY_EXCLUDE_EXCLUSIVE_PROPERTIES)
                .filter(properties -> !(props.contains(properties[0]) && props.contains(properties[1])))
                .map(t -> "-" + t[0].toString() + ", -" + t[1].toString()).findFirst().get();
    }

    public static String getMutualExclusiveProperty(List<Properties> props) {
        return Arrays.stream(MUTUALLY_EXCLUSIVE_PROPERTIES)
                .filter(properties -> !(props.contains(properties[0]) && props.contains(properties[1])))
                .map(t -> t[0].toString() + ", " + t[1].toString()).findFirst().get();
    }

    public static boolean isMutualExclusiveProperty(List<Properties> props, List<Properties> excludes) {
        return props.stream().anyMatch(excludes::contains);
    }
}
