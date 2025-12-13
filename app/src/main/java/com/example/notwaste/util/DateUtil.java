package com.example.notwaste.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static int calculateDday(String expireDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date target = sdf.parse(expireDate);

            long diff = target.getTime() - System.currentTimeMillis();
            return (int) Math.floor(diff / (1000 * 60 * 60 * 24.0));
        } catch (Exception e) {
            return 0;
        }
    }
}
