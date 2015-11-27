package com.jc.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * Created by lrh on 10/9/15.
 */
public class RateUtils {

    public static BigDecimal calculateProfit(BigDecimal totalAmt,Integer duration,BigDecimal rate){
        Calendar cal = Calendar.getInstance();
        int maxDayNum = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        return totalAmt.multiply(new BigDecimal(duration)).multiply(rate).divide(new BigDecimal(maxDayNum*100),2, RoundingMode.CEILING);
    }
}
