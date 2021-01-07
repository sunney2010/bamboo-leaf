/**
 * 
 */
package com.bamboo.leaf.client.utils;

import java.util.Random;

/**
 * @description: Snowflake算法工具类
 * @Author: Zhuzhi
 * @Date: 2020/12/16 下午12:04
 */
public class SnowflakeIdUtils {

    private static final String OS_NAME_WINDOWS_PREFIX = "Windows";

    private static final Random RANDOM = new Random();

    public static final boolean IS_OS_WINDOWS = getOsMatchesName(OS_NAME_WINDOWS_PREFIX);

    public static long nextLong(final long startInclusive, final long endExclusive) {
        isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return (long)nextDouble(startInclusive, endExclusive);
    }

    public static void isTrue(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    public static double nextDouble(final double startInclusive, final double endInclusive) {
        isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        isTrue(startInclusive >= 0, "Both range values must be non-negative.");

        if (startInclusive == endInclusive) {
            return startInclusive;
        }

        return startInclusive + ((endInclusive - startInclusive) * RANDOM.nextDouble());
    }

    public static int[] toCodePoints(final CharSequence str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new int[0];
        }

        final String s = str.toString();
        final int[] result = new int[s.codePointCount(0, s.length())];
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            result[i] = s.codePointAt(index);
            index += Character.charCount(result[i]);
        }
        return result;
    }

    public static String getHostName() {
        return IS_OS_WINDOWS ? System.getenv("COMPUTERNAME") : System.getenv("HOSTNAME");
    }

    private static boolean getOsMatchesName(final String osNamePrefix) {
        return isOSNameMatch(getSystemProperty("os.name"), osNamePrefix);
    }

    static boolean isOSNameMatch(final String osName, final String osNamePrefix) {
        if (osName == null) {
            return false;
        }
        return osName.startsWith(osNamePrefix);
    }

    private static String getSystemProperty(final String property) {
        try {
            return System.getProperty(property);
        } catch (final SecurityException ex) {
            // we are not allowed to look at this property
            // System.err.println("Caught a SecurityException reading the system property '" + property
            // + "'; the SystemUtils property value will default to null.");
            return null;
        }
    }

}
