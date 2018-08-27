package com.boilerplate.lib_player.extension.cdn;

import java.util.ArrayList;

/**
 * Created by Louis on 2018/6/25.
 */

public class LRUArray<T> extends ArrayList<T> {

    private int maxSize;

    public LRUArray(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
    }


    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public boolean add(T t) {
        while (size() > maxSize) {
            super.remove(0);
        }
        return super.add(t);
    }
}
