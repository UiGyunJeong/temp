package com.uig.remote;

import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class CallClient {

    public boolean createTempTable(List<Integer> sellers) {
        System.out.println(Thread.currentThread() +" "+  LocalTime.now(ZoneId.of("GMT+09:00")).toString() + " " + "Create Temporary Table");
        return true;
    }

    public List<String> selectLimit(int from, int interval) {
        String query = "SELECT * FROM temporary_table LIMIT " + from + ", " + (from + interval);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread() +" "+  LocalTime.now(ZoneId.of("GMT+08:00")).toString() + " " + from + " " + (from+interval));

        return new ArrayList<String>(10);
    }

}
