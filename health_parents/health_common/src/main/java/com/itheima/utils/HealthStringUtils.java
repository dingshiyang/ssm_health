package com.itheima.utils;

/**
 * @author dsy
 */
public class HealthStringUtils {

    /**
     * 当value为 null 或 value为 "" 或"   " 时，返回为true
     * @param value
     * @return
     */
    public static boolean isEmpty(String value){
        if(value == null || value.length()==0 || value.trim().length()==0){
           return true;
        }
        return false;
    }

    /**
     * 当value不为 null 或 value不为 "" 或"   " 时
     * @param value
     * @return
     */
    public static boolean isNotEmpty(String value){
        return !isEmpty(value);
    }
}
