package com.boilerplate.lib_player.extension.cdn;

import android.util.Log;

import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Louis on 2018/6/24.
 */

public class CDNChooser {


    private TreeMap<String, LRUArray<Long>> hostAverageTimeMaps = new TreeMap<>();

    private static final int DEFAULT_SCORE = 0;
    private static final long DEFAULT_AVERAGE_TIME = 0;

    public CDNChooser(String defaultHostName) {
        registerHostName(defaultHostName);
    }

    public String getDefaultHostName() {
        return hostAverageTimeMaps.firstKey();
    }


    public void registerHostName(String hostName) {
        hostAverageTimeMaps.put(hostName, new LRUArray<Long>(10));
    }

    public void putLoadIngTsTime(String hostName, long timeMillisec) {
        if (hostAverageTimeMaps.containsKey(hostName) == false) {
            registerHostName(hostName);
        }
//
//        Log.d("cdn_test", "hostName="
//                + hostName + "\n"
//                + "timeMillisec=" + timeMillisec);

        LRUArray<Long> averageTime = hostAverageTimeMaps.get(hostName);
        averageTime.add(timeMillisec);
        for (Map.Entry<String, LRUArray<Long>> entry : hostAverageTimeMaps.entrySet()) {  // Itrate through hashmap
            ListIterator<Long> temp = entry.getValue().listIterator();
            long total = 0;
            while (temp.hasNext()) {
                total += temp.next();
            }

            Log.d("cdn_test", "hostName=" + entry.getKey() + "\n"
                    + "mSec=" + (total / (double) entry.getValue().size()) + "\n"
                    + "size =" + entry.getValue().size());
        }
    }

}
