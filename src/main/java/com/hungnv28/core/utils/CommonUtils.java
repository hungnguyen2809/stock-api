package com.hungnv28.core.utils;

import org.apache.commons.lang3.StringUtils;

public class CommonUtils {
    public static boolean isNumber(String input) {
        if (StringUtils.isEmpty(input)) return false;

        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
