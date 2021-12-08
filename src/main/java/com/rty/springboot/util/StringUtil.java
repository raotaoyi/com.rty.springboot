package com.rty.springboot.util;

import java.util.Scanner;

public class StringUtil {
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        return false;
    }
    
}
