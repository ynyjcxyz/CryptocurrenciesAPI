package com.journaldev.rxjavaretrofit;

import static com.journaldev.rxjavaretrofit.GetServerTime.SIMPLE_DATE_FORMAT;
import static com.journaldev.rxjavaretrofit.GetServerTime.serverTime;

import java.util.Date;

public class FormatTime {
    public static String formatTime(long serverUpdatedTime) {
        return String.format("server updated time: %s \n refreshed time: %s",
                serverTime(serverUpdatedTime), SIMPLE_DATE_FORMAT.format(new Date()));
    }
}