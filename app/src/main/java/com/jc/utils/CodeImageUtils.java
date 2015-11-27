package com.jc.utils;

import java.util.Date;
import java.util.Random;

/**
 * Created by zy on 15/11/4.
 */
public class CodeImageUtils {

    public static String generateImgKey() {
        Long now = System.currentTimeMillis();
        Random random = new Random();
        Integer randNum = random.nextInt(89999) + 10000;
        StringBuffer sb = new StringBuffer();
        sb.append(String.valueOf(now)).append(String.valueOf(randNum));
        return HashUtils.md5(sb.toString());
    }

    public static String sendCodeUrl(String num, String key, String imgCode) {
        StringBuffer sb = new StringBuffer();
        return sb.append(num)
                .append("?")
                .append("key=").append(key)
                .append("&imgCode=").append(imgCode)
                .toString();
    }

    public static String getCodeImgUrl(String url, String key) {
        StringBuffer sb = new StringBuffer();
        return sb.append(url).append("?").append("key=").append(key).toString();
    }
}
