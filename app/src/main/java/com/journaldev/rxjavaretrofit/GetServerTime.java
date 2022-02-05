package com.journaldev.rxjavaretrofit;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GetServerTime {
    @SuppressLint("ConstantLocale")
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public static String serverTime(long serverUpdatedTime) {
        return SIMPLE_DATE_FORMAT.format(new Date(serverUpdatedTime * 1000));
    }
}