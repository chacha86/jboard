package com.jboard.util;

public class MyUtil {
    public static String nullToBlank(String target) {
       if (target == null) {
           target = "";
       }
       return target;
    }
}
