package com.jc.utils;

import java.util.regex.Pattern;

/**
 * Created by lrh on 9/8/15.
 */
public class RegexUtil {

    public static final String TEL_SEG = "^(13|15|18)\\d{9}$|^(145|147)\\d{8}$|^(1700|1705|1709)\\d{7}$|^(176|177|178)\\d{8}$";

    public static final String CHAR_REX = "(.){6,16}";



    /**
     * 校验value是否匹配regex所指定的格式
     *
     * @param value
     * @param regex
     * @return
     */
    public static Boolean match(String value, String regex) {
        return Pattern.matches(regex, value);
    }

    public static Boolean matchPhone(String phone){
        if(phone == null){
           return false;
        }
        return match(phone,TEL_SEG);
    }
}
