package com.jc.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by lrh on 9/8/15.
 */
public class NumberUtils {

    private static DecimalFormat df = new DecimalFormat(",###.##");

    private static DecimalFormat df1 = new DecimalFormat(",###.00");

    private static DecimalFormat df0 = new DecimalFormat("0.00");

    private static BigDecimal ONE = new BigDecimal(1);

    public static String formatNumber(BigDecimal val){
        if(val == null ){
            return "0";
        }
        if(val.compareTo(ONE) < 0 && val.compareTo(BigDecimal.ZERO) != 0){
            return df0.format(val.doubleValue());
        }

        String tmp = df.format(val.doubleValue());
        if(tmp.indexOf(".") != -1){
            return df1.format(val.doubleValue());
        }
        return tmp;
    }

    public static String formatNormalNumber(BigDecimal val){
        if(val == null ){
            return "0";
        }
        if(val.compareTo(ONE) < 0 && val.compareTo(BigDecimal.ZERO) != 0){
            return df0.format(val.doubleValue());
        }

        String tmp = df.format(val.doubleValue());
        return tmp;
    }
}
